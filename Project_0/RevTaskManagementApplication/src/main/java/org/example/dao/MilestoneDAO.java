package org.example.dao;

import org.example.connection.DBConnection;
import org.example.model.Milestone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MilestoneDAO implements BaseDAO<Milestone>{
    public  static Logger logger = LoggerFactory.getLogger(MilestoneDAO.class);

    Connection conn;

    public MilestoneDAO() {
        conn = DBConnection.getInstance().getDBConnection();
    }

    @Override
    public boolean create(Milestone milestone) {
        String query = "insert into milestones(milestoneName,milestoneDescription) value(?,?)";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1,milestone.getMilestoneName());
            pst.setString(2,milestone.getMilestoneDescription());
            int rows = pst.executeUpdate();
            logger.info("{} is created Successfully",milestone.getMilestoneName());
            return  rows > 0;

        } catch (SQLException e) {
            logger.error("Error while Creating a Milestone: {}", e.getMessage());
            System.out.println("Error while Creating a Milestone: "+e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Milestone milestone) {
        String query = "update milestones set milestoneName=?,milestoneDescription=? where milestoneId=?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1,milestone.getMilestoneName());
            pst.setString(2,milestone.getMilestoneDescription());
            pst.setInt(3,milestone.getMilestoneId());
            int rows = pst.executeUpdate();
            logger.info("{} is Updated Successfully",milestone.getMilestoneName());
            return  rows > 0;

        } catch (SQLException e) {
            logger.error("Error while Updating a Milestone: {}", e.getMessage());
            System.out.println("Error while Updating a Milestone: "+e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String query= "delete from milestones where milestoneId=?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,id);
            int rows = pst.executeUpdate();
            logger.info("{} is Deleted Successfully",get(id).getMilestoneName());
            return rows > 0;
        } catch (SQLException e) {
            logger.error("Error while Deleting a Milestone: {}", e.getMessage());
            System.out.println("Error while Deleting a Milestone: "+e.getMessage());
        }
        return false;
    }

    @Override
    public Milestone get(int id) {
        String query = "select * from milestones where milestoneId=?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();
            if(rs.next())
                return createMilestoneFromResultSet(rs);
        } catch (SQLException e) {
            System.out.println("Error while Retrieving a Milestone:"+e.getMessage());
        }
        return null;
    }


    @Override
    public List<Milestone> getAll() {
        List<Milestone> milestones = new ArrayList<>();
        String query = "select * from milestones";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                Milestone milestone = createMilestoneFromResultSet(rs);
                milestones.add(milestone);
            }
        }
        catch (SQLException e) {
            System.out.println("Error while Retrieving List of Milestones: "+e.getMessage());
        }
        return milestones;
    }

    private Milestone createMilestoneFromResultSet(ResultSet rs) throws SQLException {
        Milestone milestone = new Milestone();
        milestone.setMilestoneId(rs.getInt("milestoneId"));
        milestone.setMilestoneName(rs.getString("milestoneName"));
        milestone.setMilestoneDescription(rs.getString("milestoneDescription"));
        return  milestone;
    }
}

