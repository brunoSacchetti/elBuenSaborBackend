package com.entidades.buenSabor.business.service;

public interface SendEmailService {
    String sendMail(byte[] file, String to, String[] cc, String subject, String body, String attachmentFilename);
}
