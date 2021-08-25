package com.stackstone.study.rxjava.core;

/**
 * Copyright 2021 PatSnap All rights reserved.
 * AbstractObservableWithUpStream
 *
 * @author LiLei
 * @date 2021/8/23
 * @since 1.0.0
 */
public abstract class AbstractObservableWithUpStream<T, U> extends Observable<U>{
    protected final ObservableSource<T> source;

    public AbstractObservableWithUpStream(ObservableSource<T> source) {
        this.source = source;
    }
}
