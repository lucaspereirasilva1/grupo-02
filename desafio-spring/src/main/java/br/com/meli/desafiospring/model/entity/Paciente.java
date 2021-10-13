package br.com.meli.desafiospring.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "paciente")
public class Paciente implements IPaciente{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;

    @Column(name = "especie")
    private String especie;

    @Column(name = "raca")
    private String raca;

    @Column(name = "cor")
    private String cor;

    @Column(name = "datanascimento")
    private LocalDate dataDeNascimento;

    @Column(name = "nome")
    private String nome;

    @ManyToOne
    @JoinColumn(name = "proprietario_id")
    @ToString.Exclude
    private Proprietario proprietario;

    @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Consulta> listaConsulta;

    @Override
    public IPaciente comId(Integer id) {
        this.id = id;
        return this;
    }

    @Override
    public IPaciente comEspecie(String especie) {
        this.especie = especie;
        return this;
    }

    @Override
    public IPaciente comRaca(String raca) {
        this.raca = raca;
        return this;
    }

    @Override
    public IPaciente comCor(String cor) {
        this.cor = cor;
        return this;
    }

    @Override
    public IPaciente comDataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
        return this;
    }

    @Override
    public IPaciente comNome(String nome) {
        this.nome = nome;
        return this;
    }

    @Override
    public IPaciente comProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
        return this;
    }

    @Override
    public Paciente build() {
        return this;
    }
}
