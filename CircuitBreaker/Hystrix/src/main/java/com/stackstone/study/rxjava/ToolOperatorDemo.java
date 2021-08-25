package com.stackstone.study.rxjava;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Copyright 2021 PatSnap All rights reserved.
 * ToolOperatorDemo
 *
 * @author LiLei
 * @date 2021/8/23
 * @since 1.0.0
 */
public class ToolOperatorDemo {
    public static void main(String[] args) {
        System.out.println("===============");
        test1();
        System.out.println("===============");
    }

    static Observer<Object> observer = new Observer<Object>() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {
            System.out.println("onSubscribe...");
        }

        @Override
        public void onNext(@NonNull Object o) {
            System.out.println("onNext..." + o);
        }

        @Override
        public void onError(@NonNull Throwable e) {
            System.out.println("onError..." + e);
        }

        @Override
        public void onComplete() {
            System.out.println("onComplete...");
        }
    };

    private static void test1() {
        Observable.create(new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Throwable {
                        // 创建事件发射事件
                        System.out.println("subscribe..." + Thread.currentThread().getName());
                        // 模拟网络操作
                        Thread.sleep(2000L);
                        emitter.onNext("aaaaaa");
                        emitter.onNext("bbbbbb");
                        emitter.onComplete();
                    }
                })
                // 主要来决定执行subscribe方法所处的线程，也就是产生事件发射事件所在的线程
                .subscribeOn(Schedulers.newThread())
                // 来决定下游事件被处理时所处的线程
                .map(new Function<Object, Object>() {
                    @Override
                    public Object apply(Object o) throws Throwable {
                        System.out.println("map apply..." + o + " " + Thread.currentThread().getName());
                        return "bbb";
                    }
                })
                .observeOn(Schedulers.io())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        System.out.println("onSubscribe..." + Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(@NonNull Object o) {
                        System.out.println("onNext..." + o + "---" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("onError..." + e + "---" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete..." + Thread.currentThread().getName());
                    }
                });
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
