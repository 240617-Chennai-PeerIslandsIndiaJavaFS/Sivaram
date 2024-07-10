package org.example.model;

public class ReportDetail {
    private String project_name;
    private String project_manager;
    private int unique_team_members;
    private int total_tasks;
    private int completed_tasks;

    // Getters and setters
    public String getProjectName() {
        return project_name;
    }

    public void setProjectName(String project_name) {
        this.project_name = project_name;
    }

    public String getProjectManager() {
        return project_manager;
    }

    public void setProjectManager(String project_manager) {
        this.project_manager = project_manager;
    }

    public int getUniqueTeamMembers() {
        return unique_team_members;
    }

    public void setUniqueTeamMembers(int uniqueTeamMembers) {
        this.unique_team_members = uniqueTeamMembers;
    }

    public int getTotalTasks() {
        return total_tasks;
    }

    public void setTotalTasks(int total_tasks) {
        this.total_tasks = total_tasks;
    }

    public int getCompletedTasks() {
        return completed_tasks;
    }

    public void setCompletedTasks(int tasksWithMilestoneId5) {
        this.completed_tasks = tasksWithMilestoneId5;
    }
}
