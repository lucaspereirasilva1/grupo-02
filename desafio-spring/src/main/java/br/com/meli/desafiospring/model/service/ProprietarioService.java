package br.com.meli.desafiospring.model.service;

import br.com.meli.desafiospring.model.dto.ProprietarioDTO;
import br.com.meli.desafiospring.model.entity.Proprietario;
import br.com.meli.desafiospring.util.ArquivoUtil;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProprietarioService {

    private final ModelMapper modelMapper = new ModelMapper();
    @Getter
    private final List<Proprietario> listaProprietario = new ArrayList<>();
    private final File file = new File("proprietarios.json");

    public ProprietarioDTO cadastrar(ProprietarioDTO proprietarioDTO){
        Proprietario proprietario = converteProprietario(proprietarioDTO);
        proprietario.setId(listaProprietario.size() + 1);
        listaProprietario.add(proprietario);
        try {
            ArquivoUtil.collectionToJsonProprietario(file, listaProprietario);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return proprietarioDTO;
    }

    private Proprietario converteProprietario(ProprietarioDTO proprietarioDTO) {
        return modelMapper.map(proprietarioDTO, Proprietario.class);
    }

}
