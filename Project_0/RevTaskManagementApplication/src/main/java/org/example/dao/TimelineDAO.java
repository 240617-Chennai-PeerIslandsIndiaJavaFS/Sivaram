package org.example.dao;


import org.example.connection.DBConnection;
import org.example.model.Milestone;
import org.example.model.Task;
import org.example.model.Timeline;
import org.example.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

public class TimelineDAO implements BaseDAO<Timeline>{
    public  static Logger logger = LoggerFactory.getLogger(TimelineDAO.class);
    Connection conn;
    TaskDAO task_dao;
    MilestoneDAO milestone_dao;

    public TimelineDAO() {
        conn = DBConnection.getInstance().getDBConnection();
        task_dao = new TaskDAO();
        milestone_dao = new MilestoneDAO();
    }

    @Override
    public boolean create(Timeline timeline) {
        String query = "insert into timelines(taskId,milestoneId,taskTimestamp) value(?,?,?)";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,timeline.getTask().getTaskId());
            pst.setInt(2,timeline.getMilestone().getMilestoneId());
            pst.setTimestamp(3,Timestamp.valueOf(timeline.getTaskTimestamp()));
            int rows = pst.executeUpdate();
            logger.info("Timeline is created Successfully");
            return  rows > 0;

        } catch (SQLException e) {
            logger.error("Error while Creating a Timeline: {}", e.getMessage());
            System.out.println("Error while Creating a Timeline: "+e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Timeline timeline) {
        String query = "update timelines set taskId=?,milestoneId=?,taskTimestamp=? where timelineId=?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,timeline.getTask().getTaskId());
            pst.setInt(2,timeline.getMilestone().getMilestoneId());
//            pst.setTimestamp(3,new java.sql.Timestamp(timeline.getTaskTimestamp()));
            pst.setTimestamp(3,Timestamp.valueOf(timeline.getTaskTimestamp()));
            pst.setInt(4,timeline.getTimelineId());
            int rows = pst.executeUpdate();
            logger.info("Timeline is Updated Successfully");
            return  rows > 0;

        } catch (SQLException e) {
            logger.error("Error while Updating a Timeline: {}", e.getMessage());
            System.out.println("Error while Updating a Timeline: "+e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String query= "delete from timelines where timelineId=?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,id);
            int rows = pst.executeUpdate();
            logger.info("Timeline is Deleted Successfully");
            return rows > 0;
        } catch (SQLException e) {
            logger.error("Error while Deleting a Timeline: {}", e.getMessage());
            System.out.println("Error while Deleting a Timeline: "+e.getMessage());
        }
        return false;
    }

    @Override
    public Timeline get(int id) {
        String query = "select * from timelines where timelineId=?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();
            if(rs.next())
                return createTimelineFromResultSet(rs);
        } catch (SQLException e) {
            System.out.println("Error while Retrieving a Timeline:"+e.getMessage());
        }
        return null;
    }


    @Override
    public List<Timeline> getAll() {
        List<Timeline> timelines = new ArrayList<>();
        String query = "select * from timelines";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                Timeline timeline = createTimelineFromResultSet(rs);
                timelines.add(timeline);
            }
        }
        catch (SQLException e) {
            System.out.println("Error while Retrieving List of Timelines: "+e.getMessage());
        }
        return timelines;
    }

    public boolean updateTask(User user,Task task){
        String query = "select * from  team_details as td inner join tasks as tsk on td.taskId = tsk.taskId inner join milestones as m on tsk.taskStatus = m. milestoneId inner join timelines as tl on tsk.taskId = tl.taskId where  td.teamMemberId = ? and td.taskId = ?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,user.getUserId());
            pst.setInt(2,task.getTaskId());
            ResultSet rs = pst.executeQuery();
            boolean updated = updateTaskAndCreateTimeline(rs,task.getTaskId());
            return updated;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private boolean updateTaskAndCreateTimeline(ResultSet rs,int task_id) throws SQLException {
//        boolean update_success = true;
        if(rs.next()){
        Task task = task_dao.get(task_id);
        int taskStatus = task.getTaskStatus().getMilestoneId();
        taskStatus+=1;
        if( taskStatus > 5 )
            taskStatus = 5;
        Milestone milestone = new Milestone();
        milestone.setMilestoneId(taskStatus);
        task.setTaskStatus(milestone);
        boolean update_task = task_dao.update(task);
        Timeline timeline = get(rs.getInt("timelineId"));
        timeline.setMilestone(milestone);
        timeline.setTaskTimestamp(LocalDateTime.now());
        boolean create_timeline = create(timeline);
        if(update_task && create_timeline)
            return true;

//            if (!update_task || !create_timeline) {
//                update_success = false;
//                break;
//            }
        }
        //System.out.println(rs.getInt("td.taskId"));
        return false;
    }

    private Timeline createTimelineFromResultSet(ResultSet rs) throws SQLException {
        Timeline timeline = new Timeline();
        timeline.setTimelineId(rs.getInt("timelineId"));
        Task task = task_dao.get(rs.getInt("taskId"));
        timeline.setTask(task);
        Milestone milestone = milestone_dao.get(rs.getInt("milestoneId"));
        timeline.setMilestone(milestone);
        timeline.setTaskTimestamp(rs.getTimestamp("taskTimestamp").toLocalDateTime());
        return timeline;
    }
}
