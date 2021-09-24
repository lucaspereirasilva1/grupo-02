package br.com.meli.desafiospring.model.service;

import br.com.meli.desafiospring.exception.ValidaEntradaException;
import br.com.meli.desafiospring.model.dto.PacienteRequestDTO;
import br.com.meli.desafiospring.model.dto.PacienteResponseDTO;
import br.com.meli.desafiospring.model.entity.Consulta;
import br.com.meli.desafiospring.model.entity.Paciente;
import br.com.meli.desafiospring.util.ArquivoUtil;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class PacienteService {

    private final ModelMapper modelMapper = new ModelMapper();
    @Getter
    private static final List<Paciente> listaPaciente = new ArrayList<>();
    private final File file = new File ("paciente.json");
    private final ProprietarioService proprietarioService = new ProprietarioService();

    public Integer cadastrar (PacienteRequestDTO pacienteRequestDTO) {
        Paciente paciente = convertePaciente(pacienteRequestDTO);
        paciente.setProprietario(ProprietarioService.buscarProprietario(pacienteRequestDTO.getIdProprietario()));
        paciente.setId(listaPaciente.size() + 1);
        listaPaciente.add(paciente);
        try {
            ArquivoUtil.collectionToJsonPaciente(file, listaPaciente);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paciente.getId();
    }

    public Paciente convertePaciente(PacienteRequestDTO pacienteDTO){
        return modelMapper.map(pacienteDTO, Paciente.class);
    }

    public PacienteRequestDTO convertePacienteDTO(Paciente paciente) {
        return modelMapper.map(paciente, PacienteRequestDTO.class);
    }

    public PacienteResponseDTO convertePacienteResponseDTO(Paciente paciente) {
        return modelMapper.map(paciente, PacienteResponseDTO.class);
    }

    public PacienteRequestDTO editar(PacienteRequestDTO pacienteDTO, Integer id){
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
            ArquivoUtil.collectionToJsonPaciente(file, listaPaciente);
        } catch (IOException e){
            e.printStackTrace();
        }

        return convertePacienteDTO(paciente);
    }

    public static Paciente buscaPaciente(Integer id) {
        Optional<Paciente> optionalPaciente = listaPaciente.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
        return optionalPaciente.orElse(null);
    }

    public void validaEntrada(PacienteRequestDTO pacienteDTO) {
        if(ObjectUtils.isEmpty(pacienteDTO.getEspecie())
            && ObjectUtils.isEmpty(pacienteDTO.getRaca())
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
                    pacienteResponseDTO = convertePacienteResponseDTO(listaPaciente.get(i));
                    pacienteResponseDTO.setProprietarioDTO(proprietarioService.converteProprietarioDTO(
                            listaPaciente.get(i).getProprietario()));
                    listaPaciente.remove(listaPaciente.get(i));
                }
            }

            try {
                ArquivoUtil.collectionToJsonPaciente(file, listaPaciente);
            } catch (IOException e){
                e.printStackTrace();
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
            PacienteResponseDTO pacienteResponseDTO = convertePacienteResponseDTO(p);
            pacienteResponseDTO.setProprietarioDTO(proprietarioService.converteProprietarioDTO(p.getProprietario()));
            listaPacienteResponseDTO.add(pacienteResponseDTO);
        }

        listaPacienteResponseDTO.sort(Comparator.comparing(c -> c.getProprietarioDTO().getNome()));

        return listaPacienteResponseDTO;

    }

    public List<PacienteResponseDTO> listaConvertResponseDTO(List<Paciente> listaPaciente) {
        Type listType = new TypeToken<List<PacienteResponseDTO>>() {}.getType();
        return modelMapper.map(listaPaciente, listType);
    }
}



