package com.example.database;

public class User {
    private String fisrtName;
    private String lastName;
    private String uuid;

    public User() {
        this.fisrtName = "";
        this.lastName = "";
        this.uuid = "";
    }

    public User(String fisrtName, String lastName, String uuid) {
        this.fisrtName = fisrtName;
        this.lastName = lastName;
        this.uuid = uuid;
    }

    public String getName() {
       return this.fisrtName + " " + this.lastName;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setFirstName(String name) {
        this.fisrtName =  name;
    }

    public void setLastName(String name) {
        this.lastName =  name;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
