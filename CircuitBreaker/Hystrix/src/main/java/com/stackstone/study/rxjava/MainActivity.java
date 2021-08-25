package com.stackstone.study.rxjava;

import com.stackstone.study.rxjava.core.*;

/**
 * Copyright 2021 PatSnap All rights reserved.
 * MainActivity
 *
 * @author LiLei
 * @date 2021/8/23
 * @since 1.0.0
 */
public class MainActivity {
    public static void main(String[] args) {
        MainActivity mainActivity = new MainActivity();
        mainActivity.test1();
    }

    private void test1() {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(Emitter<Object> emitter) {
                System.out.println("subscribe...");
                emitter.onNext("123");
                emitter.onNext("456");
                emitter.onComplete();
            }
        })
                .map(new Function<Object, Object>() {
                    @Override
                    public Object apply(Object o) {
                        return "apply..." + o;
                    }
                })
                .subscribe(new Observer<Object>() {
            @Override
            public void onNext(Object o) {
                System.out.println("onNext..." + o);
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete...");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("onError..." + throwable);
            }

            @Override
            public void onSubscribe() {
                System.out.println("onSubscribe...");
            }
        });
    }
}
