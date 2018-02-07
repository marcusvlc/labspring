package br.com.lab2spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@CrossOrigin
@EnableSwagger2
@Controller
@SpringBootApplication
public class ApiwebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiwebApplication.class, args);
	}
	
	@RequestMapping("/login")
	public String irParaLogin() {
		return "login";
	}
	
	@RequestMapping("/index")
	public String irParaIndex() {
		return "index";
	}
	
	@RequestMapping("/registrar")
	public String irParaRegistro() {
		return "registrar";
	}
	
	@Bean
	    public Docket api() {
	        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build().apiInfo(apiInfo());
	    }
	    
	    private ApiInfo apiInfo() {
	        return new ApiInfoBuilder().title("Spring boot lab")
	                .description("")
	                .termsOfServiceUrl("http://www-03.ibm.com/software/sla/sladb.nsf/sla/bm?Open")
	                .contact("").license("Apache License Version 2.0")
	                .licenseUrl("https://github.com/IBM-Bluemix/news-aggregator/blob/master/LICENSE").version("1.0")
	                .build();
	    }
}
