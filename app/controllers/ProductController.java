package controllers;

import models.OrderDetail;
import models.Product;
import models.ProductDetail;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.product;

import javax.inject.Inject;
import javax.persistence.criteria.Order;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductController extends Controller
{
    private FormFactory formFactory;
    private JPAApi jpaApi;

    @Inject
    public ProductController(FormFactory formFactory, JPAApi jpaApi)
    {
        this.formFactory = formFactory;
        this.jpaApi = jpaApi;
    }

    @Transactional(readOnly = true)
    public Result getProducts()
    {
        List<ProductDetail> products = jpaApi.em().createQuery("SELECT NEW ProductDetail (p.productId, p.productName, p.price, p.ingredients, p.size)" +
                "FROM Product p", ProductDetail.class)
                        .getResultList();
        return ok(views.html.products.render(products));
    }

    @Transactional(readOnly = true)
    public Result getProduct(int productId)
    {
        Product product = jpaApi.em().createQuery("SELECT p FROM Product p WHERE productId = :productId", Product.class).
                setParameter("productId", productId).getSingleResult();

        return ok(views.html.product.render(product));
    }

    @Transactional (readOnly = true)
    public Result getJams()
    {
        List<ProductDetail> products = jpaApi.em().createQuery("SELECT NEW ProductDetail (p.productId, p.productName, p.price, p.ingredients, p.size, p.categoryId, p.seasonId)" +
                "FROM Product p WHERE productName LIKE '%jam' ORDER BY productName", ProductDetail.class).getResultList();

        return ok(views.html.products.render(products));
    }

    @Transactional (readOnly = true)
    public Result getJellies()
    {
        List<ProductDetail> products = jpaApi.em().createQuery("SELECT NEW ProductDetail (p.productId, p.productName, p.price, p.ingredients, p.size, p.categoryId, p.seasonId)" +
                "FROM Product p WHERE productName LIKE '%jelly' ORDER BY productName", ProductDetail.class).getResultList();

        return ok(views.html.products.render(products));
    }

    @Transactional (readOnly = true)
    public Result getButters()
    {
        List<ProductDetail> products = jpaApi.em().createQuery("SELECT NEW ProductDetail (p.productId, p.productName, p.price, p.ingredients, p.size, p.categoryId, p.seasonId)" +
                "FROM Product p WHERE productName LIKE '%butter' ORDER BY productName", ProductDetail.class).getResultList();

        return ok(views.html.products.render(products));
    }

    @Transactional (readOnly = true)
    public Result getSprays()
    {
        List<ProductDetail> products = jpaApi.em().createQuery("SELECT NEW ProductDetail (p.productId, p.productName, p.price, p.ingredients, p.size, p.categoryId, p.seasonId) " +
                "FROM Product p " +
                "JOIN Category c ON c.categoryId = p.categoryId " +
                "WHERE c.categoryName " +
                "LIKE 'Sprays' " +
                "ORDER BY productName", ProductDetail.class).getResultList();

        return ok(views.html.products.render(products));
    }

    @Transactional (readOnly = true)
    public Result getRollOns()
    {
        List<ProductDetail> products = jpaApi.em().createQuery("SELECT NEW ProductDetail (p.productId, p.productName, p.price, p.ingredients, p.size, p.categoryId, p.seasonId) " +
                "FROM Product p " +
                "JOIN Category c ON c.categoryId = p.categoryId " +
                "WHERE c.categoryName " +
                "LIKE 'Roll-Ons' " +
                "ORDER BY productName", ProductDetail.class).getResultList();

        return ok(views.html.products.render(products));
    }

    @Transactional (readOnly = true)
    public Result getInStock()
    {
        List<ProductDetail> products = jpaApi.em().createQuery("SELECT NEW ProductDetail (p.productId, p.productName, p.price, p.ingredients, p.size, p.categoryId, p.seasonId) " +
                "FROM Product p " +
                "JOIN Category c ON c.categoryId = p.categoryId " +
                "WHERE c.categoryName LIKE 'Jam' AND p.quantityInStock > 0 " +
                "OR c.categoryName LIKE 'Jelly' AND p.quantityInStock > 0 " +
                "OR c.categoryName LIKE 'Butter' AND p.quantityInStock > 0 " +
                "ORDER BY productName", ProductDetail.class).getResultList();

        return ok(views.html.products.render(products));
    }

    @Transactional (readOnly = true)
    public Result getVegan()
    {
        List<ProductDetail> products = jpaApi.em().createQuery("SELECT NEW ProductDetail (p.productId, p.productName, p.price, p.ingredients, p.size, p.categoryId, p.seasonId) " +
                "FROM Product p " +
                "WHERE vegan = true " +
                "ORDER BY productName", ProductDetail.class).getResultList();

        return ok(views.html.products.render(products));
    }

    @Transactional (readOnly = true)
    public Result getSeasons()
    {
        List<ProductDetail> products = jpaApi.em().createQuery("SELECT NEW ProductDetail (p.productId, p.productName, " +
                "p.price, p.ingredients, p.size, p.categoryId, p.seasonId) " +
                "FROM Product p " +
                "ORDER BY productName", ProductDetail.class).getResultList();

        return ok(views.html.seasons.render());
    }
    @Transactional (readOnly = true)
    public Result getPreOrders()
    {
        List<ProductDetail> products = jpaApi.em().createQuery("SELECT NEW ProductDetail (p.productId, p.productName, p.price, p.ingredients, p.size, p.categoryId, p.seasonId) " +
                "FROM Product p " +
                "JOIN Category c ON c.categoryId = p.categoryId " +
                "WHERE c.categoryName LIKE 'Jam' AND p.quantityInStock = 0 " +
                "OR c.categoryName LIKE 'Jelly' AND p.quantityInStock = 0 " +
                "OR c.categoryName LIKE 'Butter' AND p.quantityInStock = 0 " +
                "ORDER BY productName", ProductDetail.class).getResultList();

        return ok(views.html.products.render(products));
    }

    @Transactional (readOnly = true)
    public Result getBeardOils()
    {
        List<ProductDetail> products = jpaApi.em().createQuery("SELECT NEW ProductDetail (p.productId, p.productName, p.price, p.ingredients, p.size, p.categoryId, p.seasonId) " +
                "FROM Product p " +
                "JOIN Category c ON c.categoryId = p.categoryId " +
                "WHERE c.categoryName " +
                "LIKE 'Beard Oil' " +
                "ORDER BY productName", ProductDetail.class).getResultList();

        return ok(views.html.products.render(products));
    }

    @Transactional (readOnly = true)
    public Result getBeardBalms()
    {
        List<ProductDetail> products = jpaApi.em().createQuery("SELECT NEW ProductDetail (p.productId, p.productName, p.price, p.ingredients, p.size, p.categoryId, p.seasonId) " +
                "FROM Product p " +
                "JOIN Category c ON c.categoryId = p.categoryId " +
                "WHERE c.categoryName " +
                "LIKE 'Beard Balm' " +
                "ORDER BY productName", ProductDetail.class).getResultList();

        return ok(views.html.products.render(products));
    }

    @Transactional (readOnly = true)
    public Result getJamminJellies()
    {
        DynamicForm form = formFactory.form().bindFromRequest();

        String search = form.get("search");

        if(search == null)
        {
            search = "";
        }

        search = "%" + search + "%";

        List<ProductDetail> products = jpaApi.em().createQuery("SELECT NEW ProductDetail (p.productId, p.productName, p.price, p.ingredients, p.size, p.categoryId, p.seasonId) " +
                "FROM Product p " +
                "JOIN Category c ON c.categoryId = p.categoryId " +
                "WHERE c.categoryName LIKE 'Jam' " +
                "OR c.categoryName LIKE 'Jelly' " +
                "OR c.categoryName LIKE 'Butter' " +
                "AND c.categoryName LIKE :search " +
                "AND p.productName LIKE :search " +
                "ORDER BY productName", ProductDetail.class)
                .setParameter("search", search)
                .getResultList();

        return ok(views.html.orderjamminjellies.render(products));
    }

    @Transactional
    public Result getNaturals()
    {
        List<ProductDetail> products = jpaApi.em().createQuery("SELECT NEW ProductDetail (p.productId, p.productName, p.price, p.ingredients, p.size, p.categoryId, p.seasonId) " +
                "FROM Product p " +
                "JOIN Category c ON c.categoryId = p.categoryId " +
                "WHERE c.categoryName LIKE 'Beard Balm' " +
                "OR c.categoryName LIKE 'Beard Oil' " +
                "OR c.categoryName LIKE 'Sprays' " +
                "OR c.categoryName LIKE 'Roll-ons' " +
                "ORDER BY categoryName", ProductDetail.class).getResultList();

        return ok(views.html.ordernaturals.render(products));
    }
    @Transactional(readOnly = true)
    public Result getPicture(int id)
    {

        Product product = jpaApi.em().
                createQuery("SELECT p FROM Product p WHERE productId = :productId", Product.class).
                setParameter("productId", id).
                getSingleResult();

        Result picture;

        if(product.getPicture() == null)
        {
            picture = notFound();
        }
        else
        {
            picture = ok(product.getPicture()).as("image/jpg");
        }
        return picture;
    }

    @Transactional(readOnly = true)
    public Result postCheckout()
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        List<Product> products = jpaApi.em().createQuery("SELECT p FROM Product p ORDER BY productName", Product.class).
                getResultList();

        List<OrderDetail> orderDetails = new ArrayList<>();

        for(Product product : products)
        {
            String key = "" + product.getProductId();
            String value = form.get(key);

            if(value != null)
            {
                int quantity = Integer.parseInt(value);

                if(quantity > 0)
                {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setProductId(product.getProductId());
                    orderDetail.setQuantity(quantity);
                    orderDetail.setUnitPrice(product.getPrice());
                    orderDetail.setProductName(product.getProductName());
                    orderDetails.add(orderDetail);
                }


            }

        }

        return ok(views.html.ordersummary.render(orderDetails));
    }

    /*@Transactional
    public Result getTotal()
    {
        DynamicForm form = formFactory.form().bindFromRequest();

        OrderDetail orderDetail = new OrderDetail();


        List<Product> products = jpaApi.em().createQuery("SELECT p FROM Product p ORDER BY productName", Product.class).
                getResultList();

        List<OrderDetail> orderDetails = new ArrayList<>();

        for(Product product : products)
        {
            String key = "" + product.getProductId();
            String value = form.get(key);

            int total = Integer.parseInt(value);




        }

        return ok("");
    }*/

}
