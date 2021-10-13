package br.com.meli.desafiospring.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "proprietario")
public class Proprietario implements  IProprietario{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "nome")
    private String nome;

    @Column(name = "sobrenome")
    private String sobreNome;

    @Column(name = "datanascimento")
    private LocalDate dataNascimento;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "telefone")
    private long telefone;

    @OneToMany(mappedBy = "proprietario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Paciente> listaPaciente;

    @Override
    public IProprietario comId(Integer id) {
        this.id = id;
        return this;
    }

    @Override
    public IProprietario comCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    @Override
    public IProprietario comNome(String nome) {
        this.nome = nome;
        return this;
    }

    @Override
    public IProprietario comSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
        return this;
    }

    @Override
    public IProprietario comDataDeNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
        return this;
    }

    @Override
    public IProprietario comEndereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    @Override
    public IProprietario comTelefone(Long telefone) {
        this.telefone = telefone;
        return this;
    }

    @Override
    public IProprietario comPaciente(List<Paciente> listaPaciente) {
        this.listaPaciente = listaPaciente;
        return this;
    }

    @Override
    public Proprietario build() {
        return this;
    }
}
