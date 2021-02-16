package br.org.isac.portaltransparencia.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.org.isac.portaltransparencia.portal.entity.TipoUnidade;

public interface TipoUnidadeRepository extends JpaRepository<TipoUnidade, Integer> {
	
	@Query("select u from TipoUnidade u where u.ativo = 'S'")
	public List<TipoUnidade> obterTiposUnidadesAtivos();

}
