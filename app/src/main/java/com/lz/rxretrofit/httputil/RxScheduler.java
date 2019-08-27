package com.lz.rxretrofit.httputil;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxScheduler<T> implements ObservableTransformer<T,T> {
    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
