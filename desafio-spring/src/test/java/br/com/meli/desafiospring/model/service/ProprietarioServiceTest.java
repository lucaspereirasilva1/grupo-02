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


    @Test
    void validarProprietarioTest() {
        ProprietarioService.getListaProprietario().clear();
        mockProprietarioDTO.setCpf("98765432198");
        mockProprietarioDTO.setNome("Ed");
        mockProprietarioDTO.setSobreNome("NobreMix");
        mockProprietarioDTO.setDataNascimento(LocalDate.now());
        mockProprietarioDTO.setEndereco("Rua X34");
        mockProprietarioDTO.setTelefone(98798798798L);

        doNothing().when(mockProprietarioDAO).inserir(anyList());
        mockProprietarioService.validaEntradaProprietario(mockProprietarioDTO);
        assertNotNull(mockProprietarioDTO);
    }


    @Test void editarProprietarioTest() {
        ProprietarioService.getListaProprietario().clear();

        Proprietario proprietario1 = new Proprietario(1, "11111111111", "Ed1", "Mix", LocalDate.now(), "Rua X34", 98798798798L);
        Proprietario proprietario2 = new Proprietario(2, "22222222222", "Ed2", "oliveira", LocalDate.now(), "Rua X34", 98798798798L);
        Proprietario proprietario3 = new Proprietario(3, "33333333333", "Ed3", "Nobre", LocalDate.now(), "Rua X34", 98798798798L);
        Proprietario proprietario4 = new Proprietario(4, "44444444444", "Ed4", "Oliveira", LocalDate.now(), "Rua X34", 98798798798L);

        ProprietarioService.getListaProprietario().add(proprietario1);
        ProprietarioService.getListaProprietario().add(proprietario2);
        ProprietarioService.getListaProprietario().add(proprietario3);
        ProprietarioService.getListaProprietario().add(proprietario4);

        mockProprietarioDTO.setCpf("98765432198");
        mockProprietarioDTO.setNome("Ed");
        mockProprietarioDTO.setSobreNome("NobreMix");
        mockProprietarioDTO.setDataNascimento(LocalDate.now());
        mockProprietarioDTO.setEndereco("Rua X34");
        mockProprietarioDTO.setTelefone(98798798798L);

        doNothing().when(mockProprietarioDAO).inserir(anyList());
        mockProprietarioService.editar(mockProprietarioDTO, 3);

        assertNotNull(mockProprietarioDTO);
        String nome1 = proprietario1.getNome();
        String nome2 = proprietario2.getNome();
        String nome3 = proprietario3.getNome();
    }

    @Test void excluirProprietarioTest(){
        ProprietarioService.getListaProprietario().clear();
        Proprietario proprietario1 = new Proprietario(1, "11111111111", "Ed1", "Mix", LocalDate.now(), "Rua X34", 98798798798L);
//        Proprietario proprietario2 = new Proprietario(2, "22222222222", "Ed2", "oliveira", LocalDate.now(), "Rua X34", 98798798798L);
//        Proprietario proprietario3 = new Proprietario(3, "33333333333", "Ed3", "Nobre", LocalDate.now(), "Rua X34", 98798798798L);

        ProprietarioService.getListaProprietario().add(proprietario1);
//        ProprietarioService.getListaProprietario().add(proprietario2);
//        ProprietarioService.getListaProprietario().add(proprietario3);

        doNothing().when(mockProprietarioDAO).inserir(anyList());
        mockProprietarioService.excluir(1);
        assertTrue(ProprietarioService.getListaProprietario().isEmpty());

    }

    @Test void buscarProprietarioTest(){
        ProprietarioService.getListaProprietario().clear();
        Proprietario proprietario1 = new Proprietario(1, "11111111111", "Ed1", "Mix", LocalDate.now(), "Rua X34", 98798798798L);
        Proprietario proprietario2 = new Proprietario(2, "22222222222", "Ed2", "oliveira", LocalDate.now(), "Rua X34", 98798798798L);
        Proprietario proprietario3 = new Proprietario(3, "33333333333", "Ed3", "Nobre", LocalDate.now(), "Rua X34", 98798798798L);

        ProprietarioService.getListaProprietario().add(proprietario1);
        ProprietarioService.getListaProprietario().add(proprietario2);
        ProprietarioService.getListaProprietario().add(proprietario3);

        doNothing().when(mockProprietarioDAO).inserir(anyList());
        mockProprietarioService.buscarProprietario(1);

        doNothing().when(mockProprietarioDAO).inserir(anyList());
    }

}
