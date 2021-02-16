package br.org.isac.portaltransparencia.portal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.isac.portaltransparencia.portal.entity.MovimentoArquivo;

@Repository
public interface MovimentoArquivoRepository extends JpaRepository<MovimentoArquivo, Integer> {
	
	@Query("select a from MovimentoArquivo a where a.idArquivo = :id")
	public Optional<List<MovimentoArquivo>> recuperaTodosMovimentosArquivo(@Param("id") Integer id);
	

}
