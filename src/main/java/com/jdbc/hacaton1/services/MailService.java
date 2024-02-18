package com.jdbc.hacaton1.services;

import com.jdbc.hacaton1.models.UsersModel;
import jakarta.mail.internet.InternetAddress;
import lombok.AllArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MailService {

    private  final JavaMailSender javaMailSender;

    public void sendEmailForRegistration(UsersModel user, Integer userId) throws MailException {
        if(user.getMailAddress() != null && isValidEmailAddress(user.getMailAddress())) {
            SimpleMailMessage mail = new SimpleMailMessage();

            mail.setTo(user.getMailAddress());
            mail.setSubject("Registration in Apparently");
            mail.setText("Has gone successfully!\n" +
                    "Your account : \n" +
                    "\nID - " + userId +
                    "\nLogin - " + user.getLogin() +
                    "\nPassword - " + user.getPassword());

            javaMailSender.send(mail);
        }
    }

    public void informUserAboutDelete(String login, String mailAddress) throws MailException{
        if(mailAddress != null && isValidEmailAddress(mailAddress)) {
            SimpleMailMessage mail = new SimpleMailMessage();

            mail.setTo(mailAddress);
            mail.setSubject("Deleted from Apparently ");
            mail.setText(login + ", your account has been successfully deleted");

            javaMailSender.send(mail);
        }
    }

    public void notifyUserToEvaluateProducts(String login, String mailAddress) throws MailException{
        if(mailAddress != null && isValidEmailAddress(mailAddress)) {
            SimpleMailMessage mail = new SimpleMailMessage();

            mail.setTo(mailAddress);
            mail.setSubject("We have a good news from Apparently");
            mail.setText(login + ", you have products to evaluate!");

            javaMailSender.send(mail);
        }
    }

    private boolean isValidEmailAddress(String mailAddress) {
        boolean result = true;
        try {
            InternetAddress email = new InternetAddress(mailAddress);
            email.validate();
        } catch (Exception ex) {
            result = false;
        }
        return result;
    }
}
