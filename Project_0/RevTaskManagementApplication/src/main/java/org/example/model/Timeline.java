package org.example.model;

import java.time.LocalDateTime;
import java.util.Date;

public class Timeline {
    private int timeline_id;
    private Task task;
    private Milestone milestone;
    private LocalDateTime task_timestamp;

    public Timeline() {
    }

    public Timeline(int timeline_id, Task task, Milestone milestone, LocalDateTime task_timestamp) {
        this.timeline_id = timeline_id;
        this.task = task;
        this.milestone = milestone;
        this.task_timestamp = task_timestamp;
    }

    public int getTimelineId() {
        return timeline_id;
    }

    public void setTimelineId(int timeline_id) {
        this.timeline_id = timeline_id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public LocalDateTime getTaskTimestamp() {
        return task_timestamp;
    }

    public void setTaskTimestamp(LocalDateTime task_timestamp) {
        this.task_timestamp = task_timestamp;
    }
}
