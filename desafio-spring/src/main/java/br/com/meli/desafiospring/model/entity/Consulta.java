package br.com.meli.desafiospring.model.entity;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class Consulta implements IConsulta{

    private Integer id;
    private LocalDateTime dataHora;
    private String motivo;
    private String diagnostico;
    private String tratamento;
    private Medico medico;
    private Paciente paciente;

    @Override
    public IConsulta comId(Integer id) {
        this.id = id;
        return this;
    }

    @Override
    public IConsulta noPeriodo(LocalDateTime dataHora) {
        this.dataHora = dataHora;
        return this;
    }

    @Override
    public IConsulta comMotivo(String motivo) {
        this.motivo = motivo;
        return this;
    }

    @Override
    public IConsulta comDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
        return this;
    }

    @Override
    public IConsulta comTratamento(String tratamento) {
        this.tratamento = tratamento;
        return this;
    }

    @Override
    public IConsulta comMedico(Medico medico) {
        this.medico = medico;
        return this;
    }

    @Override
    public IConsulta comPaciente(Paciente paciente) {
        this.paciente = paciente;
        return this;
    }

    }
