package br.com.meli.desafiospring.model.entity;

import java.time.LocalDateTime;

public interface IConsulta {

    IConsulta comId(Integer id);
    IConsulta noPeriodo(LocalDateTime dataHora);
    IConsulta comMotivo(String motivo);
    IConsulta comDiagnostico(String diagnostico);
    IConsulta comTratamento(String tratamento);

}
