package com.stackstone.study.rxjava;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.internal.schedulers.ScheduledAction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.*;

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
//        Observable<?> observable = Observable.unsafeCreate(emitter -> {
//            emitter.onNext("Hello World!");
//            emitter.onCompleted();
//        });
//        observable.subscribe(o -> System.out.println(o + " " + Thread.currentThread().getName()));
//        observable.subscribe(o -> System.out.println(o + " " + Thread.currentThread().getName()));

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        Action0 action = new Action0() {
            @Override
            public void call() {
                try {
                    for (int i = 0; i < 10000&&!Thread.currentThread().isInterrupted(); i++) {
                        System.out.println(i);
                    }
                    File file = new File("E:\\skywalking-api.log");
                    Thread.currentThread().isInterrupted();
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write("123456");
                    fileWriter.flush();
                    long i = Integer.MAX_VALUE;
                    while (true) {
                        if (i-- < 0) {
                            System.out.println("Demo....");
                            break;
                        }
                    }
                    fileWriter.write("456789");
                    fileWriter.flush();
                    System.out.println("Done....");
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("finally");
                }
            }
        };
        // This is internal RxJava API but it is too useful.
        ScheduledAction sa = new ScheduledAction(action);
//        FutureTask<?> futureTask = new FutureTask<String>(new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//
//            }
//        });
        FutureTask<?> f = (FutureTask<?>) executor.submit(sa);
        System.out.println("futureTask start");
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean b = f.cancel(true);
        System.out.println("futureTask cancel: " + b);
        executor.shutdown();
    }
}
