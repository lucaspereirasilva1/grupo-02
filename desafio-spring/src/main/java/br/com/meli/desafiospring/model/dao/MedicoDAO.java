package br.com.meli.desafiospring.model.dao;

import br.com.meli.desafiospring.model.entity.Medico;
import br.com.meli.desafiospring.model.service.MedicoService;
import br.com.meli.desafiospring.util.ArquivoUtil;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MedicoDAO implements IPersistir<Medico>{

    private final ArquivoUtil<Medico> arquivoUtil = new ArquivoUtil<>();
    private static final Logger logger = Logger.getLogger(MedicoService.class);

    @Override
    public void inserir(File file, List<Medico> lista) {
        try {
            arquivoUtil.collectionToJson(file, MedicoService.getListaMedico());
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
