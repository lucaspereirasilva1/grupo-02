package br.com.meli.desafiospring.model.service;

import br.com.meli.desafiospring.exception.ValidaEntradaException;
import br.com.meli.desafiospring.model.dao.ConsultaDAO;
import br.com.meli.desafiospring.model.dao.ProprietarioDAO;
import br.com.meli.desafiospring.model.dto.ConsultaRequestDTO;
import br.com.meli.desafiospring.model.dto.ProprietarioDTO;
import br.com.meli.desafiospring.model.entity.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

public class ProprietarioServiceTest {
    ConsultaDAO mockConsultaDAO = mock(ConsultaDAO.class);
    ProprietarioDTO proprietarioDTO = new ProprietarioDTO();
    ProprietarioDAO mockProprietarioDAO = mock(ProprietarioDAO.class);
    ProprietarioService proprietarioService = new ProprietarioService(mockProprietarioDAO);
    @Test
    void cadastrarProprietarioTest() {
        boolean cadastrou = false;

        proprietarioDTO.setCpf("11111111111");
        proprietarioDTO.setNome("ed");
        proprietarioDTO.setSobreNome("NobreMix");
        proprietarioDTO.setDataNascimento(LocalDate.now());
        proprietarioDTO.setEndereco("Rua X34");
        proprietarioDTO.setTelefone(98798798798L);

        doNothing().when(mockProprietarioDAO).inserir(anyList());
        proprietarioService.cadastrar(proprietarioDTO);

        for (Proprietario p: ProprietarioService.getListaProprietario()) {
            if (p.getCpf().equals("11111111111")) {
                cadastrou = true;
                break;
            }
        }
        assertTrue(cadastrou);
    }


    @Test
    void validarCpfVazioTest() {
   //     proprietarioDTO.setCpf("222.222.222-22");
        proprietarioDTO.setNome("ed");
        proprietarioDTO.setSobreNome("NobreMix");
        proprietarioDTO.setDataNascimento(LocalDate.now());
        proprietarioDTO.setEndereco("Rua X34");
        proprietarioDTO.setTelefone(98798798798L);
        ValidaEntradaException validaEntradaException = assertThrows
                (ValidaEntradaException.class,() -> proprietarioService.validaEntradaProprietario(proprietarioDTO));

        String mensagemEsperada = "CPF nao informando!!! Por gentileza informar.";
        String mensagemRecebida = validaEntradaException.getMessage();

        assertTrue(mensagemEsperada.contains(mensagemRecebida));
    }

    @Test
    void validarCpfRepetidoTest() {
        Proprietario proprietario = new Proprietario(1, "22222222222", "Ed2", "oliveira", LocalDate.now(), "Rua X34", 98798798798L);
        ProprietarioService.getListaProprietario().add(proprietario);

        proprietarioDTO.setCpf("22222222222");
        proprietarioDTO.setNome("ed");
        proprietarioDTO.setSobreNome("NobreMix");
        proprietarioDTO.setDataNascimento(LocalDate.now());
        proprietarioDTO.setEndereco("Rua X34");
        proprietarioDTO.setTelefone(98798798798L);

        ValidaEntradaException validaEntradaException = assertThrows
                (ValidaEntradaException.class,() -> proprietarioService.validaEntradaProprietario(proprietarioDTO));

        String mensagemEsperada = "Proprietario ja existente!";
        String mensagemRecebida = validaEntradaException.getMessage();

        assertTrue(mensagemEsperada.contains(mensagemRecebida));
    }

    @Test
    void validarNomeVazioTest() {
        proprietarioDTO.setCpf("222.222.222-22");
 //       proprietarioDTO.setNome("ed");
        proprietarioDTO.setSobreNome("NobreMix");
        proprietarioDTO.setDataNascimento(LocalDate.now());
        proprietarioDTO.setEndereco("Rua X34");
        proprietarioDTO.setTelefone(98798798798L);
        ValidaEntradaException validaEntradaException = assertThrows
                (ValidaEntradaException.class,() -> proprietarioService.validaEntradaProprietario(proprietarioDTO));

        String mensagemEsperada = "Nome nao informando!!! Por gentileza informar.";
        String mensagemRecebida = validaEntradaException.getMessage();

        assertTrue(mensagemEsperada.contains(mensagemRecebida));
    }

    @Test
    void validarSobrenomeVazioTest() {
        proprietarioDTO.setCpf("222.222.222-22");
        proprietarioDTO.setNome("ed");
 //       proprietarioDTO.setSobreNome("NobreMix");
        proprietarioDTO.setDataNascimento(LocalDate.now());
        proprietarioDTO.setEndereco("Rua X34");
        proprietarioDTO.setTelefone(98798798798L);
        ValidaEntradaException validaEntradaException = assertThrows
                (ValidaEntradaException.class,() -> proprietarioService.validaEntradaProprietario(proprietarioDTO));

        String mensagemEsperada = "Sobrenome nao informando!!! Por gentileza informar.";
        String mensagemRecebida = validaEntradaException.getMessage();

        assertTrue(mensagemEsperada.contains(mensagemRecebida));
    }

    @Test
    void validarDataNascimentoVazioTest() {
        proprietarioDTO.setCpf("222.222.222-22");
        proprietarioDTO.setNome("ed");
        proprietarioDTO.setSobreNome("NobreMix");
 //       proprietarioDTO.setDataNascimento(LocalDate.now());
        proprietarioDTO.setEndereco("Rua X34");
        proprietarioDTO.setTelefone(98798798798L);
        ValidaEntradaException validaEntradaException = assertThrows
                (ValidaEntradaException.class,() -> proprietarioService.validaEntradaProprietario(proprietarioDTO));

        String mensagemEsperada = "Data Nascimento nao informando!!! Por gentileza informar.";
        String mensagemRecebida = validaEntradaException.getMessage();

        assertTrue(mensagemEsperada.contains(mensagemRecebida));
    }

    @Test
    void validarEnderecoVazioTest() {
        proprietarioDTO.setCpf("222.222.222-22");
        proprietarioDTO.setNome("ed");
        proprietarioDTO.setSobreNome("NobreMix");
        proprietarioDTO.setDataNascimento(LocalDate.now());
 //       proprietarioDTO.setEndereco("Rua X34");
        proprietarioDTO.setTelefone(98798798798L);
        ValidaEntradaException validaEntradaException = assertThrows
                (ValidaEntradaException.class,() -> proprietarioService.validaEntradaProprietario(proprietarioDTO));

        String mensagemEsperada = "Endereço nao informando!!! Por gentileza informar.";
        String mensagemRecebida = validaEntradaException.getMessage();

        assertTrue(mensagemEsperada.contains(mensagemRecebida));
    }

    @Test
    void editarProprietarioTest() {
        ProprietarioService.getListaProprietario().clear();
        boolean editou = false;

        Proprietario proprietario1 = new Proprietario(1, "11111111111", "Ed1", "Mix", LocalDate.now(), "Rua X34", 98798798798L);
        Proprietario proprietario2 = new Proprietario(2, "22222222222", "Ed2", "oliveira", LocalDate.now(), "Rua X34", 98798798798L);
        Proprietario proprietario3 = new Proprietario(3, "33333333333", "Ed3", "Nobre", LocalDate.now(), "Rua X34", 98798798798L);
        Proprietario proprietario4 = new Proprietario(4, "44444444444", "Ed4", "Oliveira", LocalDate.now(), "Rua X34", 98798798798L);

        ProprietarioService.getListaProprietario().add(proprietario1);
        ProprietarioService.getListaProprietario().add(proprietario2);
        ProprietarioService.getListaProprietario().add(proprietario3);
        ProprietarioService.getListaProprietario().add(proprietario4);

        proprietarioDTO.setCpf("98765432198");
        proprietarioDTO.setNome("xaxa");
        proprietarioDTO.setSobreNome("NobreMix");
        proprietarioDTO.setDataNascimento(LocalDate.now());
        proprietarioDTO.setEndereco("Rua X34");
        proprietarioDTO.setTelefone(98798798798L);

        doNothing().when(mockProprietarioDAO).inserir(anyList());
        proprietarioService.editar(proprietarioDTO, 3);

        for (Proprietario p: ProprietarioService.getListaProprietario()) {
            if (p.getCpf().equals(proprietarioDTO.getCpf())
                    && p.getNome().equals(proprietarioDTO.getNome())
                    && p.getSobreNome().equals(proprietarioDTO.getSobreNome())
                    && p.getDataNascimento().equals(proprietarioDTO.getDataNascimento())
                    && p.getEndereco().equals(proprietarioDTO.getEndereco())
                    && p.getTelefone()==(proprietarioDTO.getTelefone())) {
                editou = true;
                break;
            }
        }

        assertTrue(editou);
    }

    @Test void excluirProprietarioTest(){
        ProprietarioService.getListaProprietario().clear();
        Proprietario proprietario1 = new Proprietario(1, "11111111111", "Ed1", "Mix", LocalDate.now(), "Rua X34", 98798798798L);
        Proprietario proprietario2 = new Proprietario(2, "22222222222", "Ed2", "oliveira", LocalDate.now(), "Rua X34", 98798798798L);
        Proprietario proprietario3 = new Proprietario(3, "33333333333", "Ed3", "Nobre", LocalDate.now(), "Rua X34", 98798798798L);

        ProprietarioService.getListaProprietario().add(proprietario1);
        ProprietarioService.getListaProprietario().add(proprietario2);
        ProprietarioService.getListaProprietario().add(proprietario3);

        doNothing().when(mockProprietarioDAO).inserir(anyList());
        int idExcluir=2;
        proprietarioService.excluir(idExcluir);

        boolean excluiu=true;
        for (Proprietario p: ProprietarioService.getListaProprietario()) {
            if (p.getId().equals(idExcluir)) {
                excluiu = false;
                break;
            }
        }
        assertTrue(excluiu);

    }

    @Test
    void buscarProprietarioTest(){
        ProprietarioService.getListaProprietario().clear();
        Proprietario proprietario1 = new Proprietario(1, "11111111111", "Ed1", "Mix", LocalDate.now(), "Rua X34", 98798798798L);
        Proprietario proprietario2 = new Proprietario(2, "22222222222", "Ed2", "oliveira", LocalDate.now(), "Rua X34", 98798798798L);
        Proprietario proprietario3 = new Proprietario(3, "33333333333", "Ed3", "Nobre", LocalDate.now(), "Rua X34", 98798798798L);

        ProprietarioService.getListaProprietario().add(proprietario1);
        ProprietarioService.getListaProprietario().add(proprietario2);
        ProprietarioService.getListaProprietario().add(proprietario3);

        doNothing().when(mockProprietarioDAO).inserir(anyList());
        ProprietarioService.buscarProprietario(1);

        doNothing().when(mockProprietarioDAO).inserir(anyList());
    }

    @Test
    void verificarConsultaTest(){
        ProprietarioService.getListaProprietario().clear();

        Proprietario proprietario1 = new Proprietario(1, "11111111111", "Ed1", "Mix", LocalDate.now(), "Rua X34", 98798798798L);
        Proprietario proprietario2 = new Proprietario(2, "22222222222", "Ed2", "oliveira", LocalDate.now(), "Rua X34", 98798798798L);
        Proprietario proprietario3 = new Proprietario(3, "33333333333", "Ed3", "Nobre", LocalDate.now(), "Rua X34", 98798798798L);
        ProprietarioService.getListaProprietario().add(proprietario1);
        ProprietarioService.getListaProprietario().add(proprietario2);
        ProprietarioService.getListaProprietario().add(proprietario3);

        ConsultaService.getListaConsulta().clear();
        MedicoService.getListaMedico().clear();

        Medico medico = new Medico(1, "11111111111", "zero", "um", "CRM1", "pediatra");
        MedicoService.getListaMedico().add(medico);

        LocalDateTime dataHora = LocalDateTime.of(2021, 11, 30, 12, 1);

        IConsulta consulta = new Consulta().comId(1)
                .comMotivo("rotina")
                .comDiagnostico("a realizar")
                .comTratamento("a realizar")
                .comMedico(medico)
                .noPeriodo(dataHora)
                .comPaciente(new Paciente(1, "cao", "dalmata", "preto", LocalDate.now(), "zero"
                        ,proprietario1));
        ConsultaService.getListaConsulta().add((Consulta) consulta);


        doNothing().when(mockProprietarioDAO).inserir(anyList());
        assertTrue(ProprietarioService.verificarConsulta(proprietario1));
        assertFalse(ProprietarioService.verificarConsulta(proprietario2));


    }

    @Test
    void excluirProprietarioComConsultaTest(){
        ProprietarioService.getListaProprietario().clear();

        Proprietario proprietario1 = new Proprietario(1, "11111111111", "Ed1", "Mix", LocalDate.now(), "Rua X34", 98798798798L);
        Proprietario proprietario2 = new Proprietario(2, "22222222222", "Ed2", "oliveira", LocalDate.now(), "Rua X34", 98798798798L);
        Proprietario proprietario3 = new Proprietario(3, "33333333333", "Ed3", "Nobre", LocalDate.now(), "Rua X34", 98798798798L);
        ProprietarioService.getListaProprietario().add(proprietario1);
        ProprietarioService.getListaProprietario().add(proprietario2);
        ProprietarioService.getListaProprietario().add(proprietario3);

        ConsultaService.getListaConsulta().clear();
        MedicoService.getListaMedico().clear();

        Medico medico = new Medico(1, "11111111111", "zero", "um", "CRM1", "pediatra");
        MedicoService.getListaMedico().add(medico);

        LocalDateTime dataHora = LocalDateTime.of(2021, 11, 30, 12, 1);

        IConsulta consulta = new Consulta().comId(1)
                .comMotivo("rotina")
                .comDiagnostico("a realizar")
                .comTratamento("a realizar")
                .comMedico(medico)
                .noPeriodo(dataHora)
                .comPaciente(new Paciente(1, "cao", "dalmata", "preto", LocalDate.now(), "zero"
                        ,proprietario1));
        ConsultaService.getListaConsulta().add((Consulta) consulta);


        doNothing().when(mockProprietarioDAO).inserir(anyList());

        ValidaEntradaException validaEntradaException = assertThrows
                (ValidaEntradaException.class,() -> proprietarioService.excluir(1));

        String mensagemEsperada = "Proprietario tem uma consulta!!! Nao e possivel excluir";
        String mensagemRecebida = validaEntradaException.getMessage();

        assertTrue(mensagemEsperada.contains(mensagemRecebida));

    }


}
