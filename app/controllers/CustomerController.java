package controllers;

import Services.Email;
import models.Customer;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

import java.util.Date;
import java.util.List;

import static org.hibernate.loader.Loader.SELECT;

public class CustomerController extends Controller
{
    private FormFactory formFactory;
    private JPAApi jpaApi;

    @Inject
    public CustomerController(FormFactory formFactory, JPAApi jpaApi)
    {
        this.formFactory = formFactory;
        this.jpaApi = jpaApi;
    }

    public Result getAddCustomer()
    {
        return ok(views.html.addcustomer.render());
    }

    @Transactional
    public Result postAddCustomer()
    {
        Customer customer = new Customer();
        DynamicForm form = formFactory.form().bindFromRequest();

        Result result;

        String checkedText = form.get("textAlert");

        String checkedEmail = form.get("emailAlert");

        if (checkedText != null) {
            boolean textAlert = true;
            customer.setTextAlert(textAlert);
            //jpaApi.em().persist(customer);

        }

        if (checkedEmail != null) {
            boolean emailAlert = true;
            customer.setEmailAlert(emailAlert);
            // jpaApi.em().persist(customer);

        }

        String firstName = form.get("firstName");
        String lastName = form.get("lastName");
        String address = form.get("address");
        String city = form.get("city");
        String phone = form.get("phone");
        String email = form.get("email");
//        boolean textAlert =  new Boolean(form.get("textAlert"));
//        boolean emailAlert = new Boolean(form.get("emalAlert"));


        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setAddress(address);
        customer.setCity(city);
        customer.setPhone(phone);
        customer.setEmail(email);
//        customer.setTextAlert(textAlert);
//        customer.setEmailAlert(emailAlert);


        jpaApi.em().persist(customer);

        return redirect(routes.CustomerController.getAddCustomer());

    }

    public Result getLogIn()
    {
        return ok(views.html.addcustomer.render());
    }

    @Transactional
    public Result postLogIn()
    {
        DynamicForm form = formFactory.form().bindFromRequest();

        String email = form.get("email");
        String password = form.get("password");

        String sql = "SELECT c FROM Customer c WHERE email = :email AND password = :password";

        List<Customer> customers = jpaApi.em().createQuery(sql, Customer.class)
                .setParameter("email", email)
                .setParameter("password", password).getResultList();

        Result result;

        if (customers.size() == 1) {
            session().put("customerId", "" + customers.get(0).getCustomerId());
            result = redirect(routes.HomeController.getHello());
        }
        else {
            result = redirect(routes.CustomerController.getAddCustomer());
        }
        return result;
    }

   @Transactional
    public Result postLogOut()
    {
        session().put("customerId", null);


        return redirect(routes.HomeController.getHello());
    }

    public Result getSendEmail()
    {
        return ok(views.html.email.render());
    }

    public Result postSendEmail()
    {
        Date date = new Date();

        Email.sendEmail(date);

        return ok("Sent Email");


    }

}
