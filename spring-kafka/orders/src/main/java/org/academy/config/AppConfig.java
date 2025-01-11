package org.academy.config;

import org.academy.model.LastCreatedOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public LastCreatedOrder lastCreatedOrder() {
        return new LastCreatedOrder();
    }

}
