package br.com.meli.desafiospring.model.service;

import br.com.meli.desafiospring.exception.ValidaEntradaException;
import br.com.meli.desafiospring.model.dao.MedicoDAO;
import br.com.meli.desafiospring.model.dto.MedicoDTO;
import br.com.meli.desafiospring.model.entity.*;
import br.com.meli.desafiospring.model.repository.MedicoRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

public class MedicoServiceTest {

    MedicoDTO medicoDTO = new MedicoDTO();
    MedicoDAO mockMedicoDAO = mock(MedicoDAO.class);
    MedicoRepository medicoRepository = mock(MedicoRepository.class);
    MedicoService mockMedicoService = new MedicoService(mockMedicoDAO,medicoRepository);

    @Test
    void cadastrarMedicoTest() {
        MedicoService.getListaMedico().clear();

        medicoDTO.setCpf("98765432198");
        medicoDTO.setNome("Jhony");
        medicoDTO.setSobrenome("Zuim");
        medicoDTO.setRegistro("CRM8765");
        medicoDTO.setEspecialidade("Clinico geral");

        doNothing().when(mockMedicoDAO).inserir(anyList());
        mockMedicoService.cadastrar(medicoDTO);

        assertFalse(MedicoService.getListaMedico().isEmpty());

    }
    @Test
    void validarMedicoTest() {
        MedicoService.getListaMedico().clear();

        ValidaEntradaException exception = assertThrows(ValidaEntradaException.class, ()-> mockMedicoService.validar(medicoDTO));

        String expectedMessage1 = "Por favor preencher todos os campos";
        String actualMessage1 = exception.getMessage();

        assertTrue(actualMessage1.contains(expectedMessage1));

    }

    @Test
    void editarMedicoTest() {
        MedicoService.getListaMedico().clear();
        boolean editou = false;

        Medico medico = new Medico()
                .comId(1)
                .comCPF("98765432198")
                .comNome("Jose")
                .comSobreNome("Zuim")
                .comRegistro("CRM8765")
                .paraEspecialidade("Clinico Geral")
                .build();
        MedicoService.getListaMedico().add(medico);

        medicoDTO.setCpf("11987654321");
        medicoDTO.setNome("Raul");
        medicoDTO.setSobrenome("Seixas");
        medicoDTO.setRegistro("CRM123");
        medicoDTO.setEspecialidade("Ortopedista");

        doNothing().when(mockMedicoDAO).inserir(anyList());
        mockMedicoService.editar(medicoDTO, 1);

        for (Medico m: MedicoService.getListaMedico()){
            if (m.getRegistro().equals("CRM123")){
                editou = true;
                break;
            }
        }

        assertTrue(editou);
    }

    @Test
    void removerMedicoTest() {
        MedicoService.getListaMedico().clear();
        List<Consulta> listConsulta = ConsultaService.getListaConsulta();
        ConsultaService.getListaConsulta().removeAll(listConsulta);

        Medico medico = new Medico()
                .comId(1)
                .comCPF("98765432198")
                .comNome("Jose")
                .comSobreNome("Zuim")
                .comRegistro("CRM8765")
                .paraEspecialidade("Clinico Geral")
                .build();
        MedicoService.getListaMedico().add(medico);

        doNothing().when(mockMedicoDAO).inserir(anyList());
        mockMedicoService.removerMedico(1);

        boolean excluiu=true;
        for (Medico m: MedicoService.getListaMedico()) {
            if (m.getId() == (1)) {
                excluiu = false;
                break;
            }
        }
        assertTrue(excluiu);
    }

    @Test
    void buscarMedicoTest() {

        ProprietarioService.getListaProprietario().clear();
        boolean buscou = false;

        Medico medico1 = new Medico()
                .comId(1)
                .comCPF("98765432198")
                .comNome("Jose")
                .comSobreNome("Zuim")
                .comRegistro("CRM8765")
                .paraEspecialidade("Clinico Geral")
                .build();
        MedicoService.getListaMedico().add(medico1);

        doNothing().when(mockMedicoDAO).inserir(anyList());
        Medico medico = mockMedicoService.buscaMedico("CRM2");

        if (medico.getRegistro().equals("CRM2")){
            buscou = true;
        }

        assertTrue(buscou);

    }

    @Test
    void verificarConsultaVerdadeTest() {
        MedicoService.getListaMedico().clear();
        ConsultaService.getListaConsulta().clear();

        List<Consulta> listConsulta = ConsultaService.getListaConsulta();
        ConsultaService.getListaConsulta().removeAll(listConsulta);

        Medico medico = new Medico()
                .comId(1)
                .comCPF("98765432198")
                .comNome("Jose")
                .comSobreNome("Zuim")
                .comRegistro("CRM8765")
                .paraEspecialidade("Clinico Geral")
                .build();

        Paciente paciente = new Paciente()
                .comId(1)
                .comEspecie("cao")
                .comRaca("dalmata")
                .comCor("preto")
                .comDataDeNascimento(LocalDate.now())
                .comNome("zero")
                .comProprietario((new Proprietario().
                        comId(1).
                        comCpf("12345632101").
                        comNome("ed").
                        comSobreNome("nobre").
                        comDataDeNascimento(LocalDate.now()).
                        comEndereco("rua zero").
                        comTelefone(1199998888L).
                        build()))
                .build();

        Consulta consulta = new Consulta().comId(1)
                .comMotivo("rotina")
                .comDiagnostico("n/a")
                .comTratamento("n/a")
                .comMedico(medico)
                .noPeriodo(LocalDateTime.now())
                .comPaciente(paciente)
                .build();

        ConsultaService.getListaConsulta().add(consulta);

        boolean variavel = mockMedicoService.verificarConsulta(medico);

        assertTrue(variavel);
    }

    @Test
    void verificarConsultaFalseTest() {
        MedicoService.getListaMedico().clear();
        ConsultaService.getListaConsulta().clear();

        List<Consulta> listConsulta = ConsultaService.getListaConsulta();
        ConsultaService.getListaConsulta().removeAll(listConsulta);

        Medico medico = new Medico()
                .comId(1)
                .comCPF("98765432198")
                .comNome("Jose")
                .comSobreNome("Zuim")
                .comRegistro("CRM8765")
                .paraEspecialidade("Clinico Geral")
                .build();

        boolean variavel = mockMedicoService.verificarConsulta(medico);

        assertFalse(variavel);
    }
}