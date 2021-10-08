package br.com.meli.desafiospring.model.dao;

import br.com.meli.desafiospring.model.entity.Medico;
import br.com.meli.desafiospring.model.service.MedicoService;
import br.com.meli.desafiospring.util.ArquivoUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class MedicoDAO implements IPersistir<Medico>{

    private final ArquivoUtil<Medico> arquivoUtil = new ArquivoUtil<>();
    private static final Logger logger = Logger.getLogger(MedicoDAO.class);
    private final File file = new File("medicos.json");
    private static EntityManager em;

    public MedicoDAO() {
//        em = JPAUtil.getEntityManager();
    }

    @Override
    public void inserir(List<Medico> lista) {
        try {
            arquivoUtil.collectionToJson(file, MedicoService.getListaMedico());
        } catch (IOException e) {
            logger.error(e);
        }
    }

    @Override
    public void salva(Medico objeto) {
        if (ObjectUtils.isEmpty(objeto.getId())) {
            try {
                em.getTransaction().begin();
                em.persist(objeto);
                em.getTransaction().commit();
            } catch (PersistenceException e) {
                System.out.println(e.getMessage());
            }

        } else {
            try {
                em.getTransaction().begin();
                em.merge(objeto);
                em.getTransaction().commit();
            } catch (RuntimeException e) {
                logger.error(e.getMessage());
            }
        }
    }

}
