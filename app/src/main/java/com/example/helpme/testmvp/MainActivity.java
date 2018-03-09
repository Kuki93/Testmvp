package com.example.helpme.testmvp;

import android.widget.Toast;

import com.example.helpme.mvp.factory.CreatePresenter;
import com.example.helpme.mvp.view.AbstractMvpActivitiy;

@CreatePresenter(p.class)
public class MainActivity extends AbstractMvpActivitiy<V,p> implements V {
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
    
    @Override
    protected void onInitViewAndData() {
    //    mPresenter.pint();
    }
    
    @Override
    protected void onAddListener() {
        
    }
    
    
    @Override
    public void print() {
        Toast.makeText(this, "222", Toast.LENGTH_LONG).show();
    }
}
