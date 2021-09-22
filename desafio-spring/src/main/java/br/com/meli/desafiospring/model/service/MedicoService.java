package br.com.meli.desafiospring.model.service;

import br.com.meli.desafiospring.model.dto.MedicoDTO;
import br.com.meli.desafiospring.model.entity.Medico;
import br.com.meli.desafiospring.util.ArquivoUtil;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MedicoService {

    private final ModelMapper modelMapper = new ModelMapper();
    @Getter
    private final List<Medico> listaMedico = new ArrayList<>();
    private final File file = new File("medicos.json");

    public MedicoDTO cadastrar(MedicoDTO medicoDTO){
        Medico medico = converteMedico(medicoDTO);
        medico.setId(listaMedico.size() + 1);
        listaMedico.add(medico);
        try {
            ArquivoUtil.collectionToJsonMedico(file, listaMedico);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return medicoDTO;
    }

    private Medico converteMedico(MedicoDTO medicoDTO) {
        return modelMapper.map(medicoDTO, Medico.class);
    }

}
