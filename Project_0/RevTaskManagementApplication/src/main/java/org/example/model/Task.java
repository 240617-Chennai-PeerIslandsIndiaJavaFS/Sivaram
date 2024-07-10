package org.example.model;

import java.time.LocalDate;
import java.util.Date;

public class Task {
    private int task_id;
    private String title;
    private String task_description;
    private LocalDate start_date;
    private LocalDate due_date;
    private Milestone task_status;
    private User project_manager;

    public Task() {
    }

    public Task(int task_id, String title, String task_description, LocalDate start_date, LocalDate due_date, Milestone task_status) {
        this.task_id = task_id;
        this.title = title;
        this.task_description = task_description;
        this.start_date = start_date;
        this.due_date = due_date;
        this.task_status = task_status;
    }

    public int getTaskId() {
        return task_id;
    }

    public void setTaskId(int task_id) {
        this.task_id = task_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTaskDescription() {
        return task_description;
    }

    public void setTaskDescription(String task_description) {
        this.task_description = task_description;
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

    public Milestone getTaskStatus() {
        return task_status;
    }

    public void setTaskStatus(Milestone task_status) {
        this.task_status = task_status;
    }

}
