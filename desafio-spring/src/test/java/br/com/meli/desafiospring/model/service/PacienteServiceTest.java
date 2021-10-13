package br.com.meli.desafiospring.model.service;

import br.com.meli.desafiospring.exception.ValidaEntradaException;
import br.com.meli.desafiospring.model.dao.PacienteDAO;
import br.com.meli.desafiospring.model.dto.PacienteRequestDTO;
import br.com.meli.desafiospring.model.dto.PacienteResponseDTO;
import br.com.meli.desafiospring.model.dto.ProprietarioDTO;
import br.com.meli.desafiospring.model.entity.*;
import br.com.meli.desafiospring.model.repository.PacienteRepository;
import br.com.meli.desafiospring.util.ConvesorUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

public class PacienteServiceTest {

    PacienteDAO mockPacienteDAO = mock(PacienteDAO.class);
    ProprietarioService mockProprietarioService = mock(ProprietarioService.class);
    ConvesorUtil mockConversorUtil = mock(ConvesorUtil.class);
    PacienteRepository pacienteRepository = mock(PacienteRepository.class);
    PacienteService pacienteService = new PacienteService(mockPacienteDAO, mockProprietarioService
            , mockConversorUtil, pacienteRepository);

    public PacienteServiceTest() {
        gerarMassaPaciente();
    }

    @Test
    void cadastrar() {
        boolean cadastrou = false;

        PacienteRequestDTO pacienteRequestDTO = new PacienteRequestDTO("canina", "dalmata"
                , "preto", LocalDate.now()
                ,"um", 1);

        doNothing().when(mockPacienteDAO).inserir(anyList());
        when((mockProprietarioService).buscarProprietario(anyInt()))
                .thenReturn(new Proprietario().
                comId(1).
                comCpf("12345632101").
                comNome("ed").
                comSobreNome("nobre").
                comDataDeNascimento(LocalDate.now()).
                comEndereco("rua zero").
                comTelefone(1199998888L).
                build());
        when((mockConversorUtil).conveterDTO(any(PacienteRequestDTO.class), any()))
                .thenReturn(new Paciente()
                        .comEspecie(pacienteRequestDTO.getEspecie())
                        .comRaca(pacienteRequestDTO.getRaca())
                        .comCor(pacienteRequestDTO.getCor())
                        .comDataDeNascimento(pacienteRequestDTO.getDataDeNascimento())
                        .comNome(pacienteRequestDTO.getNome()));

        Integer id = pacienteService.cadastrar(pacienteRequestDTO);

        for (Paciente p: PacienteService.getListaPaciente()) {
            if (p.getId().equals(id)) {
                cadastrou = true;
                break;
            }
        }

        assertTrue(cadastrou);
    }

    @Test
    void editar() {
        boolean editou = false;

        PacienteRequestDTO pacienteRequestDTO = new PacienteRequestDTO("inseto", "aranha"
                , "marrom", LocalDate.now()
                ,"um", 1);

        PacienteResponseDTO pacienteResponseDTO = new PacienteResponseDTO();
        pacienteResponseDTO.setEspecie(PacienteService.getListaPaciente().get(0).getEspecie());
        pacienteResponseDTO.setRaca(PacienteService.getListaPaciente().get(0).getRaca());
        pacienteResponseDTO.setCor(PacienteService.getListaPaciente().get(0).getCor());
        pacienteResponseDTO.setDataDeNascimento(PacienteService.getListaPaciente().get(0).getDataDeNascimento());
        pacienteResponseDTO.setNome(PacienteService.getListaPaciente().get(0).getNome());

        ProprietarioDTO proprietarioDTO = new ProprietarioDTO();
        proprietarioDTO.setCpf(PacienteService.getListaPaciente().get(0).getProprietario().getCpf());
        proprietarioDTO.setNome(PacienteService.getListaPaciente().get(0).getProprietario().getNome());
        proprietarioDTO.setSobreNome(PacienteService.getListaPaciente().get(0).getProprietario().getSobreNome());
        proprietarioDTO.setDataNascimento(PacienteService.getListaPaciente().get(0).getProprietario().getDataNascimento());
        proprietarioDTO.setEndereco(PacienteService.getListaPaciente().get(0).getProprietario().getEndereco());
        proprietarioDTO.setTelefone(PacienteService.getListaPaciente().get(0).getProprietario().getTelefone());

        doNothing().when(mockPacienteDAO).inserir(anyList());
        when((mockConversorUtil).conveterDTO(any(Paciente.class), any()))
                .thenReturn(pacienteResponseDTO);
        when((mockConversorUtil).conveterDTO(any(Proprietario.class), any()))
                .thenReturn(proprietarioDTO);

        PacienteResponseDTO pacienteResponseDTORetorno = pacienteService.editar(pacienteRequestDTO, 1);

        for (Paciente p: PacienteService.getListaPaciente()) {
            if (p.getProprietario().getId().equals(pacienteRequestDTO.getIdProprietario())
                    && p.getNome().equals(pacienteRequestDTO.getNome())
                    && p.getCor().equals(pacienteRequestDTO.getCor())
                    && p.getDataDeNascimento().equals(pacienteRequestDTO.getDataDeNascimento())
                    && p.getRaca().equals(pacienteRequestDTO.getRaca())
                    && p.getEspecie().equals(pacienteRequestDTO.getEspecie())){
                editou = true;
                break;
            }
        }

        assertNotNull(pacienteResponseDTORetorno);
        assertTrue(editou);

    }

    @Test
    void buscaPaciente() {
        boolean buscou = false;
        Paciente paciente = pacienteService.buscaPaciente(1);
        if(paciente.getId() == 1) {
            buscou = true;
        }

        assertTrue(buscou);
    }

    @Test
    void validaEntradaCampoVazio() {
        PacienteRequestDTO pacienteRequestDTO = new PacienteRequestDTO();

        ValidaEntradaException validaEntradaException = assertThrows(ValidaEntradaException.class, () ->
                pacienteService.validaEntrada(pacienteRequestDTO));

        String mensagemEsperada = "Nenhum campo informado!!! Por gentileza informar: especie, raca, cor" +
                ", data de nascimento e nome";
        String mensagemRecebida = validaEntradaException.getMessage();

        assertTrue(mensagemEsperada.contains(mensagemRecebida));
    }

    @Test
    void validaEntradaProprietario() {
        PacienteRequestDTO pacienteRequestDTO = new PacienteRequestDTO();
        pacienteRequestDTO.setNome("zero");
        pacienteRequestDTO.setRaca("branco");
        pacienteRequestDTO.setEspecie("tubarao");
        pacienteRequestDTO.setDataDeNascimento(LocalDate.now());
        pacienteRequestDTO.setCor("preto");


        ValidaEntradaException validaEntradaException = assertThrows(ValidaEntradaException.class, () ->
                pacienteService.validaEntrada(pacienteRequestDTO));

        String mensagemEsperada = "Id proprietario nao informado!!! Por gentileza informar.";
        String mensagemRecebida = validaEntradaException.getMessage();

        assertTrue(mensagemEsperada.contains(mensagemRecebida));
    }

    @Test
    void validaEntradaEspecie() {
        PacienteRequestDTO pacienteRequestDTO = new PacienteRequestDTO();
        pacienteRequestDTO.setIdProprietario(1);
        pacienteRequestDTO.setNome("zero");
        pacienteRequestDTO.setRaca("branco");
        pacienteRequestDTO.setDataDeNascimento(LocalDate.now());
        pacienteRequestDTO.setCor("preto");


        ValidaEntradaException validaEntradaException = assertThrows(ValidaEntradaException.class, () ->
                pacienteService.validaEntrada(pacienteRequestDTO));

        String mensagemEsperada = "Especie nao informada!!! Por gentileza informar.";
        String mensagemRecebida = validaEntradaException.getMessage();

        assertTrue(mensagemEsperada.contains(mensagemRecebida));
    }

    @Test
    void validaEntradaRaca() {
        PacienteRequestDTO pacienteRequestDTO = new PacienteRequestDTO();
        pacienteRequestDTO.setIdProprietario(1);
        pacienteRequestDTO.setNome("zero");
        pacienteRequestDTO.setDataDeNascimento(LocalDate.now());
        pacienteRequestDTO.setEspecie("tubarao");
        pacienteRequestDTO.setCor("preto");


        ValidaEntradaException validaEntradaException = assertThrows(ValidaEntradaException.class, () ->
                pacienteService.validaEntrada(pacienteRequestDTO));

        String mensagemEsperada = "Raca nao informada!!! Por gentileza informar.";
        String mensagemRecebida = validaEntradaException.getMessage();

        assertTrue(mensagemEsperada.contains(mensagemRecebida));
    }

    @Test
    void validaEntradaCor() {
        PacienteRequestDTO pacienteRequestDTO = new PacienteRequestDTO();
        pacienteRequestDTO.setIdProprietario(1);
        pacienteRequestDTO.setNome("zero");
        pacienteRequestDTO.setDataDeNascimento(LocalDate.now());
        pacienteRequestDTO.setEspecie("tubarao");
        pacienteRequestDTO.setRaca("branco");

        ValidaEntradaException validaEntradaException = assertThrows(ValidaEntradaException.class, () ->
                pacienteService.validaEntrada(pacienteRequestDTO));

        String mensagemEsperada = "Cor nao informada!!! Por gentileza informar.";
        String mensagemRecebida = validaEntradaException.getMessage();

        assertTrue(mensagemEsperada.contains(mensagemRecebida));
    }

    @Test
    void validaEntradaDataNascimento() {
        PacienteRequestDTO pacienteRequestDTO = new PacienteRequestDTO();
        pacienteRequestDTO.setIdProprietario(1);
        pacienteRequestDTO.setNome("zero");
        pacienteRequestDTO.setEspecie("tubarao");
        pacienteRequestDTO.setRaca("branco");
        pacienteRequestDTO.setCor("preto");

        ValidaEntradaException validaEntradaException = assertThrows(ValidaEntradaException.class, () ->
                pacienteService.validaEntrada(pacienteRequestDTO));

        String mensagemEsperada = "Data de nascimento nao informada!!! Por gentileza informar.";
        String mensagemRecebida = validaEntradaException.getMessage();

        assertTrue(mensagemEsperada.contains(mensagemRecebida));
    }

    @Test
    void validaEntradaDataNome() {
        PacienteRequestDTO pacienteRequestDTO = new PacienteRequestDTO();
        pacienteRequestDTO.setIdProprietario(1);
        pacienteRequestDTO.setEspecie("tubarao");
        pacienteRequestDTO.setRaca("branco");
        pacienteRequestDTO.setCor("preto");
        pacienteRequestDTO.setDataDeNascimento(LocalDate.now());

        ValidaEntradaException validaEntradaException = assertThrows(ValidaEntradaException.class, () ->
                pacienteService.validaEntrada(pacienteRequestDTO));

        String mensagemEsperada = "Nome nao informado!!! Por gentileza informar.";
        String mensagemRecebida = validaEntradaException.getMessage();

        assertTrue(mensagemEsperada.contains(mensagemRecebida));
    }

    @Test
    void remover() {
        ProprietarioDTO proprietarioDTO = new ProprietarioDTO();
        proprietarioDTO.setCpf(PacienteService.getListaPaciente().get(0).getProprietario().getCpf());
        proprietarioDTO.setNome(PacienteService.getListaPaciente().get(0).getProprietario().getNome());
        proprietarioDTO.setSobreNome(PacienteService.getListaPaciente().get(0).getProprietario().getSobreNome());
        proprietarioDTO.setDataNascimento(PacienteService.getListaPaciente().get(0).getProprietario().getDataNascimento());
        proprietarioDTO.setEndereco(PacienteService.getListaPaciente().get(0).getProprietario().getEndereco());
        proprietarioDTO.setTelefone(PacienteService.getListaPaciente().get(0).getProprietario().getTelefone());

        PacienteResponseDTO pacienteResponseDTO = new PacienteResponseDTO();
        pacienteResponseDTO.setEspecie(PacienteService.getListaPaciente().get(0).getEspecie());
        pacienteResponseDTO.setRaca(PacienteService.getListaPaciente().get(0).getRaca());
        pacienteResponseDTO.setCor(PacienteService.getListaPaciente().get(0).getCor());
        pacienteResponseDTO.setDataDeNascimento(PacienteService.getListaPaciente().get(0).getDataDeNascimento());
        pacienteResponseDTO.setNome(PacienteService.getListaPaciente().get(0).getNome());

        doNothing().when(mockPacienteDAO).inserir(anyList());
        when((mockConversorUtil).conveterDTO(any(Paciente.class), any()))
                .thenReturn(pacienteResponseDTO);
        when((mockConversorUtil).conveterDTO(any(Proprietario.class), any()))
                .thenReturn(proprietarioDTO);
        doNothing().when(mockPacienteDAO).inserir(anyList());
        pacienteService.remover(1);
        Optional<Paciente> pacienteOptional = PacienteService.getListaPaciente().stream()
                .filter(p -> p.getId() == 1)
                .findAny();
        assertTrue(pacienteOptional.isEmpty());
    }

    @Test
    void removerComConsulta() {
        ConsultaService.getListaConsulta().clear();

        Medico medico = new Medico()
                .comId(1)
                .comCPF("11111111111")
                .comNome("zero")
                .comSobreNome("um")
                .comRegistro("CRM1")
                .paraEspecialidade("pediatra")
                .build();
        Paciente paciente = new Paciente()
                .comId(1)
                .comEspecie("mamifero")
                .comRaca("baleia")
                .comCor("preto")
                .comDataDeNascimento(LocalDate.now())
                .comNome("tres")
                .comProprietario(new Proprietario().
                        comId(1).
                        comCpf("11111111111").
                        comNome("ed").
                        comSobreNome("nobre").
                        comDataDeNascimento(LocalDate.now()).
                        comEndereco("rua zero").
                        comTelefone(1111111111L).
                        build())
                .build();
        Consulta consulta = new Consulta().comId(1)
                .comMotivo("rotina")
                .comDiagnostico("a realizar")
                .comTratamento("a realizar")
                .comMedico(medico)
                .noPeriodo(LocalDateTime.now())
                .comPaciente(paciente)
                .build();
        PacienteService.getListaPaciente().add(paciente);
        ConsultaService.getListaConsulta().add(consulta);

        doNothing().when(mockPacienteDAO).inserir(anyList());

        ValidaEntradaException validaEntradaException = assertThrows(ValidaEntradaException.class, () ->
                pacienteService.remover(paciente.getId()));

        String mensagemEsperada = "Paciente tem uma consulta!!! Nao e possivel excluir";
        String mensagemRecebida = validaEntradaException.getMessage();

        assertTrue(mensagemEsperada.contains(mensagemRecebida));
    }

    @Test
    void verificarConsultaExistente() {
        boolean verifica = false;
        ConsultaService.getListaConsulta().clear();
        Medico medico = new Medico()
                .comId(1)
                .comCPF("11111111111")
                .comNome("zero")
                .comSobreNome("um")
                .comRegistro("CRM1")
                .paraEspecialidade("pediatra")
                .build();
        Paciente paciente = new Paciente()
                .comId(1)
                .comEspecie("mamifero")
                .comRaca("baleia")
                .comCor("preto")
                .comDataDeNascimento(LocalDate.now())
                .comNome("tres")
                .comProprietario(new Proprietario().
                        comId(1).
                        comCpf("11111111111").
                        comNome("ed").
                        comSobreNome("nobre").
                        comDataDeNascimento(LocalDate.now()).
                        comEndereco("rua zero").
                        comTelefone(1111111111L).
                        build())
                .build();
        Consulta consulta = new Consulta().comId(1)
                .comMotivo("rotina")
                .comDiagnostico("a realizar")
                .comTratamento("a realizar")
                .comMedico(medico)
                .noPeriodo(LocalDateTime.now())
                .comPaciente(paciente)
                .build();
        ConsultaService.getListaConsulta().add(consulta);

        Optional<Consulta> consultaOptional = ConsultaService.getListaConsulta().stream()
                .filter(c -> c.getPaciente().equals(paciente))
                .findAny();
        if (pacienteService.verificarConsulta(paciente)) {
            verifica = consultaOptional.isPresent();
        }

        assertTrue(verifica);
    }

    @Test
    void verificarConsultaNaoExistente() {
        boolean verifica = false;
        ConsultaService.getListaConsulta().clear();
        Paciente paciente = new Paciente()
                .comId(1)
                .comEspecie("mamifero")
                .comRaca("baleia")
                .comCor("preto")
                .comDataDeNascimento(LocalDate.now())
                .comNome("tres")
                .comProprietario(new Proprietario().
                        comId(1).
                        comCpf("11111111111").
                        comNome("ed").
                        comSobreNome("nobre").
                        comDataDeNascimento(LocalDate.now()).
                        comEndereco("rua zero").
                        comTelefone(1111111111L).
                        build())
                .build();
        Optional<Consulta> consultaOptional = ConsultaService.getListaConsulta().stream()
                .filter(c -> c.getPaciente().equals(paciente))
                .findAny();
        if (!pacienteService.verificarConsulta(paciente)) {
            verifica = consultaOptional.isEmpty();
        }

        assertTrue(verifica);
    }

    @Test
    void listar() {
        PacienteResponseDTO pacienteResponseDTO = new PacienteResponseDTO();
        pacienteResponseDTO.setEspecie(PacienteService.getListaPaciente().get(0).getEspecie());
        pacienteResponseDTO.setRaca(PacienteService.getListaPaciente().get(0).getRaca());
        pacienteResponseDTO.setCor(PacienteService.getListaPaciente().get(0).getCor());
        pacienteResponseDTO.setDataDeNascimento(PacienteService.getListaPaciente().get(0).getDataDeNascimento());
        pacienteResponseDTO.setNome(PacienteService.getListaPaciente().get(0).getNome());

        ProprietarioDTO proprietarioDTO = new ProprietarioDTO();
        proprietarioDTO.setCpf(PacienteService.getListaPaciente().get(0).getProprietario().getCpf());
        proprietarioDTO.setNome(PacienteService.getListaPaciente().get(0).getProprietario().getNome());
        proprietarioDTO.setSobreNome(PacienteService.getListaPaciente().get(0).getProprietario().getSobreNome());
        proprietarioDTO.setDataNascimento(PacienteService.getListaPaciente().get(0).getProprietario().getDataNascimento());
        proprietarioDTO.setEndereco(PacienteService.getListaPaciente().get(0).getProprietario().getEndereco());
        proprietarioDTO.setTelefone(PacienteService.getListaPaciente().get(0).getProprietario().getTelefone());

        when((mockConversorUtil).conveterDTO(any(Paciente.class), any()))
                .thenReturn(pacienteResponseDTO);
        when((mockConversorUtil).conveterDTO(any(Proprietario.class), any()))
                .thenReturn(proprietarioDTO);
        List<PacienteResponseDTO> listaPacienteResponseDTO = pacienteService.listar();
        assertNotNull(listaPacienteResponseDTO);
        assertEquals(PacienteService.getListaPaciente().size(), listaPacienteResponseDTO.size());
    }

    private void gerarMassaPaciente() {
        PacienteService.getListaPaciente().clear();

        Proprietario proprietario1 = new Proprietario().
                comId(1).
                comCpf("11111111111").
                comNome("ed").
                comSobreNome("nobre").
                comDataDeNascimento(LocalDate.now()).
                comEndereco("rua zero").
                comTelefone(1111111111L).
                build();
        Proprietario proprietario2 = new Proprietario().
                comId(1).
                comCpf("22222222222").
                comNome("lucas").
                comSobreNome("pereira").
                comDataDeNascimento(LocalDate.now()).
                comEndereco("rua um").
                comTelefone(1122222222L).
                build();
        Proprietario proprietario3 = new Proprietario().
                comId(3).
                comCpf("33333333333").
                comNome("rafel").
                comSobreNome("silva").
                comDataDeNascimento(LocalDate.now()).
                comEndereco("rua dois").
                comTelefone(1133333333L).
                build();

        Paciente paciente1 = new Paciente()
                .comId(1)
                .comEspecie("felina")
                .comRaca("leao")
                .comCor("amarelo")
                .comDataDeNascimento(LocalDate.now())
                .comNome("dois")
                .comProprietario(proprietario1)
                .build();
        Paciente paciente2 = new Paciente()
                .comId(2)
                .comEspecie("canina")
                .comRaca("dalmata")
                .comCor("preto")
                .comDataDeNascimento(LocalDate.now())
                .comNome("um")
                .comProprietario(proprietario2)
                .build();
        Paciente paciente3 = new Paciente()
                .comId(3)
                .comEspecie("mamifero")
                .comRaca("baleia")
                .comCor("preto")
                .comDataDeNascimento(LocalDate.now())
                .comNome("tres")
                .comProprietario(proprietario3)
                .build();
        PacienteService.getListaPaciente().add(paciente1);
        PacienteService.getListaPaciente().add(paciente2);
        PacienteService.getListaPaciente().add(paciente3);
    }
}
