package com.kramphub.config;

import com.kramphub.executor.ServiceExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfiguration {

    @Value("${global.http.client.readTimeout}")
    private int readTimeout;

    @Value("${global.http.client.connectTimeout}")
    private int connectTimeout;

    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new SimpleClientHttpRequestFactory());
        SimpleClientHttpRequestFactory client = (SimpleClientHttpRequestFactory) restTemplate.getRequestFactory();
        client.setConnectTimeout(connectTimeout);
        client.setReadTimeout(readTimeout);
        return restTemplate;
    }

    @Bean
    public ServiceExecutor getServiceExecutor(){
        return new ServiceExecutor();
    }

}
