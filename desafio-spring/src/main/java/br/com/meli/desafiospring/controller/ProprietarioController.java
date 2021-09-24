package br.com.meli.desafiospring.controller;

import br.com.meli.desafiospring.model.dto.ProprietarioDTO;
import br.com.meli.desafiospring.model.dto.ProprietarioDTO;
import br.com.meli.desafiospring.model.entity.Proprietario;
import br.com.meli.desafiospring.model.service.ProprietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/proprietario")
public class ProprietarioController {

    @Autowired
    ProprietarioService proprietarioService;
    @PostMapping(value = "/cadastro", produces = "application/json")
    public ResponseEntity<ProprietarioDTO> cadastrarProprietario(@RequestBody ProprietarioDTO proprietarioDTO, UriComponentsBuilder uriComponentsBuilder){
        ProprietarioDTO dto = proprietarioService.cadastrar(proprietarioDTO);
        URI uri = uriComponentsBuilder.path("/verproprietario/{codigo}").buildAndExpand(proprietarioService.getListaProprietario().size()).toUri();
        return ResponseEntity.created(uri).body(dto);

    }

    @PutMapping(value = "/editar/{id}", produces = "application/json")
    public ResponseEntity<ProprietarioDTO> editarProprietario(@RequestBody ProprietarioDTO proprietarioDTO, @PathVariable Integer id, UriComponentsBuilder uriComponentsBuilder) {
        ProprietarioDTO dto = proprietarioService.editar(proprietarioDTO, id);
        URI uri = uriComponentsBuilder.path("/verproprietario/{codigo}").buildAndExpand(proprietarioService.getListaProprietario().size()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }
    int i=0;

    @DeleteMapping(value = "/excluir/{id}", produces = "application/json")
    public String excluirProprietario(@RequestBody ProprietarioDTO proprietarioDTO, @PathVariable("id") Integer id, UriComponentsBuilder uriComponentsBuilder) {
        String dto = proprietarioService.excluir(id);
        return dto;
    }

    int j=1;

}
