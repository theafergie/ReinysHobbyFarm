package Services;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import models.CartItem;

import java.util.Date;
import java.util.List;

public class Email
{
    public static void sendEmail(Date date, List<CartItem> cartItems)
    {
        String sender = "ashleyfergusond@gmail.com";
        String receiver = "ashleyfergusond@gmail.com";

        String subject = "Thank you for your order!";

        String htmlBody = "<h1>Order Confirmation</h1><p>" + date + "</p>";

        StringBuffer sb = new StringBuffer();

        sb.append("<ul>");
        for(CartItem cartItem : cartItems)
        {
            sb.append("<li>");
            sb.append(cartItem.getProductName());
                sb.append("<ul>");
                    sb.append("<li>");
                    sb.append("Quantity: ");
                    sb.append(cartItem.getQuantity());
                    sb.append("</li>");
                sb.append("</ul>");
            sb.append("</li>");

        }
        sb.append("</ul>");

        htmlBody += sb.toString();

        String textBody = "Order Confirmation " + date ;

        try
        {
            AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder
                    .standard()
                    .withRegion(Regions.US_WEST_2)
                    .build();

            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(
                            new Destination().withToAddresses(receiver))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withHtml(new Content()
                                            .withCharset("UTF-8").withData(htmlBody))
                                    .withText(new Content()
                                            .withCharset("UTF-8").withData(textBody)))
                            .withSubject(new Content()
                                    .withCharset("UTF-8").withData(subject)))
                    .withSource(sender);

            client.sendEmail(request);
            System.out.println("Email sent");
        }
        catch(Exception e)
        {
            System.out.println("Error sending email: " + e.getMessage());
        }

    }
}
