package br.com.meli.desafiospring.model.service;

import br.com.meli.desafiospring.exception.ValidaEntradaException;
import br.com.meli.desafiospring.model.dto.PacienteRequestDTO;
import br.com.meli.desafiospring.model.entity.Paciente;
import br.com.meli.desafiospring.util.ArquivoUtil;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class PacienteService {

    private final ModelMapper moddelMapper = new ModelMapper();
    @Getter
    private static final List<Paciente> listaPaciente = new ArrayList<>();
    private final File file = new File ("paciente.json");

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
        return moddelMapper.map(pacienteDTO, Paciente.class);
    }
    private PacienteRequestDTO convertePacienteDTO(Paciente paciente) {
        return moddelMapper.map(paciente, PacienteRequestDTO.class);
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
}



