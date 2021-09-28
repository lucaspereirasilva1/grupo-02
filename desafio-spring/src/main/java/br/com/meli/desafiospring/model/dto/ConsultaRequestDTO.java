package br.com.meli.desafiospring.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class ConsultaRequestDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="dd/MM/yyyy HH:mm")
    private LocalDateTime dataHora;
    private String motivo;
    private String diagnostico;
    private String tratamento;
    private String registroMedico;
    private Integer idPaciente;

}
