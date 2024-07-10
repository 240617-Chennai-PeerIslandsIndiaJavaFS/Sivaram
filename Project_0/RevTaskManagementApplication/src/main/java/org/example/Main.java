package org.example;

import org.example.controller.MainController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        MainController main = new MainController();
        main.homePage();
        //System.out.println(LocalDateTime.now());
    }
}