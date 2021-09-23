package br.com.meli.desafiospring.model.entity;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
public class Proprietario {
    private Integer id;
    private String cpf;
    private String nome;
    private String sobreNome;
    private LocalDate dataNascimento;
    private String endereco;
    private long telefone;

}
