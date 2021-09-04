package com.zhss.c2c.social.govern.report;

import com.zhss.c2c.social.govern.report.db.DruidDataSourceConfig;
import com.zhss.c2c.social.govern.report.fifter.HystrixRqeusetContextServletFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
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

    /*@Bean
    public FilterRegistrationBean indexFilterRegistration(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new HystrixRqeusetContextServletFilter());
        registrationBean.addUrlPatterns("/*");
        System.out.println("拦截所有请求： = " +System.currentTimeMillis() );
        return  registrationBean;
    }*/
    
}
