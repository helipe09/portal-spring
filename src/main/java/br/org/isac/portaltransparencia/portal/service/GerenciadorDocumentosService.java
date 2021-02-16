package br.org.isac.portaltransparencia.portal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.isac.portaltransparencia.portal.dao.GerenciadorDocumentosDao;
import br.org.isac.portaltransparencia.portal.entity.Objeto;

@Service
@Transactional
public class GerenciadorDocumentosService {
	
	@Autowired
	private GerenciadorDocumentosDao dao;
	
	public List<Objeto> getQuantidadeArquivosPorTipo () {  
		return dao.getQuantidadeArquivosPorTipo();
	}
	
	public List<Objeto> getQuantidadeArquivosPorEstado () {  
		return dao.getQuantidadeArquivosPorEstado();
	}
	
	public List<Objeto> getQuantidadeArquivosPorTipoAndUnidade (Integer idUnidade) {  
		return dao.getQuantidadeArquivosPorTipoAndUnidade(idUnidade);
	}
	
	public List<Objeto> getQuantidadeArquivosPorUnidadeEstadoVisibilidade (Integer idUnidade, Integer idEstado, String visibilidade) {  
		return dao.getQuantidadeArquivosPorUnidadeEstadoVisibilidade(idUnidade, idEstado, visibilidade);
	}
	
	public List<Objeto> getQuantidadeArquivosPorEstadoVisibilidade (Integer idEstado, String visibilidade) {  
		return dao.getQuantidadeArquivosPorEstadoVisibilidade(idEstado, visibilidade);
	}
	
	public List<Objeto> getQuantidadeArquivosPorEstadoVisibilidade (String visibilidade) {  
		return dao.getQuantidadeArquivosPorEstadoVisibilidade(visibilidade);
	}
	
	public List<Objeto> getQuantidadeArquivosPorUnidadeEstadoVisibilidade (Integer idUnidade, String visibilidade) {  
		return dao.getQuantidadeArquivosPorUnidadeEstadoVisibilidade(idUnidade, visibilidade);
	}
}