package com.lgzarturo.api.personal.infrastructure.security;

import com.lgzarturo.api.personal.config.AppConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@EnableWebMvc
public class CorsFilter implements WebMvcConfigurer {

    private final AppConfigProperties appConfigProperties;

    public CorsFilter(AppConfigProperties appConfigProperties) {
        this.appConfigProperties = appConfigProperties;
    }

    @Value("#{'${api.cors.allowed-origins}'.split(',')}")
    private List<String> allowedOrigins;
    @Value("#{'${api.cors.allowed-methods}'.split(',')}")
    private List<String> allowedMethods;
    @Value("#{'${api.cors.allowed-headers}'.split(',')}")
    private List<String> allowedHeaders;
    @Value("#{'${api.cors.exposed-headers}'.split(',')}")
    private List<String> exposedHeaders;
    @Value("${api.cors.allow-credentials}")
    private boolean allowCredentials;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins(allowedOrigins.toArray(new String[0]))
            .allowedMethods(allowedMethods.toArray(new String[0]))
            .allowedHeaders(allowedHeaders.toArray(new String[0]))
            .exposedHeaders(exposedHeaders.toArray(new String[0]))
            .allowCredentials(allowCredentials);
        WebMvcConfigurer.super.addCorsMappings(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(appConfigProperties.upload().uri()+"**")
            .addResourceLocations("file:"+appConfigProperties.upload().dir());
    }
}
