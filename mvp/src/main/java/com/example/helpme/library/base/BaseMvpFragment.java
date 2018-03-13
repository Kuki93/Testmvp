package com.example.helpme.library.base;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.example.helpme.library.mvp.BaseMvpProxy;
import com.example.helpme.library.mvp.PresenterMvpFactory;
import com.example.helpme.library.mvp.PresenterMvpFactoryImpl;
import com.example.helpme.library.mvp.PresenterProxyInterface;
import com.example.helpme.library.util.AppLogMessageUtils;


/**
 * @author helpme
 * @date 2018/1/23
 * @description 继承Fragment的MvpFragment基类
 */
public abstract class BaseMvpFragment<V extends BaseMvpView, P extends BaseMvpPresenter<V>> extends
        BaseFragment implements PresenterProxyInterface<V, P> {
    
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
    
    /**
     * 加载数据
     */
    protected abstract void onMvpLoadData();
    
    
    @Override
    protected final void onLoadData() {
        AppLogMessageUtils.e("perfect-mvp", "V getMvpPresenter");
        mPresenter = mProxy.getMvpPresenter(getContext().getApplicationContext(), (V) this);
        onMvpLoadData();
    }
    
    @Override
    protected  void onRestoreInstanceState(@NonNull Bundle savedState) {
        AppLogMessageUtils.e("perfect-mvp", "V onRestoreInstanceState");
        mProxy.onRestoreInstanceState(savedState.getBundle(PRESENTER_SAVE_KEY));
    }
    
    @Override
    public void onResume() {
        super.onResume();
        AppLogMessageUtils.e("perfect-mvp", "V onResume");
        mProxy.onResume();
    }
    
    @Override
    public void onStop() {
        super.onStop();
        AppLogMessageUtils.e("perfect-mvp", "V onStop");
        mProxy.onStop(((BaseAppCompatActivity)mContext).isFinishing());
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        AppLogMessageUtils.e("perfect-mvp", "V onDestroy = ");
        mProxy.onDestroy();
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        AppLogMessageUtils.e("perfect-mvp", "V onSaveInstanceState");
        outState.putBundle(PRESENTER_SAVE_KEY, mProxy.onSaveInstanceState());
    }
    
    
    /**
     * 可以实现自己PresenterMvpFactory工厂
     *
     * @param presenterFactory PresenterFactory类型
     */
    @Override
    public void setPresenterFactory(PresenterMvpFactory<V, P> presenterFactory) {
        AppLogMessageUtils.e("perfect-mvp", "V setPresenterFactory");
        mProxy.setPresenterFactory(presenterFactory);
    }
    
    
    /**
     * 获取创建Presenter的工厂
     *
     * @return PresenterMvpFactory类型
     */
    @Override
    public PresenterMvpFactory<V, P> getPresenterFactory() {
        AppLogMessageUtils.e("perfect-mvp", "V getPresenterFactory");
        return mProxy.getPresenterFactory();
    }
    
}
