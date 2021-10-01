package br.com.meli.desafiospring.model.dao;

import br.com.meli.desafiospring.model.entity.Consulta;
import br.com.meli.desafiospring.model.service.ConsultaService;
import br.com.meli.desafiospring.util.ArquivoUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class ConsultaDAO implements IPersistir<Consulta> {

    private final ArquivoUtil<Consulta> arquivoUtil = new ArquivoUtil<>();
    private static final Logger logger = Logger.getLogger(ConsultaDAO.class);
    private final File file = new File("consultas.json");

    @Override
    public void inserir(List<Consulta> lista) {
        try {
            arquivoUtil.collectionToJson(file, ConsultaService.getListaConsulta());
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
