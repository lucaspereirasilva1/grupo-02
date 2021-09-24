package br.com.meli.desafiospring.model.dto;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PacienteDTO {


        private String especie;
        private String raca;
        private String cor;
        private String dataDeNascimento;
        private String nome;
}
