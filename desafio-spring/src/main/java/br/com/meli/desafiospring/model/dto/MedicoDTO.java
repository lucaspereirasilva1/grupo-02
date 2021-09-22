package br.com.meli.desafiospring.model.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MedicoDTO {

    private String cpf;
    private String nome;
    private String sobrenome;
    private String registro;
    private String especialidade;

}
