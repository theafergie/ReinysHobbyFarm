package controllers;

import models.CartItem;
import models.OrderDetail;
import models.ProductDetail;
import org.hibernate.criterion.Order;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.math.BigDecimal;
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

        return ok(views.html.ordersummary.render(cartItems));
    }

    @Transactional(readOnly = true)
    public Result postOrderDetail()
    {
        //OrderDetail orderDetail = new OrderDetail();
        CartItem cartItem = new CartItem();
        DynamicForm form = formFactory.form().bindFromRequest();

        Result result;

        int orderProductId = new Integer(form.get("orderProductId"));
        int customerId = new Integer(form.get("customerId"));
        int categoryId = new Integer(form.get("categoryId"));
        int quantity = new Integer(form.get("quantity"));
        int orderId = new Integer(form.get("orderId"));
        int productId = new Integer(form.get("productId"));
        BigDecimal unitPrice = new BigDecimal(form.get("unitPrice"));


//        cartItem.setOrderProductId(orderProductId);
//        cartItem.setCustomerId(customerId);
//        cartItem.setCategoryId(categoryId);
//        cartItem.setQuantity(quantity);
//        cartItem.setOrderId(orderId);
//        cartItem.setProductId(productId);
//        cartItem.setUnitPrice(unitPrice);

        jpaApi.em().persist(cartItem);

        return redirect(routes.ShopController.getOrderDetail());

    }

}
