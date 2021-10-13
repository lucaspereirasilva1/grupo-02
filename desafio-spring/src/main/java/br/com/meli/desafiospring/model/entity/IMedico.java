package br.com.meli.desafiospring.model.entity;

public interface IMedico {

    IMedico comId(Integer id);
    IMedico comCPF(String cpf);
    IMedico comNome(String nome);
    IMedico comSobreNome(String sobreNome);
    IMedico comRegistro(String registro);
    IMedico paraEspecialidade(String especialidade);
    Medico build();

}
