package controllers;

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

        //String sqlBestSellers = "SELECT NEW BestSellers(od.productId, od.quantity"

        return ok(views.html.inventorychart.render(inventoryCounts, salesCharts));
    }

    /*@Transactional (readOnly = true)
    public Result getSalesChart()
    {

        return ok(views.html.inventorychart.render(salesCharts));
    }*/
}
