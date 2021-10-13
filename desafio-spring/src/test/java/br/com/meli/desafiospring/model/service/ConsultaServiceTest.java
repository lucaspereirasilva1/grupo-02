package br.com.meli.desafiospring.model.service;

import br.com.meli.desafiospring.exception.ValidaEntradaException;
import br.com.meli.desafiospring.model.dao.ConsultaDAO;
import br.com.meli.desafiospring.model.dto.ConsultaRequestDTO;
import br.com.meli.desafiospring.model.dto.ConsultaResponseDTO;
import br.com.meli.desafiospring.model.entity.*;
import br.com.meli.desafiospring.model.repository.ConsultaRepository;
import br.com.meli.desafiospring.model.repository.MedicoRepository;
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
    private final ConsultaRepository mockConsultaRepository = mock(ConsultaRepository.class);
    private final ConsultaService consultaService = new ConsultaService(mockConsultaDAO, mockMedicoService
            , mockPacienteService, mockConsultaRepository);

    public ConsultaServiceTest() {
        geraMassa();
    }

    @Test
    void cadastrar() {
        boolean cadastrou = false;
        ConsultaRequestDTO consultaRequestDTO = new ConsultaRequestDTO(LocalDateTime.now()
                ,"rotina", "n/a", "n/a", "CRM1", 1);

        doNothing()
                .when(mockConsultaDAO).inserir(anyList());

        when((mockMedicoService).buscaMedico((anyString())))
                .thenReturn(new Medico().
                        comId(1).
                        comCPF("12378909876").
                        comNome("zero").
                        comSobreNome("um").
                        comRegistro("CRM1").
                        paraEspecialidade("pediatria").
                        build());
        when((mockPacienteService).buscaPaciente(anyInt()))
                .thenReturn(new Paciente()
                        .comId(1)
                        .comEspecie("cao")
                        .comRaca("dalmata")
                        .comCor("preto")
                        .comDataDeNascimento(LocalDate.now())
                        .comNome("zero")
                        .comProprietario((new Proprietario().
                                comId(1).
                                comCpf("12345632101").
                                comNome("ed").
                                comSobreNome("nobre").
                                comDataDeNascimento(LocalDate.now()).
                                comEndereco("rua zero").
                                comTelefone(1199998888L).
                                build()))
                        .build());

        Integer id = consultaService.cadastrar(consultaRequestDTO);

        for (Consulta c: ConsultaService.getListaConsulta()) {
            if (c.getId().equals(id)) {
                cadastrou = true;
                break;
            }
        }
        assertTrue(cadastrou);
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

    @Test
    void validaEntradaPaciente() {
        ConsultaRequestDTO consultaRequestDTO = new ConsultaRequestDTO();
        consultaRequestDTO.setRegistroMedico("CRM1");
        consultaRequestDTO.setMotivo("rotina");
        consultaRequestDTO.setDataHora(LocalDateTime.now());

        ValidaEntradaException validaEntradaException = assertThrows(ValidaEntradaException.class, () -> consultaService.validaEntrada(consultaRequestDTO));

        String mensagemEsperada = "Id paciente nao informando!!! Por gentileza informar.";
        String mensagemRecebida = validaEntradaException.getMessage();

        assertTrue(mensagemEsperada.contains(mensagemRecebida));
    }

    @Test
    void validaEntradaMedico() {
        ConsultaRequestDTO consultaRequestDTO = new ConsultaRequestDTO();
        consultaRequestDTO.setIdPaciente(1);
        consultaRequestDTO.setMotivo("rotina");
        consultaRequestDTO.setDataHora(LocalDateTime.now());

        ValidaEntradaException validaEntradaException = assertThrows(ValidaEntradaException.class, () -> consultaService.validaEntrada(consultaRequestDTO));

        String mensagemEsperada = "Registro medico nao informando!!! Por gentileza informar.";
        String mensagemRecebida = validaEntradaException.getMessage();

        assertTrue(mensagemEsperada.contains(mensagemRecebida));
    }

    @Test
    void validaEntradaMotivo() {
        ConsultaRequestDTO consultaRequestDTO = new ConsultaRequestDTO();
        consultaRequestDTO.setIdPaciente(1);
        consultaRequestDTO.setRegistroMedico("CRM1");
        consultaRequestDTO.setDataHora(LocalDateTime.now());

        ValidaEntradaException validaEntradaException = assertThrows(ValidaEntradaException.class, () -> consultaService.validaEntrada(consultaRequestDTO));

        String mensagemEsperada = "Motivo nao informando!!! Por gentileza informar.";
        String mensagemRecebida = validaEntradaException.getMessage();

        assertTrue(mensagemEsperada.contains(mensagemRecebida));
    }

    @Test
    void validaEntradaDataHora() {
        ConsultaRequestDTO consultaRequestDTO = new ConsultaRequestDTO();
        consultaRequestDTO.setIdPaciente(1);
        consultaRequestDTO.setMotivo("rotina");
        consultaRequestDTO.setRegistroMedico("CRM1");

        ValidaEntradaException validaEntradaException = assertThrows(ValidaEntradaException.class, () -> consultaService.validaEntrada(consultaRequestDTO));

        String mensagemEsperada = "Data e hora nao informandos!!! Por gentileza informar.";
        String mensagemRecebida = validaEntradaException.getMessage();

        assertTrue(mensagemEsperada.contains(mensagemRecebida));
    }

    void geraMassa() {
        ConsultaService.getListaConsulta().clear();
        MedicoService.getListaMedico().clear();

        Medico medico1 = new Medico()
                .comId(1)
                .comCPF("11111111111")
                .comRegistro("CRM1")
                .paraEspecialidade("pediatra")
                .build();
        Medico medico2 = new Medico()
                .comId(2)
                .comCPF("22222222222")
                .comNome("um")
                .comSobreNome("tres")
                .comRegistro("CRM3")
                .paraEspecialidade("dermatologista")
                .build();
        Medico medico3 = new Medico()
                .comId(3)
                .comCPF("33333333333")
                .comNome("dois")
                .comSobreNome("quatro")
                .comRegistro("CRM4")
                .paraEspecialidade("ortopedista")
                .build();

        MedicoService.getListaMedico().add(medico1);
        MedicoService.getListaMedico().add(medico2);
        MedicoService.getListaMedico().add(medico3);

        LocalDateTime dataHora1 = LocalDateTime.of(2021, 11, 30, 12, 1);
        LocalDateTime dataHora2 = LocalDateTime.of(2021, 11, 30, 12, 2);
        LocalDateTime dataHora3 = LocalDateTime.of(2021, 12, 31, 12, 2);

        Paciente paciente = new Paciente()
                .comId(1)
                .comEspecie("cao")
                .comRaca("dalmata")
                .comCor("preto")
                .comDataDeNascimento(LocalDate.now())
                .comNome("zero")
                .comProprietario((new Proprietario().
                        comId(1).
                        comCpf("12345632101").
                        comNome("ed").
                        comSobreNome("nobre").
                        comDataDeNascimento(LocalDate.now()).
                        comEndereco("rua zero").
                        comTelefone(1199998888L).
                        build()))
                .build();

        Consulta consulta1 = new Consulta().comId(1)
                .comMotivo("rotina")
                .comDiagnostico("a realizar")
                .comTratamento("a realizar")
                .comMedico(medico1)
                .noPeriodo(dataHora2)
                .comPaciente(paciente)
                .build();
        Consulta consulta2 = new Consulta().comId(2)
                .comMotivo("exames")
                .comDiagnostico("obesidade")
                .comTratamento("exercicios")
                .comMedico(medico2)
                .noPeriodo(dataHora1)
                .comPaciente(paciente)
                .build();
        Consulta consulta3 = new Consulta().comId(3)
                .comMotivo("cirurgia")
                .comDiagnostico("apendice")
                .comTratamento("n/a")
                .comMedico(medico3)
                .noPeriodo(dataHora3)
                .comPaciente(paciente)
                .build();

        ConsultaService.getListaConsulta().add(consulta1);
        ConsultaService.getListaConsulta().add(consulta2);
        ConsultaService.getListaConsulta().add(consulta3);
    }
}