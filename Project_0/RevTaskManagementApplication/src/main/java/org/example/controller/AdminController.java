package org.example.controller;

import org.example.Main;
import org.example.model.*;
import org.example.service.AdminService;
import org.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class AdminController {
    public  static Logger logger = LoggerFactory.getLogger(AdminController.class);
    Scanner sc;
    User user;
    AdminService admin_service;
    ProjectManagerController project_manager_controller;



    public AdminController(Scanner sc,UserService user_service) {
        this.sc = sc;
        this.admin_service = user_service.getAdminService();
    }



    public void adminUser(){
        int choice;
        String option;

        System.out.println();
        while(true){
            System.out.println();
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("1) Manage Users     ||      2) Manage Client Information     ||      3) Manage Projects      ||      4) Report      ||      5) Logout      ||");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.print("Enter Your Option : ");
            choice = sc.nextInt();
            if(choice > 0 && choice < 6){
                switch (choice){
                    case 1:
                        manageUser();
                        break;
                    case 2:
                        project_manager_controller.manageClient();
                        break;
                    case 3:
                        manageProjects();
                        break;
                    case 4:
                        report();
                        break;
                    case 5:
                        logger.info("{} is Logged Out Successfully",user.getUserName());
                        user = null;
                        System.out.println("Logout SuccessFul");
                        return;
                }
            }
            else {
                System.out.println("Invalid Option !!!");
            }
        }


    }


    // Manage User

    public void manageUser(){
        int choice;
        String option;

        System.out.println();
        while(true){
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("1) Create User      ||      2) Update User      ||      3) Deactivate User      ||      4) Change Role      ||      5) Go Back        ||");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
            System.out.print("Enter Your Option : ");
            choice = sc.nextInt();
            sc.nextLine();
            if(choice > 0 && choice < 6){
                switch (choice){
                    case 1:
                        registration();
                        break;
                    case 2:
                        updateUserDetails();
                        break;
                    case 3:
                        deactivateUser();
                        break;
                    case 4:
                        changeRole();
                        break;
                    case 5:
                        return;
                }
            }
            else
                System.out.println("Invalid Option !!!");
        }


    }

    public void registration(){
        System.out.println();
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("Registration Page: ");
        System.out.println();
        User user = new User();
        System.out.print("Enter User Name : ");
        user.setUserName(sc.next());
        System.out.print("Enter User Email : ");
        user.setEmail(sc.next());
        System.out.print("Enter User Password : ");
        user.setUserPassword(sc.next());
        System.out.print("Enter User Role (\"Admin\",\"ProjectManager\",\"User\"): ");
        user.setUserRole(sc.next());
        System.out.print("Enter User PhoneNumber: ");
        user.setPhone(sc.next());

        if(admin_service.registerUser(user))
            System.out.println("Registration Successful");
        else{
            System.out.println("Please Enter Valid Details !");
            registration();
        }
    }




    public void updateUserDetails(){
        int choice;
        String option = "yes";
        User user_update = getUserNameForModifications();

        while(option.toLowerCase().equals("yes")){
            System.out.println();
            System.out.print("Select the Details wants to Update :\n 1) Name  2) email 3) Password  4) phone  :");
            choice = sc.nextInt();
            if(choice < 1 || choice > 4){
                continue;
            }
            switch (choice){
                case 1:
                    System.out.print("Enter Name : ");
                    user_update.setUserName(sc.next());
                    break;
                case 2:
                    System.out.print("Enter Email : ");
                    user_update.setEmail(sc.next());
                    break;

                case 3:
                    System.out.print("Enter Password : ");
                    user_update.setUserPassword(sc.next());
                    break;
                case 4:
                    System.out.print("Enter Phone :");
                    user_update.setPhone(sc.next());
                    break;
            }

            System.out.print("Do you want to Continue Update Other Details of User (type Yes or No): ");
            option = sc.next();
        }

        if(admin_service.updateUser(user_update))
            System.out.println("Updated Successfully");
        else
            System.out.println("Updated Failed");
    }



    public void deactivateUser(){
        User user_deactivate = getUserNameForModifications();
        user_deactivate.setAccountStatus("InActive");
        if(admin_service.updateUser(user_deactivate))
            System.out.println("Deactivated Successfully");
        else
            System.out.println("Deactivation Failed");
    }

    public void changeRole(){
        User user_role_change = getUserNameForModifications();
        System.out.print("Enter  Role (\"Admin\",\"ProjectManager\",\"User\"): ");
        user_role_change.setUserRole(sc.next());
        if(admin_service.updateUser(user_role_change))
            System.out.println("Role Changed Successfully");
        else
            System.out.println("Role not changed");

    }



    private User getUserNameForModifications() {
        User user_operation;
        while(true){
            System.out.print("Enter the User Name for modification: ");
            user_operation = admin_service.getUserByName(sc.nextLine());
            if(user_operation!=null)
                break;
            System.out.println("Please Enter Valid User Name");
        }
        return user_operation;
    }


    // Manage Projects
    public void manageProjects(){
        int choice;
        String option;

        System.out.println();
        while(true){
            System.out.println();
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("1) Create Project      ||      2) Update Project      ||      3) Delete Project      ||        4) Display Projects      ||      5) Go Back to Main Menu   ||");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.print("Enter Your Option : ");
            choice = sc.nextInt();
            sc.nextLine();
            if(choice > 0 && choice < 6){
                switch (choice){
                    case 1:
                        createProject();
                        break;
                    case 2:
                        updateProjectDetails();
                        break;
                    case 3:
                        deleteProject();
                        break;
                    case 4:
                        displayProjects();
                        break;
                    case 5:
                        return;
                }
            }
            else
                System.out.println("Invalid Option !!!");
        }

    }


    public void createProject() {
        System.out.println();
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("Project Form : ");
        System.out.println();
        Project project = new Project();
        System.out.print("Enter Project Name : ");
        project.setProjectName(sc.nextLine());
        System.out.print("Enter Project Description : ");
        project.setProjectDescription(sc.nextLine());

        System.out.print("Enter Project StartDate (dd-mm-yyyy) : ");
        String start_date_input = sc.nextLine();
        LocalDate start_date = parseDate(start_date_input);
        project.setStartDate(start_date);
        System.out.print("Enter Project DueDate (dd-mm-yyyy) : ");
        String due_date_input = sc.nextLine();
        LocalDate due_date = parseDate(due_date_input);
        project.setDueDate(due_date);

        //display clients
        displayAllClients();
        Client client = new Client();
        System.out.print("Enter Client Id: ");
        int client_id = sc.nextInt();
        client.setClientId(client_id);
        project.setClient(client);

        //display project manager
        displayAllProjectManager();
        User project_manager = new User();
        System.out.print("Enter ProjectManagerId: ");
        int project_manager_id = sc.nextInt();
        project_manager.setUserId(project_manager_id);
        project.setProjectManager(project_manager);

        if(admin_service.createProject(project))
            System.out.println("Project is created Successful");
        else{
            System.out.println("Please Enter Valid Details !");
            createProject();
        }
    }


    public void updateProjectDetails(){
        int choice;
        String option = "yes";
        Project project;
        displayProjects();

        while(true){
            System.out.print("Enter the Project ID want to Update: ");
            int project_id = sc.nextInt();
//            sc.nextLine();
            project = admin_service.getProject(project_id);
            if(project != null)
                break;
            System.out.println("Please Enter Valid Project ID ");
            System.out.println();
        }

        while(option.toLowerCase().equals("yes")){
            System.out.println();
            System.out.print("Select the Details wants to Update :\n 1) Name   2) Description   3) Start Date   4) Due Date   5) Client   6) ProjectManager  : ");
            choice = sc.nextInt();
            sc.nextLine();
            if(choice < 1 || choice > 6){
                System.out.print("Please Select Valid Option");
                continue;
            }
            switch (choice){
                case 1:
                    System.out.print("Enter Name : ");
                    project.setProjectName(sc.nextLine());
                    break;
                case 2:
                    System.out.print("Enter Description : ");
                    project.setProjectDescription(sc.nextLine());
                    break;

                case 3:
                    System.out.print("Enter Start Date (dd-mm-yyyy) : ");
                    LocalDate start_date = parseDate(sc.nextLine());
                    project.setStartDate(start_date);
                    break;
                case 4:
                    System.out.print("Enter Due Date (dd-mm-yyyy) : ");
                    LocalDate due_date = parseDate(sc.nextLine());
                    project.setStartDate(due_date);
                    break;
                case 5:
                    displayAllClients();
                    System.out.print("Enter client ID : ");
                    Client client = new Client();
                    client.setClientId(sc.nextInt());
                    project.setClient(client);
                    break;
                case 6:
                    displayAllProjectManager();
                    System.out.print("Enter ProjectManagerID :");
                    User project_manager = new User();
                    project_manager.setUserId(sc.nextInt());
                    project.setProjectManager(project_manager);
                    break;
            }

            System.out.print("Do you want to Continue Update Other Details of Project (type Yes or No): ");
            option = sc.next();
        }

        if(admin_service.updateProject(project))
            System.out.println("Updated Successfully");
        else
            System.out.println("Updated Failed");
    }



    public void deleteProject(){
        System.out.println();
        System.out.println("All Projects :");
        displayProjects();
        System.out.print("Enter the Project ID want to delete : ");
        int project_id = sc.nextInt();
        if(admin_service.deleteProject(project_id))
            System.out.println("Project Deleted Successfully");
        else
            System.out.println("Please Enter valid Details");
    }

    public void displayProjects(){
        List<Project> projects = admin_service.getAllProjects();
        System.out.println();
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-20s | %-30s | %-12s | %-12s | %-20s | %-20s |\n", "Project ID", "Project Name", "Project Description", "Start Date", "Due Date", "Client Name", "Project Manager ");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");

        for (Project project : projects) {
            System.out.println(project);
        }

        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println();
    }

    public void report(){
        List<ReportDetail> reports = admin_service.getReports();
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-30s | %-20s | %-20s | %-20s | %-20s |\n", "Project Name", "Project Manager", "No. of Team Members", "Total No. of Tasks", "No. of Completed Tasks");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
        for (ReportDetail detail : reports) {
            System.out.printf("| %-30s | %-20s | %-20d | %-20d | %-20d |\n",
                    detail.getProjectName(),
                    detail.getProjectManager(),
                    detail.getUniqueTeamMembers(),
                    detail.getTotalTasks(),
                    detail.getCompletedTasks());
        }

        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
    }


    private void displayAllClients() {
        System.out.println();
        System.out.println("All Clients: ");
        project_manager_controller.displayClients();
    }

    private void displayAllProjectManager() {
        System.out.println();
        System.out.println("All ProjectManager: ");
        List<User> project_managers = admin_service.getAllUser();
        System.out.println();
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-9s | %-20s | %-30s | %-15s | %-20s | %-15s |\n", "User ID", "User Name", "Email", "Password", "Role", "Phone");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");
        for(User project_manager:project_managers)
            if(project_manager.getUserRole().equals("ProjectManager"))
                System.out.println(project_manager);
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");
        System.out.println();
    }



    private LocalDate parseDate(String startDateInput) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            return LocalDate.parse(startDateInput, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please enter the date in dd-MM-yyyy format.");
            return null;
        }
    }


    public void setProject_manager_controller(ProjectManagerController project_manager_controller) {
        this.project_manager_controller = project_manager_controller;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
