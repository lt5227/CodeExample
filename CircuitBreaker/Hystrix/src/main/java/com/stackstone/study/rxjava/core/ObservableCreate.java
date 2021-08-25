package com.stackstone.study.rxjava.core;

/**
 * Copyright 2021 PatSnap All rights reserved.
 * ObservableCreate
 *
 * @author LiLei
 * @date 2021/8/23
 * @since 1.0.0
 */
public class ObservableCreate<T> extends Observable<T> {
   final ObservableOnSubscribe<T> source;

    public ObservableCreate(ObservableOnSubscribe<T> source) {
        this.source = source;
    }

    @Override
    protected void subscribeActual(Observer<T> observer) {
        observer.onSubscribe();
        CreateEmitter<T> emitter = new CreateEmitter<>(observer);
        source.subscribe(emitter);
    }

    static class CreateEmitter<T> implements Emitter<T> {
        Observer<T> observer;
        boolean done;

        public CreateEmitter(Observer<T> observer) {
            this.observer = observer;
        }

        @Override
        public void onNext(T t) {
            if (done) return;
            observer.onNext(t);
        }

        @Override
        public void onComplete() {
            if (done) return;
            observer.onComplete();
            done = true;
        }

        @Override
        public void onError(Throwable throwable) {
            if (done) return;
            observer.onError(throwable);
            done = true;
        }
    }
}
