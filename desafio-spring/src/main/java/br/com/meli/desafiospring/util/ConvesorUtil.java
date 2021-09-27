package br.com.meli.desafiospring.util;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ConvesorUtil {

    private final ModelMapper modelMapper = new ModelMapper();

    public <T> Object conveterDTO(Object entrada, Class<T> saida) {
        return modelMapper.map(entrada, saida);
    }

    public <S, T> List<T> conveterListaDTO(List<S> entrada, Class<T> saida) {
        return entrada
                .stream()
                .map(element -> modelMapper.map(element, saida))
                .collect(Collectors.toList());
    }

}
