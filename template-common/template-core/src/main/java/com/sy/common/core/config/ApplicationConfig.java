package com.sy.common.core.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Monster
 * @version v1.0
 */
@Configuration
@EnableAsync
@EnableScheduling
@ComponentScan(basePackages = {"com.sy"})
public class ApplicationConfig {
}
