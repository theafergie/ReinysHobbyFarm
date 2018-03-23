package controllers;

import models.Customer;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

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

        if (checkedText != null)
        {
            boolean textAlert = true;
            customer.setTextAlert(textAlert);
            //jpaApi.em().persist(customer);

        }

        if (checkedEmail != null)
        {
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

}
