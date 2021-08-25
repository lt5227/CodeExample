package com.stackstone.study.rxjava.core;

/**
 * Copyright 2021 PatSnap All rights reserved.
 * Observable
 * 被观察者核心抽象类：
 * 也是使用框架的入口
 *
 * @author LiLei
 * @date 2021/8/23
 * @since 1.0.0
 */
public abstract class Observable<T> implements ObservableSource<T>{
    @Override
    public void subscribe(Observer<T> observer) {
        subscribeActual(observer);
    }

    public static <T> Observable<T> create(ObservableOnSubscribe<T> source) {
        return new ObservableCreate<>(source);
    }

    public <R> ObservableMap<T, R> map(Function<T, R> function) {
        return new ObservableMap<>(this, function);
    }

    protected abstract void subscribeActual(Observer<T> observer);
}
