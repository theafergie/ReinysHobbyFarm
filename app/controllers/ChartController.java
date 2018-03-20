package controllers;

import models.InventoryCount;
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
        String sql = "SELECT NEW InventoryCount(c.categoryId, c.categoryName, COUNT(*)) " +
                "FROM Category c " +
                "JOIN Product p ON p.categoryId = c.categoryId " +
                "GROUP BY c.categoryName";
        List<InventoryCount> inventoryCounts = jpaApi.em().createQuery(sql, InventoryCount.class).getResultList();
        return ok(views.html.inventorychart.render(inventoryCounts));
    }
}
