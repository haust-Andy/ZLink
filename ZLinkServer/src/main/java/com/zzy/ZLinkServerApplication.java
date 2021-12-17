package com.zzy;

import com.zzy.server.WebSocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ZLinkServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZLinkServerApplication.class, args);
    }

}
