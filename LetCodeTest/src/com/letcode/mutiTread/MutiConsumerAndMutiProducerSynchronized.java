package com.letcode.mutiTread;

/**
 * 多线程实现多生产多消费
 */
public class MutiConsumerAndMutiProducerSynchronized {
    public static void main(String[] args){
        Resource resource = new Resource();
        Consumer consumer = new Consumer(resource);
        Producer producer = new Producer(resource);

        Thread t1 = new Thread(producer);
        Thread t2 = new Thread(producer);
        Thread t3 = new Thread(consumer);
        Thread t4 = new Thread(consumer);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}

/**
 * 资源
 */
class Resource{
    private String name;
    private int count = 1;
    private boolean flag = false;

    /**
     * 同步set方法
     *
     * @param name
     */
    public synchronized void  set(String name){
        if(flag){
            try {
                this.wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        this.name = name + count;
        count ++;
        System.out.println(Thread.currentThread().getName() + "生产者....."+this.name);
        flag = true;
        notifyAll();
    }

    /**
     * 同步out方法
     */
    public synchronized void out(){
        if(!flag){
            try {
                this.wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() +"......消费者："+ this.name);
        flag = false;
        notifyAll();
    }
}

/**
 * 资源生产者
 */
class Producer implements Runnable {
    private Resource resource;

    public Producer(Resource resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        while (true) {
            resource.set("Dream");
        }
    }
}
/**
 * 资源消费者
 */
class Consumer implements Runnable{
    private Resource resource;
    public Consumer(Resource resource){
        this.resource = resource;
    }

    @Override
    public void run() {
        while (true) {
            resource.out();
        }
    }
}
