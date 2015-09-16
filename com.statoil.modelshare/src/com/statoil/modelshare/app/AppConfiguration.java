package com.statoil.modelshare.app;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.statoil.modelshare.app.config.DispatcherConfig;
import com.statoil.modelshare.app.config.RepositoryConfig;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.statoil.modelshare")
@Import({ WebInitializer.class, DispatcherConfig.class, RepositoryConfig.class })
public class AppConfiguration {}