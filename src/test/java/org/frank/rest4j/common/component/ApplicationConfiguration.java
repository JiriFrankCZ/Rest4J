package org.frank.rest4j.common.component;

import org.frank.rest4j.context.EnableRestProxies;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.frank.rest4j.common.component")
@EnableRestProxies("org.frank.rest4j.common.source")
public class ApplicationConfiguration {
}
