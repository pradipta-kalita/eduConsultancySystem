package com.pol.user_service.service;

import com.pol.user_service.utils.EmailTemplates;
import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;

@Service
public class ResendService {

    @Value("${resend.from-email}")
    private String fromEmail;

    private final Resend resend;
    private final EmailTemplates emailTemplates = EmailTemplates.getInstance();

    public ResendService(Resend resend) {
        this.resend = resend;
    }


    public String sendWelcomeEmail(String to, String subject, String name) {

        CreateEmailOptions options = CreateEmailOptions.builder()
                .from(fromEmail)
                .to(to)
                .subject(subject)
                .html(emailTemplates.getWelcomeTemplate(name))
                .build();

        CreateEmailResponse response = null;
        try {
            response = resend.emails().send(options);
        } catch (ResendException e) {
            throw new MailSendException("There was a error while sending email. Please try again later.");
        }
        return response.getId();
    }


    public String sendForgotPasswordOTPEmail(String to, String subject, String name,String otp) {
        CreateEmailOptions options = CreateEmailOptions.builder()
                .from(fromEmail)
                .to(to)
                .subject(subject)
                .html(emailTemplates.getForgotPasswordTemplate(name,otp))
                .build();

        CreateEmailResponse response = null;
        try {
            response = resend.emails().send(options);
        } catch (ResendException e) {
            throw new MailSendException("There was a error while sending email. Please try again later.");
        }
        return response.getId();
    }
}
