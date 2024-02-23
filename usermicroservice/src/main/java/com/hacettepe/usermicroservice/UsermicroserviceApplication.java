package com.hacettepe.usermicroservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@SpringBootApplication
public class UsermicroserviceApplication {
    @Bean
    public JavaMailSender getJavaMailSender(@Value("${spring.mail.host}") String host,
                                            @Value("${spring.mail.port}") int port,
                                            @Value("${spring.mail.username}") String username,
                                            @Value("${spring.mail.password}") String password)
    {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);

        mailSender.setUsername(username);
        mailSender.setPassword(password);
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
    public static void main(String[] args) {
        SpringApplication.run(UsermicroserviceApplication.class, args);
    }

}
