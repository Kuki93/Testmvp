package com.example.helpme.testmvp;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.helpme.library.base.BaseMvpAppCompatActivity;
import com.example.helpme.library.mvp.CreatePresenter;

import butterknife.BindView;

@CreatePresenter(P.class)
public class MainActivity extends BaseMvpAppCompatActivity<V, P> implements V {
    
    @BindView(R.id.text)
    TextView mTextView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
    
    
    @Override
    protected void onMvpLoadData() {
        
    }
    
    @Override
    protected void onAddListener() {
        mTextView.setText("sfasfdsafas");
    }
  
    
    void click(View v) {
        //  mPresenter.pint();
        Log.d("MainActivity", "click" + mPresenter);
        mPresenter.getData();
    }
    
    
    @Override
    public void print(String s) {
        mTextView.setText(s);
    }
}
