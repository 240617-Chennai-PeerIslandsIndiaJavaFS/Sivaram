package org.example.controller;


import org.example.dao.UserDAO;
import org.example.model.User;
import org.example.service.AdminService;
import org.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class UserController {
    public  static Logger logger = LoggerFactory.getLogger(UserController.class);
    Scanner sc;
    User user;
    UserService user_service;
    AdminController admin_controller;
    ProjectManagerController project_manager_controller;
    TeamMemberController team_member_controller;

    public UserController() {
        sc = new Scanner(System.in);
        user_service = new UserService();
        admin_controller = new AdminController(sc,user_service);
        project_manager_controller = new ProjectManagerController(sc,user_service);
        admin_controller.setProject_manager_controller(project_manager_controller);
        team_member_controller = new TeamMemberController(sc,user_service);
        team_member_controller.setProject_manager_controller(project_manager_controller);
    }

    public void loginUser(){
        while(true){
            System.out.println();
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Login Page: ");
            System.out.print("Enter Your Email: ");
            String email = sc.next();
            System.out.print("Enter Your Password: ");
            String password = sc.next();

            user = user_service.loginUser(email,password);

            if(user != null){
                System.out.println("Login Sucessfull");
                logger.info("{} is LoggedIn Successfully",user.getUserName());
                System.out.println("Hi "+user.getUserName()+", Role: "+user.getUserRole());
                admin_controller.setUser(user);
                project_manager_controller.setUser(user);
                team_member_controller.setUser(user);
                displayMenuBasedOnRole();
                break;
            }
            else{
                System.out.println("------------------------------------------");
                System.out.println("Invalid Email and Password !!!");
            }
        }
    }

    public void displayMenuBasedOnRole(){
        String user_role = user.getUserRole();
        if(user_role.equals("Admin")){
            admin_controller.adminUser();
            loginUser();
        }

        else if(user_role.equals("ProjectManager")){
            project_manager_controller.projectManager();
            loginUser();
        }
        else{
            team_member_controller.teamMember();
            loginUser();
        }
    }





}
