package org.example.service;

import org.example.dao.ProjectDAO;
import org.example.dao.TeamDetailDAO;
import org.example.dao.UserDAO;
import org.example.model.Project;
import org.example.model.ReportDetail;
import org.example.model.TeamDetail;
import org.example.model.User;

import java.util.List;

public class AdminService {
    UserDAO user_dao;
    ProjectDAO project_dao;
    TeamDetailDAO team_detail_dao;

    public AdminService(UserDAO user_dao,ProjectDAO pdao,TeamDetailDAO tdao){
        this.user_dao=user_dao;
        this.project_dao=pdao;
        this.team_detail_dao=tdao;
    }

    public AdminService(UserDAO user_dao) {
        this.user_dao = user_dao;
        project_dao = new ProjectDAO();
        team_detail_dao = new TeamDetailDAO();
    }

    public boolean registerUser(User user){
        return user_dao.create(user);
    }

    public User getUserByName(String name){
        return user_dao.getByName(name);
    }

    public boolean updateUser(User user){
        return user_dao.update(user);
    }

    public List<User> getAllUser(){
        return user_dao.getAll();
    }

    //manage project
    public boolean createProject(Project project){
        return project_dao.create(project);
    }

    public Project getProject(int project_id){
        return project_dao.get(project_id);
    }

    public List<Project> getAllProjects(){
        return project_dao.getAll();
    }

    public boolean updateProject(Project project){
        return project_dao.update(project);
    }

    public boolean deleteProject(int project_id){
        return project_dao.delete(project_id);
    }

    // reports
    public List<ReportDetail> getReports(){
        return team_detail_dao.getReports();
    }
}
