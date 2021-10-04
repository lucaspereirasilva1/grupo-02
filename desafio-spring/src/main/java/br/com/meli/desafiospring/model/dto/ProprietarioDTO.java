package br.com.meli.desafiospring.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProprietarioDTO {
    private String cpf;
    private String nome;
    private String sobreNome;
    private LocalDate dataNascimento;
    private String endereco;
    private long telefone;

}
