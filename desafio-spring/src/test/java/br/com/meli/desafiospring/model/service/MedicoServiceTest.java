package br.com.meli.desafiospring.model.service;

import br.com.meli.desafiospring.model.dto.MedicoDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class MedicoServiceTest {

    @Test
    void cadastrarMedicoTest() {
        MedicoService mock = new MedicoService();
        MedicoDTO medicoDTO = new MedicoDTO();

        medicoDTO.setCpf("98765432198");
        medicoDTO.setNome("Jhony");
        medicoDTO.setSobrenome("Zuim");
        medicoDTO.setRegistro("CRM8765");
        medicoDTO.setEspecialidade("Clinico geral");

        mock.cadastrar(medicoDTO);

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
