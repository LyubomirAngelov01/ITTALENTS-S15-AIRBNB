package com.finalproject.airbnb.service;

import com.finalproject.airbnb.model.DTOs.SimpleMessageDTO;
import com.finalproject.airbnb.model.exceptions.EmailServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService extends AbstractService{

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private static final Logger logger = LogManager.getLogger(UserService.class);
    @Value("${spring.mail.from}")
    private String emailFrom;

    public void sendEmail(SimpleMessageDTO dto){
        SimpleMailMessage simpleMailMessage = createSimpleMailMessage(dto);
        emailSender.send(simpleMailMessage);


    }

    private SimpleMailMessage createSimpleMailMessage(SimpleMessageDTO dto){
        if (dto.getSendTo().isEmpty()){
            throw new EmailServiceException("no recipients");
        }

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailFrom);
        simpleMailMessage.setTo(dto.getSendTo());
        simpleMailMessage.setSubject(dto.getSubject());
        simpleMailMessage.setText(dto.getContent());

        return simpleMailMessage;
    }

}
