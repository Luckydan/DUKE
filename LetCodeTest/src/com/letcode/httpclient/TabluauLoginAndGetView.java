package com.letcode.httpclient;

import java.util.concurrent.CountDownLatch;

public class TabluauLoginAndGetView {
    private static int x = 0, y = 0;
    private static int a = 0, b =0;

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        for(;;) {
            i++;
            x = 0; y = 0;
            a = 0; b = 0;

            CountDownLatch latch = new CountDownLatch(1);
            //latch.wait();
            Thread one = new Thread(() -> {
                try {
                    //System.out.println("开始执行one线程");
                    latch.await();
                } catch (InterruptedException e) {
                }
                a = 1;
                x = b;
                //System.out.println("One线程执行完毕");
            });

            Thread other = new Thread(() -> {
                try {
                    //System.out.println("执行other线程");
                    latch.await();
                } catch (InterruptedException e) {
                }
                b = 1;
                y = a;
                //System.out.println("other线程执行完毕");
            });
            one.start();
            other.start();
            latch.countDown();
            one.join();
            other.join();

            String result = "第" + i + "次 (" + x + "," + y + "）";
            if(x == 0 && y == 0) {
                System.err.println(result);
                break;
            } else {
                System.out.println(result);
            }
        }
    }
}
