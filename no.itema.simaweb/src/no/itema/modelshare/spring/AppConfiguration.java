package no.itema.modelshare.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import no.itema.modelshare.spring.config.DispatcherConfig;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "no.itema.modelshare")
@Import({ WebInitializer.class, DispatcherConfig.class})
public class AppConfiguration {

}