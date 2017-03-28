package br.com.kecyo.resizephotos.config.rest;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter @Setter
@Configuration
public class RestProperties {

    @Value("${rest.config.connectTimeout}")
    private int connectTimeout;

    @Value("${rest.config.readTimeout}")
    private int readTimeout;

    @Value("${rest.config.b2wRepository.server}")
    private String serverB2WRepository;

    @Value("${rest.config.b2wRepository.endpointConsult}")
    private String endpointB2WConsult;

    public String getEndpointB2WConsult(){
        return serverB2WRepository.concat(endpointB2WConsult);
    }
}
