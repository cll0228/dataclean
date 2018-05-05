package com.holyrobot.lock;

/**
 * Created by cuill on 2018/4/17.
 */
public class SynchronizedTest {
    private static Object object = new Object();

    public static void main(String[] args) throws Exception {
        synchronized (object) {

        }
    }

    public static synchronized void m() {
    }
}
