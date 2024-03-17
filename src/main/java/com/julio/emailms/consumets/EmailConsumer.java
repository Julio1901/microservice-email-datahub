package com.julio.emailms.consumets;


import com.julio.emailms.dtos.EmailDto;
import com.julio.emailms.models.EmailModel;
import com.julio.emailms.services.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumer {

    final EmailService emailService;

    public EmailConsumer( EmailService emailService){
        this.emailService = emailService;
    }

    @RabbitListener(queues = {"${broker.queue.email.name}"})
    public void listenEmailQueue(@Payload EmailDto emailDto){
       var emailModel = new EmailModel();
        BeanUtils.copyProperties(emailDto, emailModel);
        emailService.sendEmail(emailModel);
    }
}