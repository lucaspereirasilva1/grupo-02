package br.com.meli.desafiospring.model.service;

import br.com.meli.desafiospring.model.dto.PacienteRequestDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDate;

@SpringBootTest
public class PacienteServiceTest {

    @Test
    void cadastrarPacienteTest() {
        PacienteService pacienteService = new PacienteService();
        PacienteRequestDTO pacienteRequestDTO = new PacienteRequestDTO();
        pacienteRequestDTO.setEspecie("cao");
        pacienteRequestDTO.setRaca("pastor alemao");
        pacienteRequestDTO.setDataDeNascimento(LocalDate.now());
        pacienteRequestDTO.setNome("teste");
        pacienteRequestDTO.setIdProprietario(1);
        Integer id = pacienteService.cadastrar(pacienteRequestDTO);
        Assertions.assertEquals(1, id);
    }

}
