package com.model;

import java.util.Date;
import java.util.Objects;

public class Message {
    private String message;
    private Long id, users;
    private Date date;

    public Message(Long id, Long users, String message, Date date) {
        this.message = message;
        this.id = id;
        this.users = users;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Long getUsers() {
        return users;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsers(Long users) {
        this.users = users;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message1 = (Message) o;
        return Objects.equals(getMessage(), message1.getMessage()) && Objects.equals(getId(), message1.getId()) && Objects.equals(getDate(), message1.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMessage(), getId(), getDate());
    }
}
