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

    /**
     * 从本质上来说，当线程释放一个锁时会强制性的将工作内存中之前所有的写操作都刷新到主内存中去，
     * 而获取一个锁则会强制性的加载可访问到的值到线程工作内存中来。
     * 虽然锁操作只对同步方法和同步代码块这一块起到作用，但是影响的却是线程执行操作所使用的所有字段。
     */
    public static synchronized void sync() {

    }
}
