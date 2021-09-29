package br.com.meli.desafiospring.model.service;

import br.com.meli.desafiospring.model.dao.MedicoDAO;
import br.com.meli.desafiospring.model.dto.MedicoDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class MedicoServiceTest {

    @Test
    void cadastrarMedicoTest() {

        MedicoDTO medicoDTO = new MedicoDTO();
        MedicoDAO medicoDAO = new MedicoDAO();
        MedicoService mockMedicoService = new MedicoService(medicoDAO);

        medicoDTO.setCpf("98765432198");
        medicoDTO.setNome("Jhony");
        medicoDTO.setSobrenome("Zuim");
        medicoDTO.setRegistro("CRM8765");
        medicoDTO.setEspecialidade("Clinico geral");

        mockMedicoService.cadastrar(medicoDTO);

        assertFalse(MedicoService.getListaMedico().isEmpty());

    }



//    @Test
//    void validaEntradaNull() {
//        MedicoDTO medicoDTO = new MedicoDTO();
//        MedicoService medicoService = new MedicoService();
//
//        ValidaEntradaException exception = assertThrows(ValidaEntradaException.class, ()->{
//            medicoService.validar(medicoDTO);
//        });
//
//        String expectedMessage = "Por favor preencher todos os campos";
//        String actualMessage = exception.getMessage();
//
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
}
