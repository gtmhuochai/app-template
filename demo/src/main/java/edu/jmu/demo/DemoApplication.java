package edu.jmu.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author guantianmin
 * @date 2019/3/4 11:20
 */
@SpringBootApplication
@EnableCaching
@ComponentScan(basePackages = {"edu.jmu.security", "edu.jmu.demo"})
@MapperScan(basePackages = {"edu.jmu.security.mapper", "edu.jmu.demo.mapper"})
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
