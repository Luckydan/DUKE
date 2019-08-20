package com.letcode.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import java.lang.reflect.Method;

/**
 * CGLIB动态代理--- 代理类（针对类）
 *
 */
public class Cglib_Aop implements MethodInterceptor{
    /** 目标对象 */
    private Object target;

    public Cglib_Aop(Object target){
        this.target = target;
    }

    public Object createProxy(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object proxy, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("预处理。。。。。");
        Object result  = null;
        System.out.println("代理类对象："+ proxy.getClass().getTypeName());
        System.out.println("方法代理类对象："+ methodProxy.getClass().getTypeName());
        result = methodProxy.invoke(target,objects);
        System.out.println("正常登陆。。。。。。");
        return result;
    }
}
