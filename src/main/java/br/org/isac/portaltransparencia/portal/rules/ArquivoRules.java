package br.org.isac.portaltransparencia.portal.rules;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.isac.portaltransparencia.portal.entity.Arquivos;
import br.org.isac.portaltransparencia.portal.entity.EstadoArquivo;
import br.org.isac.portaltransparencia.portal.entity.Mensagem;
import br.org.isac.portaltransparencia.portal.entity.TipoEstadoArquivoEnum;
import br.org.isac.portaltransparencia.portal.entity.TipoEstadoUsuarioEnum;
import br.org.isac.portaltransparencia.portal.entity.TipoFuncaoUsuarioEnum;
import br.org.isac.portaltransparencia.portal.entity.Usuario;
import br.org.isac.portaltransparencia.portal.util.ParametrosIsacSede;

@Service
public class ArquivoRules {
	
	@Autowired
	GeneralRules rules;
	
	public String tiposPermitidosParaUnidade(Integer tipoUnidade) {
		
		if(tipoUnidade == ParametrosIsacSede.TIPO_UNIDADE_GESTORA) {
			return "T,S,U";
		}else {
			return "U";
		}
	}
	
	public Mensagem podeIncluir(Arquivos arq) {
		Mensagem msg = new Mensagem();
		msg.setPermitido(false);
		
		return msg;
	}
	
	public Mensagem podeExcluir(Arquivos arq, List<EstadoArquivo> estados) {
		Mensagem msg = new Mensagem();
		msg.setPermitido(false);
		
		if(estados.size()>1) {
			msg.setMensagem("Somente é possível excluir arquivo que esteja na primeira vez na Situação Em Edição.");
			return msg;
		}else {
			msg.setMensagem("Exclusão autorizada.");
			msg.setPermitido(true);
			return msg;
		}
	}
	
	public Mensagem podeAlterar(Arquivos arquivo, Usuario usuSessao) {
		Mensagem msg = new Mensagem();
		msg.setPermitido(false);
		
		if(rules.isISACSede(usuSessao).isPermitido()) {
			msg.setPermitido(true);
			return msg;
		}
		
		if(usuSessao.getIdUnidadeAtual() == arquivo.getIdUnidade()) {
			
				if(arquivo.getIdEstadoAtual() == TipoEstadoUsuarioEnum.EM_EDICAO.getId()) {
					msg.setPermitido(true);
					return msg;
				}else {
					msg.setMensagem("Para realizar alterações no documento ele precisa estar na situação Em Edição.");
					return msg;
				}
			}else {
				msg.setMensagem("Somente usuário da mesma Unidade pode alterar o conteúdo deste documento");
				return msg;
			}
	}
	
	
	public Mensagem podeAlterarParaEmEdicao(Arquivos arquivo, Usuario usuSessao) {
		Mensagem msg = new Mensagem();
		msg.setPermitido(false);
		
		if(arquivo.getIdEstadoAtual() == TipoEstadoArquivoEnum.DEVOLVIDO.getId() || 
					arquivo.getIdEstadoAtual() == TipoEstadoArquivoEnum.CONFORME.getId() ||
						arquivo.getIdEstadoAtual() == TipoEstadoArquivoEnum.NAO_CONFORME.getId()) {
			if(rules.usuarioSessaoEhDaUnidade(arquivo.getIdUnidade(), usuSessao).isPermitido() || rules.isISACSede(usuSessao).isPermitido()) {
				msg.setPermitido(true);
				return msg;
			}else {
				msg.setMensagem("Somente usuários da Unidade do arquivo ou do ISAC Sede podem alterar a situação de um documento para Em Edição.");
				return msg;
			}
		}else {
			msg.setMensagem("Somente documentos na Situação Devolvido, Conforme, Não Conforme podem ser alterados para a situação Em Edição.");
			return msg;
		}
	}
	
	public Mensagem podeAlterarParaEmAnalise(Arquivos arquivo, Usuario usuSessao) {
		Mensagem msg = new Mensagem();
		msg.setPermitido(false);
		
		if(arquivo.getVisibilidade().equals("R")) {
			msg.setMensagem("Documentos com visibilidade Restrita não precisam ser submetidos para Análise de de Conformidade.");
			return msg;
		}
		
		if(arquivo.getIdEstadoAtual() == TipoEstadoArquivoEnum.EM_EDICAO.getId()) {
			if(rules.usuarioSessaoEhDaUnidade(arquivo.getIdUnidade(), usuSessao).isPermitido() || rules.isISACSede(usuSessao).isPermitido()) {
				msg.setPermitido(true);
				return msg;
			}else {
				msg.setMensagem("Somente usuários da Unidade do arquivo ou do ISAC Sede podem alterar a situação de um documento para Em Análise de Conformidade.");
				return msg;
			}
		}else {
			msg.setMensagem("Somente documentos na Situação Em Edição podem ser alterados para a situação Em Análise de Conformidade.");
			return msg;
		}
	}
	
	public Mensagem podeAlterarParaConforme(Arquivos arquivo, Usuario usuSessao) {
		Mensagem msg = new Mensagem();
		msg.setPermitido(false);
		
		if(rules.isCompliance(usuSessao).isPermitido()) {
			if(arquivo.getIdEstadoAtual() == TipoEstadoArquivoEnum.EM_ANALISE_CONFORMIDADE.getId()) {
				msg.setPermitido(true);
				return msg;
			}else {
				msg.setMensagem("Somente documentos na Situação Em Análise podem ser alterados para a situação Conforme.");
				return msg;
			}
			
		}else {
			msg.setMensagem("Somente usuários com a Função "+TipoFuncaoUsuarioEnum.COMPLIANCE.getNome()+" podem alterar a situação de um documento para Conforme.");
			return msg;
		}
	}
	
	public Mensagem podeAlterarParaNaoConforme(Arquivos arquivo, Usuario usuSessao) {
		Mensagem msg = new Mensagem();
		msg.setPermitido(false);
		
		if(rules.isCompliance(usuSessao).isPermitido()) {
			if(arquivo.getIdEstadoAtual() == TipoEstadoArquivoEnum.EM_ANALISE_CONFORMIDADE.getId()) {
				msg.setPermitido(true);
				return msg;
			}else {
				msg.setMensagem("Somente documentos na Situação Em Análise podem ser alterados para a situação Não Conforme.");
				return msg;
			}
		}else {
			msg.setMensagem("Somente usuários com a Função "+TipoFuncaoUsuarioEnum.COMPLIANCE.getNome()+" podem alterar a situação de um documento para Não Conforme.");
			return msg;
		}
	}
	
	public Mensagem podeAlterarParaDevolvido(Arquivos arquivo, Usuario usuSessao) {
		Mensagem msg = new Mensagem();
		msg.setPermitido(false);
		
		if(TipoEstadoArquivoEnum.CONFORME.getId() == arquivo.getIdEstadoAtual() || 
				TipoEstadoArquivoEnum.NAO_CONFORME.getId() == arquivo.getIdEstadoAtual() ||
					TipoEstadoArquivoEnum.PUBLICADO.getId() == arquivo.getIdEstadoAtual() || 
						TipoEstadoArquivoEnum.EM_ANALISE_CONFORMIDADE.getId() == arquivo.getIdEstadoAtual()) {
			
			if(TipoEstadoArquivoEnum.EM_ANALISE_CONFORMIDADE.getId() == arquivo.getIdEstadoAtual()) {
				if(rules.isCompliance(usuSessao).isPermitido()) {
					msg.setPermitido(true);
					return msg;
				}else {
					msg.setMensagem("Para Documentos na Situação Em Análise de Conformidade, apenas usuários com a função Compliance podem devolvê-lo.");
					return msg;
				}
			}
			
			if(rules.usuarioSessaoEhDaUnidade(arquivo.getIdUnidade(), usuSessao).isPermitido() || rules.isISACSede(usuSessao).isPermitido()) {
				msg.setPermitido(true);
				return msg;
			}else {
				msg.setMensagem("Somente usuários da Unidade do arquivo ou do ISAC Sede podem alterar a situação de um documento para Publicado.");
				return msg;
			}
		}else {
			msg.setMensagem("Somente documentos na Situação Conforme, Não Conforme ou Publicado podem ser alterados para a situação Devolvido."
					+ " Para documento na situação Em Análise de Conformidade, apenas usuários com a função Compliance podem devolvê-lo.");
			return msg;
		}
	}
	
	public Mensagem podeAlterarParaPublicado(Arquivos arquivo, Usuario usuSessao) {
		Mensagem msg = new Mensagem();
		msg.setPermitido(false);
		
		if(arquivo.getVisibilidade().equals("R") && arquivo.getIdEstadoAtual() == TipoEstadoArquivoEnum.EM_EDICAO.getId()) {
			msg.setPermitido(true);
			return msg;
		}
		
		if(rules.usuarioSessaoEhDaUnidade(arquivo.getIdUnidade(), usuSessao).isPermitido() || rules.isISACSede(usuSessao).isPermitido()) {
			if(arquivo.getIdEstadoAtual() == TipoEstadoArquivoEnum.CONFORME.getId()) {
				msg.setPermitido(true);
				return msg;
			}else {
				msg.setMensagem("Somente documentos na Situação Conforme podem ser alterados para a situação Publicado.");
				return msg;
			}
		}else {
			msg.setMensagem("Somente usuários da Unidade do arquivo ou do ISAC Sede podem alterar a situação de um documento para Publicado.");
			return msg;
		}
	}
	
	public Mensagem podeAlterarParaDesativado(Arquivos arquivo, Usuario usuSessao) {
		Mensagem msg = new Mensagem();
		msg.setPermitido(false);
		
		if(arquivo.getVisibilidade().equals("R") && arquivo.getIdEstadoAtual() == TipoEstadoArquivoEnum.PUBLICADO.getId()) {
			msg.setPermitido(true);
			return msg;
		}
		
		if(rules.usuarioSessaoEhDaUnidade(arquivo.getIdUnidade(), usuSessao).isPermitido() || rules.isISACSede(usuSessao).isPermitido()) {
			if(arquivo.getIdEstadoAtual() == TipoEstadoArquivoEnum.CONFORME.getId() || arquivo.getIdEstadoAtual() == TipoEstadoArquivoEnum.PUBLICADO.getId()) {
				msg.setPermitido(true);
				return msg;
			}else {
				msg.setMensagem("Somente documentos na Situação Conforme ou Publicado podem ser alterados para a situação Desativado.");
				return msg;
			}
		}else {
			msg.setMensagem("Somente usuários da Unidade do arquivo ou do ISAC Sede podem alterar a situação de um documento para Desativado.");
			return msg;
		}
	}
}