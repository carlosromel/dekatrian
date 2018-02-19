/**
 * Copyright 2018 Carlos Romel Pereira da Silva, carlos.romel@gmail.com
 */
package br.eti.romel.lounge.dekatrian.heroku;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;

@Controller
@SpringBootApplication
public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Iniciando o chatbot...");
        DekatrianChatbot.init();
        System.out.println("Iniciando o WebApp...");
        SpringApplication.run(DekatrianWebApp.class, args);
    }
}
