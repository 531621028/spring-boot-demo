package com.hkk.demo.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kang
 * @date 2022/6/8
 */
@Slf4j
public class CyclicBarrierTest {

    private static final ExecutorService executor = Executors.newFixedThreadPool(3);

    public static void main(String[] args) throws InterruptedException {
        final CyclicBarrier barrier = new CyclicBarrier(2, () -> executor.execute(
            CyclicBarrierTest::check));
        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                try {
                    log.info("part1");
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException exception) {
                }
            });

            executor.execute(() -> {
                try {
                    log.info("part2");
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException exception) {
                }
            });
            Thread.currentThread().sleep(100);
        }
        executor.shutdown();
    }


    private static void check() {
        log.info("check");
    }
}
