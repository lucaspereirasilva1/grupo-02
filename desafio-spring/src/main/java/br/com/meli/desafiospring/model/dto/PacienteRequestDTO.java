package br.com.meli.desafiospring.model.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
public class PacienteRequestDTO {

   private String especie;
   private String raca;
   private String cor;
   @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="dd/MM/yyyy")
   private LocalDate dataDeNascimento;
   private String nome;
   private Integer idProprietario;

}
