package controllers;

import Services.Email;
import Services.SMS;
import models.CartItem;
import models.Customer;
import models.Password;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


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

        String checkedText = form.get("textAlert");

        String checkedEmail = form.get("emailAlert");


        if (checkedText != null) {
            boolean textAlert = true;
            customer.setTextAlert(textAlert);

        }

        if (checkedEmail != null) {
            boolean emailAlert = true;
            customer.setEmailAlert(emailAlert);

        }

        String firstName = form.get("firstName");
        String lastName = form.get("lastName");
        String address = form.get("address");
        String city = form.get("city");
        String phone = form.get("phone");
        String email = form.get("email");
        String password = form.get("password");


        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setAddress(address);
        customer.setCity(city);
        customer.setPhone(phone);
        customer.setEmail(email);

        if (password.trim().length() > 0) {
            try {
                byte[] salt = Password.getNewSalt();
                byte[] hashedPassword = Password.hashPassword(password.toCharArray(), salt);
                customer.setPassword(hashedPassword);
                customer.setSalt(salt);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

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

        String sql = "SELECT c FROM Customer c WHERE email = :email";

        List<Customer> customers = jpaApi.em()
                .createQuery(sql, Customer.class)
                .setParameter("email", email)
                .getResultList();

        Result result = null;

        if (customers.size() == 1) {
            Customer customer = customers.get(0);
            byte[] hashedPassword = Password.hashPassword(password.toCharArray(), customer.getSalt());
            byte[] savedPassword = customer.getPassword();

            if (Arrays.equals(hashedPassword, savedPassword)) {
                session().put("customerId", "" + customers.get(0).getCustomerId());
                result = redirect(routes.HomeController.getHello());
            }

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

        String json = session().get("cart");
        List<CartItem> cartItem = CartItem.fromJSON(json);
        Email.sendEmail(date, cartItem);

        return ok(views.html.completeorder.render());
    }

    public Result getSendSMS()
    {
        return ok(views.html.sms.render());
    }

    public Result postSendText()
    {
        String phoneNumber= "";

        String message = "";

        SMS.send(phoneNumber, message);

        return ok(views.html.completeorder.render());
    }

}
