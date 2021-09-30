package br.com.meli.desafiospring.model.service;

import br.com.meli.desafiospring.model.dao.ConsultaDAO;
import br.com.meli.desafiospring.model.dto.ConsultaRequestDTO;
import br.com.meli.desafiospring.model.dto.ConsultaResponseDTO;
import br.com.meli.desafiospring.model.entity.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConsultaServiceTest {

    private final ConsultaDAO mockConsultaDAO = mock(ConsultaDAO.class);
    private final MedicoService mockMedicoService = mock(MedicoService.class);
    private final PacienteService mockPacienteService = mock(PacienteService.class);
    private final ConsultaService consultaService = new ConsultaService(mockConsultaDAO, mockMedicoService, mockPacienteService);

    @Test
    void cadastrar() {
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
        geraMassa();
        boolean validaLista = false;
        Optional<Consulta> firstConsulta = ConsultaService.getListaConsulta().stream()
                .findFirst();
        assert firstConsulta.isPresent();

        List<ConsultaResponseDTO> listaConsultaResponseDTO = consultaService.listar();
        for (ConsultaResponseDTO c: listaConsultaResponseDTO) {
            if(c.getDataHora().isAfter(firstConsulta.get().getDataHora())
                    || c.getDataHora().equals(firstConsulta.get().getDataHora()))
                validaLista = true;
                break;
        }

        assertFalse(listaConsultaResponseDTO.isEmpty());
        assertTrue(validaLista);
    }

    @Test
    void listarTotalMedicos() {
        geraMassa();
        List<String> listaTotalMedicos = consultaService.listarTotalMedicos();
        assertFalse(listaTotalMedicos.isEmpty());
    }

    @Test
    void listarPorDia() {
    }

    @Test
    void validaEntrada() {
    }

    void geraMassa() {
        List<Consulta> listaRemoveConsulta = ConsultaService.getListaConsulta();
        ConsultaService.getListaConsulta().removeAll(listaRemoveConsulta);

        List<Medico> listaRemoveMedico = MedicoService.getListaMedico();
        MedicoService.getListaMedico().removeAll(listaRemoveMedico);

        Medico medico1 = new Medico(1, "12378909876", "zero", "um", "CRM1", "pediatra");
        Medico medico2 = new Medico(2, "12378909877", "um", "dois", "CRM2", "dermatologista");

        MedicoService.getListaMedico().add(medico1);
        MedicoService.getListaMedico().add(medico2);

        LocalDateTime dataHora1 = LocalDateTime.of(2021, 9, 30, 12, 1);
        LocalDateTime dataHora2 = LocalDateTime.of(2021, 9, 30, 12, 2);

        IConsulta consulta1 = new Consulta().comId(1)
                .comMotivo("rotina")
                .comDiagnostico("n/a")
                .comTratamento("n/a")
                .comMedico(medico1)
                .noPeriodo(dataHora2)
                .comPaciente(new Paciente(1, "cao", "dalmata", "preto", LocalDate.now(), "zero"
                        , new Proprietario(1, "12345632101", "ed", "nobre", LocalDate.now(), "rua zero", 1199998888L)));
        IConsulta consulta2 = new Consulta().comId(2)
                .comMotivo("exames")
                .comDiagnostico("obesidade")
                .comTratamento("exercicios")
                .comMedico(medico2)
                .noPeriodo(dataHora1)
                .comPaciente(new Paciente(1, "cao", "dalmata", "preto", LocalDate.now(), "zero"
                        , new Proprietario(1, "12345632101", "ed", "nobre", LocalDate.now(), "rua zero", 1199998888L)));

        ConsultaService.getListaConsulta().add((Consulta) consulta1);
        ConsultaService.getListaConsulta().add((Consulta) consulta2);
    }
}