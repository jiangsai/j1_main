package com.playplan.boot.thread;

/**
 * author : jyt
 * time   : 2021/11/16
 * desc   :
 */
public class Demo1 {
    public static void main(String[] args) {
//        while (true){
//            add(2,Thread.currentThread().getName());
//        }
        Thread t1 = new Thread() {
            @Override
            public void run() {
                super.run();


            }
        };
        Thread t2 = new Thread() {
            @Override
            public void run() {
                super.run();


            }
        };
        Thread t3 = new Thread() {
            @Override
            public void run() {
                super.run();
            }
        };
        t3.setName("thread3");
        t2.setName("thread2");
        t1.setName("thread1");
        t1.start();
        t2.start();
        t3.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    static volatile int i = 0;

    public static synchronized void add(int x, String name) {
        if (i % 3 == x && i < 100) {
            i++;
            System.out.println(name + "===" + i);
        }

    }
}
