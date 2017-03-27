package br.com.kecyo.resizephotos.config.app;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties
public class ConfigProperties {

    @Value("${server.port}")
    private String serverPort;

    @Value("${server.context-path}")
    private String contextPath;

}
