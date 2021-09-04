package com.zhss.c2c.social.govern.report.controller;

import com.zhss.c2c.social.govern.report.domain.ReportTask;
import com.zhss.c2c.social.govern.report.hystrix.CommandHelloWorld;
import com.zhss.c2c.social.govern.report.hystrix.CommandHelloWorld2;
import com.zhss.c2c.social.govern.report.hystrix.CommandHelloWorld3;
import com.zhss.c2c.social.govern.report.hystrix.GetCityNameCommand;
import org.apache.dubbo.common.function.ThrowableAction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;
import rx.Observer;
import rx.Subscription;

import java.util.concurrent.Future;

/**
 * @Author: LiaoLingJian
 * @Description: ${description}
 * @Date: 2021/8/18 19:40
 * @Version: 1.0
 */

@RestController
public class TestController {


    @GetMapping("/sayHello")
    public String report(String name) throws Exception {
       // String s = new CommandHelloWorld("Bob").execute();
       // Future<String> s = new CommandHelloWorld("Bob").queue();
       // Observable<String> s = new CommandHelloWorld("Bob").observe();

        //熔断框架类
        String execute = null;
        //短路测试
        for(int a =0 ;a<100;a++){
            CommandHelloWorld hystrixCommanHelloWorld = new CommandHelloWorld(String.valueOf(a));
            //调用线程池种的线程去执行任务，通过线程池隔离资源
            execute = hystrixCommanHelloWorld.execute();
            if(a==50){
                Thread.sleep(6000);
            }

            System.out.println("本次调用的返回值为 = " + execute+"name:"+a );
        }


        CommandHelloWorld hystrixCommanHelloWorld = new CommandHelloWorld(name);
        //调用线程池种的线程去执行任务，通过线程池隔离资源
        execute = hystrixCommanHelloWorld.execute();
       /* try {

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("降级熔断策略 = " + e.toString() );
            return "降级熔断策略 = " + e.toString();
        }*/






        GetCityNameCommand getCityNameCommand = new GetCityNameCommand("1");

        String cityName = getCityNameCommand.execute();

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
        return execute+cityName;
    }


    @GetMapping("/sayHello3")
    public String report2(String name) throws Exception {

        for (int i = 0; i < 20; i++) {
            new Thread("custom11111111111111") {
                @Override
                public void run() {

                    CommandHelloWorld3 hystrixCommanHelloWorld = new CommandHelloWorld3(String.valueOf(Thread.currentThread().getName()));
                    //调用线程池种的线程去执行任务，通过线程池隔离资源
                    String execute = hystrixCommanHelloWorld.execute();

                    System.out.println("111111111本次调用的返回值为 = " + execute + "name:"+Thread.currentThread().getName() );
                }

            }.start();
        }
        for (int i = 0; i < 30; i++) {
            new Thread("custom2222222222222") {
                @Override
                 public void run() {

                    CommandHelloWorld3 hystrixCommanHelloWorld = new CommandHelloWorld3(String.valueOf(Thread.currentThread().getName()));
                    //调用线程池种的线程去执行任务，通过线程池隔离资源
                    String execute = hystrixCommanHelloWorld.execute();

                    System.out.println("22222222本次调用的返回值为 = " + execute + "name:" + Thread.currentThread().getName());
                }


            }.start();
        }

        return "success";
    }

    @GetMapping("/sayHello4")
    public String report4(String name) throws Exception {
        CommandHelloWorld3 hystrixCommanHelloWorld = new CommandHelloWorld3(String.valueOf(Thread.currentThread().getName()));
        //调用线程池种的线程去执行任务，通过线程池隔离资源
        String execute = hystrixCommanHelloWorld.execute();

        System.out.println("22222222本次调用的返回值为 = " + execute + "name:" + Thread.currentThread().getName());
        return execute;
    }
}
