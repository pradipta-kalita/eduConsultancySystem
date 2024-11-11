package com.pol.user_service.config;


import com.resend.Resend;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResendConfig {
    @Value("${resend.key}")
    private String apiKey;

    @Bean
    public Resend resend(){
        return new Resend(apiKey);
    }
}