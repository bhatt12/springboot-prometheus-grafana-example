package com.example.influxdb_datadog.request.handler;

import com.example.influxdb_datadog.model.User;
import com.example.influxdb_datadog.service.UserService;
import io.micrometer.core.instrument.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@Slf4j
public class DataController {

    UserService userService;

    MeterRegistry meterRegistry;

/*
    public DataController(UserService userService, MeterRegistry meterRegistry) {
        this.userService = userService;
        this.meterRegistry = meterRegistry;
    }
*/

    @PostMapping("/user")
    ResponseEntity<User> createUser(@RequestBody User user) throws InterruptedException {

        Counter counter = Counter.builder("user_create_requests")
                .tag("uri","/user")
                .description("User create POST Request")
                .register(meterRegistry);

        counter.increment();

        log.info("Creating user {}", user);
        Thread.sleep(3000);

        Gauge gauge = Gauge.builder("internal_waiting_time", () -> Math.random())
                .tag("uri","/user")
                .description("Time to get resources internally")
                .register(meterRegistry);

        userService.createUser(user);

        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/user/{id}")
    String searchUser(@PathVariable String id) throws InterruptedException {

        Tag tag = Tag.of("id", id);
        Timer.Sample timer = Timer.start(meterRegistry);

        Thread.sleep(5000);

        timer.stop(Timer.builder("user_search_time")
                .description("User searching time")
                .tags(List.of(tag))
                .register(meterRegistry));

    /*timer.stop(Timer.builder("service_books_find")
                .description("books searching timer")
                .tags(List.of(titleTag))
                .register(meterRegistry));*/

        return "User Found";

    }
}
