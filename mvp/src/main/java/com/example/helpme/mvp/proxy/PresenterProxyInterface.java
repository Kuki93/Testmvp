package com.example.helpme.mvp.proxy;

import com.example.helpme.mvp.factory.PresenterMvpFactory;
import com.example.helpme.mvp.presenter.BaseMvpPresenter;
import com.example.helpme.mvp.view.BaseMvpView;

/**
 * @author helpme
 * @date 2018/1/23
 * @description 代理接口
 */
public interface PresenterProxyInterface<V extends BaseMvpView, P extends BaseMvpPresenter<V>> {
    
    
    /**
     * 设置创建Presenter的工厂
     *
     * @param presenterFactory PresenterFactory类型
     */
    void setPresenterFactory(PresenterMvpFactory<V, P> presenterFactory);
    
    /**
     * 获取Presenter的工厂类
     *
     * @return 返回PresenterMvpFactory类型
     */
    PresenterMvpFactory<V, P> getPresenterFactory();
    
    
}
