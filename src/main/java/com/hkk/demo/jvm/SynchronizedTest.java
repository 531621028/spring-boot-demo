package com.hkk.demo.jvm;

/**
 * @author kang
 * @date 2022/6/8
 */
// Run with -XX:+UnlockDiagnosticVMOptions
// -XX:+PrintBiasedLockingStatistics
// -XX:TieredStopAtLevel=1
// -XX:BiasedLockingStartupDelay=0 偏向锁启动时间
// 1. 通过参数 -XX:+UseBiasedLocking，比较开关偏向锁时的输出结果。
// 2.在 main 方法的循环前添加 lock.hashCode 调用，并查看输出结果。
// 3. 在 Lock 类中复写 hashCode 方法，并查看输出结果。
// 4. 在 main 方法的循环前添加 System.identityHashCode 调用，并查看输出结果。
public class SynchronizedTest {

    static Lock lock = new Lock();
    static int counter = 0;

    public static void foo() {
        synchronized (lock) {
            counter++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        lock.hashCode(); // Step 2
        System.identityHashCode(lock); // 会调用对象默认的hashCode方法，也就是Object.hashCode()方法，不论子类是否重写的hashCode方法
        for (int i = 0; i < 1_000_000; i++) {
            foo();
        }
    }

    static class Lock {

        @Override
        public int hashCode() {
            super.hashCode();
            return 0;
        }
    }
}