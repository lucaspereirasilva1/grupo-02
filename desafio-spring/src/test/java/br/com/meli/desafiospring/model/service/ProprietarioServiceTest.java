package br.com.meli.desafiospring.model.service;

import br.com.meli.desafiospring.model.dao.ProprietarioDAO;
import br.com.meli.desafiospring.model.dto.ProprietarioDTO;
import br.com.meli.desafiospring.model.entity.Proprietario;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

public class ProprietarioServiceTest {
    ProprietarioDTO mockProprietarioDTO = new ProprietarioDTO();
    ProprietarioDAO mockProprietarioDAO = mock(ProprietarioDAO.class);
    ProprietarioService mockProprietarioService = new ProprietarioService(mockProprietarioDAO);
    @Test
    void cadastrarProprietarioTest() {
        ProprietarioService.getListaProprietario().clear();

        mockProprietarioDTO.setCpf("98765432198");
        mockProprietarioDTO.setNome("Ed");
        mockProprietarioDTO.setSobreNome("NobreMix");
        mockProprietarioDTO.setDataNascimento(LocalDate.now());
        mockProprietarioDTO.setEndereco("Rua X34");
        mockProprietarioDTO.setTelefone(98798798798L);

        doNothing().when(mockProprietarioDAO).inserir(anyList());

        mockProprietarioService.cadastrar(mockProprietarioDTO);

        assertFalse(ProprietarioService.getListaProprietario().isEmpty());
    }


}
