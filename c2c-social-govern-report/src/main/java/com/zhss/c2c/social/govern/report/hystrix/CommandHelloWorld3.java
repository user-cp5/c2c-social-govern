package com.zhss.c2c.social.govern.report.hystrix;

import com.netflix.hystrix.*;

/**
 * @Author: LiaoLingJian
 * @Description: ${description}
 * @Date: 2021/8/18 20:23   资源隔离
 * @Version: 1.0
 */
public class CommandHelloWorld3 extends HystrixCommand<String> {

    private final String name;


    /**
     * 降级措施  默认函数或者默认值  失败降级措施
     * @return
     */
    @Override
    protected String getFallback() {
        //System.out.println("降级措施  我是降级措施 我被调用了 = " +  name);
        return "我是getFallback降级措施。。。。";
    }

    public CommandHelloWorld3(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("CommandHelloWorld3Group"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("CommandHelloWorld3"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("CommandHelloWorld3Pool"))
                //设置只能两个线程调用该部分接口的资源
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(5)
               //设置队列，任务会先进入队列种，然后才会调用资源池
                .withMaxQueueSize(10)
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
                        //接口请求超时时间
                        .withExecutionTimeoutInMilliseconds(200)
                        .withExecutionTimeoutEnabled(true)
                )



        );
        this.name = name;
    }

    /**
     * 测试接口超时
     * @return
     * @throws Exception
     */
    @Override
    protected String run() throws Exception {
        //调用资源
        String cityId = "1";

       // int a = 1/0;  //模拟失败调用


        /*if(Integer.valueOf(name)%2==0){
            int a = 1/0;  //模拟失败
        }*/

        Thread.sleep(300);

        //信号量隔离
        System.out.println("这是一次新的请求 当前时间为: = " + System.currentTimeMillis()+ name);



        return "Hello " + name + "!所在的城市为：";
    }

   /* @Override
    protected String getCacheKey() {
        return "name:"+name;
    }*/
}
