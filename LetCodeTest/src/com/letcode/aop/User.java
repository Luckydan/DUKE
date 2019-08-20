package com.letcode.aop;

public class User {
    private String userName;
    private String pwd;
    private String gender;

    public String getUserName() {
        return userName;
    }

    public String getPwd() {
        return pwd;
    }

    public String getGender() {
        return gender;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void login(){
        System.out.println(userName+" 用户密码已经重置，可以重新登陆！！！");
    }
}
