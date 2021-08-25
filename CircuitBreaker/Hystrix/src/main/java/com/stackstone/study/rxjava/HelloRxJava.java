package com.stackstone.study.rxjava;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * Copyright 2021 PatSnap All rights reserved.
 * HelloRxJava
 *
 * @author LiLei
 * @date 2021/8/20
 * @since 1.0.0
 */
public class HelloRxJava {
    public static void hello(String... args) {
        Disposable disposable = Flowable.fromArray(args).subscribe(s -> System.out.println("Hello " + s + "!"));
        System.out.println(disposable);
    }

    public static void main(String[] args) {
        hello("Ben", "George");

        Observable<String> observable = Observable.fromArray("a", "b", "c");
    }
}
