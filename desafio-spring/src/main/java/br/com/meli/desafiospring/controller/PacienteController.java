package br.com.meli.desafiospring.controller;


import br.com.meli.desafiospring.model.dto.PacienteRequestDTO;
import br.com.meli.desafiospring.model.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    PacienteService pacienteService;

    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastrarPaciente(@RequestBody PacienteRequestDTO pacienteRequestDTO, UriComponentsBuilder uriComponentsBuilder){
        pacienteService.validaEntrada(pacienteRequestDTO);
        Integer id = pacienteService.cadastrar(pacienteRequestDTO);
        URI uri = uriComponentsBuilder.path("/verpaciente/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).body("Paciente cadastrado com sucesso!!!");
    }

    @PutMapping(value = "/editar/{id}", produces = "application/json")
    public ResponseEntity<PacienteRequestDTO> editarPaciente(@RequestBody PacienteRequestDTO paciente, @PathVariable Integer id, UriComponentsBuilder uriComponentsBuilder) {
        PacienteRequestDTO dto = pacienteService.editar(paciente, id);
        URI uri = uriComponentsBuilder.path("/verpaciente/{codigo}").buildAndExpand(PacienteService.getListaPaciente().size()).toUri();
        return ResponseEntity.created(uri).body(dto);

    }
}
