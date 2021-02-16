package br.org.isac.portaltransparencia.portal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.isac.portaltransparencia.portal.entity.UnidadeArquivo;

@Repository
public interface UnidadeArquivoRepository extends JpaRepository<UnidadeArquivo, Integer> {
	
	@Query("select u from UnidadeArquivo u where u.idUnidade = :idUnidade")
	public Optional<List<UnidadeArquivo>> obterArquivosPorUnidade(@Param("idUnidade") Integer idUnidade);
	
	@Query("select u from UnidadeArquivo u where u.idArquivo = :idArquivo")
	public Optional<List<UnidadeArquivo>> obterUnidadesPorArquivo(@Param("idArquivo") Integer idArquivo);
	

}
