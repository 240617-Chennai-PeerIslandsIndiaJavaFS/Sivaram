package org.example.model;

public class Client {
    private int client_id;
    private String client_name;
    private String client_email;
    private String client_description;
    private String address;
    private String city;
    private String phone;

    public Client() {
    }

    public Client(int client_id, String client_name, String client_email,String client_description, String address, String city, String phone) {
        this.client_id = client_id;
        this.client_name = client_name;
        this.client_email=client_email;
        this.client_description = client_description;
        this.address = address;
        this.city = city;
        this.phone = phone;
    }

    public int getClientId() {
        return client_id;
    }

    public void setClientId(int client_id) {
        this.client_id = client_id;
    }

    public String getClientName() {
        return client_name;
    }

    public void setClientName(String client_name) {
        this.client_name = client_name;
    }

    public String getClientEmail() {
        return client_email;
    }

    public void setClientEmail(String client_email) {
        this.client_email = client_email;
    }

    public String getClientDescription() {
        return client_description;
    }

    public void setClientDescription(String client_description) {
        this.client_description = client_description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Override
    public String toString() {
        return String.format("| %-9d | %-20s | %-50s | %-30s | %-20s | %-15s |",
                client_id, client_name, client_description, address, city, phone);
    }

}

