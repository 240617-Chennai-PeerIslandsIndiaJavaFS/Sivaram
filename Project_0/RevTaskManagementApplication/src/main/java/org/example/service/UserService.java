package org.example.service;

import org.example.dao.UserDAO;
import org.example.model.User;

public class UserService {
    UserDAO user_dao;
    AdminService admin_service;
    ProjectManagerService project_manager_service;
    TeamMemberService team_member_service;


    public UserService(UserDAO user_dao) {
        this.user_dao = user_dao;
    }

    public UserService() {
        user_dao = new UserDAO();
        admin_service = new AdminService(user_dao);
        project_manager_service = new ProjectManagerService(user_dao);
        team_member_service = new TeamMemberService(user_dao);
        //admin_service.setUserDao();
    }


    public User loginUser(String email,String password){
        User user = user_dao.getByEmail(email);
        if(user != null)
            if(user.getUserPassword().equals(password))
                return user;
        return null;
    }



    public AdminService getAdminService() {
        return admin_service;
    }

    public ProjectManagerService getProjectManagerService() {
        return project_manager_service;
    }

    public TeamMemberService getTeamMemberService() {
        return team_member_service;
    }

}