package br.com.meli.desafiospring.model.entity;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class Consulta {

    private Integer id;
    private LocalDateTime dataHora;
    private String motivo;
    private String diagnostico;
    private String tratamento;

}
