package com.model;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class User {
    private Long id;
    @NotNull
    @Size(min = 5, max = 16, message = "{login.size}")
    private String login;
    @NotNull
    @Size(min = 5, max = 25, message = "{password.size}")
    private String password;
    @NotNull
    @Size(min = 2, max = 30, message = "{firstName.size}")
    private String firstName;
    @NotNull
    @Size(min = 2, max = 30, message = "{lastName.size}")
    private String lastName;
    @NotNull
    @Email(message = "{email.valid}")
    @Size(min = 2, max = 30, message = "{email.size}")
    private String email;

    public User(String email, String password) {
        this(email, email, email, email, password);
    }

    public User(String login, String lastName, String firstName, String email, String password) {
        this.login = login;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
    }

    public User() {
        this("", "");
    }

    public String getLogin() {
        return login;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getLogin(), user.getLogin()) && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getFirstName(), user.getFirstName()) && Objects.equals(getLastName(), user.getLastName()) && Objects.equals(getEmail(), user.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin(), getPassword(), getFirstName(), getLastName(), getEmail());
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
