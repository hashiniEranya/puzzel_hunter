package com.example.nb_minegame.model;


public class CustomerModel {
    private String username;
    private String password;

    private String status;


    public CustomerModel(String nic, String password, String status) {
        this.username = nic;
        this.password = password;
        this.status = status;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
