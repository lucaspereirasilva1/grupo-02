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
        medicoService.validar(medicoDTO);
        Integer id = medicoService.cadastrar(medicoDTO);
        URI uri = uriComponentsBuilder.path("/vermedico/{codigo}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).body("Medico cadastrado");
        }

    @PutMapping(value = "/editar/{id}", produces = "application/json")
    public ResponseEntity<MedicoDTO> editarMedico(@RequestBody MedicoDTO medicoDTO, @PathVariable Integer id, UriComponentsBuilder uriComponentsBuilder) {
        MedicoDTO dto = medicoService.editar(medicoDTO, id);
        URI uri = uriComponentsBuilder.path("/vermedico/{codigo}").buildAndExpand(MedicoService.getListaMedico().size()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping(value="/deleta/{id}")
    public ResponseEntity<String> removerMedico(@PathVariable("id") Integer id){
        medicoService.removerMedico(id);
        return ResponseEntity.ok("Medico removido");
    }
}
