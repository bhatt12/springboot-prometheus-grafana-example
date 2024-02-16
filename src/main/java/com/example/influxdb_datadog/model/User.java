package com.example.influxdb_datadog.model;

import lombok.Data;

@Data
public class User {

    String id;
    String name;
    String role;
    String email;
    long level;
}
