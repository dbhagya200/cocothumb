package lk.ijse.cocothumb.controller.Util;

import javafx.scene.control.Alert;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Properties;


public class Mail {

    private static Session newSession = null;

    private static void setUpServerProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.port", "587"); // Use TLS port
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // Enable STARTTLS

        newSession = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        "dilinibhagya53@gmail.com", "dvxn wepn fgpc yiuq");
            }
        });
    }

    public static void setOtpMail(String receiverMail, String otp) throws MessagingException, IOException {
        setUpServerProperties();
        MimeMessage mimeMessage = draftOtpMail(receiverMail, otp);
        sendOtpMail(mimeMessage);
    }

    private static MimeMessage draftOtpMail(String receiverMail, String otp) throws MessagingException, IOException {
        MimeMessage mimeMessage = new MimeMessage(newSession);

        mimeMessage.setFrom(new InternetAddress("dilinibhagya53@gmail.com"));
        mimeMessage.addRecipients(Message.RecipientType.TO, receiverMail);
        mimeMessage.setSubject("One Time Password!");

        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent("Your One Time Password is: " + otp + ".", "text/html");

        MimeMultipart multipart = new MimeMultipart(); //mime msg's body
        multipart.addBodyPart(bodyPart);

        mimeMessage.setContent(multipart);

        return mimeMessage;
    }

    private static void sendOtpMail(MimeMessage mimeMessage) throws MessagingException {
        String host = "smtp.gmail.com";
        Transport transport = newSession.getTransport("smtp");
        try {
            transport.connect(host, System.getenv("dilinibhagya53@gmail.com"), System.getenv("dvxn wepn fgpc yiuq"));
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            new Alert(Alert.AlertType.INFORMATION, "OTP sent successfully!").show();
        } finally {
            transport.close();
        }
    }

}