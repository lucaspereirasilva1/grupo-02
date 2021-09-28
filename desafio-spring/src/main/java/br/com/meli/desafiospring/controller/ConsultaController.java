package br.com.meli.desafiospring.controller;

import br.com.meli.desafiospring.model.dto.ConsultaRequestDTO;
import br.com.meli.desafiospring.model.dto.ConsultaResponseDTO;
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
    public ResponseEntity<String> cadastrarConsulta(@RequestBody ConsultaRequestDTO consultaRequestDTO, UriComponentsBuilder uriComponentsBuilder) {
        consultaService.validaEntrada(consultaRequestDTO);
        Integer id = consultaService.cadastrar(consultaRequestDTO);
        URI uri = uriComponentsBuilder.path("/verconsulta/{codigo}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).body("Consulta cadastrada com sucesso");
    }

    @PutMapping(value = "/manutencao/{id}", produces = "application/json")
    public ResponseEntity<ConsultaResponseDTO> editarConsulta(@RequestBody ConsultaRequestDTO consultaDTO, @PathVariable Integer id, UriComponentsBuilder uriComponentsBuilder) {
        ConsultaResponseDTO dto = consultaService.editar(consultaDTO, id);
        URI uri = uriComponentsBuilder.path("/verconsulta/{codigo}").buildAndExpand(ConsultaService.getListaConsulta().size()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping(value = "/listaconsulta")
    public List<ConsultaResponseDTO> listarConsulta() {
        return consultaService.listar();
    }

    @GetMapping(value = "/listaconsultamedico")
    public List<String> listarConsultaMedicos() {
        return consultaService.listarTotalMedicos();
    }

    @GetMapping(value = "/listaconsultadia/{data}")
    public List<ConsultaResponseDTO> listarConsultaDia(@PathVariable String data) {
        return consultaService.listarPorDia(data);
    }

}
