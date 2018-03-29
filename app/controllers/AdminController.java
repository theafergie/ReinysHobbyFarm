package controllers;

import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

public class AdminController extends Controller
{
    private int pin;

    public Result getLogIn()
    {
        return ok(views.html.adminlogin.render());
    }

    @Transactional
    public Result postLogIn()
    {
        Integer pin = 0734;

        return redirect(routes.ChartController.getInventoryChart());
    }
}
