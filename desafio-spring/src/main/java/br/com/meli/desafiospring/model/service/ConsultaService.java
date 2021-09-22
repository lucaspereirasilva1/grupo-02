package br.com.meli.desafiospring.model.service;

import br.com.meli.desafiospring.model.dto.ConsultaDTO;
import br.com.meli.desafiospring.model.entity.Consulta;
import br.com.meli.desafiospring.util.ArquivoUtil;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultaService {

    private final ModelMapper modelMapper = new ModelMapper();
    @Getter
    private final List<Consulta> listaConsulta = new ArrayList<>();
    private final File file = new File("consultas.json");

    public ConsultaDTO cadastrar(ConsultaDTO consultaDTO) {
        consultaDTO.setDataHora(LocalDateTime.now());
        Consulta consulta = converteConsulta(consultaDTO);
        consulta.setId(listaConsulta.size() + 1);
        listaConsulta.add(consulta);
        try {
            ArquivoUtil.collectionToJson(file, listaConsulta);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return consultaDTO;
    }

    private Consulta converteConsulta(ConsultaDTO consultaDTO) {
        return modelMapper.map(consultaDTO, Consulta.class);
    }
}
