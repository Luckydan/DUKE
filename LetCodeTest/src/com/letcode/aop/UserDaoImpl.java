package com.letcode.aop;

public class UserDaoImpl implements UserDao{
    @Override
    public String getUserNmae() {
        System.out.println("用户查询正常！！！");
        return null;
    }
}
