package br.org.isac.portaltransparencia.portal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.isac.portaltransparencia.portal.entity.EstadoUsuario;

@Repository
public interface EstadoUsuarioRepository extends JpaRepository<EstadoUsuario, Integer> {
	
	@Query("select a from EstadoUsuario a where a.idUsuario = :id and a.tsFim = null")
	public Optional<EstadoUsuario> recuperaEstadoAtualUsuario(@Param("id") Integer id);
	
	@Query("select a from EstadoUsuario a where a.idUsuario = :id")
	public Optional<List<EstadoUsuario>> recuperaTodosEstadosUsuario(@Param("id") Integer id);
	

}
