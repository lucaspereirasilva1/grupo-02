package br.com.meli.desafiospring.model.dto;


import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
public class PacienteResponseDTO {

   private String especie;
   private String raca;
   private String cor;
   private LocalDate dataDeNascimento;
   private String nome;
   private ProprietarioDTO proprietarioDTO;

}
