package com.ss.userManagementClient.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RootUriTemplateHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplateHandler;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.DependsOn;


@Configuration
public class Config {
	
	@Value("${service.base.url}")
	private String baseurl;
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		UriTemplateHandler uriTemplateHandler = new RootUriTemplateHandler(baseurl);
		return builder
				.uriTemplateHandler(uriTemplateHandler)
				.setReadTimeout(Duration.ofMillis(1000))
				.build();
	}
	
	@Bean
    public MyRequestInterceptor myRequestInterceptor() {
        return new MyRequestInterceptor();
    }

    @Bean
    public MyRestTemplateCustomizer restTemplateCustomizer() {
        return new MyRestTemplateCustomizer();
    }

    @Bean
    @DependsOn("restTemplateCustomizer")
    public RestTemplateBuilder restTemplateBuilder(RestTemplateCustomizer restTemplateCustomizer) {
        return new RestTemplateBuilder(restTemplateCustomizer);
    }
}
