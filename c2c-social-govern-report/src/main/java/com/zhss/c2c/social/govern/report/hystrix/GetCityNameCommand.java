package com.zhss.c2c.social.govern.report.hystrix;

import com.netflix.hystrix.*;
import com.zhss.c2c.social.govern.report.map.CityMap;

/**
 * @Author: LiaoLingJian
 * @Description: ${description}
 * @Date: 2021/8/19 20:27   通过信号量隔离资源
 * @Version: 1.0
 */
public class GetCityNameCommand extends HystrixCommand<String> {

    private  String cityId;

    //信号量资源隔离
    public GetCityNameCommand(String cityId) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GetCityNameCommandGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("GetCityNameCommand"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("GetCityNameCommandPool"))

               // .andCommandPropertiesDefaults()
                .andCommandPropertiesDefaults(
                HystrixCommandProperties.Setter().withExecutionIsolationStrategy
                        (HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                        //设置两个信号量
                        .withExecutionIsolationSemaphoreMaxConcurrentRequests(2)
        ));
        this.cityId = cityId;
    }


    @Override
    protected String run() throws Exception {

        return CityMap.getCityName(cityId);
    }

    @Override
    protected String getCacheKey() {
        return super.getCacheKey();
    }
}
