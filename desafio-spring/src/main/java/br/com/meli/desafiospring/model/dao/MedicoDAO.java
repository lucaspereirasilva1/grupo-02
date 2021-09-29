package br.com.meli.desafiospring.model.dao;

import br.com.meli.desafiospring.model.entity.Medico;
import br.com.meli.desafiospring.model.service.MedicoService;
import br.com.meli.desafiospring.util.ArquivoUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class MedicoDAO implements IPersistir<Medico>{

    private final ArquivoUtil<Medico> arquivoUtil = new ArquivoUtil<>();
    private static final Logger logger = Logger.getLogger(MedicoService.class);
    private final File file = new File("medicos.json");

    @Override
    public void inserir(List<Medico> lista) {
        try {
            arquivoUtil.collectionToJson(file, MedicoService.getListaMedico());
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
