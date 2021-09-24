package br.com.meli.desafiospring.util;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ConvesorUtil {

    private final ModelMapper modelMapper = new ModelMapper();

    public Object conveterDTO(Object entrada, Object saida) {
        return modelMapper.map(entrada, saida.getClass());
    }

    <S, T> List<T> conveterListaDTO(List<S> entrada, Class<T> saida) {
        return entrada
                .stream()
                .map(element -> modelMapper.map(element, saida))
                .collect(Collectors.toList());
    }

}
