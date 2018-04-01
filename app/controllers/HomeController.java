package controllers;

import models.*;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

public class HomeController extends Controller
{
    private FormFactory formFactory;
    private JPAApi jpaApi;

    @Inject
    public HomeController(FormFactory formFactory, JPAApi jpaApi)
    {
        this.formFactory = formFactory;
        this.jpaApi = jpaApi;
    }

    @Transactional(readOnly = true)
    public Result getHello()
    {

        String customerIdSession = session().get("customerId");

        Customer customer = null;

        if(customerIdSession != null)
        {
            Integer customerId = Integer.parseInt(customerIdSession);
            String sql = "SELECT c FROM Customer c WHERE customerId = :customerId";

            customer = jpaApi.em().createQuery(sql, Customer.class).setParameter("customerId", customerId).getSingleResult();
        }


        return ok(views.html.homepage.render(customer));
    }

    public Result getRequest()
    {


        return ok(views.html.request.render());
    }

    @Transactional
    public Result postRequest()
    {


        Request request = new Request();
        DynamicForm form = formFactory.form().bindFromRequest();


        String checked = form.get("addMe");

        String checkedText = form.get("textAlert");

        String checkedEmail = form.get("emailAlert");


        if (checkedText != null) {
            Customer customer = new Customer();
            boolean textAlert = true;
            customer.setTextAlert(textAlert);

        }

        if (checkedEmail != null) {
            Customer customer = new Customer();
            boolean emailAlert = true;
            customer.setEmailAlert(emailAlert);

        }

        if (checked != null) {
            //create object to save
            Customer customer = new Customer();
            //save object to database
            String firstName = form.get("firstName");
            String lastName = form.get("lastName");
            String phone = form.get("phone");
            String email = form.get("email");

            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setPhone(phone);
            customer.setEmail(email);

            jpaApi.em().persist(customer);
        }

        String productName = form.get("productName");
        String firstName = form.get("firstName");
        String lastName = form.get("lastName");
        String phone = form.get("phone");
        String email = form.get("email");
        int requestedQuantity = new Integer(form.get("requestedQuantity"));
        String comments = form.get("comments");

        request.setProductName(productName);
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setPhone(phone);
        request.setEmail(email);
        request.setRequestedQuantity(requestedQuantity);
        request.setComments(comments);

        jpaApi.em().persist(request);

        return redirect(routes.HomeController.getRequest());

    }

    @Transactional(readOnly = true)
    public Result getOrder()
    {
        DynamicForm form = formFactory.form().bindFromRequest();

        String search = form.get("search");

        if (search == null) {
            search = "";
        }
        search = "%" + search + "%";

        List<ProductDetail> products = jpaApi.em().createQuery("SELECT NEW ProductDetail (p.productId, p.productName, " +
                "p.price, p.ingredients, p.size, p.categoryId, p.seasonId, p.quantityInStock) " +
                "FROM Product p " +
                "WHERE p.productName " +
                "LIKE :search", ProductDetail.class).setParameter("search", search).getResultList();
        return ok(views.html.order.render(products));
    }

    @Transactional
    public Result postOrder()
    {
        OrderDetail order = new OrderDetail();

         jpaApi.em().persist(order);


        return redirect(routes.HomeController.getOrder());
    }



    public Result getTestView()
    {
        return ok(views.html.testview.render());
    }
}
