package br.com.meli.desafiospring.model.service;

import br.com.meli.desafiospring.exception.ValidaEntradaException;
import br.com.meli.desafiospring.model.dao.ConsultaDAO;
import br.com.meli.desafiospring.model.dao.MedicoDAO;
import br.com.meli.desafiospring.model.dto.MedicoDTO;
import br.com.meli.desafiospring.model.entity.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

public class MedicoServiceTest {

    MedicoDTO mockMedicoDTO = new MedicoDTO();
    MedicoDAO mockMedicoDAO = mock(MedicoDAO.class);
    MedicoService mockMedicoService = new MedicoService(mockMedicoDAO);
    private final ConsultaDAO mockConsultaDAO = mock(ConsultaDAO.class);
    private final PacienteService mockPacienteService = mock(PacienteService.class);
    private final ConsultaService consultaService = new ConsultaService(mockConsultaDAO, mockMedicoService, mockPacienteService);



    @Test
    void cadastrarMedicoTest() {

        mockMedicoDTO.setCpf("98765432198");
        mockMedicoDTO.setNome("Jhony");
        mockMedicoDTO.setSobrenome("Zuim");
        mockMedicoDTO.setRegistro("CRM8765");
        mockMedicoDTO.setEspecialidade("Clinico geral");

        doNothing().when(mockMedicoDAO).inserir(anyList());

        mockMedicoService.cadastrar(mockMedicoDTO);

        assertFalse(MedicoService.getListaMedico().isEmpty());

    }
    @Test
    void validar() {

        ValidaEntradaException exception = assertThrows(ValidaEntradaException.class, ()->{
            mockMedicoService.validar(mockMedicoDTO);
        });

        String expectedMessage1 = "Por favor preencher todos os campos";
        String actualMessage1 = exception.getMessage();

        assertTrue(actualMessage1.contains(expectedMessage1));

    }

    @Test
    void editar() {

        Medico medico = new Medico(1, "98765432198", "Jose", "Zuim", "CRM8765", "Clinico Geral");
        MedicoService.getListaMedico().add(medico);

        doNothing().when(mockMedicoDAO).inserir(anyList());
        mockMedicoService.editar(mockMedicoDTO, 1);

        assertNotNull(mockMedicoDTO);
        mockMedicoService.removerMedico(1);
    }

    @Test
    void removerMedico() {
        List<Consulta> listConsulta = ConsultaService.getListaConsulta();
        ConsultaService.getListaConsulta().removeAll(listConsulta);

        Medico medico = new Medico(1, "98765432198", "Joao", "Zuim", "CRM8765", "Clinico Geral");
        MedicoService.getListaMedico().add(medico);

        doNothing().when(mockMedicoDAO).inserir(anyList());
        mockMedicoService.removerMedico(1);

        assertTrue(MedicoService.getListaMedico().isEmpty());
    }

    @Test
    void buscaMedico() {


    }

    @Test
    void verificarConsultaVerdade() {

        List<Consulta> listConsulta = ConsultaService.getListaConsulta();
        ConsultaService.getListaConsulta().removeAll(listConsulta);

        Medico medico = new Medico(1, "98765432198", "Joao", "Zuim", "CRM8765", "Clinico Geral");

        IConsulta consulta = new Consulta().comId(1)
                .comMotivo("rotina")
                .comDiagnostico("n/a")
                .comTratamento("n/a")
                .comMedico(medico)
                .noPeriodo(LocalDateTime.now())
                .comPaciente(new Paciente(1, "cao", "dalmata", "preto", LocalDate.now(), "zero"
                        , new Proprietario(1, "12345632101", "ed", "nobre", LocalDate.now(), "rua zero", 1199998888L)));

        ConsultaService.getListaConsulta().add((Consulta) consulta);

        boolean variavel = mockMedicoService.verificarConsulta(medico);

        assertTrue(variavel);
    }

    @Test
    void verificarConsultaFalse() {

        List<Consulta> listConsulta = ConsultaService.getListaConsulta();
        ConsultaService.getListaConsulta().removeAll(listConsulta);

        Medico medico = new Medico(1, "98765432198", "Joao", "Zuim", "CRM8765", "Clinico Geral");

        boolean variavel = mockMedicoService.verificarConsulta(medico);

        assertFalse(variavel);

    }

}
