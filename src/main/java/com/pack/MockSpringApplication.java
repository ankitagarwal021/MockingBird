package com.pack;

import com.pack.configs.AppConfig;
import com.pack.configs.InterceptorConfig;
import com.pack.configs.MongoConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Created By: Ankit Agarwal
 **/

@Import({AppConfig.class, MongoConfig.class, InterceptorConfig.class})
@SpringBootApplication(scanBasePackages = {"com.pack","com.pack.controller","com.pack.handler","com.pack.repository",
                "com.service"})
public class MockSpringApplication {

    public static void main(String[] args){
        SpringApplication.run(MockSpringApplication.class, args);
    }

}
