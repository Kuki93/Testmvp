package com.example.helpme.library.rxretrofit;

import android.content.Context;

import com.example.helpme.library.util.AppLogMessageUtils;
import com.example.helpme.library.util.AppNetworkUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Created by helpme on 2018/3/11.
 * @Description
 */
public class RetrofitFactory {
    
    private static String BASE_URL;
    private static int DEFAULT_TIMEOUT;
    
    private static Context context;
    
    private Retrofit retrofit;
    
    //  创建单例
    private static class SingletonHolder {
        private static final RetrofitFactory INSTANCE = new RetrofitFactory();
    }
    
    
    public void initRetrofit(Context context,String BASE_URL, int DEFAULT_TIMEOUT) {
        RetrofitFactory.context = context;
        RetrofitFactory.BASE_URL = BASE_URL;
        RetrofitFactory.DEFAULT_TIMEOUT = DEFAULT_TIMEOUT;
    }
    
    public static <T> T getApiService(Class<T> service) {
        return SingletonHolder.INSTANCE.retrofit.create(service);
    }
    
    private RetrofitFactory() {
    
        File cacheFile = new File(context.getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new HttpCacheInterceptor())
                .addInterceptor(new HttpLoggingInterceptor(message -> {
                    try {
                        String text = URLDecoder.decode(message, "utf-8");
                        AppLogMessageUtils.e("OKHttp-----", text);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        AppLogMessageUtils.e("OKHttp-----", message);
                    }
                }).setLevel(HttpLoggingInterceptor.Level.BASIC))
                .cache(cache)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        
        
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    
    
    private class HttpCacheInterceptor implements Interceptor {
        
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            
            if (!AppNetworkUtils.isNetworkConnected(context)){
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                AppLogMessageUtils.d("Okhttp", "no network");
            }
            
            Response originalResponse = chain.proceed(request);
            if (AppNetworkUtils.isNetworkConnected(context)) {
                //有网的时候读接口上的@Headers里的配置，可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }
        }
    }
}
