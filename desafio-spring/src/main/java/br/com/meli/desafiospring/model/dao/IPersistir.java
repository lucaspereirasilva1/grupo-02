package br.com.meli.desafiospring.model.dao;

import java.io.File;
import java.util.List;

public interface IPersistir<T> {

    void inserir(File file, List<T> lista);

}
