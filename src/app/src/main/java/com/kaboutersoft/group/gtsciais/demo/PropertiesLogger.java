package com.kaboutersoft.group.gtsciais.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class PropertiesLogger {


    private static final Logger logger = LoggerFactory.getLogger(PropertiesLogger.class);


    @Autowired
    private Environment env;

    @PostConstruct
    public void logProperties() {
        // Fetch all property sources from the environment
        MutablePropertySources propertySources = ((org.springframework.core.env.AbstractEnvironment) env).getPropertySources();
        
        propertySources.forEach(propertySource -> {
            if (propertySource instanceof MapPropertySource) {
              //  System.out.println("Property Source: " + propertySource.getName());
              logger.info("Property Source: " + propertySource.getName());
              logger.info("===============================================================================================");
              ((MapPropertySource) propertySource).getSource().forEach((key, value) -> {
                //    System.out.println(key + "=" + value);
                    logger.info(key + "=" + value);

                });
            }
        });
    }
}
