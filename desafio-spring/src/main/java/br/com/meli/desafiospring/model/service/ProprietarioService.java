package br.com.meli.desafiospring.model.service;

import br.com.meli.desafiospring.exception.ValidaEntradaException;
import br.com.meli.desafiospring.model.dto.ProprietarioDTO;
import br.com.meli.desafiospring.model.entity.Consulta;
import br.com.meli.desafiospring.model.entity.Proprietario;
import br.com.meli.desafiospring.util.ArquivoUtil;
import br.com.meli.desafiospring.util.FormatdorUtil;
import lombok.Getter;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProprietarioService {

    private final ModelMapper modelMapper = new ModelMapper();
    @Getter
    private static final List<Proprietario> listaProprietario = new ArrayList<>();
    private final File file = new File("proprietarios.json");
    private final ArquivoUtil<Proprietario> arquivoUtil = new ArquivoUtil<>();
    private static final Logger logger = Logger.getLogger(ProprietarioService.class);

    public Integer cadastrar(ProprietarioDTO proprietarioDTO){
        Proprietario proprietario = converteProprietario(proprietarioDTO);

        int tamanho;
        if(listaProprietario.isEmpty())
            tamanho=0;
        else
            tamanho=listaProprietario.size()+1;

        for(Proprietario p:listaProprietario) {
            tamanho=(p.getId());
        }

        proprietario.setId(tamanho + 1);
        proprietario.setCpf(FormatdorUtil.formatarCPF(proprietarioDTO.getCpf()));
        listaProprietario.add(proprietario);

        try {
            arquivoUtil.collectionToJson(file, listaProprietario);
        } catch (IOException e) {
            logger.info(e);
        }
        return proprietario.getId();
    }

    public void validaEntradaProprietario(ProprietarioDTO proprietarioDTO) {
        if (ObjectUtils.isEmpty(proprietarioDTO.getCpf()))
            throw new ValidaEntradaException("CPF nao informando!!! Por gentileza informar.");
        else{
            for(int i=0; i < listaProprietario.size(); i++) {
                if (listaProprietario.get(i).getCpf().equals(proprietarioDTO.getCpf())) {
                    throw new ValidaEntradaException("Proprietario ja existente!");
                }
            }
        }
        if (ObjectUtils.isEmpty(proprietarioDTO.getNome()))
            throw new ValidaEntradaException("Nome nao informando!!! Por gentileza informar.");
        if(ObjectUtils.isEmpty(proprietarioDTO.getSobreNome()))
            throw new ValidaEntradaException("Sobre nome nao informandos!!! Por gentileza informar.");
        if(ObjectUtils.isEmpty(proprietarioDTO.getDataNascimento()))
            throw new ValidaEntradaException("Data de nascimento nao informandos!!! Por gentileza informar.");
        if(ObjectUtils.isEmpty(proprietarioDTO.getEndereco()))
            throw new ValidaEntradaException("Endereco nao informandos!!! Por gentileza informar.");
        if(ObjectUtils.isEmpty(proprietarioDTO.getTelefone()))
            throw new ValidaEntradaException("Telefone nao informandos!!! Por gentileza informar.");
    }

    public Proprietario converteProprietario(ProprietarioDTO proprietarioDTO) {
        return modelMapper.map(proprietarioDTO, Proprietario.class);
    }

    public ProprietarioDTO converteProprietarioDTO(Proprietario proprietario) {
        return modelMapper.map(proprietario, ProprietarioDTO.class);
    }

    public ProprietarioDTO editar(ProprietarioDTO proprietarioDTO, Integer id) {
        Optional<Proprietario> optionalProprietario = listaProprietario.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
        Proprietario proprietario = optionalProprietario.orElse(null);
        assert proprietario != null;
        proprietario.setCpf(proprietarioDTO.getCpf());
        proprietario.setNome(proprietarioDTO.getNome());
        proprietario.setSobreNome(proprietarioDTO.getSobreNome());
        proprietario.setDataNascimento(proprietario.getDataNascimento());
        proprietario.setEndereco(proprietario.getEndereco());
        proprietario.setTelefone(proprietario.getTelefone());
        try {
            arquivoUtil.collectionToJson(file, listaProprietario);
        } catch (IOException e) {
            logger.info(e);
        }
        return converteProprietarioDTO(proprietario);
    }


    public ProprietarioDTO excluir(Integer id) {
        ProprietarioDTO propritarioDTO = new ProprietarioDTO();
        for (int i = 0; i < listaProprietario.size(); i++) {
            if(listaProprietario.get(i).getId().equals(id)) {
                if(verificarConsulta(listaProprietario.get(i))) {
                    throw new ValidaEntradaException("Proprietario tem uma consulta!!! Nao e possivel excluir");
                }else {
                    listaProprietario.remove(listaProprietario.get(i));

                }
            }
        }

        try {
            arquivoUtil.collectionToJson(file, listaProprietario);
        } catch (IOException e) {
            logger.info(e);
        }

        return propritarioDTO;
    }

    public static Proprietario buscarProprietario(Integer id) {
        Optional<Proprietario> optionalProprietario = listaProprietario.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
        return optionalProprietario.orElse(null);
    }

    public boolean verificarConsulta(Proprietario proprietario) {
        for (Consulta c: ConsultaService.getListaConsulta()) {
            if(c.getPaciente().getProprietario().equals(proprietario))
                return true;
        }
        return false;
    }

}
