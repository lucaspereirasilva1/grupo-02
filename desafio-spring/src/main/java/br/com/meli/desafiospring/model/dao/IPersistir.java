package br.com.meli.desafiospring.model.dao;

import java.util.List;

public interface IPersistir<T> {

    void inserir(List<T> lista);

}
