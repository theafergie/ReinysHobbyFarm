package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class BlogController extends Controller
{
    public Result getBlog()
    {
        return ok(views.html.aboutus.render());
    }

    public Result getGallery()
    {
        return ok(views.html.gallery.render());
    }

    public Result getAnimals()
    {
        return ok(views.html.animals.render());
    }

    public Result getMarket()
    {
        return ok(views.html.market.render());
    }

    public Result getAroundTheFarm()
    {
        return ok(views.html.aroundfarm.render());
    }

    public Result getFromTheGarden()
    {
        return ok(views.html.fromgarden.render());
    }

    public Result getJamminJellies()
    {
        return ok(views.html.jamminjellies.render());
    }
}
