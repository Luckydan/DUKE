package com.letcode.mutiTread;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试Lock接口的唯一实现ReentranLock锁的功能
 *
 * @author gl
 * @version 1.0
 * @time 2019年2月27日 09点52分
 */
public class ReentranLockTest {
    private static ArrayList<Integer> arrayList = new ArrayList<Integer>();
    private Lock lock = new ReentrantLock();
    public static void main(String[] args)  {
        // 测试ReentranLock类的lock()和tryLock()方法，
        //final ReentranLockTest test = new ReentranLockTest();
        //
        //new Thread(){
        //    public void run() {
        //        test.tryLockInsert(Thread.currentThread());
        //    };
        //}.start();
        //
        //new Thread(){
        //    public void run() {
        //        test.tryLockInsert(Thread.currentThread());
        //    };
        //}.start();
        //System.out.println(arrayList.size());

        // 测试ReentranLock类的lockInterruptibly()是否可以在Thread1获取锁后，打断Tread2
        ReentranLockTest test = new ReentranLockTest();
        MyThread thread1 = new MyThread(test);
        MyThread thread2 = new MyThread(test);
        thread1.start();
        thread2.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread2.interrupt();

    }

    /**
     * 实现类RnentranLock的lock()方法测试。
     *
     * @param thread
     */
    public void lockInsert(Thread thread) {
        /** 此处的lock属于局部变量，对于insert()方法没有约束性，所以会出现在线程1获取锁并未释放锁的情况下，线程2也获取到了锁 。
         *  每个线程执行该方法时都会保存一个副本，那么理所当然每个线程执行到lock.lock()处获取的是不同的锁，所以就不会发生冲突
         * */
        // Lock lock = new ReentrantLock();    //注意这个地方
        lock.lock();
        try {
            System.out.println(thread.getName()+"得到了锁");
            for(int i=0;i<5;i++) {
                arrayList.add(i);
            }
        } catch (Exception e) {
        }finally {
            System.out.println(thread.getName()+"释放了锁");
            lock.unlock();
        }
    }

    /**
     * 实现类RnentranLock的tryLock()方法测试
     *
     * @param thread
     */
    public void tryLockInsert(Thread thread) {
        if(lock.tryLock()) {
            try {
                System.out.println(thread.getName()+"得到了锁");
                for(int i=0;i<5;i++) {
                    arrayList.add(i);
                }
            } catch (Exception e) {

            }finally {
                System.out.println(thread.getName()+"释放了锁");
                lock.unlock();
            }
        } else {
            System.out.println(thread.getName()+"获取锁失败");
        }
    }

    /**
     * 实现类ReentranLoc的lockInterru
     *
     * @param thread
     * @throws InterruptedException
     */
    public void lockInterruptiblyInsert(Thread thread) throws InterruptedException{
        lock.lockInterruptibly();   //注意，如果需要正确中断等待锁的线程，必须将获取锁放在外面，然后将InterruptedException抛出
        try {
            System.out.println(thread.getName()+"得到了锁");
            long startTime = System.currentTimeMillis();
            for(    ;     ;) {
                if(System.currentTimeMillis() - startTime >= Integer.MAX_VALUE)
                    break;
                //插入数据
            }
        }
        finally {
            System.out.println(Thread.currentThread().getName()+"执行finally");
            lock.unlock();
            System.out.println(thread.getName()+"释放了锁");
        }
    }
}

class MyThread extends Thread {
    private ReentranLockTest test = null;
    public MyThread(ReentranLockTest test) {
        this.test = test;
    }
    @Override
    public void run() {

        try {
            test.lockInterruptiblyInsert(Thread.currentThread());
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName()+"被中断");
        }
    }
}


