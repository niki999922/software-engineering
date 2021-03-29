package com.kochetkov.fitnes.clock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClockConfiguration {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Bean
    public Clock clock() {
        if (activeProfile.equals("test")) return new FixedClock();
        return new RealClock();
    }
}
