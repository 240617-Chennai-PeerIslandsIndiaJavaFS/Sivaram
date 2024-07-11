package org.example.service;

import org.example.dao.*;
import org.example.model.Message;
import org.example.model.Task;
import org.example.model.TeamDetail;
import org.example.model.User;

import java.util.List;

public class TeamMemberService {
    UserDAO user;
    TeamDetailDAO team_details_dao;
    TimelineDAO timeline_dao;
    MessageDAO message_dao;

    public TeamMemberService(UserDAO user, TeamDetailDAO team_details_dao, TimelineDAO timeline_dao, MessageDAO message_dao) {
        this.user = user;
        this.team_details_dao = team_details_dao;
        this.timeline_dao = timeline_dao;
        this.message_dao = message_dao;
    }

    public TeamMemberService(UserDAO user) {
        this.user = user;
        team_details_dao= new TeamDetailDAO();
        timeline_dao = new TimelineDAO();
        message_dao = new MessageDAO();
    }

    public List<TeamDetail> getProjectDetails(User user){
        return team_details_dao.getProjectDetailsByUserId(user);
    }

    public boolean updateTask(User user, Task task){
        return timeline_dao.updateTask(user,task);
    }

    public List<Integer> getProjectManagerAndTeam(User user,int project_id){
        return team_details_dao.getProjectManagerAndTeam(user,project_id);
    }

    public boolean updateProgress(Message message){
        return message_dao.create(message);
    }

}
