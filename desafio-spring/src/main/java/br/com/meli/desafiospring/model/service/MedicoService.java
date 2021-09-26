package br.com.meli.desafiospring.model.service;

import br.com.meli.desafiospring.exception.ValidaEntradaException;
import br.com.meli.desafiospring.model.dto.MedicoDTO;
import br.com.meli.desafiospring.model.entity.Consulta;
import br.com.meli.desafiospring.model.entity.Medico;
import br.com.meli.desafiospring.util.ArquivoUtil;
import br.com.meli.desafiospring.util.ConvesorUtil;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MedicoService {

    @Getter
    private static final List<Medico> listaMedico = new ArrayList<>();
    private final File file = new File("medicos.json");
    private final ConvesorUtil convesorUtil = new ConvesorUtil();
    private final ArquivoUtil<Medico> arquivoUtil = new ArquivoUtil<>();

    public Integer cadastrar(MedicoDTO medicoDTO){
        Medico medico = new Medico();
        medico = (Medico) convesorUtil.conveterDTO(medicoDTO, medico);
        medico.setId(listaMedico.size() + 1);
        listaMedico.add(medico);
        try {
            arquivoUtil.collectionToJson(file, listaMedico);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return medico.getId();
    }

    // validacao campos nulos e duplicidade
    public void validar(MedicoDTO medicoDTO){
        if (medicoDTO.getCpf() == null || medicoDTO.getNome() == null || medicoDTO.getSobrenome() == null
                || medicoDTO.getRegistro() == null || medicoDTO.getEspecialidade() == null) {
                    throw new ValidaEntradaException("Por favor preencher todos os campos");
                }

        listaMedico.forEach(m -> {
            if(m.getCpf().equals(medicoDTO.getCpf())) {
                throw new ValidaEntradaException("Medico ja existente!");
            }
        });
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
            arquivoUtil.collectionToJson(file, listaMedico);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (MedicoDTO) convesorUtil.conveterDTO(medico, new MedicoDTO());
    }

    public void removerMedico(Integer id) {
        for (int i = 0; i < listaMedico.size(); i++) {
            if(listaMedico.get(i).getId().equals(id)) {
                if(verificarConsulta(listaMedico.get(i))) {
                    throw new ValidaEntradaException("Medico tem uma consulta!!! Nao e possivel excluir");
                }else {
                    listaMedico.remove(listaMedico.get(i));
                }
            }
        }

        try {
            arquivoUtil.collectionToJson(file, listaMedico);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
