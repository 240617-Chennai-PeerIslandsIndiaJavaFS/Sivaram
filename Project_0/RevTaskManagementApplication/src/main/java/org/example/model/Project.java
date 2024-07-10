package org.example.model;

import java.time.LocalDate;



public class Project {
    private int project_id;
    private String project_name;
    private String project_description;
    private LocalDate start_date;
    private LocalDate due_date;
    private Client client;
    private User project_manager;

    public Project() {
    }

    public Project(int project_id, String project_name, String project_description, LocalDate start_date, LocalDate due_date, Client client, User project_manager) {
        this.project_id = project_id;
        this.project_name = project_name;
        this.project_description = project_description;
        this.start_date = start_date;
        this.due_date = due_date;
        this.client = client;
        this.project_manager = project_manager;
    }

    public int getProjectId() {
        return project_id;
    }

    public void setProjectId(int project_id) {
        this.project_id = project_id;
    }

    public String getProjectName() {
        return project_name;
    }

    public void setProjectName(String project_name) {
        this.project_name = project_name;
    }

    public String getProjectDescription() {
        return project_description;
    }

    public void setProjectDescription(String project_description) {
        this.project_description = project_description;
    }

    public LocalDate getStartDate() {
        return start_date;
    }

    public void setStartDate(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getDueDate() {
        return due_date;
    }

    public void setDueDate(LocalDate due_date) {
        this.due_date = due_date;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public User getProjectManager() {
        return project_manager;
    }

    public void setProjectManager(User project_manager) {
        this.project_manager = project_manager;
    }

    @Override
    public String toString() {
        return String.format("| %-10d | %-20s | %-30s | %-12s | %-12s | %-20s | %-20s |",
                project_id, project_name, project_description, start_date, due_date, client.getClientName(), project_manager.getUserName());
    }

}
