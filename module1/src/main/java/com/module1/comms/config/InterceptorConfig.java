package com.module1.comms.config;

import com.module1.comms.config.Interceptor.JWTInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
//@EnableWebMvc
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration ir = registry.addInterceptor(new JWTInterceptor());
        // 配置拦截的路径
        ir.addPathPatterns("/c/*");
        // 配置不拦截的路径
        ir.excludePathPatterns("/**.html","/**/.ftl","/**.css","/**.js",
                "/c/login");


//        super.addInterceptors(registry);
    }
}
