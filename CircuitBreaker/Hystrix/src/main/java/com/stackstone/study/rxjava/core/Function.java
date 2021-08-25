package com.stackstone.study.rxjava.core;

/**
 * Copyright 2021 PatSnap All rights reserved.
 * Function
 *
 * @author LiLei
 * @date 2021/8/23
 * @since 1.0.0
 */
public interface Function<T, R> {
    R apply(T t);
}
