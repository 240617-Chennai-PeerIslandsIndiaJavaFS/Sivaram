package org.example.model;

public class Team {
    private int team_id;
    private String team_name;
    private User project_manager;
    private Project project;

    public Team() {
    }

    public Team(int team_id, String team_name, User project_manager, Project project) {
        this.team_id = team_id;
        this.team_name = team_name;
        this.project_manager = project_manager;
        this.project = project;
    }

    public int getTeamId() {
        return team_id;
    }

    public void setTeamId(int team_id) {
        this.team_id = team_id;
    }

    public String getTeamName() {
        return team_name;
    }

    public void setTeamName(String team_name) {
        this.team_name = team_name;
    }

    public User getProjectManager() {
        return project_manager;
    }

    public void setProjectManager(User project_manager) {
        this.project_manager = project_manager;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

}
