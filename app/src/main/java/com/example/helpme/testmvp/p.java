package com.example.helpme.testmvp;

import android.content.Context;

import com.example.helpme.mvp.presenter.BaseMvpPresenter;
import com.example.helpme.mvp.view.BaseMvpView;

/**
 * @Created by helpme on 2018/3/9.
 * @Description
 */
public class p extends BaseMvpPresenter<V> {
  
    
    public p(Context context, BaseMvpView view) {
        super(context, view);
        getMvpView().print();
    }
    
    public void pint() {
        getMvpView().print();
       // Toast.makeText(mContext, "111", Toast.LENGTH_LONG).show();
    }
}
