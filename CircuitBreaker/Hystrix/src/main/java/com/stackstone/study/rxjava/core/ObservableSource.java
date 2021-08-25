package com.stackstone.study.rxjava.core;

/**
 * Copyright 2021 PatSnap All rights reserved.
 * ObservableSource
 *
 * @author LiLei
 * @date 2021 /8/23
 * @since 1.0.0
 */
public interface ObservableSource<T> {

    /**
     * add observer
     *
     * @param observer the observer
     */
    void subscribe(Observer<T> observer);
}
