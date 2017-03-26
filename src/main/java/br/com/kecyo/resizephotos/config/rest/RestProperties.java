package br.com.kecyo.resizephotos.config.rest;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rest.config")
public class RestProperties {

    @Getter
    @Setter
    private int connectTimeout;

    @Getter
    @Setter
    private int readTimeout;

    @Getter
    @Setter
    private String imagesRepository;

}
