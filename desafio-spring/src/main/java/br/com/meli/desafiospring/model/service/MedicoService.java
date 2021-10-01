package br.com.meli.desafiospring.model.service;

import br.com.meli.desafiospring.exception.ValidaEntradaException;
import br.com.meli.desafiospring.model.dao.MedicoDAO;
import br.com.meli.desafiospring.model.dto.MedicoDTO;
import br.com.meli.desafiospring.model.entity.Consulta;
import br.com.meli.desafiospring.model.entity.Medico;
import br.com.meli.desafiospring.util.ConvesorUtil;
import br.com.meli.desafiospring.util.FormatdorUtil;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MedicoService {

    @Getter
    private static final List<Medico> listaMedico = new ArrayList<>();
    private MedicoDAO medicoDAO;
    private final ConvesorUtil convesorUtil = new ConvesorUtil();

    public MedicoService(MedicoDAO medicoDAO){
        this.medicoDAO = medicoDAO;
    }

    public Integer cadastrar(MedicoDTO medicoDTO){
        Medico medico = (Medico) convesorUtil.conveterDTO(medicoDTO, Medico.class);
        medico.setCpf(FormatdorUtil.formatarCPF(medicoDTO.getCpf()));
        medico.setId(listaMedico.size() + 1);
        listaMedico.add(medico);
        medicoDAO.inserir(listaMedico);
        return medico.getId();
    }

    // validacao campos nulos e duplicidade
    public void validar(MedicoDTO medicoDTO){
        if (medicoDTO.getCpf() == null || medicoDTO.getNome() == null || medicoDTO.getSobrenome() == null
                || medicoDTO.getRegistro() == null || medicoDTO.getEspecialidade() == null) {
                    throw new ValidaEntradaException("Por favor preencher todos os campos");
                }

        listaMedico.forEach(m -> {
            if(m.getCpf().equals(medicoDTO.getCpf())) {
                throw new ValidaEntradaException("Medico ja existente para esse CPF!");
            }
            if(m.getRegistro().equals(medicoDTO.getRegistro())) {
                throw new ValidaEntradaException("Medico ja existente para esse CRM!");
            }
        });
    }

    public MedicoDTO editar(MedicoDTO medicoDTO, Integer id) {
        Optional<Medico> optionalMedico = listaMedico.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
        Medico medico = optionalMedico.orElse(null);
        assert medico != null;
        medico.setCpf(medicoDTO.getCpf());
                medico.setNome(medicoDTO.getNome());
                medico.setSobrenome(medicoDTO.getSobrenome());
                medico.setRegistro(medicoDTO.getRegistro());
                medico.setEspecialidade(medicoDTO.getEspecialidade());
        medicoDAO.inserir(listaMedico);
        return (MedicoDTO) convesorUtil.conveterDTO(medico, MedicoDTO.class);
    }

    public void removerMedico(Integer id) {
        for (int i = 0; i < listaMedico.size(); i++) {
            if(listaMedico.get(i).getId().equals(id)) {
                if(verificarConsulta(listaMedico.get(i))) {
                    throw new ValidaEntradaException("Medico tem uma consulta!!! Nao e possivel excluir");
                }else {
                    listaMedico.remove(listaMedico.get(i));
                }
            }
        }
        medicoDAO.inserir(listaMedico);
    }

    public Medico buscaMedico(String registro) {
        Optional<Medico> optionalMedico = listaMedico.stream()
                .filter(c -> c.getRegistro().equals(registro))
                .findFirst();
        return optionalMedico.orElse(null);
    }

    public boolean verificarConsulta(Medico medico) {
        for (Consulta c: ConsultaService.getListaConsulta()) {
            if(c.getMedico().equals(medico))
                return true;
        }
        return false;
    }


}
