package com.spring.model;

import javax.validation.constraints.NotNull;

/**
 * Created by Sam on 19/11/2016.
 */
public class User {

    @NotNull
    private int id;
    @NotNull
    private String userName;
    @NotNull
    private String passWord;
    @NotNull
    private String fullName;
    @NotNull
    private int age;
    @NotNull
    private String address;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
