package com.zhss.c2c.social.govern.reward;

import com.zhss.c2c.social.govern.reward.db.DruidDataSourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @Author: LiaoLingJian
 * @Description: ${description}
 * @Date: 2021/7/23 19:31
 * @Version: 1.0
 */
@SpringBootApplication
@Import({DruidDataSourceConfig.class})
public class Application {
    
    public static void main(String [] arge) {


        SpringApplication.run(Application.class,arge);
    }
    
}
