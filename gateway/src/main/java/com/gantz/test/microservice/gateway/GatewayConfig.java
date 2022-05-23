package com.gantz.test.microservice.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/category/**").uri("lb://category-service/"))
                .route(r -> r.path("/api/videoInfo/**").uri("lb://video-service"))
                .build();
    }

}
