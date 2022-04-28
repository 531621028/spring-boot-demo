package com.hkk.demo.thread;

import com.hkk.demo.utils.JsonUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 延时队列实现
 *
 * @author kang
 * @date 2022/4/28
 */
public class DelayQueueTest {

    public static void main(String[] args) throws InterruptedException {
        ScheduleDelayTask delayTask = new ScheduleDelayTask();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                delayTask.run();
            }
        }, 1000, 1000);
        Thread.sleep(10000);
        delayTask.addTask(() -> log("等待5秒"), 5);
        delayTask.addTask(() -> log("等待61秒"), 61);
        delayTask.addTask(() -> log("等待18秒"), 18);
        delayTask.addTask(() -> log("等待78秒"), 78);
    }

    private static void log(String message) {
        System.out.println(
            System.currentTimeMillis() + ":" + Thread.currentThread() + ":" + message);
    }


    public static class ScheduleDelayTask implements Runnable {

        private static final Executor EXECUTOR = Executors.newCachedThreadPool();
        private static final int QUEUE_SIZE = 60;
        List<Set<DelayTask>> queue = new ArrayList<>(QUEUE_SIZE);
        private volatile int currentIndex;

        public ScheduleDelayTask() {
            for (int i = 0; i < QUEUE_SIZE; i++) {
                queue.add(new LinkedHashSet<>());
            }
        }

        public synchronized void addTask(Runnable task, int delaySeconds) {
            int index = calculateIndex(delaySeconds);
            int cycleNum = calculateCycleNum(delaySeconds);
            DelayTask delayTask = new DelayTask(task, cycleNum);
            queue.get(index).add(delayTask);

        }

        private int calculateIndex(int delaySeconds) {
            return (delaySeconds + currentIndex) % QUEUE_SIZE;
        }


        private int calculateCycleNum(int delaySeconds) {
            return (delaySeconds + currentIndex) / QUEUE_SIZE;
        }

        private int getExecuteIndexAndAdd() {
            int executeIndex = currentIndex;
            if (executeIndex >= QUEUE_SIZE) {
                executeIndex = executeIndex - QUEUE_SIZE;
            }
            currentIndex = executeIndex + 1;
            return executeIndex;
        }

        @Override
        public void run() {
            int executeIndex = getExecuteIndexAndAdd();
            Set<DelayTask> delayTasks = queue.get(executeIndex);
            Iterator<DelayTask> it = delayTasks.iterator();
            while (it.hasNext()) {
                DelayTask delayTask = it.next();
                if (delayTask.getCycleNum() == 0) {
                    EXECUTOR.execute(delayTask);
                    it.remove();
                } else {
                    delayTask.setCycleNum(delayTask.getCycleNum() - 1);
                }
            }
            log("执行完毕" + executeIndex + ":" + JsonUtil.toJsonStringOrEmpty(queue));
        }

        /**
         * @EqualsAndHashCode 标注的实体类 作为Set或者Map的Key时，里面的变量不能随便修改，不然会被定位到不同桶里面从而找不到映射的对象
         */
        public static class DelayTask implements Runnable {

            private volatile int cycleNum;
            private final Runnable task;


            DelayTask(Runnable task, int cycleNum) {
                this.cycleNum = cycleNum;
                this.task = task;
            }

            public int getCycleNum() {
                return cycleNum;
            }

            public void setCycleNum(int cycleNum) {
                this.cycleNum = cycleNum;
            }

            @Override
            public void run() {
                task.run();
            }
        }
    }

}
