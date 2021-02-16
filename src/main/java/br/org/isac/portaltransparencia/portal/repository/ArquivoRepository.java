package br.org.isac.portaltransparencia.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.isac.portaltransparencia.portal.entity.Arquivos;


@Repository
public interface ArquivoRepository extends JpaRepository<Arquivos, Integer> {
	
	//@Query("select u from Arquivos u where u.tipo = :tipo")
	//public Optional<List<Arquivos>> obterArquivosPorTipo(@Param("tipo") Integer tipo);
	
	@Query("select new br.org.isac.portaltransparencia.portal.entity.Arquivos(u.id, u.nome, u.nomeExibicao, u.descricao,u.idUsuario, u.tipo, "
			+ "u.tamanho,u.mimetype, u.idUnidade, u.idContratante,u.idEstadoAtual, u.dataProtocolo, u.mesReferencia, u.anoReferencia, u.visibilidade) "
			+ "from Arquivos u where u.tipo = :tipo and u.visibilidade='P'")
	public List<Arquivos> obterArquivosPorTipoSemBlobAreaPublica(@Param("tipo") Integer tipo);
	
	@Query("select new br.org.isac.portaltransparencia.portal.entity.Arquivos(u.id, u.nome, u.nomeExibicao, u.descricao,u.idUsuario, u.tipo, "
			+ "u.tamanho,u.mimetype, u.idUnidade, u.idContratante,u.idEstadoAtual, u.dataProtocolo, u.mesReferencia, u.anoReferencia, u.visibilidade) "
			+ "from Arquivos u where u.idEstadoAtual = 5 and u.visibilidade='R'")
	public List<Arquivos> obterArquivosPublicadosSemBlobAreaRestrita();
	
	@Query("select new br.org.isac.portaltransparencia.portal.entity.Arquivos(u.id, u.nome, u.nomeExibicao, u.descricao,u.idUsuario, u.tipo, "
			+ "u.tamanho,u.mimetype, u.idUnidade, u.idContratante,u.idEstadoAtual, u.dataProtocolo, u.mesReferencia, u.anoReferencia, u.visibilidade) "
			+ "from Arquivos u where u.tipo = :tipo")
	public List<Arquivos> obterArquivosPorTipoSemBlob(@Param("tipo") Integer tipo);
	
	//@Query("select u from Arquivos u where u.idUnidade = :idUnidade and u.tipo > 0")
	//public Optional<List<Arquivos>> obterArquivosPorUnidade(@Param("idUnidade") Integer idUnidade);
	
	@Query("select new br.org.isac.portaltransparencia.portal.entity.Arquivos(u.id, u.nome, u.nomeExibicao, u.descricao,u.idUsuario, u.tipo, "
			+ "u.tamanho,u.mimetype, u.idUnidade, u.idContratante,u.idEstadoAtual, u.dataProtocolo, u.mesReferencia, u.anoReferencia, u.visibilidade) "
			+ "from Arquivos u where u.idUnidade = :idUnidade and u.tipo > 0") 
	public List<Arquivos> obterArquivosPorUnidadeSemBlob(@Param("idUnidade") Integer idUnidade);
	
	@Query("select new br.org.isac.portaltransparencia.portal.entity.Arquivos(u.id, u.nome, u.nomeExibicao, u.descricao,u.idUsuario, u.tipo, "
			+ "u.tamanho,u.mimetype, u.idUnidade, u.idContratante,u.idEstadoAtual, u.dataProtocolo, u.mesReferencia, u.anoReferencia, u.visibilidade)"
			+ " from Arquivos u where u.idEstadoAtual = 5 and  u.idUnidade = :idUnidade and u.tipo > 0 and u.tipo IN (:tipos)") 
	public List<Arquivos> obterArquivosPorUnidadePorGrupoSemBlob(@Param("idUnidade") Integer idUnidade, @Param("tipos") List<Integer> tipos);
	
	@Query("select new br.org.isac.portaltransparencia.portal.entity.Arquivos(u.id, u.nome, u.nomeExibicao, u.descricao,u.idUsuario, u.tipo, "
			+ "u.tamanho,u.mimetype, u.idUnidade, u.idContratante,u.idEstadoAtual, u.dataProtocolo, u.mesReferencia, u.anoReferencia, u.visibilidade) "
			+ "from Arquivos u where u.idUnidade = :idUnidade and u.tipo > 0 and u.idEstadoAtual = 5 and u.visibilidade='P'")
	public List<Arquivos> obterArquivosPorUnidadeAreaPublicaSemBlobo(@Param("idUnidade") Integer idUnidade);
	
	//@Query("select u from Arquivos u where u.idUnidade = :idUnidade and u.tipo = 0")
	//public Optional<Arquivos> imagemUnidade(@Param("idUnidade") Integer idUnidade);
	
	@Query("select new br.org.isac.portaltransparencia.portal.entity.Arquivos(u.id, u.nome, u.nomeExibicao, u.descricao,u.idUsuario, u.tipo, "
			+ "u.tamanho,u.mimetype, u.idUnidade, u.idContratante,u.idEstadoAtual, u.dataProtocolo, u.mesReferencia, u.anoReferencia, u.visibilidade) "
			+ "from Arquivos u where u.idEstadoAtual = :idEstadoAtual and u.tipo > 0 and u.visibilidade='P'")
	public List<Arquivos> obterArquivosPorEstadoAreaPublica(@Param("idEstadoAtual") Integer idEstadoAtual);
	
	@Query("select new br.org.isac.portaltransparencia.portal.entity.Arquivos(u.id, u.nome, u.nomeExibicao, u.descricao,u.idUsuario, u.tipo, "
			+ "u.tamanho,u.mimetype, u.idUnidade, u.idContratante,u.idEstadoAtual, u.dataProtocolo, u.mesReferencia, u.anoReferencia, u.visibilidade) "
			+ "from Arquivos u where u.idEstadoAtual = :idEstadoAtual and u.tipo > 0 and u.visibilidade='R'")
	public List<Arquivos> obterArquivosPorEstadoAreaRestrita(@Param("idEstadoAtual") Integer idEstadoAtual);
	
	@Query("select count(u.id) from Arquivos u where u.idEstadoAtual = :idEstadoAtual and u.tipo > 0 and u.visibilidade = 'P'")
	public Long quantidadeArquivosPorEstadoVisibilidadePublica(@Param("idEstadoAtual") Integer idEstadoAtual);
	
	@Query("select count(u.id) from Arquivos u where u.idEstadoAtual = :idEstadoAtual and u.tipo > 0 and u.visibilidade = 'R'")
	public Long quantidadeArquivosPorEstadoVisibilidadeRestrita(@Param("idEstadoAtual") Integer idEstadoAtual);
	
	@Query("select new br.org.isac.portaltransparencia.portal.entity.Arquivos(u.id, u.nome, u.nomeExibicao, u.descricao,u.idUsuario, u.tipo, "
			+ "u.tamanho,u.mimetype, u.idUnidade, u.idContratante,u.idEstadoAtual, u.dataProtocolo, u.mesReferencia, u.anoReferencia, u.visibilidade) "
			+ "from Arquivos u where u.idEstadoAtual = :idEstadoAtual and u.idUnidade = :idUnidade and u.tipo > 0 and u.visibilidade = 'P'")
	public List<Arquivos> obterArquivosPorEstadoAndUnidadeAreaPublica(@Param("idEstadoAtual") Integer idEstadoAtual, @Param("idUnidade") Integer idUnidade);
	
	@Query("select new br.org.isac.portaltransparencia.portal.entity.Arquivos(u.id, u.nome, u.nomeExibicao, u.descricao,u.idUsuario, u.tipo, "
			+ "u.tamanho,u.mimetype, u.idUnidade, u.idContratante,u.idEstadoAtual, u.dataProtocolo, u.mesReferencia, u.anoReferencia, u.visibilidade) "
			+ "from Arquivos u where u.idEstadoAtual = :idEstadoAtual and u.idUnidade = :idUnidade and u.tipo > 0 and u.visibilidade = 'R'")
	public List<Arquivos> obterArquivosPorEstadoAndUnidadeAreaRestrita(@Param("idEstadoAtual") Integer idEstadoAtual, @Param("idUnidade") Integer idUnidade);
	
	@Query("select count(u.id) from Arquivos u where u.idEstadoAtual = :idEstadoAtual and u.idUnidade = :idUnidade and u.tipo > 0 and u.visibilidade = 'P'")
	public Long quantidadeArquivosPorEstadoAndUnidadeVisibilidadePublica(@Param("idEstadoAtual") Integer idEstadoAtual, @Param("idUnidade") Integer idUnidade);
	
	@Query("select count(u.id) from Arquivos u where u.idEstadoAtual = :idEstadoAtual and u.idUnidade = :idUnidade and u.tipo > 0 and u.visibilidade = 'R'")
	public Long quantidadeArquivosPorEstadoAndUnidadeVisibilidadeRestrita(@Param("idEstadoAtual") Integer idEstadoAtual, @Param("idUnidade") Integer idUnidade);
	
//	@Query(value = "select count(u.id) as quantidade, u.tipo as tipo from Arquivos u where  u.tipo > 0 group by u.tipo", nativeQuery = true)
//	public List<Objeto> getQuantidadeArquivosPorTipo() ;
	
}