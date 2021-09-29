package br.com.meli.desafiospring.model.service;

import br.com.meli.desafiospring.exception.ValidaEntradaException;
import br.com.meli.desafiospring.model.dao.ConsultaDAO;
import br.com.meli.desafiospring.model.dto.*;
import br.com.meli.desafiospring.model.entity.Consulta;
import br.com.meli.desafiospring.model.entity.IConsulta;
import br.com.meli.desafiospring.model.entity.Medico;
import br.com.meli.desafiospring.model.entity.Paciente;
import br.com.meli.desafiospring.util.ArquivoUtil;
import br.com.meli.desafiospring.util.ConvesorUtil;
import lombok.Getter;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class ConsultaService {

    private final ConvesorUtil convesorUtil = new ConvesorUtil();
    @Getter
    private static final List<Consulta> listaConsulta = new ArrayList<>();
    private final File file = new File("consultas.json");
    private final ConsultaDAO consultaDAO = new ConsultaDAO();


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
        consultaDAO.inserir(file, listaConsulta);
        return ((Consulta) consulta).getId();
    }

    public ConsultaResponseDTO editar(ConsultaRequestDTO consultaRequestDTO, Integer id) {
        Optional<Consulta> optionalConsulta = listaConsulta.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
        Consulta consulta = optionalConsulta.orElse(null);
        assert consulta != null;
        consulta.comMotivo(consultaRequestDTO.getMotivo()).comDiagnostico(consultaRequestDTO.getDiagnostico())
                .comTratamento(consultaRequestDTO.getTratamento());
        consultaDAO.inserir(file, listaConsulta);
        ConsultaResponseDTO consultaResponseDTO = (ConsultaResponseDTO) convesorUtil.conveterDTO(consulta, ConsultaResponseDTO.class);
        consultaResponseDTO.setMedicoDTO((MedicoDTO) convesorUtil.conveterDTO(consulta.getMedico(), MedicoDTO.class));
        return consultaResponseDTO;
    }

    public List<ConsultaResponseDTO> listar() {
        List<ConsultaResponseDTO> listaConsultaResponseDTO = converterListaConsultaResponseDTO(listaConsulta);
        return listaConsultaResponseDTO.stream()
                .sorted((c1, c2) -> c2.getDataHora().compareTo(c1.getDataHora()))
                .collect(toList());
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
        List<ConsultaResponseDTO> listaConsultaResponseDTO = converterListaConsultaResponseDTO(listaConsulta.stream()
                .filter(c -> c.getDataHora().getDayOfMonth() == dataConsulta.getDayOfMonth())
                .filter(c -> c.getDataHora().getMonth() == dataConsulta.getMonth())
                .filter(c -> c.getDataHora().getYear() == dataConsulta.getYear())
                .collect(toList()));
        listaConsultaResponseDTO.sort(Comparator.comparing(ConsultaResponseDTO::getDataHora));
        return listaConsultaResponseDTO;
    }

    public void validaEntrada(ConsultaRequestDTO consultaRequestDTO) {
        if(ObjectUtils.isEmpty(consultaRequestDTO.getIdPaciente())
                && ObjectUtils.isEmpty(consultaRequestDTO.getRegistroMedico())
                && ObjectUtils.isEmpty(consultaRequestDTO.getMotivo())
                && ObjectUtils.isEmpty(consultaRequestDTO.getDataHora()))
            throw new ValidaEntradaException("Campos obrigatorios nao informados: Id do paciente, registro medico, motivo, data e hora");
        if(ObjectUtils.isEmpty(consultaRequestDTO.getIdPaciente()))
            throw new ValidaEntradaException("Id paciente nao informando!!! Por gentileza informar.");
        if (ObjectUtils.isEmpty(consultaRequestDTO.getRegistroMedico()))
            throw new ValidaEntradaException("Registro medico nao informando!!! Por gentileza informar.");
        if (ObjectUtils.isEmpty(consultaRequestDTO.getMotivo()))
            throw new ValidaEntradaException("Motivo nao informando!!! Por gentileza informar.");
        if(ObjectUtils.isEmpty(consultaRequestDTO.getDataHora()))
            throw new ValidaEntradaException("Data e hora nao informandos!!! Por gentileza informar.");
    }

    public List<ConsultaResponseDTO> converterListaConsultaResponseDTO(List<Consulta> listaConsulta) {
        List<ConsultaResponseDTO> listaConsultaResponseDTO = new ArrayList<>();
        listaConsulta.forEach(c -> {
            ConsultaResponseDTO consultaResponseDTO = (ConsultaResponseDTO) convesorUtil.conveterDTO(c, ConsultaResponseDTO.class);
            consultaResponseDTO.setMedicoDTO((MedicoDTO) convesorUtil.conveterDTO(c.getMedico(), MedicoDTO.class));
            consultaResponseDTO.setPacienteResponseDTO((PacienteResponseDTO) convesorUtil.conveterDTO(c.getPaciente(), PacienteResponseDTO.class));
            consultaResponseDTO.getPacienteResponseDTO().setProprietarioDTO((ProprietarioDTO) convesorUtil.conveterDTO(c.getPaciente().getProprietario(), ProprietarioDTO.class));
            listaConsultaResponseDTO.add(consultaResponseDTO);
        });
        return listaConsultaResponseDTO;
    }

}
