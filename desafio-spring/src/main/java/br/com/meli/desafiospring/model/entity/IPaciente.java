package br.com.meli.desafiospring.model.entity;

import java.time.LocalDate;

public interface IPaciente {

    IPaciente comId(Integer id);
    IPaciente comEspecie(String especie);
    IPaciente comRaca(String raca);
    IPaciente comCor(String cor);
    IPaciente comDataDeNascimento(LocalDate dataDeNascimento);
    IPaciente comNome(String nome);
    IPaciente comProprietario(Proprietario proprietario);
    Paciente build();


}
