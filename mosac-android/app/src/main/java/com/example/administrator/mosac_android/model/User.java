package com.example.administrator.mosac_android.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/20 0020.
 */

public class User implements Serializable {
    private int user_id;
    private String username;
    private String number;
    private String contact_number;
    private String email;
    private String department;
    public User(int user_id, String username, String number, String contact_number, String email, String department) {
        this.user_id = user_id;
        this.username = username;
        this.number = number;
        this.contact_number = contact_number;
        this.email = email;
        this.department = department;
    }
    public User(String username, String email, String department) {
        this.username = username;
        this.email = email;
        this.department = department;
    }
    public User(int user_id, String username, String department, String email) {
        this.username = username;
        this.user_id = user_id;
        this.department = department;
        this.email = email;
    }
    public int getUser_id() {return user_id;}
    public String getUsername() {return username;}
    public String getNumber() { return number;}
    public String getContact_number() { return contact_number;}
    public String getEmail() { return email;}
    public String getDepartment() { return department;}
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
}
