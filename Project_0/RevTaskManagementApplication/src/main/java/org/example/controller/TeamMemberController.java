package org.example.controller;

import org.example.Main;
import org.example.model.*;
import org.example.service.TeamMemberService;
import org.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TeamMemberController {
    public  static Logger logger = LoggerFactory.getLogger(TeamMemberController.class);
    Scanner sc;
    User user;
    TeamMemberService team_member_service;
    ProjectManagerController project_manager_controller;



    public TeamMemberController(Scanner sc, UserService user_service) {
        this.sc = sc;
        this.team_member_service = user_service.getTeamMemberService();
    }

    public void teamMember(){
        int choice;
        String option;

        System.out.println();
        while(true){
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("1) OverAll Project Details      ||      2) View Client information       ||      3) Update Task Status      ||      4) Update Progress      ||      5) Display Message       ||      6) Logout       || ");
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.print("Enter Your Option : ");
            choice = sc.nextInt();
            if(choice > 0 && choice < 7){
                switch (choice){
                    case 1:
                        projectDetails();
                        break;
                    case 2:
                        clientInformation();
                        break;
                    case 3:
                        updateStatus();
                        break;
                    case 4:
                        updateProgress();
                        break;
                    case 5:
                        project_manager_controller.displayMessage();
                        break;
                    case 6:
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


    public void projectDetails(){

        List<TeamDetail> projectDetails = team_member_service.getProjectDetails(user);

        System.out.println();

//        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
//        System.out.printf("%-20s %-30s %-15s %-15s %-20s %-30s %-20s %-30s %-30s %-20s %-15s %-15s\n", "Project Name", "Project Description", "Start Date", "Due Date", "Task Title", "Task Description", "Client Name", "Client Email", "Client Description", "Address", "City", "Phone");
//        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
//
//        for (TeamDetail detail : projectDetails) {
//            System.out.printf("%-20s %-30s %-15s %-15s %-20s %-30s %-20s %-30s %-30s %-20s %-15s %-15s\n",
//                    detail.getTeam().getProject().getProjectName(),
//                    detail.getTeam().getProject().getProjectDescription(),
//                    detail.getTeam().getProject().getStartDate(),
//                    detail.getTeam().getProject().getDueDate(),
//                    detail.getTask().getTitle(),
//                    detail.getTask().getTaskDescription(),
//                    detail.getTeam().getProject().getClient().getClientName(),
//                    detail.getTeam().getProject().getClient().getClientEmail(),
//                    detail.getTeam().getProject().getClient().getClientDescription(),
//                    detail.getTeam().getProject().getClient().getAddress(),
//                    detail.getTeam().getProject().getClient().getCity(),
//                    detail.getTeam().getProject().getClient().getPhone());
//        }
//
//        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
//        System.out.println();

        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-20s %-30s %-15s %-15s %-20s %-30s\n", "Project Name", "Project Description", "Start Date", "Due Date", "Task Title", "Task Description");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        for (TeamDetail detail : projectDetails) {
            String projectName = truncateString(detail.getTeam().getProject().getProjectName(), 20);
            String projectDescription = truncateString(detail.getTeam().getProject().getProjectDescription(), 30);
            LocalDate startDate = detail.getTeam().getProject().getStartDate();
            LocalDate dueDate = detail.getTeam().getProject().getDueDate();
            String taskTitle = truncateString(detail.getTask().getTitle(), 20);
            String taskDescription = truncateString(detail.getTask().getTaskDescription(), 30);
            String clientName = truncateString(detail.getTeam().getProject().getClient().getClientName(), 20);
            String clientEmail = truncateString(detail.getTeam().getProject().getClient().getClientEmail(), 30);
            String clientDescription = truncateString(detail.getTeam().getProject().getClient().getClientDescription(), 30);
            String address = truncateString(detail.getTeam().getProject().getClient().getAddress(), 20);
            String city = truncateString(detail.getTeam().getProject().getClient().getCity(), 15);
            String phone = truncateString(detail.getTeam().getProject().getClient().getPhone(), 15);

            System.out.printf("%-20s %-30s %-15s %-15s %-20s %-30s %-20s %-30s %-30s %-20s %-15s %-15s\n",
                    projectName, projectDescription, startDate, dueDate, taskTitle, taskDescription,
                    clientName, clientEmail, clientDescription, address, city, phone);
        }

        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println();


    }

    public void  updateStatus(){
        System.out.println();
        List<TeamDetail> projectDetails = team_member_service.getProjectDetails(user);

        System.out.println("-------------------------------------------------------");
        System.out.printf("%-20s %-10s %-30s\n", "Project Name", "Task ID", "Title");
        System.out.println("-------------------------------------------------------");

        for (TeamDetail detail : projectDetails) {
            System.out.printf("%-20s %-10d %-30s\n",
                    detail.getTeam().getProject().getProjectName(),
                    detail.getTask().getTaskId(),
                    detail.getTask().getTitle());
        }

        System.out.println("--------------------------------------------------");
        System.out.println();

        System.out.print("Enter the taskId :");
        Task task = new Task();
        task.setTaskId(sc.nextInt());
        boolean updated = team_member_service.updateTask(user,task);
        if(updated)
            System.out.println("Task's Milestone Updated Successfully");
        else
            System.out.println("Updating Task's Milestone got failed");
    }

    public void clientInformation(){
        System.out.println();
        List<TeamDetail> project_details = team_member_service.getProjectDetails(user);
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-20s %-20s %-30s %-30s %-20s %-15s %-15s\n", "Project Name", "Client Name", "Client Email", "Client Description", "Address", "City", "Phone");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");

        for (TeamDetail detail : project_details) {
            String projectName = truncateString(detail.getTeam().getProject().getProjectName(), 20);
            String clientName = truncateString(detail.getTeam().getProject().getClient().getClientName(), 20);
            String clientEmail = truncateString(detail.getTeam().getProject().getClient().getClientEmail(), 30);
            String clientDescription = truncateString(detail.getTeam().getProject().getClient().getClientDescription(), 30);
            String address = truncateString(detail.getTeam().getProject().getClient().getAddress(), 20);
            String city = truncateString(detail.getTeam().getProject().getClient().getCity(), 15);
            String phone = truncateString(detail.getTeam().getProject().getClient().getPhone(), 15);

            System.out.printf("%-20s %-20s %-30s %-30s %-20s %-15s %-15s\n",
                    projectName, clientName, clientEmail, clientDescription, address, city, phone);
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");

    }


    public void updateProgress(){
        boolean updated = false;

        System.out.println();
        List<TeamDetail> projectDetails = team_member_service.getProjectDetails(user);

        if(projectDetails.size()==0){
            System.out.println("You have No Projects !");
            return;
        }


        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.printf("%10s %-20s %-10s %-30s\n","Project ID", "Project Name", "Task ID", "Title");
        System.out.println("--------------------------------------------------------------------------------------------");

        for (TeamDetail detail : projectDetails) {
            System.out.printf("%-10s %-20s %-10d %-30s\n",
                    detail.getTeam().getProject().getProjectId(),
                    detail.getTeam().getProject().getProjectName(),
                    detail.getTask().getTaskId(),
                    detail.getTask().getTitle());
        }

        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println();

        System.out.print("Enter Project ID : ");
        int project_id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter the Progress Update :");
        String content = sc.nextLine();

       // System.out.println("DEBUG: Captured progress update: " + content);
        Message message = new Message();
        message.setSender(user);
        message.setMessageDescription(content);
        message.setMessageTime(LocalDateTime.now());


        List<Integer> ids =team_member_service.getProjectManagerAndTeam(user,project_id);
        User project_manager = new User();
        project_manager.setUserId(ids.getFirst());
        message.setReceiver(project_manager);
        //System.out.println("Sender: "+message.getSender().getUserId()+" Receiver: "+message.getReceiver().getUserId());
        updated = team_member_service.updateProgress(message);

        ids.removeFirst();

        for (int memberId : ids) {
            User team_member = new User();
            team_member.setUserId(memberId);
            message.setReceiver(team_member);
            updated = team_member_service.updateProgress(message);
        }
        if(updated)
            System.out.println("Progress update sent successfully.");
        else
            System.out.println("Progress update  sent got failed.");
    }


    private String truncateString(String text, int maxLength) {
        if (text.length() > maxLength) {
            return text.substring(0, maxLength - 3) + "...";
        }
        return text;
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
