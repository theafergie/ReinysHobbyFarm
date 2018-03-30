package controllers;

import models.*;
import org.hibernate.criterion.Order;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
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
       /* OrderDetail orderDetail = new OrderDetail();
        List<OrderDetail> orderDetails = jpaApi.em().createQuery("SELECT NEW OrderDetail (od.orderProductId, " +
                "od.customerId, od.categoryId, od.quantity, od.orderId, od.productId, od.unitPrice) " +
                "FROM OrderDetail od", OrderDetail.class).getResultList(); */

        CartItem cartItem = new CartItem();
        List<CartItem> cartItems = jpaApi.em().createQuery("SELECT NEW CartItem (od.orderProductId, " +
                "od.customerId, od.categoryId, od.quantity, od.orderId, od.productId, od.unitPrice) " +
                "FROM CartItem od", CartItem.class).getResultList();

        //BigDecimal total = getTotal(cartItems);



        return ok(views.html.ordersummary.render(cartItems));
    }

    @Transactional(readOnly = true)
    public Result postOrderDetail()
    {
        //OrderDetail orderDetail = new OrderDetail();

        DynamicForm form = formFactory.form().bindFromRequest();

        Result result;

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

        BigDecimal total = new BigDecimal("0.00");

        if(json == null)
        {
            cartItems = new ArrayList<>();
        }
        else
        {
            cartItems = CartItem.fromJSON(json);
           // total= getTotal(cartItems);

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
            //TODO error
        }
        else
        {
            cartItems = CartItem.fromJSON(json);

            //TODO customerId needs to set to logged in customer
            OrderHeader orderHeader = new OrderHeader();
            orderHeader.setCustomerId(7);
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

            session().put("cart", null);

        }
        //TODO finish purchasing process and send email
        return ok("sold!");
    }

    public Result postEmptyCart()
    {
        session().put("cart", null);

        return ok(views.html.emptycart.render());
    }

    public Result getCheckout()
    {
        String json = session().get("cart");
        List<CartItem> cartItems = null;

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

   /* public BigDecimal getTotal(List<CartItem> cartItems)
    {
        String json = session().get("cart");
        BigDecimal total = new BigDecimal("0.00");

        if(json == null)
        {
            //
        }
        else
        {
            for(CartItem cartItem : cartItems)
            {
                BigDecimal quantity = new BigDecimal(cartItem.getQuantity());
                total = total.add(cartItem.getUnitPrice().multiply(quantity));
            }
        }



        return total;
    }*/

   public Result getTotal()
   {
       String json = session().get("cart");
       BigDecimal total = new BigDecimal("0.00");
       List<CartItem> cartItems = null;

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
