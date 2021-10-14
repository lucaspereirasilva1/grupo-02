package br.com.meli.desafiospring.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "medico")
public class Medico implements IMedico{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Setter(AccessLevel.NONE)
    private Integer id;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "nome")
    private String nome;

    @Column(name = "sobrenome")
    private String sobreNome;

    @Column(name = "registro")
    private String registro;

    @Column(name = "especialidade")
    private String especialidade;

    @OneToMany(mappedBy = "medico", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Consulta> listaConsulta;

    @Override
    public IMedico comId(Integer id) {
        this.id = id;
        return this;
    }

    @Override
    public IMedico comCPF(String cpf) {
        this.cpf = cpf;
        return this;
    }

    @Override
    public IMedico comNome(String nome) {
        this.nome = nome;
        return this;
    }

    @Override
    public IMedico comSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
        return this;
    }

    @Override
    public IMedico comRegistro(String registro) {
        this.registro = registro;
        return this;
    }

    @Override
    public IMedico paraEspecialidade(String especialidade) {
        this.especialidade = especialidade;
        return this;
    }

    @Override
    public Medico build() {
        return this;
    }

}
