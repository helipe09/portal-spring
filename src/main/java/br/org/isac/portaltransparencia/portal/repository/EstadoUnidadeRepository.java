package br.org.isac.portaltransparencia.portal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.isac.portaltransparencia.portal.entity.EstadoUnidade;

@Repository
public interface EstadoUnidadeRepository extends JpaRepository<EstadoUnidade, Integer> {
	
	@Query("select a from EstadoUnidade a where a.idUnidade = :idUnidade and a.tsFim = null")
	public Optional<EstadoUnidade> recuperaEstadoAtualUnidade(@Param("idUnidade") Integer idUnidade);
	
	@Query("select a from EstadoUnidade a where a.idUnidade = :idUnidade")
	public Optional<List<EstadoUnidade>> recuperaTodosEstadosUnidade(@Param("idUnidade") Integer idUnidade);
	

}
