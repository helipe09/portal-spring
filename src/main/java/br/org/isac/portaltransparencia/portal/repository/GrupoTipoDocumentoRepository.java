package br.org.isac.portaltransparencia.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.org.isac.portaltransparencia.portal.entity.GrupoTipoDocumento;

public interface GrupoTipoDocumentoRepository extends JpaRepository<GrupoTipoDocumento, Integer> {
	
	@Query("select u from GrupoTipoDocumento u where u.ativo = 'S'")
	public List<GrupoTipoDocumento> obterGruposAtivos();

}
