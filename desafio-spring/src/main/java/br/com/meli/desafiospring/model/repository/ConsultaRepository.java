package br.com.meli.desafiospring.model.repository;

import br.com.meli.desafiospring.model.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {
}
