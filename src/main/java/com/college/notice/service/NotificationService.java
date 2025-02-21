package com.college.notice.service;

import com.college.notice.entity.Event;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.util.List;

@Service
public class NotificationService {

    private final JavaMailSender mailSender;

    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void notifyRegisteredUsers(Event event, String message) {
        List<String> recipients = event.getRegistrations().stream()
                .map(reg -> reg.getUserEmail())
                .toList();

        String subject = "Update on Event: " + event.getTitle();
        String body = "Dear participant, \n\n" + message + "\n\nEvent Details:\n" +
                "Title: " + event.getTitle() + "\nDate: " + event.getEventDate() +
                "\nOrganizer: " + event.getOrganizer() + "\n\nRegards, \nCollege Notice Board";

        sendEmail(recipients, subject, body);
    }

    public void notifyUserWaitlisted(String userEmail, Event event) {
        String subject = "Waitlisted for Event: " + event.getTitle();
        String body = "Dear user,\n\nYou have been waitlisted for the event: " + event.getTitle() +
                ".\nWe will notify you if a spot becomes available.\n\nRegards,\nCollege Notice Board";

        sendEmail(List.of(userEmail), subject, body);
    }

    public void notifyUserConfirmed(String userEmail, Event event) {
        String subject = "Registration Confirmed: " + event.getTitle();
        String body = "Dear user,\n\nYour registration for the event: " + event.getTitle() +
                " has been confirmed!\nSee you at the event.\n\nRegards,\nCollege Notice Board";

        sendEmail(List.of(userEmail), subject, body);
    }

    public void sendEmail(List<String> recipients, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(recipients.toArray(new String[0]));
            helper.setSubject(subject);
            helper.setText(body, true); // Enables HTML emails

            mailSender.send(message);
            System.out.println("Email sent successfully!");
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
