package org.example.dao;

import org.example.connection.DBConnection;
import org.example.model.Client;
import org.example.model.Project;
import org.example.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO implements BaseDAO<Project>{
    public  static Logger logger = LoggerFactory.getLogger(ProjectDAO.class);
    Connection conn;
    UserDAO user;
    ClientDAO client_dao;

    public ProjectDAO() {
        conn = DBConnection.getInstance().getDBConnection();
        user=new UserDAO();
        client_dao = new ClientDAO();
    }

    @Override
    public boolean create(Project project) {
        String query = "insert into projects(projectName,projectDescription,startDate,dueDate,clientId,projectManagerId) value(?,?,?,?,?,?)";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1,project.getProjectName());
            pst.setString(2,project.getProjectDescription());
            pst.setDate(3,convertLocalDateToSqlDate(project.getStartDate()));
            pst.setDate(4,convertLocalDateToSqlDate(project.getDueDate()));
            pst.setInt(5,project.getClient().getClientId());
            pst.setInt(6,project.getProjectManager().getUserId());

            int rows = pst.executeUpdate();
            logger.info("{} is created Successfully",project.getProjectName());
            return  rows > 0;

        } catch (SQLException e) {
            logger.error("Error while Creating a Project: {}", e.getMessage());
            System.out.println("Error while Creating a Project: "+e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Project project) {
        String query = "update projects set projectName=?,projectDescription=?,startDate=?,dueDate=?,clientId=?,projectManagerId=? where projectId=?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1,project.getProjectName());
            pst.setString(2,project.getProjectDescription());
            pst.setDate(3,convertLocalDateToSqlDate(project.getStartDate()));
            pst.setDate(4,convertLocalDateToSqlDate(project.getDueDate()));
            pst.setInt(5,project.getClient().getClientId());
            pst.setInt(6,project.getProjectManager().getUserId());
            pst.setInt(7,project.getProjectId());
            int rows = pst.executeUpdate();
            logger.info("{} is Updated Successfully",project.getProjectName());
            return  rows > 0;

        } catch (SQLException e) {
            logger.error("Error while Updating a Project: {}", e.getMessage());
            System.out.println("Error while Updating a Project: "+e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String query= "delete from projects where projectId=?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,id);
            int rows = pst.executeUpdate();
            logger.info("{} is Deleted Successfully",get(id).getProjectName());
            return rows > 0;
        } catch (SQLException e) {
            logger.error("Error while Deleting a Project: {}", e.getMessage());
            System.out.println("Error while Deleting a Project: "+e.getMessage());
        }
        return false;
    }

    @Override
    public Project get(int id) {
        String query = "select * from projects where projectId=?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();
            if(rs.next())
                return createProjectFromResultSet(rs);
        } catch (SQLException e) {
            System.out.println("Error while Retrieving a Project:"+e.getMessage());
        }
        return null;
    }


    @Override
    public List<Project> getAll() {
        List<Project> projects = new ArrayList<>();
        String query = "select * from projects";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                Project project = createProjectFromResultSet(rs);
                projects.add(project);
            }
        }
        catch (SQLException e) {
            System.out.println("Error while Retrieving List of Projects: "+e.getMessage());
        }
        return projects;
    }




    public List<Client> getAllClientsByManager(User user){
        List<Client> clients = new ArrayList<>();
        String query;
        PreparedStatement pst;
        try {
            if(user.getUserRole().equals("Admin")){
                 query = "select * from clients";
                 pst = conn.prepareStatement(query);
            }
            else {
                query = "select * from clients as c inner join projects as p on c.clientId = p.clientId where p.projectManagerId = ?";
                pst = conn.prepareStatement(query);
                pst.setInt(1,user.getUserId());
            }
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Client client = client_dao.createClientFromResultSet(rs);
                clients.add(client);
            }
        }
        catch (SQLException e) {
            System.out.println("Error while Retrieving List of Client: "+e.getMessage());
        }
        return clients;
    }


    public List<Project> getAllProjectsByProjectManager(User project_manager) {
        List<Project> projects = new ArrayList<>();
        String query = "select * from projects where projectManagerId = ?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,project_manager.getUserId());
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Project project = createProjectFromResultSet(rs);
                projects.add(project);
            }
        }
        catch (SQLException e) {
            System.out.println("Error while Retrieving List of Projects: "+e.getMessage());
        }
        return projects;
    }

    private Project createProjectFromResultSet(ResultSet rs) throws SQLException {
        Project project = new Project();
        project.setProjectId(rs.getInt("projectId"));
        project.setProjectName(rs.getString("projectName"));
        project.setProjectDescription(rs.getString("projectDescription"));
        project.setStartDate(rs.getDate("startDate").toLocalDate());
        project.setDueDate(rs.getDate("dueDate").toLocalDate());
        Client client = client_dao.get(rs.getInt("clientId"));
        project.setClient(client);
        User project_manager = user.get(rs.getInt("projectManagerId"));
        project.setProjectManager(project_manager);
        return project;
    }



    private java.sql.Date convertLocalDateToSqlDate(LocalDate date){
        return  java.sql.Date.valueOf(date);
    }
}
