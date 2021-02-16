package br.org.isac.portaltransparencia.portal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.isac.portaltransparencia.portal.entity.EstadoArquivo;

@Repository
public interface EstadoArquivoRepository extends JpaRepository<EstadoArquivo, Integer> {
	
	@Query("select a from EstadoArquivo a where a.idArquivo = :id and a.tsFim = null")
	public Optional<EstadoArquivo> recuperaEstadoAtualArquivo(@Param("id") Integer id);
	
	@Query("select a from EstadoArquivo a where a.idArquivo = :id")
	public Optional<List<EstadoArquivo>> recuperaTodosEstadosArquivo(@Param("id") Integer id);
}
