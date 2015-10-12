package org.frank.rest4j.common.component;

import org.frank.rest4j.context.EnableRestProxies;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@ComponentScan(basePackages = "org.frank.rest4j.common.component")
@EnableRestProxies("org.frank.rest4j.common.source")
@EnableAsync
public class ApplicationConfiguration {
}
