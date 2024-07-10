package org.example.dao;

import org.example.connection.DBConnection;
import org.example.model.Milestone;
import org.example.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskDAO implements BaseDAO<Task>{
    public  static Logger logger = LoggerFactory.getLogger(TaskDAO.class);
    Connection conn;
    MilestoneDAO milestone_dao;

    public TaskDAO() {
        conn = DBConnection.getInstance().getDBConnection();
        milestone_dao = new MilestoneDAO();
    }

    @Override
    public boolean create(Task task) {
        String query = "insert into tasks(title,taskDescription,startDate,dueDate,taskStatus) value(?,?,?,?,?)";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1,task.getTitle());
            pst.setString(2,task.getTaskDescription());
            pst.setDate(3,convertLocalDateToSqlDate(task.getStartDate()));
            pst.setDate(4,convertLocalDateToSqlDate(task.getDueDate()));
            pst.setInt(5,task.getTaskStatus().getMilestoneId());

            int rows = pst.executeUpdate();
            logger.info("{} is created Successfully",task.getTitle());
            return  rows > 0;

        } catch (SQLException e) {
            logger.error("Error while Creating a Task: {}", e.getMessage());
            System.out.println("Error while Creating a Task: "+e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Task task) {
        String query = "update tasks set title=?,taskDescription=?,startDate=?,dueDate=?,taskStatus=? where taskId=?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1,task.getTitle());
            pst.setString(2,task.getTaskDescription());
            pst.setDate(3,convertLocalDateToSqlDate(task.getStartDate()));
            pst.setDate(4,convertLocalDateToSqlDate(task.getDueDate()));
            pst.setInt(5,task.getTaskStatus().getMilestoneId());
            pst.setInt(6,task.getTaskId());
            int rows = pst.executeUpdate();
            logger.info("{} is Updated Successfully",task.getTitle());
            return  rows > 0;

        } catch (SQLException e) {
            logger.error("Error while Updating a Task: {}", e.getMessage());
            System.out.println("Error while Updating a Task: "+e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String query= "delete from tasks where TaskId=?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,id);
            int rows = pst.executeUpdate();
            logger.info("{} is Deleted Successfully",get(id).getTitle());
            return rows > 0;
        } catch (SQLException e) {
            logger.error("Error while Deleting a Task: {}", e.getMessage());
            System.out.println("Error while Deleting a Task: "+e.getMessage());
        }
        return false;
    }

    @Override
    public Task get(int id) {
        String query = "select * from tasks where taskId=?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();
            if(rs.next())
                return createTaskFromResultSet(rs);
        } catch (SQLException e) {
            System.out.println("Error while Retrieving a Task:"+e.getMessage());
        }
        return null;
    }


    @Override
    public List<Task> getAll() {
        List<Task> tasks = new ArrayList<>();
        String query = "select * from tasks";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                Task task = createTaskFromResultSet(rs);
                tasks.add(task);
            }
        }
        catch (SQLException e) {
            System.out.println("Error while Retrieving List of Tasks: "+e.getMessage());
        }
        return tasks;
    }

    private Task createTaskFromResultSet(ResultSet rs) throws SQLException {
        Task task = new Task();
        task.setTaskId(rs.getInt("taskId"));
        task.setTitle(rs.getString("title"));
        task.setTaskDescription(rs.getString("taskDescription"));
        task.setStartDate(rs.getDate("startDate").toLocalDate());
        task.setDueDate(rs.getDate("dueDate").toLocalDate());
        Milestone milestone = milestone_dao.get(rs.getInt("taskStatus"));
        task.setTaskStatus(milestone);
        return task;
    }

    private java.sql.Date convertLocalDateToSqlDate(LocalDate date){
        return  java.sql.Date.valueOf(date);
    }
}
