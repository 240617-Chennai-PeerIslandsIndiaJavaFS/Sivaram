package org.example.model;

public class TeamDetail {
    private Team team;
    private User team_member;
    private Task task;

    public TeamDetail() {
    }

    public TeamDetail(Team team, User team_member, Task task) {
        this.team = team;
        this.team_member = team_member;
        this.task = task;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public User getTeamMember() {
        return team_member;
    }

    public void setTeamMember(User team_member) {
        this.team_member = team_member;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
