package br.com.meli.desafiospring.model.entity;

import java.time.LocalDate;
import java.util.List;

public interface IProprietario {

    IProprietario comId(Integer id);
    IProprietario comCpf(String cpf);
    IProprietario comNome(String nome);
    IProprietario comSobreNome(String sobreNome);
    IProprietario comDataDeNascimento(LocalDate dataNascimento);
    IProprietario comEndereco(String endereco);
    IProprietario comTelefone(Long telefone);
    IProprietario comPaciente(List<Paciente> listaPaciente);
    Proprietario build();

}
