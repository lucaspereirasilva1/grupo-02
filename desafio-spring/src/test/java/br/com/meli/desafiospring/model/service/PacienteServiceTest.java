package br.com.meli.desafiospring.model.service;

import br.com.meli.desafiospring.model.dao.PacienteDAO;
import br.com.meli.desafiospring.model.dto.PacienteRequestDTO;
import br.com.meli.desafiospring.model.entity.Paciente;
import br.com.meli.desafiospring.model.entity.Proprietario;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

public class PacienteServiceTest {

    public PacienteServiceTest() {
        gerarMassaPaciente();
    }

    @Test
    void cadastrar() {
        boolean cadastrou = false;
        PacienteDAO mockPacienteDAO = mock(PacienteDAO.class);
        ProprietarioService mockProprietarioService = mock(ProprietarioService.class);
        PacienteService pacienteService = new PacienteService(mockPacienteDAO, mockProprietarioService);

        PacienteRequestDTO pacienteRequestDTO = new PacienteRequestDTO("canina", "dalmata"
                , "preto", LocalDate.now()
                ,"um", 1);

        doNothing().when(mockPacienteDAO).inserir(anyList());
        when((mockProprietarioService).buscarProprietario(anyInt()))
                .thenReturn(new Proprietario(1, "12345632101", "ed", "nobre", LocalDate.now(), "rua zero", 1199998888L));

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

        PacienteDAO mockPacienteDAO = mock(PacienteDAO.class);
        ProprietarioService mockProprietarioService = mock(ProprietarioService.class);
        PacienteService pacienteService = new PacienteService(mockPacienteDAO, mockProprietarioService);

        PacienteRequestDTO pacienteRequestDTO = new PacienteRequestDTO("inseto", "aranha"
                , "marrom", LocalDate.now()
                ,"um", 1);

        doNothing().when(mockPacienteDAO).inserir(anyList());

        pacienteService.editar(pacienteRequestDTO, 1);

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

        assertTrue(editou);

    }

    @Test
    void buscaPaciente() {
    }

    @Test
    void validaEntrada() {
    }

    @Test
    void remover() {
    }

    @Test
    void verificarConsulta() {
    }

    @Test
    void listar() {
    }

    private void gerarMassaPaciente() {
        PacienteService.getListaPaciente().clear();
        Proprietario proprietario1 = new Proprietario(1, "11111111111", "ed", "nobre", LocalDate.now(), "rua zero", 1111111111L);
        Proprietario proprietario2 = new Proprietario(1, "22222222222", "lucas", "pereira", LocalDate.now(), "rua um", 1122222222L);
        Proprietario proprietario3 = new Proprietario(1, "33333333333", "rafel", "silva", LocalDate.now(), "rua dois", 1133333333L);

        Paciente paciente1 = new Paciente(1,"canina", "dalmata"
                , "preto", LocalDate.now()
                ,"um", proprietario1);
        Paciente paciente2 = new Paciente(1,"felina", "leao"
                , "amarelo", LocalDate.now()
                ,"dois", proprietario2);
        Paciente paciente3 = new Paciente(1,"mamifero", "baleia"
                , "preto", LocalDate.now()
                ,"tres", proprietario3);

        PacienteService.getListaPaciente().add(paciente1);
        PacienteService.getListaPaciente().add(paciente2);
        PacienteService.getListaPaciente().add(paciente3);
    }
}
