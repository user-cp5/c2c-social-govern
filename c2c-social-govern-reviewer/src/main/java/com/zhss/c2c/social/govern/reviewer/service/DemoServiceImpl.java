package com.zhss.c2c.social.govern.reviewer.service;

import com.zhss.c2c.social.govern.reviewer.api.DemoService;
import com.zhss.c2c.social.govern.reviewer.api.ReviewerService;
import org.apache.dubbo.config.annotation.Service;

/**
 * @Author: LiaoLingJian
 * @Description: ${description}
 * @Date: 2021/8/2 22:19
 * @Version: 1.0
 */

@Service(
        version = "1.0.0",
        interfaceClass = DemoService.class,
        cluster = "failfast",
        loadbalance = "roundrobin"
)
public class DemoServiceImpl implements DemoService {
}
