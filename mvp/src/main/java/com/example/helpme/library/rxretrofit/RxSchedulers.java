package com.example.helpme.library.rxretrofit;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Created by helpme on 2018/3/11.
 * @Description
 */
public class RxSchedulers {
    public static <T> ObservableTransformer<T, T> compose() {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    
                })
                .observeOn(AndroidSchedulers.mainThread());
    }
}
