package br.org.isac.portaltransparencia.portal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.isac.portaltransparencia.portal.entity.FuncaoUsuario;

@Repository
public interface FuncaoUsuarioRepository extends JpaRepository<FuncaoUsuario, Integer> {
	
	@Query("select a from FuncaoUsuario a where a.idUsuario = :id and a.tsFim = null")
	public Optional<FuncaoUsuario> recuperaFuncaoAtualUsuario(@Param("id") Integer id);
	
	@Query("select a from FuncaoUsuario a where a.idUsuario = :id")
	public Optional<List<FuncaoUsuario>> recuperaTodasFuncoesUsuario(@Param("id") Integer id);
	

}
