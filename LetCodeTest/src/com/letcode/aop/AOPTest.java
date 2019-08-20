package com.letcode.aop;

import java.util.concurrent.CountDownLatch;

public class AOPTest {
    public static void main(String args[]) throws Exception{
        // JDK动态代理测试
        UserDao userDao  = new UserDaoImpl();
        Jdk_Aop proxy = new Jdk_Aop(userDao);
        UserDao proxyUserDao = (UserDao) proxy.createProxy();
        proxyUserDao.getUserNmae();
        System.out.println("-------------------------------------------------------------");
        // CGLIB动态代理测试
        User user = new User();
        user.setUserName("撒库拉给");
        Cglib_Aop cglib_aop = new Cglib_Aop(user);
        User  proxyUser = (User) cglib_aop.createProxy();
        proxyUser.login();
    }
}
