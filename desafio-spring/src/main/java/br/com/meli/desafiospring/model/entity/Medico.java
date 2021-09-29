package br.com.meli.desafiospring.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Medico {

    private Integer id;
    private String cpf;
    private String nome;
    private String sobrenome;
    private String registro;
    private String especialidade;
}
