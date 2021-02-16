package br.org.isac.portaltransparencia.portal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.isac.portaltransparencia.portal.entity.Unidade;

@Repository
public interface UnidadeRepository extends JpaRepository<Unidade, Integer> {
	
	@Query("select u from Unidade u where u.cnpj = :cnpj")
	public List<Unidade> obterUnidadesPorCNPJ(@Param("cnpj") String cnpj);
	
	@Query("select u from Unidade u where u.tipoUnidade = :tipo and u.tipoHierarquia = 'S'")
	public Optional<List<Unidade>> obterUnidadesPorTipo(@Param("tipo") Integer tipo);
	
	@Query("select u from Unidade u where u.idEstadoAtual = :idEstadoAtual")
	public List<Unidade> obterUnidadesPublicadas(@Param("idEstadoAtual") Integer idEstadoAtual);
	
	@Query("select u from Unidade u where u.uf = :uf and u.idEstadoAtual = 2")
	public List<Unidade> obterUnidadesPublicadasPorUF(@Param("uf") String uf);
	
	@Query("select u from Unidade u where u.idEstadoAtual = :idEstadoAtual")
	public List<Unidade> unidadesEmEdicao(@Param("idEstadoAtual") Integer idEstadoAtual);
	
	@Query("select count(u.id) from Unidade u where u.idEstadoAtual = :idEstadoAtual")
	public Integer quantidadeUnidadesEmEdicao(@Param("idEstadoAtual") Integer idEstadoAtual);

}
