package com.playplan.boot.thread;

/**
 * author : jyt
 * time   : 2021/11/16
 * desc   :
 */
public class Demo2 {

    public static void main(String[] args) {
        Object ob = new Object();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (ob) {
                        ob.wait();
                        System.out.println("hhhhhh");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (ob) {
                    ob.notify();
                    try {
                        ob.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    while (true) {

                    }
                }
            }
        });
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread2.start();
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
