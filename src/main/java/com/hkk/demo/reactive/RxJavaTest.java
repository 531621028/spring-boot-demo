package com.hkk.demo.reactive;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kang
 * @date 2022/2/22
 */
@Slf4j
public class RxJavaTest {

    public static void main(String[] args) throws InterruptedException {
        Observable
            .create((ObservableOnSubscribe<Integer>) emitter -> {
                emitter.onNext(1);
                emitter.onComplete();
            })
            .subscribeOn(Schedulers.newThread())
            .map(item -> {
                log.info("before subscribe");
                return item;
            })
            // .observeOn(Schedulers.newThread())
            .map(item -> item * 2)
            .subscribe((res) -> log.info("after subscribe"));
        Thread.sleep(1000);
    }
}
