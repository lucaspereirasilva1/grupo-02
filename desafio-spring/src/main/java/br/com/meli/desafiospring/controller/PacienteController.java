package br.com.meli.desafiospring.controller;


import br.com.meli.desafiospring.model.dto.PacienteDTO;
import br.com.meli.desafiospring.model.entity.Paciente;
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
    public ResponseEntity<Paciente> cadastrarPaciente(@RequestBody PacienteDTO pacienteDTO, UriComponentsBuilder uriComponentsBuilder){
        pacienteDTO dto = pacienteService.cadastrar(pacienteDTO);
        URI uri = uriComponentsBuilder.path("/verpaciente/ {codigo]").buildAndExpand(pacienteService.getListapaciente().size().toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/editar/{id}", produces = "application/json")
    public ResponseEntity<PacienteDTO> editarPaciente(@RequestBody PacienteDTO paciente, @PathVariable Integer id, UriComponentsBuilder uriComponentsBuilder) {
        PacienteDTO dto = paciente.editar(PacienteDTO, id);
        URI uri = uriComponentsBuilder.path("/verpaciente/{codigo}").buildAndExpand(paciente.getListaPaciente().size()).toUri();
        return ResponseEntity.created(uri).body(dto);


}
