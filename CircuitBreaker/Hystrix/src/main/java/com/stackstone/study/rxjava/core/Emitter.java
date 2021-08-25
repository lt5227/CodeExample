package com.stackstone.study.rxjava.core;

/**
 * Copyright 2021 PatSnap All rights reserved.
 * Emitter
 *
 * @author LiLei
 * @date 2021/8/23
 * @since 1.0.0
 */
public interface Emitter<T> {
    /**
     * On next.
     *
     * @param t the t
     */
    void onNext(T t);

    /**
     * On complete.
     */
    void onComplete();

    /**
     * On error.
     *
     * @param throwable the throwable
     */
    void onError(Throwable throwable);
}
