package com.example.helpme.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.helpme.mvp.factory.PresenterMvpFactory;
import com.example.helpme.mvp.factory.PresenterMvpFactoryImpl;
import com.example.helpme.mvp.presenter.BaseMvpPresenter;
import com.example.helpme.mvp.proxy.BaseMvpProxy;
import com.example.helpme.mvp.proxy.PresenterProxyInterface;


/**
 * @author helpme
 * @date 2018/1/23
 * @description 继承Fragment的MvpFragment基类
 */
public abstract class AbstractMvpFragment<V extends BaseMvpView, P extends BaseMvpPresenter<V>> extends 
        Fragment implements PresenterProxyInterface<V, P> {
    
    /**
     * 调用onSaveInstanceState时存入Bundle的key
     */
    private static final String PRESENTER_SAVE_KEY = "presenter_save_key";
    /**
     * 创建被代理对象,传入默认Presenter的工厂
     */
    private BaseMvpProxy<V, P> mProxy = new BaseMvpProxy<>(PresenterMvpFactoryImpl.<V,
            P>createFactory(getClass()));
    
    protected P mPresenter;
    
    protected abstract int getLayoutId();
    
    protected abstract void onInitViewAndData();
    
    protected abstract void onAddListener();
    
    protected View rootView;
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mProxy.onRestoreInstanceState(savedInstanceState.getBundle(PRESENTER_SAVE_KEY));
        }
        mPresenter = mProxy.getMvpPresenter(getContext().getApplicationContext(), (V) this);
        Log.e("perfect-mvp", "V onCreate");
        Log.e("perfect-mvp", "V onCreate mProxy = " + mProxy);
        Log.e("perfect-mvp", "V onCreate this = " + this.hashCode());
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable 
            Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false);
            onInitViewAndData();
            onAddListener();
        }
        return rootView;
    }
    
    @Override
    public void onResume() {
        super.onResume();
        mProxy.onResume();
    }
    
    @Override
    public void onStop() {
        super.onStop();
        mProxy.onStop();
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        mProxy.onDestroy();
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(PRESENTER_SAVE_KEY, mProxy.onSaveInstanceState());
    }
    
    
    /**
     * 可以实现自己PresenterMvpFactory工厂
     *
     * @param presenterFactory PresenterFactory类型
     */
    @Override
    public void setPresenterFactory(PresenterMvpFactory<V, P> presenterFactory) {
        mProxy.setPresenterFactory(presenterFactory);
    }
    
    
    /**
     * 获取创建Presenter的工厂
     *
     * @return PresenterMvpFactory类型
     */
    @Override
    public PresenterMvpFactory<V, P> getPresenterFactory() {
        return mProxy.getPresenterFactory();
    }
    
 
    
}
