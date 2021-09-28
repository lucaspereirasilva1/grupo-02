package br.com.meli.desafiospring.model.service;

import br.com.meli.desafiospring.exception.ValidaEntradaException;
import br.com.meli.desafiospring.model.dto.MedicoDTO;
import br.com.meli.desafiospring.model.dto.PacienteRequestDTO;
import br.com.meli.desafiospring.model.dto.PacienteResponseDTO;
import br.com.meli.desafiospring.model.dto.ProprietarioDTO;
import br.com.meli.desafiospring.model.entity.Paciente;
import br.com.meli.desafiospring.model.entity.Proprietario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PacienteServiceTest {

    @Test
    void cadastrarPacienteTest() {
        PacienteService mock = new PacienteService();
        PacienteRequestDTO pacienteRequestDTO = new PacienteRequestDTO();

        ProprietarioDTO proprietarioDTO = new ProprietarioDTO();
        proprietarioDTO.setCpf("12345678901");
        proprietarioDTO.setEndereco("rua teste");
        proprietarioDTO.setDataNascimento(LocalDate.now());
        proprietarioDTO.setNome("ed");
        proprietarioDTO.setSobreNome("mix");
        proprietarioDTO.setTelefone(1199998888L);

        pacienteRequestDTO.setCor("branco");
        pacienteRequestDTO.setEspecie("cao");
        pacienteRequestDTO.setRaca("dalmata");
        pacienteRequestDTO.setDataDeNascimento(LocalDate.now());
        pacienteRequestDTO.setNome("teste");
        pacienteRequestDTO.setIdProprietario(1);

        mock.cadastrar(pacienteRequestDTO);

        assertFalse(PacienteService.getListaPaciente().isEmpty());

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
