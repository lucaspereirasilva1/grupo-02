package br.com.meli.desafiospring.model.service;

import br.com.meli.desafiospring.exception.ValidaEntradaException;
import br.com.meli.desafiospring.model.dao.MedicoDAO;
import br.com.meli.desafiospring.model.dto.ConsultaRequestDTO;
import br.com.meli.desafiospring.model.dto.ConsultaResponseDTO;
import br.com.meli.desafiospring.model.dto.MedicoDTO;
import br.com.meli.desafiospring.model.entity.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

public class MedicoServiceTest {

    @Test
    void cadastrarMedicoTest() {

        MedicoDTO mockMedicoDTO = new MedicoDTO();
        MedicoDAO mockMedicoDAO = mock(MedicoDAO.class);
        MedicoService mockMedicoService = new MedicoService(mockMedicoDAO);

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
        MedicoDTO medicoDTO = new MedicoDTO();
        MedicoDAO mockMedicoDAO = mock(MedicoDAO.class);
        MedicoService mockMedicoService = new MedicoService(mockMedicoDAO);

        ValidaEntradaException exception = assertThrows(ValidaEntradaException.class, ()->{
            mockMedicoService.validar(medicoDTO);
        });

        String expectedMessage = "Por favor preencher todos os campos";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void editar() {
        MedicoDTO mockMedicoDTO = new MedicoDTO();
        MedicoDAO mockMedicoDAO = mock(MedicoDAO.class);
        MedicoService mockMedicoService = new MedicoService(mockMedicoDAO);

        mockMedicoDTO.setCpf("98765432198");
        mockMedicoDTO.setNome("Jhony");
        mockMedicoDTO.setSobrenome("Zuim");
        mockMedicoDTO.setRegistro("CRM8765");
        mockMedicoDTO.setEspecialidade("Clinico geral");

        doNothing().when(mockMedicoDAO).inserir(anyList());
        mockMedicoService.cadastrar(mockMedicoDTO);

        mockMedicoService.editar(mockMedicoDTO, 1);
        assertNotNull(mockMedicoDTO);

    }

    @Test
    void removerMedico() {
    }

    @Test
    void buscaMedico() {
    }

    @Test
    void verificarConsulta() {
    }

}
