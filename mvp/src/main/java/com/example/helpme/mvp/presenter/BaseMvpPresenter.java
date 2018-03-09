package com.example.helpme.mvp.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.helpme.mvp.view.BaseMvpView;

import java.lang.ref.WeakReference;

/**
 * @author helpme
 * @date 2018/1/23
 * @description 所有Presenter的基类，并不强制实现这些方法，有需要在重写
 */
public abstract class BaseMvpPresenter<V extends BaseMvpView> {
    
    protected Context mContext;
    
    /**
     * V层view
     */
    private WeakReference<V> mView;
    
    public BaseMvpPresenter(Context context, BaseMvpView view) {
        mContext = context;
        mView = new WeakReference<>((V) view);
    }
    
    /**
     * 解除绑定View
     */
    public final void onDetachMvpView() {
        if (mView != null) {
            mView.clear();
            mView = null;
        }
        Log.e("perfect-mvp", "P onDetachMvpView = ");
    }
    
    /**
     * 获取V层接口View
     *
     * @return 返回当前MvpView
     */
    protected final V getMvpView() {
        return mView.get();
    }
    
    /**
     * Presenter被创建后调用
     *
     * @param savedState 被意外销毁后重建后的Bundle
     */
    public void onCreatePersenter(@Nullable Bundle savedState) {
        Log.e("perfect-mvp", "P onCreatePersenter = ");
    }
    
    /**
     * Presenter
     */
    public void onResumePersenter() {
        Log.e("perfect-mvp", "P onResume = ");
    }
    
    /**
     * Presenter被销毁时调用
     */
    public void onStopPersenter() {
        Log.e("perfect-mvp", "P onStop = ");
    }
    
    /**
     * Presenter被销毁时调用
     */
    public void onDestroyPersenter() {
        Log.e("perfect-mvp", "P onDestroy = ");
    }
    
    /**
     * 在Presenter意外销毁的时候被调用，它的调用时机和Activity、Fragment、View中的onSaveInstanceState
     * 时机相同
     *
     * @param outState
     */
    public void onSaveInstanceState(Bundle outState) {
        Log.e("perfect-mvp", "P onSaveInstanceState = ");
    }
    
}
