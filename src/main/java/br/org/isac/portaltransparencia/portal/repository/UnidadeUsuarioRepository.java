package br.org.isac.portaltransparencia.portal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.isac.portaltransparencia.portal.entity.UnidadeUsuario;

@Repository
public interface UnidadeUsuarioRepository extends JpaRepository<UnidadeUsuario, Integer> {
	
	@Query("select a from UnidadeUsuario a where a.idUsuario = :id and a.tsFim = null")
	public Optional<UnidadeUsuario> recuperaUnidadeAtualUsuario(@Param("id") Integer id);
	
	@Query("select a from UnidadeUsuario a where a.idUsuario = :id")
	public Optional<List<UnidadeUsuario>> recuperaTodasUnidadesUsuario(@Param("id") Integer id);
	
	@Query("select a from UnidadeUsuario a where a.idUnidade =: idUnidade")
	public Optional<List<UnidadeUsuario>> recuperaTodosUsuarioDeUmaUnidade(@Param("idUnidade") Integer idUnidade);
	

}
