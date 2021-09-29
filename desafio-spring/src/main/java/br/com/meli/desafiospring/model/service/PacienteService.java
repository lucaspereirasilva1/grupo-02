package br.com.meli.desafiospring.model.service;

import br.com.meli.desafiospring.exception.ValidaEntradaException;
import br.com.meli.desafiospring.model.dto.PacienteRequestDTO;
import br.com.meli.desafiospring.model.dto.PacienteResponseDTO;
import br.com.meli.desafiospring.model.dto.ProprietarioDTO;
import br.com.meli.desafiospring.model.entity.Consulta;
import br.com.meli.desafiospring.model.entity.Paciente;
import br.com.meli.desafiospring.util.ArquivoUtil;
import br.com.meli.desafiospring.util.ConvesorUtil;
import lombok.Getter;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service

public class PacienteService {

    @Getter
    private static final List<Paciente> listaPaciente = new ArrayList<>();
    private final File file = new File ("paciente.json");
    private final ProprietarioService proprietarioService = new ProprietarioService();
    private final ConvesorUtil convesorUtil = new ConvesorUtil();
    private final ArquivoUtil<Paciente> arquivoUtil = new ArquivoUtil<>();
    private static final Logger logger = Logger.getLogger(PacienteService.class);

    public Integer cadastrar (PacienteRequestDTO pacienteRequestDTO) {
        Paciente paciente = (Paciente) convesorUtil.conveterDTO(pacienteRequestDTO, Paciente.class);
        paciente.setProprietario(ProprietarioService.buscarProprietario(pacienteRequestDTO.getIdProprietario()));
        paciente.setId(listaPaciente.size() + 1);
        listaPaciente.add(paciente);

        try {
            arquivoUtil.collectionToJson(file, listaPaciente);
        } catch (IOException e) {
            logger.error(e);
        }
        return paciente.getId();
    }

    public PacienteResponseDTO editar(PacienteRequestDTO pacienteDTO, Integer id){
        Optional<Paciente>optionalPaciente = listaPaciente.stream().filter(c -> c.getId().equals(id))
                .findFirst();
        Paciente paciente = optionalPaciente.orElse(null);
        assert paciente != null;
        paciente.setEspecie(pacienteDTO.getEspecie());
        paciente.setRaca(pacienteDTO.getRaca());
        paciente.setCor(pacienteDTO.getCor());
        paciente.setDataDeNascimento(pacienteDTO.getDataDeNascimento());
        paciente.setNome(pacienteDTO.getNome());

        try {
            arquivoUtil.collectionToJson(file, listaPaciente);
        } catch (IOException e){
            logger.error(e);
        }

        PacienteResponseDTO pacienteResponseDTO = (PacienteResponseDTO) convesorUtil.conveterDTO(paciente, PacienteResponseDTO.class);
        pacienteResponseDTO.setProprietarioDTO((ProprietarioDTO) convesorUtil.conveterDTO(paciente.getProprietario(), ProprietarioDTO.class));
        return pacienteResponseDTO;
    }

    public Paciente buscaPaciente(Integer id) {
        Optional<Paciente> optionalPaciente = listaPaciente.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
        return optionalPaciente.orElse(null);
    }

    public void validaEntrada(PacienteRequestDTO pacienteDTO) {
        if(ObjectUtils.isEmpty(pacienteDTO.getEspecie())
            && ObjectUtils.isEmpty(pacienteDTO.getRaca())
            && ObjectUtils.isEmpty(pacienteDTO.getCor())
            && ObjectUtils.isEmpty(pacienteDTO.getDataDeNascimento())
            && ObjectUtils.isEmpty(pacienteDTO.getNome())
            && ObjectUtils.isEmpty(pacienteDTO.getIdProprietario()))
            throw new ValidaEntradaException("Nenhum campo informado!!! Por gentileza informar: especie, raca, cor" +
                    ", data de nascimento e nome");
        if(ObjectUtils.isEmpty(pacienteDTO.getIdProprietario()))
            throw new ValidaEntradaException("Id proprietario nao informado!!! Por gentileza informar.");
        if(ObjectUtils.isEmpty(pacienteDTO.getEspecie()))
            throw new ValidaEntradaException("Especie nao informada!!! Por gentileza informar.");
        if(ObjectUtils.isEmpty(pacienteDTO.getRaca()))
            throw new ValidaEntradaException("Raca nao informada!!! Por gentileza informar.");
        if(ObjectUtils.isEmpty(pacienteDTO.getCor()))
            throw new ValidaEntradaException("Cor nao informada!!! Por gentileza informar.");
        if(ObjectUtils.isEmpty(pacienteDTO.getDataDeNascimento()))
            throw new ValidaEntradaException("Data de nascimento nao informada!!! Por gentileza informar.");
        if(ObjectUtils.isEmpty(pacienteDTO.getNome()))
            throw new ValidaEntradaException("Nome nao informado!!! Por gentileza informar.");
    }

    public PacienteResponseDTO remover(Integer id) {
        PacienteResponseDTO pacienteResponseDTO = new PacienteResponseDTO();
        for (int i = 0; i < listaPaciente.size(); i++) {
            if(listaPaciente.get(i).getId().equals(id)) {
                if(verificarConsulta(listaPaciente.get(i))) {
                    throw new ValidaEntradaException("Paciente tem uma consulta!!! Nao e possivel excluir");
                }else {
                    pacienteResponseDTO = (PacienteResponseDTO) convesorUtil.conveterDTO(listaPaciente.get(i), PacienteResponseDTO.class);
                    pacienteResponseDTO.setProprietarioDTO(proprietarioService.converteProprietarioDTO(
                            listaPaciente.get(i).getProprietario()));
                    listaPaciente.remove(listaPaciente.get(i));
                }
            }

            try {
                arquivoUtil.collectionToJson(file, listaPaciente);
            } catch (IOException e){
                logger.error(e);
            }
        }

        return pacienteResponseDTO;
    }

    public boolean verificarConsulta(Paciente paciente) {
        for (Consulta c: ConsultaService.getListaConsulta()) {
            if(c.getPaciente().equals(paciente))
                return true;
        }
        return false;
    }

    public List<PacienteResponseDTO> listar() {
        List<PacienteResponseDTO> listaPacienteResponseDTO = new ArrayList<>();
        for (Paciente p: listaPaciente) {
            PacienteResponseDTO pacienteResponseDTO = (PacienteResponseDTO) convesorUtil.conveterDTO(p, PacienteResponseDTO.class);
            pacienteResponseDTO.setProprietarioDTO(proprietarioService.converteProprietarioDTO(p.getProprietario()));
            listaPacienteResponseDTO.add(pacienteResponseDTO);
        }

        listaPacienteResponseDTO.sort(Comparator.comparing(c -> c.getProprietarioDTO().getNome()));

        return listaPacienteResponseDTO;

    }

}



