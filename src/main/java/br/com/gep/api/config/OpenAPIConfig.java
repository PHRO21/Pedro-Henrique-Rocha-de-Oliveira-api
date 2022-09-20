package br.com.gep.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
public class OpenAPIConfig {

	@Bean
	public OpenAPI springShopOpenAPI() {
		OpenAPI openApi = new OpenAPI();

		Info info = new Info();
		info.title("Avaliação 03 - Listas");
		info.version("v0.0.1");
		info.description("Descreve os endpoints para utilizar a API de Listas");
		openApi.info(info);
		openApi.addTagsItem(new Tag().name("Lista").description("Gerencia as listas"));
		openApi.addTagsItem(new Tag().name("Item").description("Gerencia os itens das listas"));

		return openApi;
	}
}
