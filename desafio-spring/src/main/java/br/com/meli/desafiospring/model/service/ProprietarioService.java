package br.com.meli.desafiospring.model.service;

import br.com.meli.desafiospring.model.dto.MedicoDTO;
import br.com.meli.desafiospring.model.dto.ProprietarioDTO;
import br.com.meli.desafiospring.model.entity.Medico;
import br.com.meli.desafiospring.model.entity.Proprietario;
import br.com.meli.desafiospring.util.ArquivoUtil;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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

    public ProprietarioDTO cadastrar(ProprietarioDTO proprietarioDTO){
        Proprietario proprietario = converteProprietario(proprietarioDTO);

        int tamanho;
        if(listaProprietario.size()==0)
            tamanho=0;
        else
            tamanho=listaProprietario.size()+1;

        for(Proprietario p:listaProprietario) {
            System.out.print(p.getId()+", ");
            tamanho=(p.getId());
        }
        proprietario.setId(tamanho + 1);
        listaProprietario.add(proprietario);

        try {
            ArquivoUtil.collectionToJsonProprietario(file, listaProprietario);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return proprietarioDTO;
    }

    public Proprietario converteProprietario(ProprietarioDTO proprietarioDTO) {
        return modelMapper.map(proprietarioDTO, Proprietario.class);
    }

    public ProprietarioDTO converteProprietarioDTO(Proprietario proprietario) {
        return modelMapper.map(proprietario, ProprietarioDTO.class);
    }

    public ProprietarioDTO editar(ProprietarioDTO proprietarioDTO, Integer id) {
      //  String retorno="Não existe";
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
            ArquivoUtil.collectionToJsonProprietario(file, listaProprietario);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return converteProprietarioDTO(proprietario);
    }


    public String excluir(Integer id) {
        String retorno="Não existe";
        for(Proprietario p:listaProprietario){
            if(p.getId().equals(id)){
                retorno = p.getNome()+" "+p.getSobreNome()+" excluido";
                listaProprietario.remove(p);
            }
        }

        Optional<Proprietario> optionalProprietario = listaProprietario.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
        Proprietario proprietario = optionalProprietario.orElse(null);
        assert proprietario != null;


        try {
            ArquivoUtil.collectionToJsonProprietario(file, listaProprietario);
            return retorno;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

    }

    public static Proprietario buscarProprietario(Integer id) {
        Optional<Proprietario> optionalProprietario = listaProprietario.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
        return optionalProprietario.orElse(null);
    }

}
