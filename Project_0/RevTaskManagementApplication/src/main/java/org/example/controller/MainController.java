package org.example.controller;

public class MainController {
    UserController user_controller;

    public MainController() {
        this.user_controller = new UserController();
    }

    public void homePage(){
        displayWelcomeMessage();
        user_controller.loginUser();
    }

    private void displayWelcomeMessage(){
        System.out.println("******************************************************");
        System.out.println("Welcome to Rev Task Management App");
        System.out.println("******************************************************");
    }
}
