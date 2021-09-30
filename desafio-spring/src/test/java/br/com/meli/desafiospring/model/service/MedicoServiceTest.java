package br.com.meli.desafiospring.model.service;

import br.com.meli.desafiospring.exception.ValidaEntradaException;
import br.com.meli.desafiospring.model.dao.MedicoDAO;
import br.com.meli.desafiospring.model.dto.MedicoDTO;
import br.com.meli.desafiospring.model.entity.Medico;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

public class MedicoServiceTest {

    MedicoDTO mockMedicoDTO = new MedicoDTO();
    MedicoDAO mockMedicoDAO = mock(MedicoDAO.class);
    MedicoService mockMedicoService = new MedicoService(mockMedicoDAO);


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

        String expectedMessage = "Por favor preencher todos os campos";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
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
    void verificarConsulta() {
    }

}
