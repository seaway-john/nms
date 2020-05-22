package com.oem.nms.common.swagger.config;

import com.oem.nms.common.util.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Seaway John
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(setHeaderToken());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("NMS API Document")
                .description("Designed by OEM team")
                .termsOfServiceUrl("https://www.baidu.com/")
                .version("2.0")
                .build();
    }

    private List<Parameter> setHeaderToken() {
        List<Parameter> parameters = new ArrayList<>();

        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameterBuilder.name(Constants.JWT_AUTH_HEADER_KEY)
                .description(String.format("%sXXX", Constants.JWT_TOKEN_PREFIX))
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(true)
                .build();

        parameters.add(parameterBuilder.build());

        return parameters;
    }

}
