package com.zzy.config;


import com.zzy.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/ZLink/verifyLogin")
                .excludePathPatterns("/ZLink/registerAccount")
                .excludePathPatterns("/ZLink/tokenError")
                .excludePathPatterns("/ZLink/imServer/**");
    }
    @Bean
    public TokenInterceptor authenticationInterceptor() {
        return new TokenInterceptor();
    }
}
