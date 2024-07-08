package ru.postal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MailingApplication {
    public static void main(String[] args) {

        SpringApplication.run(MailingApplication.class, args);
    }
}
