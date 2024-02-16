package com.example.influxdb_datadog.service;

import com.example.influxdb_datadog.model.User;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class UserService{

    MeterRegistry meterRegistry;

    public User createUser(User user) throws InterruptedException {

        Counter counter = Counter.builder("total_users")
                .tag("Total Users", "User")
                .description("Users created so far")
                .register(meterRegistry);

        counter.increment();

        Thread.sleep(1000);
        log.debug("User Created {}", user);
        return user;
    }
}
