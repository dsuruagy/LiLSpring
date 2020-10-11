package com.frankmoley.boot.essentials;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class DemoHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        /* used to show that the 'status' indicator uses a logical AND
         * to show that our system is Up or Down. In this case, will be Down.
         */
        return Health.down().withDetail("reason", "testing").build();
    }
}
