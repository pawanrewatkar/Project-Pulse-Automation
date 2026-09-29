package com.pulse.automation.utils;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.Properties;

public class EmailUtil {

    private static final String FROM_EMAIL = "pavan@aristasystems.in";

    private static final String APP_PASSWORD = "ufvd rmon cpdi bkij";

    private static final String[] RECIPIENTS = {
            "swapnilkk@aristasystems.in",
            "hrishikesh@aristasystems.in"
    };

    public static void sendReport(
            String attachmentPath,
            String subject) throws Exception {

        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(
                props,
                new Authenticator() {

                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {

                        return new PasswordAuthentication(
                                FROM_EMAIL,
                                APP_PASSWORD);
                    }
                });

        Message message = new MimeMessage(session);

        message.setFrom(
                new InternetAddress(FROM_EMAIL));

        InternetAddress[] recipientAddresses =
                new InternetAddress[RECIPIENTS.length];

        for (int i = 0; i < RECIPIENTS.length; i++) {

            recipientAddresses[i] =
                    new InternetAddress(RECIPIENTS[i]);
        }

        message.setRecipients(
                Message.RecipientType.TO,
                recipientAddresses);

        message.setSubject(subject);

        BodyPart messageBodyPart =
                new MimeBodyPart();

        messageBodyPart.setText(
                "Hi Team,\n\n" +
                        "Please find attached the Project Pulse automation test execution report.\n\n" +
                        "The report contains the test execution results and failure details.\n\n" +
                        "Thanks,\n" +
                        "Pavan (Automation Tester)");

        Multipart multipart =
                new MimeMultipart();

        multipart.addBodyPart(messageBodyPart);

        MimeBodyPart attachmentPart =
                new MimeBodyPart();

        DataSource source =
                new FileDataSource(attachmentPath);

        attachmentPart.setDataHandler(
                new DataHandler(source));

        attachmentPart.setFileName(
                "ProjectPulse_TestReport.html");

        multipart.addBodyPart(attachmentPart);

        message.setContent(multipart);

        Transport.send(message);

        System.out.println("EMAIL SENT SUCCESSFULLY");
    }
}