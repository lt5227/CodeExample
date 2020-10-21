package com.stackstone.study.springboot.rabbitmq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * MailService
 *
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020/10/21 20:50
 * @since 1.0.0
 */
@Service
@Slf4j
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String content) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        mimeMessage.setFrom("lt5227@qq.com");
        mimeMessage.setSubject("邮件测试");
        mimeMessage.setContent(content, "text/html;charset=utf-8");
        mimeMessage.addRecipients(Message.RecipientType.TO, "lilei@oristand.com");
        mimeMessage.addRecipients(Message.RecipientType.CC, "15850374663@163.com");
        javaMailSender.send(mimeMessage);
    }
}
