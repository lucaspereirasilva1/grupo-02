package br.com.meli.desafiospring.controller;

import br.com.meli.desafiospring.model.dto.ProprietarioDTO;
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
    public ResponseEntity<String> cadastrarProprietario(@RequestBody ProprietarioDTO proprietarioDTO, UriComponentsBuilder uriComponentsBuilder){
        proprietarioService.validaEntradaProprietario(proprietarioDTO);
        Integer id = proprietarioService.cadastrar(proprietarioDTO);
        URI uri = uriComponentsBuilder.path("/verproprietario/{codigo}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).body("Proprietario cadastrado");
    }

    @PutMapping(value = "/editar/{id}", produces = "application/json")
    public ResponseEntity<ProprietarioDTO> editarProprietario(@RequestBody ProprietarioDTO proprietarioDTO, @PathVariable Integer id, UriComponentsBuilder uriComponentsBuilder) {
        ProprietarioDTO dto = proprietarioService.editar(proprietarioDTO, id);
        URI uri = uriComponentsBuilder.path("/verproprietario/{codigo}").buildAndExpand(proprietarioService.getListaProprietario().size()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping(value = "/excluir/{id}", produces = "application/json")
    public String excluirProprietario(@PathVariable("id") Integer id) {
        return proprietarioService.excluir(id);
    }


}
