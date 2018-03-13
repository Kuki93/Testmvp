package com.example.helpme.library.rxretrofit;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.helpme.library.util.AppLogMessageUtils;
import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * @Created by helpme on 2018/3/11.
 * @Description
 */
public abstract class DefaultObserver<T> extends DisposableObserver<T> {
    
    private Context mContext;
    
    public DefaultObserver(Context context) {
        mContext = context;
    }
    
    public DefaultObserver(Context context, boolean isShowLoading) {
        mContext = context;
        if (isShowLoading)
            onShowLoadingView();
    }
    
    public void onShowLoadingView() {
        
    }
    
    public void onDismissLoadingView(){
       
    }
    
    public boolean isShowLoading() {
        return false;
    }
    
    
    @Override
    public void onNext(T response) {
        if (isShowLoading())
            onDismissLoadingView();
        onSuccess(response);
    }
    
    @Override
    public void onError(Throwable e) {
        Toast.makeText(mContext, "请求失败"+e, Toast.LENGTH_SHORT).show();
        Log.d("DefaultObserver", "onError" + e);
        if (e instanceof HttpException) {     //   HTTP错误
            onHandlerError(ExceptionReason.BAD_NETWORK);
            AppLogMessageUtils.e("HTTP错误");
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {   //   连接错误
            onHandlerError(ExceptionReason.CONNECT_ERROR);
            AppLogMessageUtils.e("连接错误");
        } else if (e instanceof InterruptedIOException) {   //  连接超时
            onHandlerError(ExceptionReason.CONNECT_TIMEOUT);
            AppLogMessageUtils.e("连接超时");
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException 
                || e instanceof MalformedJsonException) {   //  解析错误
            onHandlerError(ExceptionReason.PARSE_ERROR);
            AppLogMessageUtils.e("解析错误");
        } else {
            onHandlerError(ExceptionReason.UNKNOWN_ERROR);
            AppLogMessageUtils.e("未知错误");
        }
    }
    
    @Override
    public void onComplete() {
    }
    
    /**
     * 请求成功
     *
     * @param response 服务器返回的数据
     */
    public abstract void onSuccess(T response);
    
    /**
     * 
     * @param reason 异常
     */
    public abstract void onHandlerError(ExceptionReason reason);
    
    /**
     * 服务器返回数据，但响应码不为200
     *
     * @param response 服务器返回的数据
     */
    public void onFail(T response) {
        
    }
    
    
    /**
     * 请求网络失败原因
     */
    public enum ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR,
    }
}