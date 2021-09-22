package br.com.meli.desafiospring.model.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Medico {

    private Integer id;
    private String cpf;
    private String nome;
    private String sobrenome;
    private String registro;
    private String especialidade;
}
