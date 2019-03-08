package com.chasel.passbook.merchants;

import com.chasel.passbook.merchants.security.AuthCheckInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@SpringBootApplication
public class MerchantsApplication extends WebMvcConfigurationSupport {

    @Bean
    public AuthCheckInterceptor authCheckInterceptor() {
        return new AuthCheckInterceptor();
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(authCheckInterceptor())
                .addPathPatterns("/merchants/**");
    }

    public static void main(String[] args) {
        SpringApplication.run(MerchantsApplication.class, args);
    }

}
