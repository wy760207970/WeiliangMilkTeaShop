package com.weiliang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class WYMilkTeaApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(WYMilkTeaApplication.class, args);
    }
}
