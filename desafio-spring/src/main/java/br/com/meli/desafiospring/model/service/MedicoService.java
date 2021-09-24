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
import java.util.Optional;

@Service
public class MedicoService {

    private final ModelMapper modelMapper = new ModelMapper();
    @Getter
    private static final List<Medico> listaMedico = new ArrayList<>();
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

    public boolean validar(MedicoDTO medicoDTO){
        if (medicoDTO.getCpf()!= null && medicoDTO.getNome()!=null && medicoDTO.getSobrenome()!=null
            && medicoDTO.getRegistro()!=null && medicoDTO.getEspecialidade()!=null) {
            return true;
        } else {
            return false;
        }

    }


    public Medico converteMedico(MedicoDTO medicoDTO) {
        return modelMapper.map(medicoDTO, Medico.class);
    }

    public MedicoDTO converteMedicoDTO(Medico medico) {
        return modelMapper.map(medico, MedicoDTO.class);
    }

    public MedicoDTO editar(MedicoDTO medicoDTO, Integer id) {
        Optional<Medico> optionalMedico = listaMedico.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
        Medico medico = optionalMedico.orElse(null);
        assert medico != null;
        medico.setCpf(medicoDTO.getCpf());
                medico.setNome(medicoDTO.getNome());
                medico.setSobrenome(medicoDTO.getSobrenome());
                medico.setRegistro(medicoDTO.getRegistro());
                medico.setEspecialidade(medicoDTO.getEspecialidade());
        try {
            ArquivoUtil.collectionToJsonMedico(file, listaMedico);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return converteMedicoDTO(medico);
    }

    public boolean removerMedico(Integer id) {
        for (Medico m : listaMedico) {
            if (m.getId().equals(id))
                return listaMedico.remove(m);
        }
        return false;
    }

    public static Medico buscaMedico(String registro) {
        Optional<Medico> optionalMedico = listaMedico.stream()
                .filter(c -> c.getRegistro().equals(registro))
                .findFirst();
        return optionalMedico.orElse(null);
    }
}
