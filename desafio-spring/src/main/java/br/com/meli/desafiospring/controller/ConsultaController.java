package br.com.meli.desafiospring.controller;

import br.com.meli.desafiospring.model.dto.ConsultaDTO;
import br.com.meli.desafiospring.model.entity.Consulta;
import br.com.meli.desafiospring.model.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {

    @Autowired
    ConsultaService consultaService;

    @PostMapping(value = "/cadastro", produces = "application/json")
    public ResponseEntity<ConsultaDTO> cadastrarConsulta(@RequestBody ConsultaDTO consultaDTO, UriComponentsBuilder uriComponentsBuilder) {
        ConsultaDTO dto = consultaService.cadastrar(consultaDTO);
        URI uri = uriComponentsBuilder.path("/verconsulta/{codigo}").buildAndExpand(consultaService.getListaConsulta().size()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

}
