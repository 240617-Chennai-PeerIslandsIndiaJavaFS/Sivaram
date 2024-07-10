package org.example.dao;

import org.example.connection.DBConnection;
import org.example.model.Message;
import org.example.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO implements BaseDAO<Message>{
    public  static Logger logger = LoggerFactory.getLogger(MessageDAO.class);
    Connection conn;
    UserDAO user_dao;

    public MessageDAO() {
        conn = DBConnection.getInstance().getDBConnection();
        user_dao = new UserDAO();
    }

    @Override
    public boolean create(Message message) {
        String query = "insert into messages(senderId,receiverId,messageDescription,messageTime) value(?,?,?,?)";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,message.getSender().getUserId());
            pst.setInt(2,message.getReceiver().getUserId());
            pst.setString(3,message.getMessageDescription());
            pst.setTimestamp(4,Timestamp.valueOf(message.getMessageTime()));
            int rows = pst.executeUpdate();
            logger.info("Message is send Successfully");
            return  rows > 0;

        } catch (SQLException e) {
            logger.error("Error while Creating a Message: {}", e.getMessage());
            System.out.println("Error while Creating a Message: "+e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Message message) {
        String query = "update messages set senderId=?,receiverId=?,messageDescription=?,messageTime=? where messageId=?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,message.getSender().getUserId());
            pst.setInt(2,message.getReceiver().getUserId());
            pst.setString(3,message.getMessageDescription());
            pst.setTimestamp(4,Timestamp.valueOf(message.getMessageTime()));
            pst.setInt(5,message.getMessageId());
            int rows = pst.executeUpdate();
            logger.info("Message is Updated Successfully");
            return  rows > 0;

        } catch (SQLException e) {
            logger.error("Error while Updating a Message: {}", e.getMessage());
            System.out.println("Error while Updating a Message: "+e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String query= "delete from messages where messageId=?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,id);
            int rows = pst.executeUpdate();
            logger.info("Message is Deleted Successfully");
            return rows > 0;
        } catch (SQLException e) {
            logger.error("Error while Deleting a Message: {}", e.getMessage());
            System.out.println("Error while Deleting a Message: "+e.getMessage());
        }
        return false;
    }

    @Override
    public Message get(int id) {
        String query = "select * from messages where messageId=?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();
            if(rs.next())
                return createMessageFromResultSet(rs);
        } catch (SQLException e) {
            System.out.println("Error while Retrieving a Message:"+e.getMessage());
        }
        return null;
    }


    @Override
    public List<Message> getAll() {
        List<Message> messages = new ArrayList<>();
        String query = "select * from messages";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                Message message = createMessageFromResultSet(rs);
                messages.add(message);
            }
        }
        catch (SQLException e) {
            System.out.println("Error while Retrieving List of Messages: "+e.getMessage());
        }
        return messages;
    }


    public List<Message> displayMessage(User project_manager) {
        List<Message> messages = new ArrayList<>();
        String query = "select * from messages where receiverId = "+project_manager.getUserId();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                Message message = createMessageFromResultSet(rs);
                messages.add(message);
            }
        }
        catch (SQLException e) {
            System.out.println("Error while Retrieving List of Messages: "+e.getMessage());
        }
        return messages;
    }

    private Message createMessageFromResultSet(ResultSet rs) throws SQLException {
        Message message = new Message();
        message.setMessageId(rs.getInt("messageId"));
        User sender_user = user_dao.get(rs.getInt("senderId"));
        message.setSender(sender_user);
        User receiver_user = user_dao.get(rs.getInt("receiverId"));
        message.setReceiver(receiver_user);
        message.setMessageDescription(rs.getString("messageDescription"));
        message.setMessageTime(rs.getTimestamp("messageTime").toLocalDateTime());
        return message;
    }
}

