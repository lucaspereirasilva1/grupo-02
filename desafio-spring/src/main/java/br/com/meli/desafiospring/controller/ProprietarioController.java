package br.com.meli.desafiospring.controller;

import br.com.meli.desafiospring.model.dto.ProprietarioDTO;
import br.com.meli.desafiospring.model.entity.Proprietario;
import br.com.meli.desafiospring.model.service.ProprietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
