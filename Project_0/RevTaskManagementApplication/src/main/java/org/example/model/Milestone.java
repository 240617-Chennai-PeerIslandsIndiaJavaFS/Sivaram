package org.example.model;

public class Milestone {
    private int milestone_id;
    private String milestone_name;
    private String milestone_description;

    public Milestone() {
    }

    public Milestone(int milestone_id, String milestone_name, String milestone_description) {
        this.milestone_id = milestone_id;
        this.milestone_name = milestone_name;
        this.milestone_description = milestone_description;
    }

    public int getMilestoneId() {
        return milestone_id;
    }

    public void setMilestoneId(int milestone_id) {
        this.milestone_id = milestone_id;
    }

    public String getMilestoneName() {
        return milestone_name;
    }

    public void setMilestoneName(String milestone_name) {
        this.milestone_name = milestone_name;
    }

    public String getMilestoneDescription() {
        return milestone_description;
    }

    public void setMilestoneDescription(String milestone_description) {
        this.milestone_description = milestone_description;
    }
}
