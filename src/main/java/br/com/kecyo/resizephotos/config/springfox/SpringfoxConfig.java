package br.com.kecyo.resizephotos.config.springfox;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Configuration
@EnableSwagger2
public class SpringfoxConfig {

    @Bean
    public Docket gatewayApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/api/images.*"))
                .build()
                .pathMapping("/")
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.POST, defaultErrorsMessage())
                .apiInfo(apiInfo());
    }

    private List<ResponseMessage> defaultErrorsMessage() {
        return Lists.newArrayList(
                new ResponseMessageBuilder().code(NOT_FOUND.value())
                        .message("Data not found").build(),
                new ResponseMessageBuilder().code(INTERNAL_SERVER_ERROR.value())
                        .message("Internal error on resizephotos").build(),
                new ResponseMessageBuilder().code(BAD_REQUEST.value())
                        .message("Invalid data ").build(),
                new ResponseMessageBuilder().code(BAD_GATEWAY.value())
                        .message("Communication between the resizephotos and upstream server failed").build()
        );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Resize-Photos API")
                .description("API provides resizing photos.")
                .version("1.0")
                .contact("Kecyo")
                .build();
    }

    @Bean
    public UiConfiguration uiConfig() {
        return UiConfiguration.DEFAULT;
    }

}
