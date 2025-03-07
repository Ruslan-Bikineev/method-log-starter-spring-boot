package org.logger.starter.configuration;

import org.logger.aspect.LoggingAspect;
import org.logger.starter.configuration.properties.LoggerProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@EnableConfigurationProperties(LoggerProperties.class)
@ConditionalOnProperty(name = "method-logger.enable", havingValue = "true", matchIfMissing = true)
public class LoggerConfiguration {
    private final LoggerProperties loggerProperties;

    public LoggerConfiguration(LoggerProperties loggerProperties) {
        this.loggerProperties = loggerProperties;
    }

    @Bean
    public LoggingAspect loggerConfiguration() {
        return new LoggingAspect(loggerProperties.level());
    }
}
