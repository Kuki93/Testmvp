package com.example.helpme.library.mvp;

import android.content.Context;

import com.example.helpme.library.base.BaseMvpPresenter;
import com.example.helpme.library.base.BaseMvpView;

/**
 * @author helpme
 * @date 2018/1/23
 * @description Persenter工厂接口
 */
public interface PresenterMvpFactory<V extends BaseMvpView, P extends BaseMvpPresenter<V>> {
    
    /**
     * 创建Presenter的接口方法
     *
     * @return 需要创建的Presenter
     */
    P createMvpPresenter(Context context, V v);
}
