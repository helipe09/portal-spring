package br.org.isac.portaltransparencia.portal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.isac.portaltransparencia.portal.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	@Query("select u from Usuario u where u.cpf = :cpf")
	public Optional<Usuario> obterUsuarioPorCPF(@Param("cpf") String cpf);
	
	@Query("select u from Usuario u where u.tipoUsuario = :tipoUsuario and u.idUnidadeAtual = :idUnidadeAtual and u.idEstadoAtual = 2 and u.idFuncaoAtual in(1,2,3)")
	public Optional<List<Usuario>> administradoresUnidade(@Param("tipoUsuario") Integer tipoUsuario, @Param("idUnidadeAtual") Integer idUnidadeAtual);
	
	@Query("select u from Usuario u where u.idUnidadeAtual = :idUnidadeAtual")
	public List<Usuario> usuariosUnidade(@Param("idUnidadeAtual") Integer idUnidadeAtual);
	
	@Query("select u from Usuario u where u.idEstadoAtual = :idEstadoAtual")
	public List<Usuario> usuariosEmEdicao(@Param("idEstadoAtual") Integer idEstadoAtual);
	
	@Query("select count(u.id) from Usuario u where u.idEstadoAtual = :idEstadoAtual")
	public Integer quantidadeUsuariosEmEdicao(@Param("idEstadoAtual") Integer idEstadoAtual);

}
