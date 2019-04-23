package io.hua.blockchainexplorer1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@MapperScan("io.hua.blockchainexplorer1.dao")
public class Blockchainexplorer1Application {

    public static void main(String[] args) {
        SpringApplication.run(Blockchainexplorer1Application.class, args);
    }

}
