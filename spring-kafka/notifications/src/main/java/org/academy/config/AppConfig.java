package org.academy.config;

import org.academy.model.LastNotificatedOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public LastNotificatedOrder lastNotificatedOrder() {
        return new LastNotificatedOrder();
    }

}
