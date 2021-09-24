package br.com.meli.desafiospring.model.service;

import br.com.meli.desafiospring.exception.ValidaEntradaException;
import br.com.meli.desafiospring.model.dto.MedicoDTO;
import br.com.meli.desafiospring.model.entity.Consulta;
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

    // validacao campos nulos e duplicidade
    public void validar(MedicoDTO medicoDTO){
        if (medicoDTO.getCpf()!= null && medicoDTO.getNome()!=null && medicoDTO.getSobrenome()!=null
            && medicoDTO.getRegistro()!=null && medicoDTO.getEspecialidade()!=null) {

        } else {
            throw new ValidaEntradaException("Por favor preencher todos os campos");
        }

        for (int i = 0; i < listaMedico.size(); i++) {
            if (listaMedico.get(i).getCpf().equals(medicoDTO.getCpf())) {
                throw new ValidaEntradaException("Medico ja existente!");
            }
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

    public MedicoDTO removerMedico(Integer id) {
        MedicoDTO medicoDTO = new MedicoDTO();
        for (int i = 0; i < listaMedico.size(); i++) {
            if(listaMedico.get(i).getId().equals(id)) {
                if(verificarConsulta(listaMedico.get(i))) {
                    throw new ValidaEntradaException("Medico tem uma consulta!!! Nao e possivel excluir");
                }else {
                    listaMedico.remove(listaMedico.get(i));
                }
            }
        }
        return medicoDTO;
    }

    public static Medico buscaMedico(String registro) {
        Optional<Medico> optionalMedico = listaMedico.stream()
                .filter(c -> c.getRegistro().equals(registro))
                .findFirst();
        return optionalMedico.orElse(null);
    }

    public boolean verificarConsulta(Medico medico) {
        for (Consulta c: ConsultaService.getListaConsulta()) {
            if(c.getMedico().equals(medico))
                return true;
        }
        return false;
    }
}
