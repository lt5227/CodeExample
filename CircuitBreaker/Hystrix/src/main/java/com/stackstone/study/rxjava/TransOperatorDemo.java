package com.stackstone.study.rxjava;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;

/**
 * Copyright 2021 PatSnap All rights reserved.
 * TransOperatorDemo
 *
 * @author LiLei
 * @date 2021/8/23
 * @since 1.0.0
 */
public class TransOperatorDemo {
    public static void main(String[] args) {
        System.out.println("===============");
        test4();
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
        // map: 直接对发射出来的事件进行处理并且产生新的事件，再次发射
        Observable.just("aaa")
                .map(new Function<String, Object>() {
                    @Override
                    public Object apply(String s) throws Throwable {
                        System.out.println("apply:" + s);
                        return "BBBB";
                    }
                }).subscribe(observer);
    }

    private static void test2() {
        Observable.just("111", "222", "333", "444", "555")
                // 无序的
                .flatMap(new Function<String, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(String s) throws Throwable {
                        System.out.println("apply..." + s);
                        return Observable.just("BBBB");
                    }
                }).subscribe(observer);
    }

    private static void test3() {
        Observable.just("111", "222", "333", "444", "555")
                // 有序的
                .concatMap(new Function<String, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(String s) throws Throwable {
                        System.out.println("apply..." + s);
                        return Observable.just("BBBB");
                    }
                }).subscribe(observer);
    }

    private static void test4() {
        Observable.just("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
                .buffer(3)
                .subscribe(observer);
    }

}
