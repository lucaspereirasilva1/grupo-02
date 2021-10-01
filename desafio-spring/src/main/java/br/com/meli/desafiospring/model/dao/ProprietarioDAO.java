package br.com.meli.desafiospring.model.dao;

import br.com.meli.desafiospring.model.entity.Proprietario;
import br.com.meli.desafiospring.model.service.ProprietarioService;
import br.com.meli.desafiospring.util.ArquivoUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class ProprietarioDAO implements IPersistir<Proprietario>{

    private final ArquivoUtil<Proprietario> arquivoUtil = new ArquivoUtil<>();
    private static final Logger logger = Logger.getLogger(ProprietarioDAO.class);
    private final File file = new File("proprietario.json");

    @Override
    public void inserir(List<Proprietario> lista) {
        try {
            arquivoUtil.collectionToJson(file, ProprietarioService.getListaProprietario());
        } catch (IOException e) {
            logger.error(e);
        }
    }
}