package org.example.dao;


import org.example.Main;
import org.example.connection.DBConnection;
import org.example.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO implements BaseDAO<Client> {
    Connection conn;
    public  static Logger logger = LoggerFactory.getLogger(ClientDAO.class);

    public ClientDAO() {
        conn = DBConnection.getInstance().getDBConnection();
    }

    @Override
    public boolean create(Client client) {
        String query = "insert into clients(clientName,clientEmail,clientDescription,address,city,phone) value(?,?,?,?,?,?)";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1,client.getClientName());
            pst.setString(2,client.getClientEmail());
            pst.setString(3,client.getClientDescription());
            pst.setString(4,client.getAddress());
            pst.setString(5,client.getCity());
            pst.setString(6,client.getPhone());

            int rows = pst.executeUpdate();
            logger.info("{} is created Successfully", client.getClientName());
            return  rows > 0;

        } catch (SQLException e) {
            logger.error("Error while Creating a Client: {}", e.getMessage());
            System.out.println("Error while Creating a Client: "+e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Client client) {
        String query = "update clients set clientName=?,clientEmail=?,clientDescription=?,address=?,city=?,phone=? where clientId=?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1,client.getClientName());
            pst.setString(2,client.getClientEmail());
            pst.setString(3,client.getClientDescription());
            pst.setString(4,client.getAddress());
            pst.setString(5,client.getCity());
            pst.setString(6,client.getPhone());
            pst.setInt(7,client.getClientId());

            int rows = pst.executeUpdate();
            logger.info("{} is Updated Successfully", client.getClientName());
            return  rows > 0;

        } catch (SQLException e) {
            logger.error("Error while Updating a Client: {}", e.getMessage());
            System.out.println("Error while Updating a Client: "+e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String query= "delete from clients where clientId=?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,id);
            int rows = pst.executeUpdate();
            logger.info("{} is Deleted Successfully",get(id).getClientName());
            return rows > 0;
        } catch (SQLException e) {
            logger.error("Error while Deleting a Client: {}", e.getMessage());
            System.out.println("Error while Deleting a Client: "+e.getMessage());
        }
        return false;
    }

    @Override
    public Client get(int id) {
        String query = "select * from clients where clientId=?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();
            if(rs.next())
                return createClientFromResultSet(rs);
        } catch (SQLException e) {
            System.out.println("Error while Retrieving a client:"+e.getMessage());
        }
        return null;
    }


    @Override
    public List<Client> getAll() {
        List<Client> clients = new ArrayList<>();
        String query = "select * from clients";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                Client client = createClientFromResultSet(rs);
                clients.add(client);
            }
        }
        catch (SQLException e) {
            System.out.println("Error while Retrieving List of clients: "+e.getMessage());
        }
        return clients;
    }

    public Client getClientByName(String clientName){
        String query = "select * from clients where clientName=?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1,clientName);
            ResultSet rs = pst.executeQuery();
            if(rs.next())
                return createClientFromResultSet(rs);
        } catch (SQLException e) {
            System.out.println("Error while Retrieving a client:"+e.getMessage());
        }
        return null;
    }

    public Client createClientFromResultSet(ResultSet rs) throws SQLException {
        Client client = new Client();
        client.setClientId(rs.getInt("clientId"));
        client.setClientName(rs.getString("clientName"));
        client.setClientEmail(rs.getString("clientEmail"));
        client.setClientDescription(rs.getString("clientDescription"));
        client.setAddress(rs.getString("address"));
        client.setCity(rs.getString("city"));
        client.setPhone(rs.getString("phone"));
        return  client;
    }


}
