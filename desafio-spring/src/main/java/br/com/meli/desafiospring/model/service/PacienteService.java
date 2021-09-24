package br.com.meli.desafiospring.model.service;

import br.com.meli.desafiospring.model.dto.PacienteDTO;
import br.com.meli.desafiospring.model.entity.Medico;
import br.com.meli.desafiospring.model.entity.Paciente;
import br.com.meli.desafiospring.util.ArquivoUtil;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.matcher.StringMatcher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Service

public class PacienteService {

    private final ModdelMapper moddelMapper = new ModelMapper();
    @Getter
    private static final List<Paciente>listPaciente = new ArrayList<>();
    private final File file = new File ("paciente.json");

    public PacienteDTO cadastrar (PacienteDTO pacienteDTO) {
        Paciente paciente = convertePaciente(pacienteDTO);
        paciente.setId(listPaciente.size() + 1);
        listPaciente.add(paciente);
        try {
            ArquivoUtil.collectionToJsonPaciente(file, listPaciente);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return PacienteDTO;
    }

        public Paciente convertePaciente(PacienteDTO pacienteDTO){
            return moddelMapper.map(paciente, Paciente.class);
        }

        paciente PacienteDTO convertePaciente(Paciente paciente) {
            return moddelMapper.map(paciente, PacienteDTO.class);
        }

        public PacienteDTO editar(pacienteDTO paciente, Integer id){
            Optional<Paciente>optionalPaciente = listPaciente.stream();
            .filter(c -> c.getId().equals(id))
                    .findFirst();
            Paciente paciente = optionalPaciente.orElse(null);
            assert paciente != null;
            paciente.setEspecie(pacienteDTO.getEspecie());
            paciente.setRaca(pacienteDTO.getRaca());
            paciente.setCor(pacienteDTO.getCor());
            paciente.setdataDeNascimento(pacienteDTO.getDataDeNascimento());
            paciente.setNome(pacienteDTO.getNome());
            try {
                ArquivoUtil.collectionToJson(file, listPaciente);
            } catch (IOException e){
                e.printStackTrace();
            }
            return convertePaciente(paciente);
        }
    public static Paciente buscaPaciente(String registrto){
            Optional<Paciente> optionalPaciente = listPaciente.stream()
                    .filter(c -> c.getRegistro().equals(registrto))
                    .findFisrt();
            return optionalPaciente.orElse(null);
    }
}



