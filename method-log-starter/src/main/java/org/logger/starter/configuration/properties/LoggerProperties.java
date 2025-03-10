package org.logger.starter.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "method-logger")
public record LoggerProperties(String level) {
}
