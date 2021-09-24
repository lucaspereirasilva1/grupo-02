package br.com.meli.desafiospring.model.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString

public class Paciente {
    private Integer id;
    private String especie;
    private String raca;
    private String cor;
    private String dataDeNascimento;
    private String nome;

}
