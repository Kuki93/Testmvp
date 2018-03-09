package com.example.helpme.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.helpme.mvp.factory.PresenterMvpFactory;
import com.example.helpme.mvp.factory.PresenterMvpFactoryImpl;
import com.example.helpme.mvp.presenter.BaseMvpPresenter;
import com.example.helpme.mvp.proxy.BaseMvpProxy;
import com.example.helpme.mvp.proxy.PresenterProxyInterface;


/**
 * @author helpme
 * @date 2018/1/23
 * @description 继承自AppCompatActivity的基类MvpAppCompatActivity
 * 使用代理模式来代理Presenter的创建、销毁、绑定、解绑以及Presenter的状态保存,其实就是管理Presenter的生命周期
 */
public abstract class AbstractMvpAppCompatActivity<V extends BaseMvpView, P extends BaseMvpPresenter<V>>
        extends AppCompatActivity implements PresenterProxyInterface<V, P> {
    
    private static final String PRESENTER_SAVE_KEY = "presenter_save_key";
    /**
     * 创建被代理对象,传入默认Presenter的工厂
     */
    private BaseMvpProxy<V, P> mProxy = new BaseMvpProxy<>(PresenterMvpFactoryImpl.<V, P>createFactory(getClass()));
    
    protected P mPresenter;
    
    protected abstract int getLayoutId();
    
    protected abstract void onInitViewAndData();
    
    protected abstract void onAddListener();
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mProxy.onRestoreInstanceState(savedInstanceState.getBundle(PRESENTER_SAVE_KEY));
        }
        setContentView(getLayoutId());
        Log.e("perfect-mvp", "V onCreate");
        Log.e("perfect-mvp", "V onCreate mProxy = " + mProxy);
        Log.e("perfect-mvp", "V onCreate this = " + this.hashCode());
        mPresenter = mProxy.getMvpPresenter(getApplicationContext(), (V) this);
        onInitViewAndData();
        onAddListener();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        Log.e("perfect-mvp", "V onResume");
        mProxy.onResume();
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        Log.e("perfect-mvp", "V onStop");
        mProxy.onStop();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("perfect-mvp", "V onDestroy = " + isChangingConfigurations());
        mProxy.onDestroy();
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("perfect-mvp", "V onSaveInstanceState");
        outState.putBundle(PRESENTER_SAVE_KEY, mProxy.onSaveInstanceState());
    }
    
    @Override
    public void setPresenterFactory(PresenterMvpFactory<V, P> presenterFactory) {
        Log.e("perfect-mvp", "V setPresenterFactory");
        mProxy.setPresenterFactory(presenterFactory);
    }
    
    @Override
    public PresenterMvpFactory<V, P> getPresenterFactory() {
        Log.e("perfect-mvp", "V getPresenterFactory");
        return mProxy.getPresenterFactory();
    }
}
