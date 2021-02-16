package br.org.isac.portaltransparencia.portal.rules;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.isac.portaltransparencia.portal.entity.EstadoUnidade;
import br.org.isac.portaltransparencia.portal.entity.Mensagem;
import br.org.isac.portaltransparencia.portal.entity.TipoEstadoUnidadeEnum;
import br.org.isac.portaltransparencia.portal.entity.TipoEstadoUsuarioEnum;
import br.org.isac.portaltransparencia.portal.entity.TipoFuncaoUsuarioEnum;
import br.org.isac.portaltransparencia.portal.entity.Unidade;
import br.org.isac.portaltransparencia.portal.entity.Usuario;
import br.org.isac.portaltransparencia.portal.util.ParametrosIsacSede;

@Service
public class UnidadeRules {
	
	@Autowired
	GeneralRules rules;
	
	public Mensagem podeVerTodasUnidades(Usuario usuSessao) {
		
		Mensagem msg = new Mensagem();
		msg.setPermitido(false);
		
		if(rules.isISACSede(usuSessao).isPermitido()) {
			msg.setPermitido(true);
			return msg;
		}else {
			return msg;
		}
	}
	
	public Mensagem podeIncluir(Usuario usuSessao) {
		Mensagem msg = new Mensagem();
		msg.setPermitido(false);
		
		if(!usuSessao.getCnpjUnidade().equals(ParametrosIsacSede.cnpjSede)) {
			msg.setMensagem("Somente a Unidade ISAC Sede pode incluir novas unidades.");
			return msg;
		}
		
		if(rules.isPresidente(usuSessao).isPermitido() || rules.isDiretor(usuSessao).isPermitido() || 
				rules.isSuperindente(usuSessao).isPermitido() || rules.isGerente(usuSessao).isPermitido() || 
				rules.isCoordenador(usuSessao).isPermitido() || rules.isAssessor(usuSessao).isPermitido() || 
				rules.isCompliance(usuSessao).isPermitido()) {
			msg.setPermitido(true);
		}else {
			msg.setMensagem("Para cadastrar uma nova unidade é necessário pertencer as funções: "+TipoFuncaoUsuarioEnum.PRESIDENTE.getNome() + ", "+
					TipoFuncaoUsuarioEnum.DIRETOR.getNome() + " , "+
					TipoFuncaoUsuarioEnum.SUPERINTENDENTE.getNome() + " , "+
					TipoFuncaoUsuarioEnum.GERENTE.getNome() + " , "+
					TipoFuncaoUsuarioEnum.COORDENADOR.getNome() + " , "+
					TipoFuncaoUsuarioEnum.ASSESSOR.getNome() + ".");
		}
		
		return msg;
	}
	
	public Mensagem podeAlterar(Usuario usuSessao, Unidade unidade) {
		Mensagem msg = new Mensagem();
		msg.setPermitido(false);
		
		if(rules.isISACSede(usuSessao).isPermitido()) {
			msg.setPermitido(true);
			return msg;
		}
		
		if(rules.usuarioSessaoEhDaUnidade(unidade.getId(), usuSessao).isPermitido()) {
			
				if(unidade.getIdEstadoAtual() == TipoEstadoUnidadeEnum.EM_EDICAO.getId()) {
					msg.setPermitido(true);
					return msg;
				}else {
					msg.setMensagem("Para realizar alterações no cadastro desta unidade ela precisa estar na situação Em Edição.");
					return msg;
				}
			
		}else {
			msg.setMensagem("O usuário registrado não pertence a Unidade informado");
			return msg;
		}
	}
	
	public Mensagem podeExcluir(Usuario usuSessao, Unidade unidade, List<EstadoUnidade> estados) {
		Mensagem msg = new Mensagem();
		msg.setPermitido(false);
		
		if(estados.size()>1) {
			msg.setMensagem("Somente é possível excluir uma Unidade se ela estiver na situação Em Edição pela primeira vez. Neste caso, sugerimos utilizar a opção Desligar.");
			return msg;
		}else {
			if(rules.isISACSede(usuSessao).isPermitido()) {
				msg.setPermitido(true);
				return msg;
			}
			
			if(rules.isCoordenador(usuSessao).isPermitido() || rules.isGerente(usuSessao).isPermitido()) {
				if(rules.usuarioSessaoEhDaUnidade(unidade.getId(), usuSessao).isPermitido()) {
					msg.setPermitido(true);
					return msg;
				}else {
					msg.setMensagem("Somente é possível excluir uma Unidade caso o usuário registrado seja da mesma Unidade.");
					return msg;
				}
			}else {
				msg.setMensagem("Somente é possível excluir uma Unidade caso o usuário registrado seja Gerente ou Coordenador.");
				return msg;
			}
		}
	}
	
	public Mensagem podeAlterarParaEmEdicao(Usuario usuSessao, Unidade unidade) {
		Mensagem msg = new Mensagem();
		msg.setPermitido(false);
		
		if(rules.isISACSede(usuSessao).isPermitido() || rules.usuarioSessaoEhDaUnidade(unidade.getId(), usuSessao).isPermitido()) {
			if(unidade.getIdEstadoAtual() == TipoEstadoUnidadeEnum.PUBLICADO.getId() ||  unidade.getIdEstadoAtual() == TipoEstadoUnidadeEnum.DESATIVADA.getId()) {
				msg.setPermitido(true);
				return msg;
			}else {
				msg.setMensagem("A Unidade já está na situação Em Edição.");
				return msg;
			}
		}else {
			msg.setMensagem("Somente usuário registrado pertencente a mesma Unidade pode Alterar a situação para Em Edição.");
			return msg;
		}
		
	}
	
	public Mensagem podeAlterarParaPublicada(Usuario usuSessao, Unidade unidade) {
		Mensagem msg = new Mensagem();
		msg.setPermitido(false);

		if(rules.isISACSede(usuSessao).isPermitido() || rules.isGerente(usuSessao).isPermitido() || rules.isCoordenador(usuSessao).isPermitido()) {
			if(rules.usuarioSessaoEhDaUnidade(unidade.getId(), usuSessao).isPermitido() || rules.isISACSede(usuSessao).isPermitido()) {
				if(unidade.getIdEstadoAtual() == TipoEstadoUnidadeEnum.EM_EDICAO.getId()) {
					msg.setPermitido(true);
					return msg;
				}else {
					msg.setMensagem("Para alterar a situação de uma Unidade para Publicada, ela deve estar na situação Em Edição.");
					return msg;
				}
			}else {
				msg.setMensagem("Para alterar a situação de uma Unidade para Publicada, o usuário registrado deve pertencer a mesma Unidade.");
				return msg;
			}
		}else {
			msg.setMensagem("Para alterar a Unidade para a situação Publicada, o usuário registrado deve pertencer as funções: "+TipoFuncaoUsuarioEnum.PRESIDENTE.getNome() + " ou "+
					TipoFuncaoUsuarioEnum.DIRETOR.getNome() + " ou "+
					TipoFuncaoUsuarioEnum.SUPERINTENDENTE.getNome() + " ou "+
					TipoFuncaoUsuarioEnum.GERENTE.getNome() + " ou "+
					TipoFuncaoUsuarioEnum.COORDENADOR.getNome() + ".");
			return msg;
		}
	}
	
	public Mensagem podeAlterarParaDesativada(Usuario usuSessao, Unidade unidade, List<Usuario> usuarios) {
		Mensagem msg = new Mensagem();
		msg.setPermitido(false);
		
		if(usuarios!=null && usuarios.size()>0) {
			for (Usuario usuario : usuarios) {
				if(usuario.getIdEstadoAtual() == TipoEstadoUsuarioEnum.ATIVO.getId()) {
					msg.setMensagem("Para alterar a situação de uma Unidade para Desativada, não podem existir usuários cadastrados na unidade e na situação Ativo.");
					return msg;
				}
			}
		}
		
		if(rules.isSuperUsuario(usuSessao).isPermitido() || rules.isGerente(usuSessao).isPermitido() || rules.isCoordenador(usuSessao).isPermitido()) {
			if(rules.usuarioSessaoEhDaUnidade(unidade.getId(), usuSessao).isPermitido() || rules.isISACSede(usuSessao).isPermitido()) {
				if(unidade.getIdEstadoAtual() == TipoEstadoUnidadeEnum.PUBLICADO.getId() || unidade.getIdEstadoAtual() == TipoEstadoUnidadeEnum.EM_EDICAO.getId()) {
					msg.setPermitido(true);
					return msg;
				}else {
					msg.setMensagem("Para alterar a situação de uma Unidade para Desativada, ela deve estar na situação Em Edição ou Ativo.");
					return msg;
				}
			}else {
				msg.setMensagem("Para alterar a situação de uma Unidade para Desativada, o usuário registrado deve pertencer a mesma Unidade.");
				return msg;
			}
		}else {
			msg.setMensagem("Para alterar a situação de uma Unidade para Desativada, o usuário registrado deve pertencer as funções: "+TipoFuncaoUsuarioEnum.PRESIDENTE.getNome() + " ou "+
					TipoFuncaoUsuarioEnum.DIRETOR.getNome() + " ou "+
					TipoFuncaoUsuarioEnum.SUPERINTENDENTE.getNome() + " ou "+
					TipoFuncaoUsuarioEnum.GERENTE.getNome() + " ou "+
					TipoFuncaoUsuarioEnum.COORDENADOR.getNome() + ".");
			return msg;
		}
	}

}
