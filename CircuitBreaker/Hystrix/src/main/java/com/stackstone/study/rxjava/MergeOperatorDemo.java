package com.stackstone.study.rxjava;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * Copyright 2021 PatSnap All rights reserved.
 * MergeOperatorDemo
 *
 * @author LiLei
 * @date 2021/8/23
 * @since 1.0.0
 */
public class MergeOperatorDemo {
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
        Observable.concat(Observable.just("1111"),
                        Observable.just("2222"))
                .subscribe(observer);


    }
}
