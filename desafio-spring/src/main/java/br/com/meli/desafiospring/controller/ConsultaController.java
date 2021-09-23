package br.com.meli.desafiospring.controller;

import br.com.meli.desafiospring.model.dto.ConsultaDTO;
import br.com.meli.desafiospring.model.entity.Consulta;
import br.com.meli.desafiospring.model.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @PostMapping(value = "/cadastro", produces = "application/json")
    public ResponseEntity<ConsultaDTO> cadastrarConsulta(@RequestBody ConsultaDTO consultaDTO, UriComponentsBuilder uriComponentsBuilder) {
        ConsultaDTO dto = consultaService.cadastrar(consultaDTO);
        URI uri = uriComponentsBuilder.path("/verconsulta/{codigo}").buildAndExpand(consultaService.getListaConsulta().size()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/manutencao/{id}", produces = "application/json")
    public ResponseEntity<ConsultaDTO> editarConsulta(@RequestBody ConsultaDTO consultaDTO, @PathVariable Integer id, UriComponentsBuilder uriComponentsBuilder) {
        ConsultaDTO dto = consultaService.editar(consultaDTO, id);
        URI uri = uriComponentsBuilder.path("/verconsulta/{codigo}").buildAndExpand(consultaService.getListaConsulta().size()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping(value = "/listaconsulta")
    public List<ConsultaDTO> listarConsulta() {
        return consultaService.listar();
    }

}
