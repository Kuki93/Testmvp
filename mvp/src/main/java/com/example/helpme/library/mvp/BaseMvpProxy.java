package com.example.helpme.library.mvp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.helpme.library.base.BaseMvpPresenter;
import com.example.helpme.library.base.BaseMvpView;


/**
 * @author helpme
 * @date 2018/1/23
 * @description 代理实现类，用来管理Presenter的生命周期，还有和view之间的关联
 */
public class BaseMvpProxy<V extends BaseMvpView, P extends BaseMvpPresenter<V>> implements
        PresenterProxyInterface<V, P> {
    
    /**
     * 获取onSaveInstanceState中bundle的key
     */
    private static final String PRESENTER_KEY = "presenter_key";
    
    /**
     * Presenter工厂类
     */
    private PresenterMvpFactory<V, P> mFactory;
    private P mPresenter;
    
    public BaseMvpProxy(PresenterMvpFactory<V, P> presenterMvpFactory) {
        this.mFactory = presenterMvpFactory;
    }
    
    /**
     * 设置Presenter的工厂类,这个方法只能在创建Presenter之前调用,也就是调用getMvpPresenter()之前，如果Presenter已经创建则不能再修改
     *
     * @param presenterFactory PresenterFactory类型
     */
    @Override
    public void setPresenterFactory(PresenterMvpFactory<V, P> presenterFactory) {
        if (mPresenter != null) {
            throw new IllegalArgumentException("这个方法只能在getMvpPresenter()" +
                    "之前调用，如果Presenter已经创建则不能再修改");
        }
        this.mFactory = presenterFactory;
    }
    
    /**
     * 获取Presenter的工厂类
     *
     * @return PresenterMvpFactory类型
     */
    @Override
    public PresenterMvpFactory<V, P> getPresenterFactory() {
        return mFactory;
    }
    
    /**
     * 获取创建的Presenter
     *
     * @return 指定类型的Presenter
     * 如果之前创建过，而且是以外销毁则从Bundle中恢复
     */
    public P getMvpPresenter(Context mContext, V mvpView) {
        Log.e("perfect-mvp", "Proxy getMvpPresenter");
        if (mFactory != null && mPresenter == null) {
            mPresenter = mFactory.createMvpPresenter(mContext, mvpView);
        }
        Log.e("perfect-mvp", "Proxy getMvpPresenter = " + mPresenter);
        return mPresenter;
    }
    
    /**
     * 绑定Presenter和view
     */
    public void onResume() {
        Log.e("perfect-mvp", "Proxy onResume");
        if (mPresenter != null) {
            mPresenter.onResumePersenter();
        }
    }
    
    
    /**
     * 切换到后台的时候调用
     */
    public void onStop(boolean isFinishing) {
        Log.e("perfect-mvp", "Proxy onStop = ");
        if (mPresenter != null) {
            if (isFinishing)
                onDetachMvpView();
            mPresenter.onStopPersenter();
        }
    }
    
    /**
     * 销毁Presenter持有的View
     */
    private void onDetachMvpView() {
        Log.e("perfect-mvp", "Proxy onDetachMvpView = ");
        if (mPresenter != null) {
            mPresenter.onDetachMvpView();
        }
    }
    
    /**
     * 销毁Presenter
     */
    public void onDestroy() {
        Log.e("perfect-mvp", "Proxy onDestroy = ");
        if (mPresenter != null) {
            onDetachMvpView();
            mPresenter.onDestroyPersenter();
            mPresenter = null;
        }
    }
    
    /**
     * 意外销毁的时候调用
     *
     * @return Bundle，存入回调给Presenter的Bundle和当前Presenter的id
     */
    public Bundle onSaveInstanceState() {
        Log.e("perfect-mvp", "Proxy onSaveInstanceState = ");
        Bundle bundle = new Bundle();
        if (mPresenter != null) {
            Bundle presenterBundle = new Bundle();
            //回调Presenter
            mPresenter.onSaveInstanceState(presenterBundle);
            bundle.putBundle(PRESENTER_KEY, presenterBundle);
        }
        return bundle;
    }
    
    /**
     * 意外关闭恢复Presenter
     *
     * @param savedInstanceState 意外关闭时存储的Bundler
     */
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.e("perfect-mvp", "Proxy onRestoreInstanceState = ");
        Log.e("perfect-mvp", "Proxy onRestoreInstanceState Presenter = " + mPresenter);
        mPresenter.onRestoreInstanceState(savedInstanceState == null ? null : savedInstanceState
                .getBundle
                (PRESENTER_KEY));
    }
}
