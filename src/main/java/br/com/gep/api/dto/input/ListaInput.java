package br.com.gep.api.dto.input;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListaInput {

	@NotNull(message = "O titulo é obrigatório.")
	@Length(max = 100, message = "O titulo só pode ter no máximo 100 caracteres.")
	private String titulo;

}
