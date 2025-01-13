package org.academy.config;

import org.academy.model.LastShippedOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public LastShippedOrder lastShippedOrder() {
        return new LastShippedOrder();
    }

}
