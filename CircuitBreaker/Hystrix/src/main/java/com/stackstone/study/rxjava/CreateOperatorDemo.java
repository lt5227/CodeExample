package com.stackstone.study.rxjava;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * Copyright 2021 PatSnap All rights reserved.
 * CreateOperatorDemo
 *
 * @author LiLei
 * @date 2021/8/23
 * @since 1.0.0
 */
public class CreateOperatorDemo {
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
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Throwable {
                // 事件产生的地方
                emitter.onNext("1");
                emitter.onNext("222");
                emitter.onNext("aaaa");
                // onError和Complete互斥
//                emitter.onError(new Throwable("手动丢出异常"));
                emitter.onComplete();
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Throwable {
                System.out.println("accept..." + o);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Throwable {
                System.out.println("accept..." + throwable);
            }
        });
    }

    private static void test2() {
        Observable.just("1", "AAAA", "2")
                .subscribe(observer);
    }

    private static void test3() {
        Observable.fromArray("1", "AAA", "2", "1", "AAAA")
                .subscribe(observer);
    }

    private static void test4() {
//        ArrayList<String> list = new ArrayList<>();
//        list.add("111");
//        list.add("222");
//        Observable.fromIterable(list).subscribe(observer);

//        Observable.fromFuture(new Future<Object>() {
//            @Override
//            public boolean cancel(boolean mayInterruptIfRunning) {
//                return false;
//            }
//
//            @Override
//            public boolean isCancelled() {
//                return false;
//            }
//
//            @Override
//            public boolean isDone() {
//                return false;
//            }
//
//            @Override
//            public Object get() throws InterruptedException, ExecutionException {
//                return "aaaa";
//            }
//
//            @Override
//            public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
//                return null;
//            }
//        }).subscribe(observer);

        Observable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return "1234call";
            }
        }).subscribe(observer);
    }
}
