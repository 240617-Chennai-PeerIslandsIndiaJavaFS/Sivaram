package org.example.dao;


import org.example.connection.DBConnection;
import org.example.model.Project;
import org.example.model.Team;
import org.example.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamDAO implements BaseDAO<Team>{
    public  static Logger logger = LoggerFactory.getLogger(TeamDAO.class);
    Connection conn;
    UserDAO user_dao;
    ProjectDAO project_dao;

    public TeamDAO() {
        conn = DBConnection.getInstance().getDBConnection();
        user_dao = new UserDAO();
        project_dao = new ProjectDAO();
    }


    @Override
    public boolean create(Team team) {
        String query = "insert into teams(teamName,projectManagerId,projectId) value(?,?,?)";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1,team.getTeamName());
            pst.setInt(2,team.getProjectManager().getUserId());
            pst.setInt(3,team.getProject().getProjectId());

            int rows = pst.executeUpdate();
            logger.info("{} is created Successfully",team.getTeamName());
            return  rows > 0;

        } catch (SQLException e) {
            logger.error("Error while Creating a Team: {}", e.getMessage());
            System.out.println("Error while Creating a Team: "+e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Team team) {
        String query = "update teams set teamName=?,projectManagerId=?,projectId=? where teamId=?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1,team.getTeamName());
            pst.setInt(2,team.getProjectManager().getUserId());
            pst.setInt(3,team.getProject().getProjectId());
            pst.setInt(4,team.getTeamId());

            int rows = pst.executeUpdate();
            logger.info("{} is Updated Successfully",team.getTeamName());
            return  rows > 0;

        } catch (SQLException e) {
            logger.error("Error while Updating a Team: {}", e.getMessage());
            System.out.println("Error while Updating a Team: "+e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String query= "delete from teams where teamId=?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,id);
            int rows = pst.executeUpdate();
            logger.info("{} is Deleted Successfully",get(id).getTeamName());
            return rows > 0;
        } catch (SQLException e) {
            logger.error("Error while Deleting a Team: {}", e.getMessage());
            System.out.println("Error while Deleting a Team: "+e.getMessage());
        }
        return false;
    }

    @Override
    public Team get(int id) {
        String query = "select * from teams where teamId=?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();
            if(rs.next())
                return createTeamFromResultSet(rs);
        } catch (SQLException e) {
            System.out.println("Error while Retrieving a Team:"+e.getMessage());
        }
        return null;
    }


    @Override
    public List<Team> getAll() {
        List<Team> teams = new ArrayList<>();
        String query = "select * from teams";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                Team team = createTeamFromResultSet(rs);
                teams.add(team);
            }
        }
        catch (SQLException e) {
            System.out.println("Error while Retrieving List of Teams: "+e.getMessage());
        }
        return teams;
    }


    public List<Team> getAllTeamsByProjectManager(User project_manager) {
        List<Team> teams = new ArrayList<>();
        String query = "select * from teams where projectManagerId = ?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,project_manager.getUserId());
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Team team = createTeamFromResultSet(rs);
                teams.add(team);
            }
        }
        catch (SQLException e) {
            System.out.println("Error while Retrieving List of Teams: "+e.getMessage());
        }
        return teams;
    }

    private Team createTeamFromResultSet(ResultSet rs) throws SQLException {
        Team team = new Team();
        team.setTeamId(rs.getInt("teamId"));
        team.setTeamName(rs.getString("teamName"));
        User user = user_dao.get(rs.getInt("projectManagerId"));
        team.setProjectManager(user);
        Project project = project_dao.get(rs.getInt("projectId"));
        team.setProject(project);
        return team;
    }
}

