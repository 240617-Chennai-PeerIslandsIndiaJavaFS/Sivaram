package org.example.model;

public class User {
    private int user_id;
    private String user_name;
    private String email;
    private String user_password;
    private String user_role;
    private String account_status;
    private String phone;

    public User() {
    }

    public User(int user_id, String user_name, String email, String user_password, String user_role, String account_status, String phone) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.email = email;
        this.user_password = user_password;
        this.user_role = user_role;
        this.account_status = account_status;
        this.phone = phone;
    }


    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public String getUserName() {
        return user_name;
    }

    public void setUserName(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserPassword() {
        return user_password;
    }

    public void setUserPassword(String user_password) {
        this.user_password = user_password;
    }

    public String getUserRole() {
        return user_role;
    }

    public void setUserRole(String user_role) {
        this.user_role = user_role;
    }

    public String getAccountStatus() {
        return account_status;
    }

    public void setAccountStatus(String account_status) {
        this.account_status = account_status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return String.format("| %-9d | %-20s | %-30s | %-15s | %-20s | %-15s |",
                user_id, user_name, email, user_password, user_role, phone);
    }

}
