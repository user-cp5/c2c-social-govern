package com.zhss.c2c.social.govern.report.fifter;


import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

import javax.servlet.*;
import java.io.IOException;
import java.util.logging.LogRecord;

/**
 * @Author: LiaoLingJian
 * @Description: ${description}
 * @Date: 2021/8/19 22:38
 * @Version: 1.0
 */
public class HystrixRqeusetContextServletFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try{
            filterChain.doFilter(servletRequest,servletResponse);
        }catch (Exception e){
            context.shutdown();
        }
    }
}
