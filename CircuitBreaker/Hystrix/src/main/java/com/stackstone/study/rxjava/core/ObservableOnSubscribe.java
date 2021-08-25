package com.stackstone.study.rxjava.core;

/**
 * Copyright 2021 PatSnap All rights reserved.
 * ObservableOnSubscribe
 *
 * @author LiLei
 * @date 2021/8/23
 * @since 1.0.0
 */
public interface ObservableOnSubscribe<T> {
    void subscribe(Emitter<T> emitter);
}
