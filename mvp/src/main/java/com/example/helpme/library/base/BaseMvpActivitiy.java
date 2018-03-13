package com.example.helpme.library.base;

import android.os.Bundle;

import com.example.helpme.library.mvp.BaseMvpProxy;
import com.example.helpme.library.mvp.PresenterMvpFactory;
import com.example.helpme.library.mvp.PresenterMvpFactoryImpl;
import com.example.helpme.library.mvp.PresenterProxyInterface;
import com.example.helpme.library.util.AppLogMessageUtils;

/**
 * @author helpme
 * @date 2018/1/23
 * @description 继承自Activity的基类MvpActivity
 * 使用代理模式来代理Presenter的创建、销毁、绑定、解绑以及Presenter的状态保存,其实就是管理Presenter的生命周期
 */
public abstract class BaseMvpActivitiy<V extends BaseMvpView, P extends BaseMvpPresenter<V>>
        extends BaseActivity implements PresenterProxyInterface<V, P> {
    
    private static final String PRESENTER_SAVE_KEY = "presenter_save_key";
    /**
     * 创建被代理对象,传入默认Presenter的工厂
     */
    private BaseMvpProxy<V, P> mProxy = new BaseMvpProxy<>(PresenterMvpFactoryImpl.<V,
            P>createFactory(getClass()));
    
    protected P mPresenter;
    
    /**
     * 加载数据
     */
    protected abstract void onMvpLoadData();
    
    
    @Override
    protected final void onLoadData() {
        AppLogMessageUtils.e("perfect-mvp", "V getMvpPresenter");
        mPresenter = mProxy.getMvpPresenter(getApplicationContext(), (V) this);
        onMvpLoadData();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        AppLogMessageUtils.e("perfect-mvp", "V onRestoreInstanceState");
        mProxy.onRestoreInstanceState(savedInstanceState.getBundle(PRESENTER_SAVE_KEY));
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        AppLogMessageUtils.e("perfect-mvp", "V onResume");
        mProxy.onResume();
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        AppLogMessageUtils.e("perfect-mvp", "V onStop");
        mProxy.onStop(isFinishing());
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppLogMessageUtils.e("perfect-mvp", "V onDestroy = ");
        mProxy.onDestroy();
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        AppLogMessageUtils.e("perfect-mvp", "V onSaveInstanceState");
        outState.putBundle(PRESENTER_SAVE_KEY, mProxy.onSaveInstanceState());
    }
    
    @Override
    public void setPresenterFactory(PresenterMvpFactory<V, P> presenterFactory) {
        AppLogMessageUtils.e("perfect-mvp", "V setPresenterFactory");
        mProxy.setPresenterFactory(presenterFactory);
    }
    
    @Override
    public PresenterMvpFactory<V, P> getPresenterFactory() {
        AppLogMessageUtils.e("perfect-mvp", "V getPresenterFactory");
        return mProxy.getPresenterFactory();
    }
    
}
