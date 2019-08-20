package com.letcode.mutiTread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MutiConsumerAndMutiProducerLock {
    public static void main(String[] args){
        ResourceLock resourceLock = new ResourceLock();
        ConsumerLock consumerLock = new ConsumerLock(resourceLock);
        ProducerLock producerLock = new ProducerLock(resourceLock);

        Thread t1 = new Thread(consumerLock);
        Thread t2 = new Thread(consumerLock);
        Thread t3 = new Thread(producerLock);
        Thread t4 = new Thread(producerLock);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}

class ResourceLock{
    private String name;
    private int count = 1;
    private boolean flag = false;
    Lock lock = new ReentrantLock();
    Condition con = lock.newCondition();
    Condition pro = lock.newCondition();


    /**
     * 同步set()方法
     * @param name
     */
    public void set(String name) {
        lock.lock();
        try {
            while (flag){
                try {
                    pro.await();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            this.name = name + count;
            count ++;
            System.out.println(Thread.currentThread().getName()+"生产者....."+this.name);
            flag = true;
            con.signal();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void out(){
        lock.lock();
        try {
            while(!flag){
                try{
                    con.await();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName()+".........消费者:"+this.name);
            flag = false;
            pro.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

class ProducerLock implements Runnable {
    private ResourceLock resource;

    public ProducerLock(ResourceLock resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        while (true) {
            resource.set("Locking Dream!!!");
        }
    }
}
/**
 * 资源消费者
 */
class ConsumerLock implements Runnable{
    private ResourceLock resource;
    public ConsumerLock(ResourceLock resource){
        this.resource = resource;
    }

    @Override
    public void run() {
        while (true) {
            resource.out();
        }
    }
}
