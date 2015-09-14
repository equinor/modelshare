package com.statoil.modelshare.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.statoil.modelshare.spring.config.DispatcherConfig;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.statoil.modelshare")
@Import({ WebInitializer.class, DispatcherConfig.class})
public class AppConfiguration {

}