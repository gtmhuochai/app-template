package edu.jmu.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
 * swagger2配置类
 *
 * @author guantianmin
 * @date 2018/12/17 21:58
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    /**
     * 创建API应用
     * apiInfo() 增加API相关信息
     * 通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
     * 本例采用指定扫描的包路径来定义指定要建立API的目录。
     *
     * @return
     */
    @Bean
    public Docket createAppRestApi() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("Authorization").description("令牌").modelRef(new ModelRef("string"))
                .parameterType("header").required(false).build();
        pars.add(tokenPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("应用模块接口文档")
                .apiInfo(apiInfo("应用模块 Restful APIs", "", "1.0"))
                .select()
                .apis(RequestHandlerSelectors.basePackage("edu.jmu.demo.controller"))
                .paths(PathSelectors.any()).build()
                .globalOperationParameters(pars);

    }

    @Bean
    public Docket createSecurityRestApi() {


        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("安全模块接口文档")
                .apiInfo(apiInfo("安全模块 Restful APIs", "", "1.0"))
                .select()
                .apis(RequestHandlerSelectors.basePackage("edu.jmu.security"))
                .paths(PathSelectors.any()).build();

    }

    /**
     * 创建该API的基本信息（这些基本信息会展现在文档页面中）
     * 访问地址：http://项目实际地址/swagger-ui.html
     *
     * @return
     */
    private ApiInfo apiInfo(String name, String description, String version) {
        return new ApiInfoBuilder().title(name)
                .description(description)
                //.termsOfServiceUrl("http://jipingou.xmiss.top")
                .version(version).build();
    }

}