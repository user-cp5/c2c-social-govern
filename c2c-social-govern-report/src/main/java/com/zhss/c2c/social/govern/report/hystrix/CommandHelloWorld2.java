package com.zhss.c2c.social.govern.report.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * @Author: LiaoLingJian
 * @Description: ${description}
 * @Date: 2021/8/18 21:23
 * @Version: 1.0
 */
public class CommandHelloWorld2 extends HystrixObservableCommand<String> {

    private final String [] names;

    public CommandHelloWorld2(String[] names) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup2"));
        this.names = names;
    }

    @Override
    protected Observable<String> construct() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> observer) {
                try {
                    if (!observer.isUnsubscribed()) {
                        // a real example would do work like a network call here

                        for (String name: names) {
                            //调用服务
                            observer.onNext("Hello");
                            observer.onNext(name + "!");

                        }
                        observer.onCompleted();

                    }
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        } ).subscribeOn(Schedulers.io());
    }
}
