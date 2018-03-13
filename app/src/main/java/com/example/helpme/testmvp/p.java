package com.example.helpme.testmvp;

import android.content.Context;
import android.widget.Toast;

import com.example.helpme.library.base.BaseMvpView;
import com.example.helpme.library.base.BaseRxMvpPresenter;
import com.example.helpme.library.rxretrofit.DefaultObserver;
import com.example.helpme.library.rxretrofit.RxSchedulers;

/**
 * @Created by helpme on 2018/3/9.
 * @Description
 */
public class P extends BaseRxMvpPresenter<V> {
    
    public P(Context context, BaseMvpView view) {
        super(context, view);
        //        Flowable.interval(2, 1, TimeUnit.SECONDS)
        //                //这里的x是0,1,2,3...
        //                .subscribe(x -> Log.i("tag", String.valueOf(x)));
        // Flowable.range(0,5).subscribe();
        //long类型范围
        //        Flowable.rangeLong(Integer.MAX_VALUE,  5L)
        //                .subscribe(x -> Log.i("tag", String.valueOf(x)));
        
        //        Flowable<String> flowable = Flowable.defer(new Callable<Publisher<String>>() {
        //            @Override
        //            public Publisher<String> call() throws Exception {
        //                return Flowable.just("1", "2");
        //            }
        //        });
        //
        ////订阅第一个观察者
        //        flowable.subscribe(str -> Log.i("tag1", str));
        ////订阅第二个观察者
        //        flowable.subscribe(str -> Log.i("tag2", str));
        getMvpView().print("safasfsaf");
    }
    
    public void getData() {
        ApiService api = Retrofit2Factory.getApiService(ApiService.class);
        DefaultObserver<String> observer = api.getWeather("101240101").compose(RxSchedulers
                .compose())
                .subscribeWith(new DefaultObserver<String>(mContext) {
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                        getMvpView().print("请求成功");
                    }
                    
                    @Override
                    public void onHandlerError(ExceptionReason reason) {
                        
                    }
                });
        registerSubscribe(observer);
    }
    
    //    {
    //        Completable completable = Completable.create(new CompletableOnSubscribe() {
    //            @Override
    //            public void subscribe(CompletableEmitter emitter) throws Exception {
    //                
    //                
    //            }
    //        });
    //        
    //        CompletableObserver observer = new CompletableObserver() {
    //            @Override
    //            public void onSubscribe(Disposable d) {
    //                
    //            }
    //            
    //            @Override
    //            public void onComplete() {
    //                
    //            }
    //            
    //            @Override
    //            public void onError(Throwable e) {
    //                
    //            }
    //        };
    //        
    //        completable.subscribe(observer);
    //        
    //        completable.subscribe(new Action() {
    //            @Override
    //            public void run() throws Exception {
    //                
    //            }
    //        }, new Consumer<Throwable>() {
    //            @Override
    //            public void accept(Throwable throwable) throws Exception {
    //                
    //            }
    //        });
    //        
    //        Observable<String> observable = null;
    //        observable.subscribe(new Consumer<String>() {
    //            @Override
    //            public void accept(String s) throws Exception {
    //                
    //            }
    //        }, new Consumer<Throwable>() {
    //            @Override
    //            public void accept(Throwable throwable) throws Exception {
    //                
    //            }
    //        }, new Action() {
    //            @Override
    //            public void run() throws Exception {
    //                
    //            }
    //        }, new Consumer<Disposable>() {
    //            @Override
    //            public void accept(Disposable disposable) throws Exception {
    //                
    //            }
    //        });
    //        
    //        Maybe<String> maybe = Maybe.create(new MaybeOnSubscribe<String>() {
    //            @Override
    //            public void subscribe(MaybeEmitter<String> e) throws Exception {
    //                e.onSuccess("test");//发送一个数据的情况，或者onError，不需要再调用onComplete
    // (调用了也不会触发onComplete回调方法)
    //                //e.onComplete();//不需要发送数据的情况，或者onError
    //            }
    //        });
    //        
    //
    ////订阅观
    //        Observable.timer(1000, TimeUnit.MILLISECONDS)
    //                .subscribe(new Observer<Long>() {
    //                    @Override
    //                    public void onSubscribe(Disposable d) {
    //                        
    //                    }
    //                    
    //                    @Override
    //                    public void onNext(Long aLong) {
    //                        
    //                    }
    //                    
    //                    @Override
    //                    public void onError(Throwable e) {
    //                        
    //                    }
    //                    
    //                    @Override
    //                    public void onComplete() {
    //                        
    //                    }
    //                });
    //    }
    
    
}
