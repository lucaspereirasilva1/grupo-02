package br.com.meli.desafiospring.model.service;

import br.com.meli.desafiospring.exception.ValidaEntradaException;
import br.com.meli.desafiospring.model.dao.ProprietarioDAO;
import br.com.meli.desafiospring.model.dto.ProprietarioDTO;
import br.com.meli.desafiospring.model.entity.*;
import br.com.meli.desafiospring.model.repository.ProprietarioRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

public class ProprietarioServiceTest {
    ProprietarioDTO proprietarioDTO = new ProprietarioDTO();
    ProprietarioDAO mockProprietarioDAO = mock(ProprietarioDAO.class);
    ProprietarioRepository proprietarioRepository = mock(ProprietarioRepository.class);
    ProprietarioService proprietarioService = new ProprietarioService(mockProprietarioDAO, proprietarioRepository);
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
        Proprietario proprietario = new Proprietario()
                .comId(1)
                .comCpf("222.222.222-22")
                .comNome("Ed2")
                .comSobreNome("oliveira")
                .comDataDeNascimento(LocalDate.now())
                .comEndereco("Rua X34")
                .comTelefone(98798798798L)
                .build();
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
        proprietarioDTO.setTelefone(98798798798L);
        ValidaEntradaException validaEntradaException = assertThrows
                (ValidaEntradaException.class,() -> proprietarioService.validaEntradaProprietario(proprietarioDTO));

        String mensagemEsperada = "Endereço nao informando!!! Por gentileza informar.";
        String mensagemRecebida = validaEntradaException.getMessage();

        assertTrue(mensagemEsperada.contains(mensagemRecebida));
    }
    @Test
    void validarTelefoneVazioTest() {
        proprietarioDTO.setCpf("222.222.222-22");
        proprietarioDTO.setNome("ed");
        proprietarioDTO.setSobreNome("NobreMix");
        proprietarioDTO.setDataNascimento(LocalDate.now());
        proprietarioDTO.setEndereco("Rua X34");
        ValidaEntradaException validaEntradaException = assertThrows
                (ValidaEntradaException.class,() -> proprietarioService.validaEntradaProprietario(proprietarioDTO));

        String mensagemEsperada = "Telefone nao informando!!! Por gentileza informar.";
        String mensagemRecebida = validaEntradaException.getMessage();

        assertTrue(mensagemEsperada.contains(mensagemRecebida));
    }

    @Test
    void editarProprietarioTest() {
        ProprietarioService.getListaProprietario().clear();
        boolean editou = false;

        Proprietario proprietario1 = new Proprietario()
                .comId(1)
                .comCpf("11111111111")
                .comNome("Ed1")
                .comSobreNome("Mix")
                .comDataDeNascimento(LocalDate.now())
                .comEndereco("Rua X34")
                .comTelefone(98798798798L)
                .build();
        Proprietario proprietario2 = new Proprietario()
                .comId(2)
                .comCpf("22222222222")
                .comNome("Ed2")
                .comSobreNome("oliveira")
                .comDataDeNascimento(LocalDate.now())
                .comEndereco("Rua X34")
                .comTelefone(98798798798L)
                .build();
        Proprietario proprietario3 = new Proprietario()
                .comId(3)
                .comCpf("33333333333")
                .comNome("Ed3")
                .comSobreNome("Nobre")
                .comDataDeNascimento(LocalDate.now())
                .comEndereco("Rua X34")
                .comTelefone(98798798798L)
                .build();
        Proprietario proprietario4 = new Proprietario()
                .comId(4)
                .comCpf("44444444444")
                .comNome("Ed3")
                .comSobreNome("Oliveira")
                .comDataDeNascimento(LocalDate.now())
                .comEndereco("Rua X34")
                .comTelefone(98798798798L)
                .build();

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
        Proprietario proprietario1 = new Proprietario()
                .comId(1)
                .comCpf("11111111111")
                .comNome("Ed1")
                .comSobreNome("Mix")
                .comDataDeNascimento(LocalDate.now())
                .comEndereco("Rua X34")
                .comTelefone(98798798798L)
                .build();
        Proprietario proprietario2 = new Proprietario()
                .comId(2)
                .comCpf("22222222222")
                .comNome("Ed2")
                .comSobreNome("oliveira")
                .comDataDeNascimento(LocalDate.now())
                .comEndereco("Rua X34")
                .comTelefone(98798798798L)
                .build();
        Proprietario proprietario3 = new Proprietario()
                .comId(3)
                .comCpf("33333333333")
                .comNome("Ed3")
                .comSobreNome("Nobre")
                .comDataDeNascimento(LocalDate.now())
                .comEndereco("Rua X34")
                .comTelefone(98798798798L)
                .build();

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
        boolean buscou = false;
        ProprietarioService.getListaProprietario().clear();
        Proprietario proprietario1 = new Proprietario()
                .comId(1)
                .comCpf("11111111111")
                .comNome("Ed1")
                .comSobreNome("Mix")
                .comDataDeNascimento(LocalDate.now())
                .comEndereco("Rua X34")
                .comTelefone(98798798798L)
                .build();
        Proprietario proprietario2 = new Proprietario()
                .comId(2)
                .comCpf("22222222222")
                .comNome("Ed2")
                .comSobreNome("oliveira")
                .comDataDeNascimento(LocalDate.now())
                .comEndereco("Rua X34")
                .comTelefone(98798798798L)
                .build();
        Proprietario proprietario3 = new Proprietario()
                .comId(3)
                .comCpf("33333333333")
                .comNome("Ed3")
                .comSobreNome("Nobre")
                .comDataDeNascimento(LocalDate.now())
                .comEndereco("Rua X34")
                .comTelefone(98798798798L)
                .build();

        ProprietarioService.getListaProprietario().add(proprietario1);
        ProprietarioService.getListaProprietario().add(proprietario2);
        ProprietarioService.getListaProprietario().add(proprietario3);

        doNothing().when(mockProprietarioDAO).inserir(anyList());
        Proprietario proprietario = proprietarioService.buscarProprietario(1);

        if(proprietario.getId() == 1) {
            buscou = true;
        }
        assertTrue(buscou);
    }

    @Test
    void verificarConsultaTest(){
        ProprietarioService.getListaProprietario().clear();

        Proprietario proprietario1 = new Proprietario()
                .comId(1)
                .comCpf("11111111111")
                .comNome("Ed1")
                .comSobreNome("Mix")
                .comDataDeNascimento(LocalDate.now())
                .comEndereco("Rua X34")
                .comTelefone(98798798798L)
                .build();
        Proprietario proprietario2 = new Proprietario()
                .comId(2)
                .comCpf("22222222222")
                .comNome("Ed2")
                .comSobreNome("oliveira")
                .comDataDeNascimento(LocalDate.now())
                .comEndereco("Rua X34")
                .comTelefone(98798798798L)
                .build();
        Proprietario proprietario3 = new Proprietario()
                .comId(3)
                .comCpf("33333333333")
                .comNome("Ed3")
                .comSobreNome("Nobre")
                .comDataDeNascimento(LocalDate.now())
                .comEndereco("Rua X34")
                .comTelefone(98798798798L)
                .build();
        ProprietarioService.getListaProprietario().add(proprietario1);
        ProprietarioService.getListaProprietario().add(proprietario2);
        ProprietarioService.getListaProprietario().add(proprietario3);

        ConsultaService.getListaConsulta().clear();
        MedicoService.getListaMedico().clear();

        Medico medico = new Medico()
                .comId(1)
                .comCPF("11111111111")
                .comNome("zero")
                .comSobreNome("um")
                .comRegistro("CRM1")
                .paraEspecialidade("pediatra")
                .build();
        MedicoService.getListaMedico().add(medico);

        LocalDateTime dataHora = LocalDateTime.of(2021, 11, 30, 12, 1);

        Consulta consulta = new Consulta().comId(1)
                .comMotivo("rotina")
                .comDiagnostico("a realizar")
                .comTratamento("a realizar")
                .comMedico(medico)
                .noPeriodo(dataHora)
                .comPaciente(new Paciente()
                        .comId(1)
                        .comEspecie("cao")
                        .comRaca("dalmata")
                        .comCor("preto")
                        .comDataDeNascimento(LocalDate.now())
                        .comNome("zero")
                        .comProprietario(proprietario1)
                        .build())
                .build();
        ConsultaService.getListaConsulta().add(consulta);


        doNothing().when(mockProprietarioDAO).inserir(anyList());
        assertTrue(ProprietarioService.verificarConsulta(proprietario1));
        assertFalse(ProprietarioService.verificarConsulta(proprietario2));


    }

    @Test
    void excluirProprietarioComConsultaTest(){
        ProprietarioService.getListaProprietario().clear();

        Proprietario proprietario1 = new Proprietario()
                .comId(1)
                .comCpf("11111111111")
                .comNome("Ed1")
                .comSobreNome("Mix")
                .comDataDeNascimento(LocalDate.now())
                .comEndereco("Rua X34")
                .comTelefone(98798798798L)
                .build();
        Proprietario proprietario2 = new Proprietario()
                .comId(2)
                .comCpf("22222222222")
                .comNome("Ed2")
                .comSobreNome("oliveira")
                .comDataDeNascimento(LocalDate.now())
                .comEndereco("Rua X34")
                .comTelefone(98798798798L)
                .build();
        Proprietario proprietario3 = new Proprietario()
                .comId(3)
                .comCpf("33333333333")
                .comNome("Ed3")
                .comSobreNome("Nobre")
                .comDataDeNascimento(LocalDate.now())
                .comEndereco("Rua X34")
                .comTelefone(98798798798L)
                .build();
        ProprietarioService.getListaProprietario().add(proprietario1);
        ProprietarioService.getListaProprietario().add(proprietario2);
        ProprietarioService.getListaProprietario().add(proprietario3);

        ConsultaService.getListaConsulta().clear();
        MedicoService.getListaMedico().clear();

        Medico medico = new Medico()
                .comId(1)
                .comCPF("11111111111")
                .comNome("zero")
                .comSobreNome("um")
                .comRegistro("CRM1")
                .paraEspecialidade("pediatra")
                .build();
        MedicoService.getListaMedico().add(medico);

        LocalDateTime dataHora = LocalDateTime.of(2021, 11, 30, 12, 1);

        Consulta consulta = new Consulta().comId(1)
                .comMotivo("rotina")
                .comDiagnostico("a realizar")
                .comTratamento("a realizar")
                .comMedico(medico)
                .noPeriodo(dataHora)
                .comPaciente(new Paciente()
                        .comId(1)
                        .comEspecie("cao")
                        .comRaca("dalmata")
                        .comCor("preto")
                        .comDataDeNascimento(LocalDate.now())
                        .comNome("zero")
                        .comProprietario(proprietario1)
                        .build())
                .build();
        ConsultaService.getListaConsulta().add(consulta);


        doNothing().when(mockProprietarioDAO).inserir(anyList());

        ValidaEntradaException validaEntradaException = assertThrows
                (ValidaEntradaException.class,() -> proprietarioService.excluir(1));

        String mensagemEsperada = "Proprietario tem uma consulta!!! Nao e possivel excluir";
        String mensagemRecebida = validaEntradaException.getMessage();

        assertTrue(mensagemEsperada.contains(mensagemRecebida));
    }
}
