package br.com.meli.desafiospring.model.entity;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString

public class Paciente {

    private Integer id;
    private String especie;
    private String raca;
    private String cor;
    private LocalDate dataDeNascimento;
    private String nome;
    private Proprietario proprietario;

}
