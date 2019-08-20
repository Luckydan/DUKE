package com.letcode.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK的动态代理demo,Jdk_Aop为接口UserDao的代理类---针对接口
 * @author gl
 * @version  1.0.0
 * @time 2019年3月27日15点52分
 * */
public class Jdk_Aop implements InvocationHandler{

    public Object target;

    public Jdk_Aop(Object target){
        this.target = target;
    }

    public Object createProxy(){
        return  Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("授权验证处理。。。");
        before();
        Object result =  null;
        System.out.println("proxy的名称：" + proxy.getClass().getName());
        System.out.println("接口的方法名称为："+ method.getName());
        result = method.invoke(target, args);
        after();
        return  result;
    }

    private void before() {//方法执行前
        System.out.println("方法执行前。。。授权 !");
    }
    private void after() {//方法执行后
        System.out.println("方法执行后。。。。验证");
    }
}
