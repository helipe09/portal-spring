package br.org.isac.portaltransparencia.portal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.org.isac.portaltransparencia.portal.entity.TipoDocumento;

public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Integer> {
	
	@Query("select u from TipoDocumento u where u.ativo = 'S'")
	public Optional<List<TipoDocumento>> findAllAtivos();
	
	@Query("select u from TipoDocumento u where u.id > 0")
	public List<TipoDocumento> findAllTipos();
	
	@Query("select u from TipoDocumento u where u.id > 0 and u.grupo = :grupo")
	public List<TipoDocumento> findAllTiposPorGrupo(@Param("grupo") Integer grupo);
	
	@Query("select u from TipoDocumento u where u.id > 0 and u.ativo = 'S' and (u.localAplicacao = 'U' or u.localAplicacao = 'T')")
	public List<TipoDocumento> findAllTiposUnidades();
	

}
