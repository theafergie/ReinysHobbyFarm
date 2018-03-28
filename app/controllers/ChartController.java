package controllers;

import models.BestSellers;
import models.InventoryCount;
import models.SalesChart;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;


public class ChartController extends Controller
{
    private JPAApi jpaApi;

    @Inject
    public ChartController(JPAApi jpaApi)
    {
        this.jpaApi = jpaApi;
    }
    public Result getChart()
    {
        return ok(views.html.chart.render());
    }

    @Transactional(readOnly = true)
    public Result getInventoryChart()
    {
        String sql = "SELECT NEW InventoryCount(c.categoryId, c.categoryName, SUM(p.quantityInStock)) " +
                "FROM Category c " +
                "JOIN Product p ON p.categoryId = c.categoryId " +
                "GROUP BY c.categoryName";
        List<InventoryCount> inventoryCounts = jpaApi.em().createQuery(sql, InventoryCount.class).getResultList();

        String sqlSales = "SELECT NEW SalesChart( YEAR(orderDate), SUM(quantity * unitPrice)) " +
                "FROM OrderDetail od " +
                "JOIN OrderHeader oh ON oh.orderId = od.orderId " +
                " GROUP BY YEAR(orderDate)";
        List<SalesChart> salesCharts = jpaApi.em().createQuery(sqlSales, SalesChart.class).getResultList();

        String sqlBestSellers = "SELECT NEW BestSellers(p.productName, SUM(od.quantity * od.unitPrice)) " +
                "FROM OrderDetail od " +
                "JOIN Product p ON p.productId = od.productId " +
                "GROUP BY p.productName " +
                "ORDER BY SUM(od.quantity * od.unitPrice) DESC";

        List<BestSellers> bestSellers = jpaApi.em().createQuery(sqlBestSellers, BestSellers.class).setMaxResults(5).getResultList();

        return ok(views.html.inventorychart.render(inventoryCounts, salesCharts, bestSellers));
    }

    /*@Transactional (readOnly = true)
    public Result getSalesChart()
    {

        return ok(views.html.inventorychart.render(salesCharts));
    }*/
}
