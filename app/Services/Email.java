package Services;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;

import java.util.Date;

public class Email
{
    public static void sendEmail(Date date)
    {
        String sender = "ashleyfergusond@gmail.com";
        String receiver = "ashleyfergusond@gmail.com";

        String subject = "Your order confirmation!";

        String htmlBody = "<h1>Order Date</h1><p>" + date + "</p>";

        String textBody = "Ordered " + date ;

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
