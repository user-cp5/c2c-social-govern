package com.zhss.c2c.social.govern.report.controller;

import com.zhss.c2c.social.govern.report.hystrix.CommandHelloWorld;
import com.zhss.c2c.social.govern.report.hystrix.GetCityNameCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: LiaoLingJian
 * @Description: ${description}
 * @Date: 2021/8/18 19:40
 * @Version: 1.0
 */

@RestController
public class TestController2 {


    @GetMapping("/sayHello2")
    public String report(String name) {
       // String s = new CommandHelloWorld("Bob").execute();
       // Future<String> s = new CommandHelloWorld("Bob").queue();
       // Observable<String> s = new CommandHelloWorld("Bob").observe();

        //熔断框架类
        String execute = null;
        String[] split = name.split(",");
        try {

            for (int a =0 ;a<split.length;a++){
                CommandHelloWorld hystrixCommanHelloWorld = new CommandHelloWorld(split[a]);
                //调用线程池种的线程去执行任务，通过线程池隔离资源
                execute += hystrixCommanHelloWorld.execute();
                
                
                System.out.println("查看是否再次调用： = " +hystrixCommanHelloWorld.isResponseFromCache() );
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("降级熔断策略 = " + e.toString() );
            return "降级熔断策略 = " + e.toString();
        }






       // GetCityNameCommand getCityNameCommand = new GetCityNameCommand("1");

       // String cityName = getCityNameCommand.execute();

       // CommandHelloWorld2 commandHelloWorld2 = new CommandHelloWorld2(name.split(","));

       // Observable<String> stringObservable = commandHelloWorld2.toObservable();//延迟执行
        /* Observable<String> observe = commandHelloWorld2.observe();
        Subscription subscribe = observe.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("返回数据： = " +111111 );
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(String s) {
                System.out.println("看看是啥数据呢 = " +s );
            }
        });*/
        //System.out.println("返回值： = " + subscribe.toString());
        //return ex.toString();
        return execute;
    }
}
