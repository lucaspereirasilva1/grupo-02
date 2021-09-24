package br.com.meli.desafiospring.model.service;

import br.com.meli.desafiospring.exception.ValidaEntradaException;
import br.com.meli.desafiospring.model.dto.ConsultaRequestDTO;
import br.com.meli.desafiospring.model.dto.ConsultaResponseDTO;
import br.com.meli.desafiospring.model.dto.PacienteResponseDTO;
import br.com.meli.desafiospring.model.entity.Consulta;
import br.com.meli.desafiospring.model.entity.IConsulta;
import br.com.meli.desafiospring.model.entity.Medico;
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
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ConsultaService {

    private final ModelMapper modelMapper = new ModelMapper();
    @Getter
    private static final List<Consulta> listaConsulta = new ArrayList<>();
    private final File file = new File("consultas.json");
    private final MedicoService medicoService = new MedicoService();
    private final PacienteService pacienteService = new PacienteService();
    private final ProprietarioService proprietarioService = new ProprietarioService();

    public Integer cadastrar(ConsultaRequestDTO consultaRequestDTO) {
        Medico medico = MedicoService.buscaMedico(consultaRequestDTO.getRegistroMedico());
        Paciente paciente = PacienteService.buscaPaciente(consultaRequestDTO.getIdPaciente());
        IConsulta consulta = new Consulta().comId(listaConsulta.size() + 1)
                .comMotivo(consultaRequestDTO.getMotivo())
                .comDiagnostico(consultaRequestDTO.getDiagnostico())
                .comTratamento(consultaRequestDTO.getTratamento())
                .comMedico(medico)
                .noPeriodo(consultaRequestDTO.getDataHora())
                .comPaciente(paciente);
        listaConsulta.add((Consulta) consulta);

        try {
            ArquivoUtil.collectionToJson(file, listaConsulta);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ConsultaResponseDTO consultaResponseDTO =  converteConsultaResponseDTO((Consulta) consulta);
        consultaResponseDTO.setMedicoDTO(medicoService.converteMedicoDTO(medico));
        PacienteResponseDTO pacienteResponseDTO = pacienteService.convertePacienteResponseDTO(paciente);
        pacienteResponseDTO.setProprietarioDTO(proprietarioService.converteProprietarioDTO(paciente.getProprietario()));
        consultaResponseDTO.setPacienteResponseDTO(pacienteResponseDTO);
        return ((Consulta) consulta).getId();
    }

    private ConsultaRequestDTO converteConsultaDTO(Consulta consulta) {
        return modelMapper.map(consulta, ConsultaRequestDTO.class);
    }

    private ConsultaResponseDTO converteConsultaResponseDTO(Consulta consulta) {
        return modelMapper.map(consulta, ConsultaResponseDTO.class);
    }

    public ConsultaResponseDTO editar(ConsultaRequestDTO consultaRequestDTO, Integer id) {
        Optional<Consulta> optionalConsulta = listaConsulta.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
        Consulta consulta = optionalConsulta.orElse(null);
        assert consulta != null;
        consulta.comMotivo(consultaRequestDTO.getMotivo()).comDiagnostico(consultaRequestDTO.getDiagnostico())
                .comTratamento(consultaRequestDTO.getTratamento());
        try {
            ArquivoUtil.collectionToJson(file, listaConsulta);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ConsultaResponseDTO consultaResponseDTO = converteConsultaResponseDTO(consulta);
        consultaResponseDTO.setMedicoDTO(medicoService.converteMedicoDTO(consulta.getMedico()));
        return consultaResponseDTO;
    }

    public List<ConsultaResponseDTO> listar() {
        List<ConsultaResponseDTO> listaConsultaResponseDTO = new ArrayList<>();
        for (Consulta c : listaConsulta) {
            ConsultaResponseDTO consultaResponseDTO = converteConsultaResponseDTO(c);
            consultaResponseDTO.setMedicoDTO(medicoService.converteMedicoDTO(c.getMedico()));
            listaConsultaResponseDTO.add(consultaResponseDTO);
        }

        return listaConsultaResponseDTO.stream()
                .sorted((c1, c2) -> c2.getDataHora().compareTo(c1.getDataHora()))
                .collect(Collectors.toList());
    }

    public List<ConsultaResponseDTO> listaConvertResponseDTO(List<Consulta> listaConsulta) {
        Type listType = new TypeToken<List<ConsultaResponseDTO>>() {}.getType();
        return modelMapper.map(listaConsulta, listType);
    }

    public List<String> listarTotalMedicos() {
        List<String> consultaPorMedico = new ArrayList<>();

        for (Medico m : MedicoService.getListaMedico()) {
            String retorno = m.getNome();
            int contador = 0;
            for (Consulta c : listaConsulta) {
                if (m.getRegistro().equals(c.getMedico().getRegistro())) {
                    contador++;
                }
            }
            retorno = retorno + " " + contador;
            consultaPorMedico.add(retorno);
        }

        return consultaPorMedico;
    }


    public List<ConsultaResponseDTO> listarPorDia(String data) {
        LocalDate dataConsulta = LocalDate.parse(data);
        List<ConsultaResponseDTO> listaConsultaResponseDTO = new ArrayList<>();
        List<Consulta> retornoListaConsultas = listaConsulta.stream()
                .filter(c -> c.getDataHora().getDayOfMonth() == dataConsulta.getDayOfMonth())
                .filter(c -> c.getDataHora().getMonth() == dataConsulta.getMonth())
                .filter(c -> c.getDataHora().getYear() == dataConsulta.getYear())
                .collect(Collectors.toList());
        for (Consulta c : retornoListaConsultas) {
            ConsultaResponseDTO consultaResponseDTO = converteConsultaResponseDTO(c);
            consultaResponseDTO.setMedicoDTO(medicoService.converteMedicoDTO(c.getMedico()));
            listaConsultaResponseDTO.add(consultaResponseDTO);
        }
        listaConsultaResponseDTO.sort(Comparator.comparing(ConsultaResponseDTO::getDataHora));
        return listaConsultaResponseDTO;
    }

    public void validaEntrada(ConsultaRequestDTO consultaRequestDTO) {
        if (ObjectUtils.isEmpty(consultaRequestDTO.getRegistroMedico()))
            throw new ValidaEntradaException("Registro medico nao informando!!! Por gentileza informar.");
        if (ObjectUtils.isEmpty(consultaRequestDTO.getMotivo()))
            throw new ValidaEntradaException("Motivo nao informando!!! Por gentileza informar.");
        if(ObjectUtils.isEmpty(consultaRequestDTO.getDataHora()))
            throw new ValidaEntradaException("Data e hora nao informandos!!! Por gentileza informar.");
    }

}
