package com.playplan.boot.thread;

/**
 * author : jyt
 * time   : 2021/11/16
 * desc   : 生产消费模型 SynchronousQueue
 */
public class Demo3 {

    public static int i = 0;

    public static void main(String[] args) throws InterruptedException {

        Object object = new Object();

        Thread thread_sc = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (object) {
                    while (true) {
                        if (i == 0) {
                            System.out.println("生产");
                            i++;
                            object.notify();
                        } else {
                            try {
                                Thread.sleep(1000);
                                object.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
            }
        });
        Thread thread_xf = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (object) {
                    while (true) {
                        if (i == 1) {
                            System.out.println("消费");
                            i--;
                            object.notify();
                        } else {
                            try {
                                Thread.sleep(1000);
                                object.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
            }
        });
        thread_sc.start();
        thread_xf.start();

        Thread.sleep(Integer.MAX_VALUE);
    }
}
