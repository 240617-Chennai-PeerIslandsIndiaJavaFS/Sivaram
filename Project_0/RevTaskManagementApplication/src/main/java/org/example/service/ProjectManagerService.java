package org.example.service;

import org.example.dao.*;
import org.example.model.*;
import java.util.List;


public class ProjectManagerService {
    UserDAO user_dao;
    ClientDAO client_dao;
    ProjectDAO project_dao;
    TeamDAO team_dao;
    TeamDetailDAO team_details_dao;
    TaskDAO task_dao;
    MilestoneDAO milestone_dao;
    MessageDAO message_dao;

    public ProjectManagerService(UserDAO user_dao, ClientDAO client_dao, ProjectDAO project_dao, TeamDAO team_dao, TeamDetailDAO team_details_dao, TaskDAO task_dao, MilestoneDAO milestone_dao, MessageDAO message_dao) {
        this.user_dao = user_dao;
        this.client_dao = client_dao;
        this.project_dao = project_dao;
        this.team_dao = team_dao;
        this.team_details_dao = team_details_dao;
        this.task_dao = task_dao;
        this.milestone_dao = milestone_dao;
        this.message_dao = message_dao;
    }

    public ProjectManagerService(UserDAO user_dao) {
        this.user_dao = user_dao;
        client_dao = new ClientDAO();
        project_dao = new ProjectDAO();
        team_dao = new TeamDAO();
        team_details_dao = new TeamDetailDAO();
        task_dao = new TaskDAO();
        milestone_dao = new MilestoneDAO();
        message_dao = new MessageDAO();
    }

    public boolean passwordReset(User user){
        return user_dao.update(user);
    }

    // manage clients
    public boolean createClient(Client client){
        return client_dao.create(client);
    }

    public Client getClientByName(String clientName){
        return client_dao.getClientByName(clientName);
    }

    public boolean updateClient(Client client){
        return client_dao.update(client);
    }

    public boolean deleteClient(Client client){
        return client_dao.delete(client.getClientId());
    }

    public List<Client> getAllClients(User user){
        return project_dao.getAllClientsByManager(user);
    }

    //manage Team
    public List<Project> getAllProjectsByProjectManager(User project_manager){
        return project_dao.getAllProjectsByProjectManager(project_manager);
    }

    public boolean createTeam(Team team){
        return team_dao.create(team);
    }

    public Team getTeam(int team_id){
        return team_dao.get(team_id);
    }

    public List<Team> getAllTeamsByProjectManager(User user){
        return team_dao.getAllTeamsByProjectManager(user);
    }

    public boolean updateTeam(Team team){
        return team_dao.update(team);
    }

    public boolean deleteProject(int team_id){
        return team_dao.delete(team_id);
    }

    // manage task
    public List<Milestone> getAllMilestone(){
        return milestone_dao.getAll();
    }

    public boolean createTask(Task task){
        return task_dao.create(task);
    }

    public List<Task> getAllTaskByManager(User user){
        return team_details_dao.getAllTaskByProjectManager(user);
    }

    public List<Task> getAllTask(){
        return task_dao.getAll();
    }

    public boolean deleteTask(int task_id){
        return task_dao.delete(task_id);
    }

    public boolean deleteTaskFromTeamDetails(int task_id){
        return team_details_dao.deleteByTaskID(task_id);
    }

    public List<TeamDetail> getAllTeamMember(int team_id){
        return team_details_dao.getAllByTeamMemberById(team_id);
    }

    public List<TeamDetail> getAllUniqueTeamMember(int team_id){
        return team_details_dao.getAllUniqueTeamMember(team_id);
    }

    public List<User> getAllUsers(){
        return user_dao.getAllNormalUser();
    }

    public boolean addTeamMember(TeamDetail team_detail){
        return team_details_dao.create(team_detail);
    }

    public Task getTaskById(int task_id){
        return task_dao.get(task_id);
    }

    public boolean reassignTask(TeamDetail team_detail, User new_team_member){
        return team_details_dao.reassignTask(team_detail,new_team_member);
    }

    // display Messages
    public List<Message> displayMessage(User project_manager){
        return message_dao.displayMessage(project_manager);
    }
}
