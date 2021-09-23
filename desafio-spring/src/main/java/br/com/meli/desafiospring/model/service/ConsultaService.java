package br.com.meli.desafiospring.model.service;

import br.com.meli.desafiospring.model.dto.ConsultaDTO;
import br.com.meli.desafiospring.model.entity.Consulta;
import br.com.meli.desafiospring.model.entity.IConsulta;
import br.com.meli.desafiospring.util.ArquivoUtil;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsultaService {

    private final ModelMapper modelMapper = new ModelMapper();
    @Getter
    private final List<Consulta> listaConsulta = new ArrayList<>();
    private final File file = new File("consultas.json");

    public ConsultaDTO cadastrar(ConsultaDTO consultaDTO) {
        IConsulta consulta = new Consulta().comId(listaConsulta.size() + 1)
                .noPeriodo(LocalDateTime.now())
                .comMotivo(consultaDTO.getMotivo())
                .comDiagnostico(consultaDTO.getDiagnostico())
                .comTratamento(consultaDTO.getTratamento());
        listaConsulta.add((Consulta) consulta);
        try {
            ArquivoUtil.collectionToJson(file, listaConsulta);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return converteConsultaDTO((Consulta) consulta);
    }

    private ConsultaDTO converteConsultaDTO(Consulta consulta) {
        return modelMapper.map(consulta, ConsultaDTO.class);
    }

    public ConsultaDTO editar(ConsultaDTO consultaDTO, Integer id) {
        Optional<Consulta> optionalConsulta = listaConsulta.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
        Consulta consulta = optionalConsulta.orElse(null);
        assert consulta != null;
        consulta.comMotivo(consultaDTO.getMotivo()).comDiagnostico(consultaDTO.getDiagnostico())
                .comTratamento(consultaDTO.getTratamento());
        try {
            ArquivoUtil.collectionToJson(file, listaConsulta);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return converteConsultaDTO(consulta);
    }

    public List<ConsultaDTO> listar() {
        return listaConvertDTO(listaConsulta.stream()
                .sorted((c1, c2) -> c2.getDataHora().compareTo(c1.getDataHora()))
                .collect(Collectors.toList()));
    }

    public List<ConsultaDTO> listaConvertDTO(List<Consulta> listaConsulta) {
        Type listType = new TypeToken<List<ConsultaDTO>>() {}.getType();
        return modelMapper.map(listaConsulta, listType);
    }


}
