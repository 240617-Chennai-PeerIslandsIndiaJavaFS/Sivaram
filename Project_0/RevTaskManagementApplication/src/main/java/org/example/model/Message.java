package org.example.model;

import java.time.LocalDateTime;

public class Message {
    private  int message_id;
    private User sender;
    private User receiver;
    private String message_description;
    private LocalDateTime message_time;

    public Message() {
    }

    public Message(int message_id, User sender, User receiver, String message_description, LocalDateTime message_time) {
        this.message_id = message_id;
        this.sender = sender;
        this.receiver = receiver;
        this.message_description = message_description;
        this.message_time = message_time;
    }

    public int getMessageId() {
        return message_id;
    }

    public void setMessageId(int message_id) {
        this.message_id = message_id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getMessageDescription() {
        return message_description;
    }

    public void setMessageDescription(String message_description) {
        this.message_description = message_description;
    }

    public LocalDateTime getMessageTime() {
        return message_time;
    }

    public void setMessageTime(LocalDateTime message_time) {
        this.message_time = message_time;
    }
}
