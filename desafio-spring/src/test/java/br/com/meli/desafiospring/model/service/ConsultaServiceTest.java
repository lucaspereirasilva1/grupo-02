package br.com.meli.desafiospring.model.service;

import br.com.meli.desafiospring.model.dao.ConsultaDAO;
import br.com.meli.desafiospring.model.dto.ConsultaRequestDTO;
import br.com.meli.desafiospring.model.dto.ConsultaResponseDTO;
import br.com.meli.desafiospring.model.entity.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ConsultaServiceTest {

    @Test
    void cadastrar() {

        ConsultaDAO mockConsultaDAO = mock(ConsultaDAO.class);
        MedicoService mockMedicoService = mock(MedicoService.class);
        PacienteService mockPacienteService = mock(PacienteService.class);

        ConsultaService consultaService = new ConsultaService(mockConsultaDAO, mockMedicoService, mockPacienteService);
        ConsultaRequestDTO consultaRequestDTO = new ConsultaRequestDTO(LocalDateTime.now()
                ,"rotina", "n/a", "n/a", "CRM1", 1);

        doNothing()
                .when(mockConsultaDAO).inserir(anyList());
        when((mockMedicoService).buscaMedico((anyString())))
                .thenReturn(new Medico(1, "12378909876", "zero", "um", "CRM1", "pediatra"));
        when((mockPacienteService).buscaPaciente(anyInt()))
                .thenReturn(new Paciente(1, "cao", "dalmata", "preto", LocalDate.now(), "zero"
                        , new Proprietario(1, "12345632101", "ed", "nobre", LocalDate.now(), "rua zero", 1199998888L)));

        consultaService.cadastrar(consultaRequestDTO);
        assertFalse(ConsultaService.getListaConsulta().isEmpty());

    }

    @Test
    void editar() {
        ConsultaDAO mockConsultaDAO = mock(ConsultaDAO.class);
        MedicoService mockMedicoService = mock(MedicoService.class);
        PacienteService mockPacienteService = mock(PacienteService.class);

        ConsultaService consultaService = new ConsultaService(mockConsultaDAO, mockMedicoService, mockPacienteService);
        ConsultaRequestDTO consultaRequestDTO = new ConsultaRequestDTO(LocalDateTime.now()
                ,"rotina", "n/a", "n/a", "CRM1", 1);

        IConsulta consulta = new Consulta().comId(1)
                .comMotivo(consultaRequestDTO.getMotivo())
                .comDiagnostico(consultaRequestDTO.getDiagnostico())
                .comTratamento(consultaRequestDTO.getTratamento())
                .comMedico(new Medico(1, "12378909876", "zero", "um", "CRM1", "pediatra"))
                .noPeriodo(consultaRequestDTO.getDataHora())
                .comPaciente(new Paciente(1, "cao", "dalmata", "preto", LocalDate.now(), "zero"
                        , new Proprietario(1, "12345632101", "ed", "nobre", LocalDate.now(), "rua zero", 1199998888L)));

        ConsultaService.getListaConsulta().add((Consulta) consulta);
        doNothing().when(mockConsultaDAO).inserir(anyList());

        ConsultaResponseDTO consultaResponseDTO = consultaService.editar(consultaRequestDTO, 1);
        assertNotNull(consultaResponseDTO);
    }

    @Test
    void listar() {
    }

    @Test
    void listarTotalMedicos() {
    }

    @Test
    void listarPorDia() {
    }

    @Test
    void validaEntrada() {
    }
}