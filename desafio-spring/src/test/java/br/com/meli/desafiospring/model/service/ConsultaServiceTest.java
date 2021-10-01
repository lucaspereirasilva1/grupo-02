package br.com.meli.desafiospring.model.service;

import br.com.meli.desafiospring.exception.ValidaEntradaException;
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

    public ConsultaServiceTest() {
        geraMassa();
    }

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
        boolean editou = false;
        ConsultaRequestDTO consultaRequestDTO = new ConsultaRequestDTO(LocalDateTime.now()
                ,"rotina", "n/a", "n/a", "CRM1", 1);
        doNothing().when(mockConsultaDAO).inserir(anyList());

        consultaService.editar(consultaRequestDTO, 1);
        for (Consulta c: ConsultaService.getListaConsulta()) {
            if (c.getDataHora().equals(consultaRequestDTO.getDataHora())
                    && c.getMotivo().equals(consultaRequestDTO.getMotivo())
                    && c.getDiagnostico().equals(consultaRequestDTO.getDiagnostico())
                    && c.getTratamento().equals(consultaRequestDTO.getTratamento())
                    && c.getMedico().getRegistro().equals(consultaRequestDTO.getRegistroMedico())
                    && c.getPaciente().getId().equals(consultaRequestDTO.getIdPaciente())) {
                editou = true;
                break;
            }
        }

        assertTrue(editou);
    }

    @Test
    void listar() {
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
        List<String> listaTotalMedicos = consultaService.listarTotalMedicos();
        assertFalse(listaTotalMedicos.isEmpty());
    }

    @Test
    void listarPorDia() {
        boolean listou = false;
        LocalDate dia = LocalDate.of(2021, 11, 30);
        List<ConsultaResponseDTO> listaConsultaResponseDTO = consultaService.listarPorDia("2021-11-30");

        for (ConsultaResponseDTO c: listaConsultaResponseDTO) {
            if (c.getDataHora().getYear() != dia.getYear()
                    || c.getDataHora().getMonth() != dia.getMonth()
                    || c.getDataHora().getDayOfMonth() != dia.getDayOfMonth()) {
                continue;
            }
            listou = true;
        }

        assertTrue(listou);
    }

    @Test
    void validaEntradaTodosCampos() {
        ConsultaRequestDTO consultaRequestDTO = new ConsultaRequestDTO();

        ValidaEntradaException validaEntradaException = assertThrows(ValidaEntradaException.class, () -> consultaService.validaEntrada(consultaRequestDTO));

        String mensagemEsperada = "Campos obrigatorios nao informados: Id do paciente, registro medico, motivo, data e hora";
        String mensagemRecebida = validaEntradaException.getMessage();

        assertTrue(mensagemEsperada.contains(mensagemRecebida));
    }

    void geraMassa() {
        ConsultaService.getListaConsulta().clear();
        MedicoService.getListaMedico().clear();

        Medico medico1 = new Medico(1, "11111111111", "zero", "um", "CRM1", "pediatra");
        Medico medico2 = new Medico(2, "22222222222", "um", "tres", "CRM3", "dermatologista");
        Medico medico3 = new Medico(3, "33333333333", "dois", "quatro", "CRM4", "ortopedista");

        MedicoService.getListaMedico().add(medico1);
        MedicoService.getListaMedico().add(medico2);
        MedicoService.getListaMedico().add(medico3);

        LocalDateTime dataHora1 = LocalDateTime.of(2021, 11, 30, 12, 1);
        LocalDateTime dataHora2 = LocalDateTime.of(2021, 11, 30, 12, 2);
        LocalDateTime dataHora3 = LocalDateTime.of(2021, 12, 31, 12, 2);

        IConsulta consulta1 = new Consulta().comId(1)
                .comMotivo("rotina")
                .comDiagnostico("a realizar")
                .comTratamento("a realizar")
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
        IConsulta consulta3 = new Consulta().comId(3)
                .comMotivo("cirurgia")
                .comDiagnostico("apendice")
                .comTratamento("n/a")
                .comMedico(medico3)
                .noPeriodo(dataHora3)
                .comPaciente(new Paciente(1, "cao", "dalmata", "preto", LocalDate.now(), "zero"
                        , new Proprietario(1, "12345632101", "ed", "nobre", LocalDate.now(), "rua zero", 1199998888L)));

        ConsultaService.getListaConsulta().add((Consulta) consulta1);
        ConsultaService.getListaConsulta().add((Consulta) consulta2);
        ConsultaService.getListaConsulta().add((Consulta) consulta3);
    }
}