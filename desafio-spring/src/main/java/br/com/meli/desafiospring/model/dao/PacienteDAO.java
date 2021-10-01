package br.com.meli.desafiospring.model.dao;

import br.com.meli.desafiospring.model.entity.Consulta;
import br.com.meli.desafiospring.model.entity.Paciente;
import br.com.meli.desafiospring.model.service.ConsultaService;
import br.com.meli.desafiospring.model.service.PacienteService;
import br.com.meli.desafiospring.util.ArquivoUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class PacienteDAO implements IPersistir<Paciente>{

    private final ArquivoUtil<Paciente> arquivoUtil = new ArquivoUtil<>();
    private static final Logger logger = Logger.getLogger(PacienteDAO.class);
    private final File file = new File("paciente.json");

    @Override
    public void inserir(List<Paciente> lista) {
        try {
            arquivoUtil.collectionToJson(file, PacienteService.getListaPaciente());
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
