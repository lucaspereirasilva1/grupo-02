package br.com.meli.desafiospring.model.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class ConsultaRequestDTO {

    private LocalDateTime dataHora;
    private String motivo;
    private String diagnostico;
    private String tratamento;
    private String registroMedico;

}
