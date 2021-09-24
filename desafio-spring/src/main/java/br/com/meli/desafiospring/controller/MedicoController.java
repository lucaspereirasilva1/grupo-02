package br.com.meli.desafiospring.controller;

import br.com.meli.desafiospring.model.dto.MedicoDTO;
import br.com.meli.desafiospring.model.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medico")
public class MedicoController {

    @Autowired
    MedicoService medicoService;
    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastrarMedico(@RequestBody MedicoDTO medicoDTO, UriComponentsBuilder uriComponentsBuilder){
        if(medicoService.validar(medicoDTO)==true) {
            MedicoDTO dto = medicoService.cadastrar(medicoDTO);
            URI uri = uriComponentsBuilder.path("/vermedico/{codigo}").buildAndExpand(medicoService.getListaMedico().size()).toUri();
            return ResponseEntity.created(uri).body("Medico cadastrado");
        } else {
            return ResponseEntity.badRequest().body("Favor preencher todos os campos");
        }
    }

    @PutMapping(value = "/editar/{id}", produces = "application/json")
    public ResponseEntity<MedicoDTO> editarMedico(@RequestBody MedicoDTO medicoDTO, @PathVariable Integer id, UriComponentsBuilder uriComponentsBuilder) {
        MedicoDTO dto = medicoService.editar(medicoDTO, id);
        URI uri = uriComponentsBuilder.path("/vermedico/{codigo}").buildAndExpand(medicoService.getListaMedico().size()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping(value="/deleta/{id}")
    public String removerMedico(@PathVariable("id") Integer id){
        return medicoService.removerMedico(id) ? "Medico removido" : "Medico nao removido";
    }
}
