package br.com.meli.desafiospring.model.repository;


import br.com.meli.desafiospring.model.entity.Proprietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProprietarioRepository extends JpaRepository<Proprietario, Integer> {
}
