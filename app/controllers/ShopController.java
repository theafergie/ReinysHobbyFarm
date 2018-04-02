package controllers;

import Services.Email;
import Services.SMS;
import models.*;
import play.api.Mode;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.product;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShopController extends Controller
{
    private FormFactory formFactory;
    private JPAApi jpaApi;

    @Inject
    public ShopController(FormFactory formFactory, JPAApi jpaApi)
    {
        this.formFactory = formFactory;
        this.jpaApi = jpaApi;
    }

    public Result getOrderDetail()
    {
        CartItem cartItem = new CartItem();
        List<CartItem> cartItems = jpaApi.em().createQuery("SELECT NEW CartItem (od.orderProductId, " +
                "od.customerId, od.categoryId, od.quantity, od.orderId, od.productId, od.unitPrice) " +
                "FROM CartItem od", CartItem.class).getResultList();

        return ok(views.html.ordersummary.render(cartItems));
    }

    @Transactional(readOnly = true)
    public Result postOrderDetail()
    {

        DynamicForm form = formFactory.form().bindFromRequest();


        int orderProductId = new Integer(form.get("orderProductId"));
        int customerId = new Integer(form.get("customerId"));
        int categoryId = new Integer(form.get("categoryId"));
        int quantity = new Integer(form.get("quantity"));
        int orderId = new Integer(form.get("orderId"));
        int productId = new Integer(form.get("productId"));
        BigDecimal unitPrice = new BigDecimal(form.get("unitPrice"));


        return redirect(routes.ShopController.getOrderDetail());

    }

    @Transactional(readOnly = true)
    public Result postCheckout()
    {
        DynamicForm form = formFactory.form().bindFromRequest();

        String sql = "SELECT p FROM Product p ORDER BY productName";
        List<Product> products = jpaApi.em().createQuery(sql, Product.class).getResultList();

        List<CartItem> cartItems;

        String json = session().get("cart");

        if(json == null)
        {
            cartItems = new ArrayList<>();
        }
        else
        {
            cartItems = CartItem.fromJSON(json);

        }

        for(Product product : products)
        {
            String key = "" + product.getProductId();
            String value = form.get(key);




            if(value != null)
            {
                int quantity = Integer.parseInt(value);

                if(quantity > 0)
                {
                    CartItem cartItem = new CartItem();
                    cartItem.setProductId(product.getProductId());
                    cartItem.setQuantity(quantity);
                    cartItem.setUnitPrice(product.getPrice());
                    cartItem.setCategortyId(product.getCategoryId());
                    cartItem.setProductName(product.getProductName());
                    cartItems.add(cartItem);
                }
            }
        }

        json = CartItem.toJSON(cartItems);
        session().put("cart", json);


        return ok(views.html.ordersummary.render(cartItems));
    }




    @Transactional
    public Result postPurchase()
    {
        String json = session().get("cart");
        List<CartItem> cartItems;

        if(json == null)
        {
            return ok(views.html.emptycart.render());
        }
        else
        {
            cartItems = CartItem.fromJSON(json);

            OrderHeader orderHeader = new OrderHeader();
            int customerId = Integer.parseInt(session().get("customerId"));
            orderHeader.setCustomerId(customerId);
            orderHeader.setOrderDate(new Date());
            jpaApi.em().persist(orderHeader);

            for(CartItem cartItem : cartItems)
            {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderId(orderHeader.getOrderId());
                orderDetail.setUnitPrice(cartItem.getUnitPrice());
                orderDetail.setProductId(cartItem.getProductId());
                orderDetail.setQuantity(cartItem.getQuantity());
                orderDetail.setCustomerId(orderHeader.getCustomerId());
                orderDetail.setCategoryId(cartItem.getCategortyId());

                jpaApi.em().persist(orderDetail);
            }

            Date date = new Date();

            Email.sendEmail(date, cartItems);

            //for text alerts (hard coded for now)
            /*String phoneNumber = "+1 501 730 2952";
            String message = "Thank You! Your order has been received!";
            SMS.send(phoneNumber, message);*/


            for(CartItem cartItem : cartItems)
            {
               String sql = "SELECT p FROM Product p " +
                       "WHERE productId = :productId";

               int productId = cartItem.getProductId();
               Product products = jpaApi.em().createQuery(sql, Product.class).setParameter("productId", productId).getSingleResult();

                products.setQuantityInStock(products.getQuantityInStock()-cartItem.getQuantity());
                jpaApi.em().persist(products);
            }
            cartItems = CartItem.fromJSON(json);
            session().remove("cart");
        }

        return ok(views.html.completeorder.render());
    }

    public Result postEmptyCart()
    {
        session().remove("cart");
        return ok(views.html.emptycart.render());
    }

    public Result getCheckout()
    {
        String json = session().get("cart");
        List<CartItem> cartItems;

        Result result;
        if(json != null)
        {
            cartItems = CartItem.fromJSON(json);
            result = ok(views.html.ordersummary.render(cartItems));


        }
        else
        {
            result = ok(views.html.emptycart.render());

        }

        return result;

    }


   public Result getTotal()
   {
       String json = session().get("cart");
       BigDecimal total = new BigDecimal("0.00");
       List<CartItem> cartItems;

       if (json == null)
       {
           return ok(views.html.emptycart.render());
       }
       else
       {
               cartItems = CartItem.fromJSON(json);

               for (CartItem cartItem : cartItems)
               {
               BigDecimal quantity = new BigDecimal(cartItem.getQuantity());
               total = total.add(cartItem.getUnitPrice().multiply(quantity));
               }
       }
       return ok(views.html.revieworder.render(cartItems, total));


   }


}
