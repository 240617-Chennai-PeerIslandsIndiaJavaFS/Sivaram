package org.example.controller;

import org.example.Main;
import org.example.model.*;
import org.example.service.ProjectManagerService;
import org.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ProjectManagerController {
    public  static Logger logger = LoggerFactory.getLogger(ProjectManagerController.class);
    Scanner sc;
    User user;
    ProjectManagerService project_manager_service;

    public ProjectManagerController(Scanner sc, UserService user_service) {
        this.sc = sc;
        this.project_manager_service = user_service.getProjectManagerService();
    }

    public void projectManager(){
        int choice;
        String option;

        System.out.println();
        while(true){
            System.out.println();
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("1) Reset Password    ||  2) Manage Client Information     ||  3) Manage Team   ||  4) Manage Team Member (Assign Task)  ||  5) displayMessages   ||  6) Logout       ||");
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.print("Enter Your Option : ");
            choice = sc.nextInt();
            if(choice > 0 && choice < 7){
                switch (choice){
                    case 1:
                        resetPassword();
                        break;
                    case 2:
                        manageClient();
                        break;
                    case 3:
                        manageTeam();
                        break;
                    case 4:
                        addMemberAndAssignTask();
                        break;
                    case 5:
                        displayMessage();
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


    public void resetPassword(){
        System.out.println();
        System.out.print("Enter the New  Password: ");
        user.setUserPassword(sc.next());
        if(project_manager_service.passwordReset(user))
            System.out.println("Password Changed Successfully");
        else
            System.out.println("Password not changed");
    }



    // Manage client
    public void manageClient(){
        int choice;
        String option;

        System.out.println();
        while(true){
            System.out.println();
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("1) Create Client      ||      2) Update Client      ||      3) Delete Client      ||        4) Display clients      ||      5) Go Back to Main Menu     ||");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.print("Enter Your Option : ");
            choice = sc.nextInt();
            sc.nextLine();
            if(choice > 0 && choice < 6){
                switch (choice){
                    case 1:
                        createClient();
                        break;
                    case 2:
                        updateClientDetails();
                        break;
                    case 3:
                        deleteClient();
                        break;
                    case 4:
                        displayClients();
                        break;
                    case 5:
                        return;
                }
            }
            else
                System.out.println("Invalid Option !!!");
        }

    }

    public void createClient() {
        System.out.println();
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("Client Form : ");
        System.out.println();
        Client client = new Client();
        System.out.print("Enter Client Name : ");
        client.setClientName(sc.nextLine());
        System.out.print("Enter Client Email : ");
        client.setClientEmail(sc.nextLine());
        //sc.nextLine();
        System.out.print("Enter Client Description : ");
        client.setClientDescription(sc.nextLine());
        System.out.print("Enter Client Address: ");
        client.setAddress(sc.nextLine());
        System.out.print("Enter Client City: ");
        client.setCity(sc.nextLine());
        System.out.print("Enter Client Phone: ");
        client.setPhone(sc.nextLine());

        if(project_manager_service.createClient(client))
            System.out.println("Client is created Successful");
        else{
            System.out.println("Please Enter Valid Details !");
            createClient();
        }
    }


    public void updateClientDetails(){
        int choice;
        String option = "yes";
        Client client = getClientNameForModifications();

        while(option.toLowerCase().equals("yes")){
            System.out.println();
            System.out.print("Select the Details wants to Update :\n 1) Name  2) Email 3) Description  4) Address  5) City  6) Phone  :");
            choice = sc.nextInt();
            sc.nextLine();
            if(choice < 1 || choice > 6){
                continue;
            }
            switch (choice){
                case 1:
                    System.out.print("Enter Name : ");
                    client.setClientName(sc.nextLine());
                    break;
                case 2:
                    System.out.print("Enter Email : ");
                    client.setClientEmail(sc.nextLine());
                    break;

                case 3:
                    System.out.print("Enter Description : ");
                    client.setClientDescription(sc.nextLine());
                    break;
                case 4:
                    System.out.print("Enter Address : ");
                    client.setAddress(sc.nextLine());
                    break;
                case 5:
                    System.out.print("Enter City : ");
                    client.setCity(sc.nextLine());
                    break;
                case 6:
                    System.out.print("Enter Phone :");
                    client.setPhone(sc.nextLine());
                    break;
            }

            System.out.print("Do you want to Continue Update Other Details of Client (type Yes or No): ");
            option = sc.next();
        }

        if(project_manager_service.updateClient(client))
            System.out.println("Updated Successfully");
        else
            System.out.println("Updated Failed");
    }


    public void deleteClient(){
        Client client = getClientNameForModifications();
        if(project_manager_service.deleteClient(client))
            System.out.println("Deleted Successfully");
        else
            System.out.println("Deletion Failed");
    }


    public void displayClients(){
        List<Client> clients = project_manager_service.getAllClients(user);
        if(clients.isEmpty()){
            System.out.println("Sorry You don't have Clients");
            return;
        }

        System.out.println();
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-9s | %-20s | %-50s | %-30s | %-20s | %-15s |\n", "Client ID", "Client Name", "Client Description", "Address", "City", "Phone");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        for(Client client: clients)
            System.out.println(client);
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        System.out.println();
    }


    public void manageTeam(){
        int choice;
        String option;

        System.out.println();
        while(true){
            System.out.println();
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("1) Create Team      ||      2) Update Team      ||      3) Delete Team      ||        4) Display Teams      ||      5) Go Back to Main Menu     ||");
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.print("Enter Your Option : ");
            choice = sc.nextInt();
            sc.nextLine();
            if(choice > 0 && choice < 6){
                switch (choice){
                    case 1:
                        createTeam();
                        break;
                    case 2:
                        updateTeam();
                        break;
                    case 3:
                        deleteTeam();
                        break;
                    case 4:
                        displayTeam();
                        break;
                    case 5:
                        return;
                }
            }
            else
                System.out.println("Invalid Option !!!");
        }
    }


    public void createTeam() {
        System.out.println();
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("Team Form : ");
        System.out.println();
        Team team = new Team();
        System.out.print("Enter Team Name : ");
        team.setTeamName(sc.nextLine());
        team.setProjectManager(user);
        //sc.nextLine();

        displayAllProjects();
        System.out.print("Enter Project ID, you want to create Team : ");
        Project project = new Project();
        project.setProjectId(sc.nextInt());
        team.setProject(project);


        if(project_manager_service.createTeam(team))
            System.out.println("Team is created Successfully");
        else{
            System.out.println("Please Enter Valid Details !");
            createClient();
        }
    }

    public void updateTeam(){
        int choice;
        String option = "yes";
        displayTeam();
        Team team = getTeamForModifications();

        while(option.toLowerCase().equals("yes")){
            System.out.println();
            System.out.print("Select the Details wants to Update :\n 1) Name  2) project :");
            choice = sc.nextInt();
            sc.nextLine();
            if(choice < 1 || choice > 2){
                continue;
            }
            switch (choice){
                case 1:
                    System.out.print("Enter Name : ");
                    team.setTeamName(sc.nextLine());
                    break;
                case 2:
                    displayAllProjects();
                    System.out.print("Enter ProjectID : ");
                    Project project = new Project();
                    project.setProjectId(sc.nextInt());
                    team.setProject(project);
                    break;

            }

            System.out.print("Do you want to Continue Update Other Details of Client (type Yes or No): ");
            option = sc.next();
        }

        if(project_manager_service.updateTeam(team))
            System.out.println("Updated Successfully");
        else
            System.out.println("Updated Failed");
    }


    public void deleteTeam(){
        displayTeam();
        System.out.print("Enter the TeamID want to delete : ");
        int team_id = sc.nextInt();
        if(project_manager_service.deleteProject(team_id))
            System.out.println("Team Deleted Successfully");
        else
            System.out.println("Please Enter valid Details");

    }

    public void displayTeam(){
        List<Team> teams = project_manager_service.getAllTeamsByProjectManager(user);
        if(teams.isEmpty()){
            System.out.println("Sorry You don't have Teams");
            return;
        }

        System.out.println();
        System.out.println("All teams: ");
        System.out.println("-------------------------------------------------------------------");
        System.out.printf("| %-7s | %-20s | %-30s |\n", "Team ID", "Team Name", "Project Name");
        System.out.println("-------------------------------------------------------------------");

        for (Team team : teams) {
            System.out.printf("| %-7d | %-20s | %-30s |\n", team.getTeamId(), team.getTeamName(), team.getProject().getProjectName());
        }

        System.out.println("-------------------------------------------------------------------");
        System.out.println();
    }

    public void addMemberAndAssignTask(){
        int choice;
        String option;

        System.out.println();
        while(true){
            System.out.println();
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("1) Add Team Member      ||      2) Remove TeamMember (by Reassign Task)      ||      3) Display TeamMember     ||      4)  Go Back to Main Menu     ||");
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.print("Enter Your Option : ");
            choice = sc.nextInt();
            sc.nextLine();
            if(choice > 0 && choice < 5){
                switch (choice){
                    case 1:
                        addTeamMember();
                        break;
                    case 2:
                        removeTeamMember();
                        break;
                    case 3:
                        displayUniqueTeamMember();
                        break;
                    case 4:
                        return;
                }
            }
            else
                System.out.println("Invalid Option !!!");
        }

    }

    public void addTeamMember(){
        displayTeam();
        System.out.print("Enter the TeamID for adding TeamMember: ");
        Team team = new Team();
        team.setTeamId(sc.nextInt());

        displayAllNormalUser();
        System.out.print("Enter the UserID want to add in the Team: ");
        User team_member = new User();
        team_member.setUserId(sc.nextInt());

        getTaskForTeamDetails();
        Task task;
        while(true){
            System.out.print("Enter the TaskID To assign: ");
            task = project_manager_service.getTaskById(sc.nextInt());
            if(task!=null)
                break;
        }
        TeamDetail team_detail = new TeamDetail();
        team_detail.setTeam(team);
        team_detail.setTeamMember(team_member);
        team_detail.setTask(task);
        if(project_manager_service.addTeamMember(team_detail))
            System.out.println("Team Member Added Successfully");
        else
            System.out.println("Please Enter Valid Details");
    }

    public void removeTeamMember(){
        displayTask();
        System.out.print("Enter the taskID want to Reassign: ");
        Task task = new Task();
        task.setTaskId(sc.nextInt());

        displayTeamMember();
        System.out.print("Enter the TeamMemberID: ");
        User team_member = new User();
        team_member.setUserId(sc.nextInt());

        TeamDetail team_detail = new TeamDetail();
        team_detail.setTask(task);
        team_detail.setTeamMember(team_member);

        displayAllNormalUser();
        System.out.print("Enter the UserID want to reassign the same Task: ");
        User new_team_member = new User();
        new_team_member.setUserId(sc.nextInt());
        if(project_manager_service.reassignTask(team_detail,new_team_member))
            System.out.print("Team Member is Removed and task Reassigned");
        else{
            System.out.println("Please Enter Valid Details !");
            removeTeamMember();
        }
    }

    public void displayTeamMember(){
        displayTeam();
        System.out.print("Enter the teamID: ");
        int team_id = sc.nextInt();
        List<TeamDetail> team_members = project_manager_service.getAllTeamMember(team_id);
        if (team_members.isEmpty()) {
            System.out.println("Sorry, you don't have any team members.");
            return;
        }

        System.out.println();
        System.out.println("--------------------------------------------------------------------------------------------------");
        System.out.printf("| %-7s | %-20s | %-7s | %-20s | %-7s | %-20s |\n",
                "Team ID", "Team Name", "User ID", "User Name", "Task ID", "Task Name");
        System.out.println("--------------------------------------------------------------------------------------------------");

        for (TeamDetail teamDetail : team_members) {
            System.out.printf("| %-7d | %-20s | %-7d | %-20s | %-7d | %-20s |\n",
                    teamDetail.getTeam().getTeamId(), teamDetail.getTeam().getTeamName(),
                    teamDetail.getTeamMember().getUserId(), teamDetail.getTeamMember().getUserName(),
                    teamDetail.getTask().getTaskId(), teamDetail.getTask().getTitle());
        }

        System.out.println("--------------------------------------------------------------------------------------------------");
        System.out.println();
    }

    public void displayUniqueTeamMember(){
        displayTeam();
        System.out.print("Enter the teamID: ");
        int team_id = sc.nextInt();
        List<TeamDetail> team_members = project_manager_service.getAllUniqueTeamMember(team_id);
        if (team_members.isEmpty()) {
            System.out.println("Sorry, you don't have any team members.");
            return;
        }

        System.out.println();
        System.out.println("--------------------------------------------------------------------");
        System.out.printf("| %-7s | %-20s | %-7s | %-20s |\n",
                "Team ID", "Team Name", "User ID", "User Name");
        System.out.println("--------------------------------------------------------------------");

        for (TeamDetail teamDetail : team_members) {
            System.out.printf("| %-7d | %-20s | %-7d | %-20s |\n",
                    teamDetail.getTeam().getTeamId(), teamDetail.getTeam().getTeamName(),
                    teamDetail.getTeamMember().getUserId(), teamDetail.getTeamMember().getUserName()
                    );
        }

        System.out.println("--------------------------------------------------------------------");
        System.out.println();
    }


    public void getTaskForTeamDetails(){
        int choice;
        while(true){
            System.out.println();
            System.out.println("Assign Task: ");
            System.out.println(" 1) create New Task  2) Exisiting task");
            System.out.print("Enter Your Option: ");
            choice = sc.nextInt();
            sc.nextLine();
            if(choice > 0 && choice < 3){
                switch (choice){
                    case 1:
                        createTask();
                        return;
                    case 2:
                        displayTask();
                        return;
                }
            }
            else
                System.out.println("Invalid Option !!!");
        }

    }

//    public void manageTask(){
//        int choice;
//        String option;
//
//        System.out.println();
//        while(true){
//            System.out.println();
//            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");
//            System.out.println("1) Create Task      ||      2) Update Task      ||      3) Delete Task      ||        4) Display Task      ||      5) Go Back to Main Menu     ||");
//            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");
//            System.out.print("Enter Your Option : ");
//            choice = sc.nextInt();
//            sc.nextLine();
//            if(choice > 0 && choice < 6){
//                switch (choice){
//                    case 1:
//                        createTask();
//                        break;
//                    case 2:
//                        //updateTask();
//                        break;
//                    case 3:
//                        deleteTask();
//                        break;
//                    case 4:
//                        displayTask();
//                        break;
//                    case 5:
//                        return;
//                }
//            }
//            else
//                System.out.println("Invalid Option !!!");
//        }
//    }

    // manage task Details
    public void createTask(){
        System.out.println();
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("Task Form : ");
        System.out.println();
        Task task = new Task();
        System.out.print("Enter Task Title : ");
        task.setTitle(sc.nextLine());
        System.out.print("Enter Task Description : ");
        task.setTaskDescription(sc.nextLine());


        System.out.print("Enter Task StartDate (dd-mm-yyyy) : ");
        String start_date_input = sc.nextLine();
        LocalDate start_date = parseDate(start_date_input);
        task.setStartDate(start_date);
        System.out.print("Enter Task DueDate (dd-mm-yyyy) : ");
        String due_date_input = sc.nextLine();
        LocalDate due_date = parseDate(due_date_input);
        task.setDueDate(due_date);



        //displayAllMilestone();
        //System.out.print("Enter taskStatus(milestoneID): ");
        Milestone milestone = new Milestone();
        milestone.setMilestoneId(1);
        task.setTaskStatus(milestone);

        if(project_manager_service.createTask(task)){
            System.out.println("Task is created Successful");
            displayTask();
        }
        else{
            System.out.println("Please Enter Valid Details !");
            createTask();
        }
    }

    public void deleteTask(){
        displayTask();
        //delete task from team_details
        System.out.print("Enter the TaskID want to delete : ");
        int task_id = sc.nextInt();
        if(project_manager_service.deleteTask(task_id)){
            if(project_manager_service.deleteTaskFromTeamDetails(task_id))
                System.out.println("Task Deleted Successfully");
        }
        else
            System.out.println("Please Enter valid Details");
        System.out.println("Task Deleted Successfully");
    }

    public void displayTask(){
//        List<Task> tasks = project_manager_service.getAllTaskByManager(user);
        List<Task> tasks = project_manager_service.getAllTask();
        if (tasks.isEmpty()) {
            System.out.println("Sorry, There are no tasks.");
            return;
        }

        System.out.println();
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-8s | %-20s | %-50s | %-12s | %-12s | %-20s |\n", "Task ID", "Title", "Description", "Start Date", "Due Date", "Milestone Name");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------");

        for (Task task : tasks) {
            System.out.printf("| %-8d | %-20s | %-50s | %-12s | %-12s | %-20s |\n",
                    task.getTaskId(), task.getTitle(), task.getTaskDescription(), task.getStartDate(), task.getDueDate(), task.getTaskStatus().getMilestoneName());
        }

        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println();
    }



    public void displayMessage() {
        List<Message> messages = project_manager_service.displayMessage(user);

        if (messages.isEmpty()) {
            System.out.println("No messages to display.");
            return;
        }

        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.printf("| %-20s | %-50s | %-20s |\n", "Sender Name", "Message Description", "Message Time");
        System.out.println("----------------------------------------------------------------------------------------------------");

        for (Message message : messages) {
            System.out.printf("| %-20s | %-50s | %-20s |\n",
                    message.getSender().getUserName(),
                    message.getMessageDescription(),
                    message.getMessageTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }

        System.out.println("----------------------------------------------------------------------------------------------------");
    }


    private void displayAllMilestone(){
        List<Milestone> milestones = project_manager_service.getAllMilestone();
        if (milestones.isEmpty()) {
            System.out.println("Sorry, there are no milestones.");
            return;
        }

        System.out.println();
        System.out.println("-------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-12s | %-30s | %-50s |\n", "Milestone ID", "Milestone Name", "Milestone Description ");
        System.out.println("-------------------------------------------------------------------------------------------------------");

        for (Milestone milestone : milestones) {
            System.out.printf("| %-12d | %-30s | %-50s |\n",
                    milestone.getMilestoneId(), milestone.getMilestoneName(), milestone.getMilestoneDescription());
        }

        System.out.println("-------------------------------------------------------------------------------------------------------");
        System.out.println();
    }

    private void displayAllProjects() {
        List<Project> projects = project_manager_service.getAllProjectsByProjectManager(user);
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

    public void displayAllNormalUser(){
        List<User> users = project_manager_service.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("Sorry, there are no users.");
            return;
        }

        System.out.println();
        System.out.println("------------------------------------");
        System.out.printf("| %-9s | %-20s |\n", "User ID", "User Name");
        System.out.println("------------------------------------");

        for (User user : users) {
            System.out.printf("| %-9d | %-20s |\n", user.getUserId(), user.getUserName());
        }

        System.out.println("------------------------------------");
        System.out.println();
    }


    private Client getClientNameForModifications() {
        Client client;
        while(true){
            System.out.print("Enter the User Name for modification: ");
            client = project_manager_service.getClientByName(sc.nextLine());
            if(client!=null)
                break;
            System.out.println("Please Enter Valid User Name");
        }
        return client;
    }


    private Team getTeamForModifications() {
        Team team;
        while(true){
            System.out.print("Enter the TeamID for modification: ");
            team = project_manager_service.getTeam(sc.nextInt());
            if(team!=null)
                break;
            System.out.println("Please Enter Valid TeamID");
        }
        return team;
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


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
