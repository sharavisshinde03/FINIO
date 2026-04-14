package com.bank.model;

public class Person {

    protected String name;
    protected String email;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDetails() {
        return name + " - " + email;
    }
}