package org.example.dao;


import org.example.connection.DBConnection;
import org.example.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements BaseDAO<User>{
    public  static Logger logger = LoggerFactory.getLogger(UserDAO.class);
    private Connection conn;

    public UserDAO() {
        conn = DBConnection.getInstance().getDBConnection();
    }


    @Override
    public boolean create(User user) {
        String query = "insert into users(userName,email,userPassword,userRole,phone) value(?,?,?,?,?)";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1,user.getUserName());
            pst.setString(2,user.getEmail());
            pst.setString(3,user.getUserPassword());
            pst.setString(4,user.getUserRole());
            pst.setString(5,user.getPhone());

            int rows = pst.executeUpdate();
            logger.info("{} is Registered Successfully",user.getUserName());
            return  rows > 0;

        } catch (SQLException e) {
            logger.error("Error while Creating a User: {}", e.getMessage());
            System.out.println("Error while Creating a User: "+e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(User user) {
        String query = "update users set userName=?,email=?,userPassword=?,userRole=?,accountStatus=?,phone=? where userId=?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1,user.getUserName());
            pst.setString(2,user.getEmail());
            pst.setString(3,user.getUserPassword());
            pst.setString(4,user.getUserRole());
            pst.setString(5, user.getAccountStatus());
            pst.setString(6,user.getPhone());
            pst.setInt(7,user.getUserId());
            int rows = pst.executeUpdate();
            logger.info("{} is Updated Successfully",user.getUserName());
            return  rows > 0;

        } catch (SQLException e) {
            logger.error("Error while Updating a User: {}", e.getMessage());
            System.out.println("Error while Updating a User: "+e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String query= "delete from users where userId=?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,id);
            int rows = pst.executeUpdate();
            logger.info("{} is Deleted Successfully",get(id).getUserName());
            return rows > 0;
        } catch (SQLException e) {
            logger.error("Error while Deleting a User: {}", e.getMessage());
            System.out.println("Error while Deleting a User: "+e.getMessage());
        }
        return false;
    }

    @Override
    public User get(int id) {
        String query = "select * from users where userId=?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();
            if(rs.next())
                return createUserFromResultSet(rs);
        } catch (SQLException e) {
            System.out.println("Error while Retrieving a User:"+e.getMessage());
        }
        return null;
    }


    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String query = "select * from users";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                User user = createUserFromResultSet(rs);
                users.add(user);
            }
        }
        catch (SQLException e) {
            System.out.println("Error while Retrieving List of Users: "+e.getMessage());
        }
        return users;
    }

    public List<User> getAllNormalUser() {
        List<User> users = new ArrayList<>();
        String query = "select * from users";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                if(rs.getString("userRole").equals("User")){
                    User user = createUserFromResultSet(rs);
                    users.add(user);
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Error while Retrieving List of Users: "+e.getMessage());
        }
        return users;
    }

    private User createUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("userId"));
        user.setUserName(rs.getString("userName"));
        user.setEmail(rs.getString("email"));
        user.setUserPassword(rs.getString("userPassword"));
        user.setUserRole(rs.getString("userRole"));
        //System.out.println(rs.getString("accountStatus"));
        user.setAccountStatus(rs.getString("accountStatus"));
        user.setPhone(rs.getString("phone"));
        return user;
    }

    public User getByEmail(String email){
        String query = "select * from users where email = ? ";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1,email);
            ResultSet rs = pst.executeQuery();
            if(rs.next())
                return createUserFromResultSet(rs);
        } catch (SQLException e) {
            System.out.println("Error while Retrieving a User by Email: "+e.getMessage());
        }
        return null;
    }

    public User getByName(String name){
        String query = "select * from users where userName = ? ";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1,name);
            ResultSet rs = pst.executeQuery();
            if(rs.next())
                return createUserFromResultSet(rs);
        } catch (SQLException e) {
            System.out.println("Error while Retrieving a User by Name: "+e.getMessage());
        }
        return null;
    }
}
