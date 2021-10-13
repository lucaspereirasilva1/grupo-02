package br.com.meli.desafiospring.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "consulta")
public class Consulta implements IConsulta{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "datahora")
    private LocalDateTime dataHora;

    @Column(name = "motivo")
    private String motivo;

    @Column(name = "diagnostico")
    private String diagnostico;

    @Column(name = "tratamento")
    private String tratamento;

    @ManyToOne
    @JoinColumn(name = "medico_id")
    @ToString.Exclude
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    @ToString.Exclude
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

    @Override
    public Consulta build() {
        return this;
    }

}
