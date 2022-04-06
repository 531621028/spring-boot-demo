package com.hkk.demo.thread;

/**
 * @author kang
 * @date 2022/4/6
 */
public class VisibilityTest extends Thread {

    private boolean keepRun = true;

    public static void main(String[] args) throws InterruptedException {
        VisibilityTest test = new VisibilityTest();
        System.out.println("start:" + test.isKeepRun());
        test.start();
        Thread.currentThread().join(1000);
        test.setKeepRun(false);
        System.out.println("end:" + test.isKeepRun());
    }

    @Override
    public void run() {
        int x = 1;
        while (keepRun) {
            // sync();
            x++;
        }
    }

    public boolean isKeepRun() {
        return keepRun;
    }

    public void setKeepRun(boolean keepRun) {
        this.keepRun = keepRun;
    }

    public static synchronized void sync() {

    }
}
