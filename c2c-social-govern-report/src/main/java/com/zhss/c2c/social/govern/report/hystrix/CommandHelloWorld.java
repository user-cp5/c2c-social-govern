package com.zhss.c2c.social.govern.report.hystrix;

import com.netflix.hystrix.*;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

/**
 * @Author: LiaoLingJian
 * @Description: ${description}
 * @Date: 2021/8/18 20:23   资源隔离
 * @Version: 1.0
 */
public class CommandHelloWorld extends HystrixCommand<String> {

    private final String name;


    /**
     * 降级措施  默认函数或者默认值  失败降级措施
     * @return
     */
    @Override
    protected String getFallback() {
        return "我是getFallback降级措施。。。。";
    }

    public CommandHelloWorld(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("CommandHelloWorldGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("CommandHelloWorld"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("CommandHelloWorldPool"))
                //设置只能两个线程调用该部分接口的资源
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(2)
               //设置队列，任务会先进入队列种，然后才会调用资源池
                .withMaxQueueSize(3)
                )
                //断路器
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        //开启短路
                        .withCircuitBreakerEnabled(true)
                        //每秒最多多少请求
                        .withCircuitBreakerRequestVolumeThreshold(30)
                        //错误率达到多少开启短路
                        .withCircuitBreakerErrorThresholdPercentage(40)
                        //时间范围内  过了找个时间会重试
                        .withCircuitBreakerSleepWindowInMilliseconds(5000)
                )



        );
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        //调用资源
        String cityId = "1";

       // int a = 1/0;  //模拟失败调用


        if(Integer.valueOf(name)%2==0){
            int a = 1/0;  //模拟失败
        }

        Thread.sleep(200);

        //信号量隔离
        System.out.println("这是一次新的请求 当前时间为: = " + System.currentTimeMillis()+ name);



        return "Hello " + name + "!所在的城市为：";
    }

   /* @Override
    protected String getCacheKey() {
        return "name:"+name;
    }*/
}
