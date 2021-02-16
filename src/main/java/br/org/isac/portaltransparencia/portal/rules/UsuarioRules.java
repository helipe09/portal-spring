package br.org.isac.portaltransparencia.portal.rules;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.isac.portaltransparencia.portal.entity.EstadoUsuario;
import br.org.isac.portaltransparencia.portal.entity.Mensagem;
import br.org.isac.portaltransparencia.portal.entity.TipoEstadoUsuarioEnum;
import br.org.isac.portaltransparencia.portal.entity.TipoFuncaoUsuarioEnum;
import br.org.isac.portaltransparencia.portal.entity.Usuario;
import br.org.isac.portaltransparencia.portal.util.ParametrosIsacSede;

@Service
public class UsuarioRules {
	
	@Autowired
	GeneralRules rules;
	
	public Mensagem podeAlterarImagemUsuario(Usuario usuImagem, Usuario usuarioSessao) {
		Mensagem msg = new Mensagem();
		msg.setPermitido(false);
		
		if(usuImagem.getId() == usuarioSessao.getId()) {
			msg.setPermitido(true);
			return msg;
		}else {
			msg.setMensagem("Somente o próprio usuário pode alterar sua imagem no Portal.");
			return msg;
		}
	}
	
	public Mensagem podeVerTodosUsuarios(Usuario usuSessao) {
		
		Mensagem msg = new Mensagem();
		msg.setPermitido(false);
		
		if(rules.isISACSede(usuSessao).isPermitido()) {
			msg.setPermitido(true);
			return msg;
		}else {
			return msg;
		}
	}
	
	public Mensagem podeIncluir(Usuario usuSessao, Usuario usu) {
		Mensagem msg = new Mensagem();
		msg.setPermitido(false);
		
		if(usu.getCnpjUnidade()!= null || !usu.getCnpjUnidade().equals("")) {
			if(!usu.getCnpjUnidade().equals(ParametrosIsacSede.cnpjSede)) {
				if(usu.getIdFuncaoAtual() == TipoFuncaoUsuarioEnum.PRESIDENTE.getId() || 
						usu.getIdFuncaoAtual() == TipoFuncaoUsuarioEnum.SUPERINTENDENTE.getId() || 
								usu.getIdFuncaoAtual() == TipoFuncaoUsuarioEnum.GERENTE.getId() || usu.getIdFuncaoAtual() == TipoFuncaoUsuarioEnum.COMPLIANCE.getId()) {
					msg.setMensagem("As funções Presidente, Superitendente, Gerente e Compliance somente são acionáveis nas unidades do CNPJ do ISAC Sede.");
					return msg;
				}
			}
		}
		
		if(rules.isISACSede(usuSessao).isPermitido()) {
			msg.setPermitido(true);
			return msg;
		}
		
		if(rules.isPresidente(usuSessao).isPermitido() || rules.isDiretor(usuSessao).isPermitido() || 
				rules.isSuperindente(usuSessao).isPermitido() || rules.isGerente(usuSessao).isPermitido() || 
				rules.isCoordenador(usuSessao).isPermitido() || rules.isAssessor(usuSessao).isPermitido()) {
			msg.setPermitido(true);
		}else {
			msg.setMensagem("Para cadastrar um novo usuário é necessário pertencer as funções: "+TipoFuncaoUsuarioEnum.PRESIDENTE.getNome() + " ou "+
					TipoFuncaoUsuarioEnum.DIRETOR.getNome() + " ou "+
					TipoFuncaoUsuarioEnum.SUPERINTENDENTE.getNome() + " ou "+
					TipoFuncaoUsuarioEnum.GERENTE.getNome() + " ou "+
					TipoFuncaoUsuarioEnum.COORDENADOR.getNome() + " ou "+
					TipoFuncaoUsuarioEnum.ASSESSOR.getNome() + ".");
		}
		
		return msg;
	}
	
	public Mensagem podeAlterar(Usuario usu, Usuario usuSessao) {
		Mensagem msg = new Mensagem();
		msg.setPermitido(false);
		
		if(usu.getCnpjUnidade()!= null || !usu.getCnpjUnidade().equals("")) {
			if(!usu.getCnpjUnidade().equals(ParametrosIsacSede.cnpjSede)) {
				if(usu.getIdFuncaoAtual() == TipoFuncaoUsuarioEnum.PRESIDENTE.getId() || 
						usu.getIdFuncaoAtual() == TipoFuncaoUsuarioEnum.SUPERINTENDENTE.getId() || 
								usu.getIdFuncaoAtual() == TipoFuncaoUsuarioEnum.GERENTE.getId() || usu.getIdFuncaoAtual() == TipoFuncaoUsuarioEnum.COMPLIANCE.getId()) {
					msg.setMensagem("As funções Presidente, Superitendente, Gerente e Compliance somente são acionáveis nas unidades do CNPJ do ISAC Sede.");
					return msg;
				}
			}
		}
		
		if(rules.isISACSede(usuSessao).isPermitido()) {
			msg.setPermitido(true);
			return msg;
		}
		
		if(!rules.usuariosTemMesmoId(usu, usuSessao).isPermitido()) {
			
			if(rules.ehMesmaUnidade(usu, usuSessao).isPermitido()) {
				if(usu.getIdEstadoAtual() == TipoEstadoUsuarioEnum.EM_EDICAO.getId()) {
					msg.setPermitido(true);
					return msg;
				}else {
					msg.setMensagem("Para realizar alterações no cadastro deste usuário ele precisa estar na situação Em Edição.");
					return msg;
				}
			}else {
				msg.setMensagem("Somente usuário da mesma Unidade pode alterar o cadastro deste usuário");
				return msg;
			}
		}else {
			msg.setMensagem("O próprio usuário não pode alterar seu cadastro");
			return msg;
		}
	}
	
	public Mensagem podeColocarEmEdicao(Usuario usu, Usuario usuSessao) {
		Mensagem msg = new Mensagem();
		msg.setPermitido(false);
		
		if(rules.isISACSede(usuSessao).isPermitido()) {
			msg.setPermitido(true);
			return msg;
		}
		
		if(rules.ehMesmaUnidade(usu, usuSessao).isPermitido()) {
			if(usu.getIdEstadoAtual() == TipoEstadoUsuarioEnum.ATIVO.getId() ||  usu.getIdEstadoAtual() == TipoEstadoUsuarioEnum.DESLIGADO.getId()) {
				msg.setPermitido(true);
				return msg;
			}else {
				msg.setMensagem("O Usuário já está na situação Em Edição.");
				return msg;
			}
		}
		
		return msg;
	}
	
	public Mensagem podeAtivar(Usuario usu, Usuario usuSessao) {
		Mensagem msg = new Mensagem();
		msg.setPermitido(false);
		
		if(usu.getId() == usuSessao.getId()) {
			msg.setMensagem("O próprio usuário não pode alterar o seu cadastro para Ativo. Solicite a outro usuário para realizar esta alteração.");
			return msg;
		}
		
		if(rules.isISACSede(usuSessao).isPermitido()) {
			msg.setPermitido(true);
			return msg;
		}
		
		if(rules.isSuperUsuario(usuSessao).isPermitido() || rules.isGerente(usuSessao).isPermitido() || rules.isCoordenador(usuSessao).isPermitido()) {
			if(rules.ehMesmaUnidade(usu, usuSessao).isPermitido()) {
				if(usu.getIdEstadoAtual() == TipoEstadoUsuarioEnum.EM_EDICAO.getId()) {
					msg.setPermitido(true);
					return msg;
				}else {
					msg.setMensagem("Para alterar a situação de um usuário para Ativo, ele deve estar na situação Em Edição.");
					return msg;
				}
			}else {
				msg.setMensagem("Para alterar a situação de um usuário para Ativo, o usuário registrado deve pertencer a mesma Unidade.");
				return msg;
			}
		}else {
			msg.setMensagem("Para colocar na situação Ativo, o usuário registrado deve pertencer as funções: "+TipoFuncaoUsuarioEnum.PRESIDENTE.getNome() + " ou "+
					TipoFuncaoUsuarioEnum.DIRETOR.getNome() + " ou "+
					TipoFuncaoUsuarioEnum.SUPERINTENDENTE.getNome() + " ou "+
					TipoFuncaoUsuarioEnum.GERENTE.getNome() + " ou "+
					TipoFuncaoUsuarioEnum.COORDENADOR.getNome() + ".");
			return msg;
		}
		
	}
	
	public Mensagem podeDesligar(Usuario usu, Usuario usuSessao) {
		Mensagem msg = new Mensagem();
		msg.setPermitido(false);
		
		if(rules.isISACSede(usuSessao).isPermitido()) {
			msg.setPermitido(true);
			return msg;
		}
		
		if(rules.isSuperUsuario(usuSessao).isPermitido() || rules.isGerente(usuSessao).isPermitido() || rules.isCoordenador(usuSessao).isPermitido()) {
			if(rules.ehMesmaUnidade(usu, usuSessao).isPermitido()) {
				if(usu.getIdEstadoAtual() == TipoEstadoUsuarioEnum.ATIVO.getId() || usu.getIdEstadoAtual() == TipoEstadoUsuarioEnum.EM_EDICAO.getId()) {
					msg.setPermitido(true);
					return msg;
				}else {
					msg.setMensagem("Para alterar a situação de um usuário para Desligado, ele deve estar na situação Em Edição ou Ativo.");
					return msg;
				}
			}else {
				msg.setMensagem("Para alterar a situação de um usuário para Desligado, o usuário registrado deve pertencer a mesma Unidade.");
				return msg;
			}
		}else {
			msg.setMensagem("Para alterar a situação de um usuário para Desligado, o usuário registrado deve pertencer as funções: "+TipoFuncaoUsuarioEnum.PRESIDENTE.getNome() + " ou "+
					TipoFuncaoUsuarioEnum.DIRETOR.getNome() + " ou "+
					TipoFuncaoUsuarioEnum.SUPERINTENDENTE.getNome() + " ou "+
					TipoFuncaoUsuarioEnum.GERENTE.getNome() + " ou "+
					TipoFuncaoUsuarioEnum.COORDENADOR.getNome() + ".");
			return msg;
		}
	}
	
	public Mensagem podeExcluir(Usuario usu, Usuario usuSessao, List<EstadoUsuario> estados) {
		Mensagem msg = new Mensagem();
		msg.setPermitido(false);
		
		if(estados.size()>1) {
			msg.setMensagem("Somente é possível excluir um Usuário se ele estiver na situação Em Edição pela primeira vez. Neste caso, sugerimos utilizar a opção Desligar.");
			return msg;
		}else {
			if(rules.isSuperUsuario(usuSessao).isPermitido()) {
				msg.setPermitido(true);
				return msg;
			}
			
			if(rules.isCoordenador(usuSessao).isPermitido() || rules.isGerente(usuSessao).isPermitido() 
					|| rules.isPresidente(usuSessao).isPermitido() || rules.isSuperindente(usuSessao).isPermitido() 
						|| rules.isDiretor(usuSessao).isPermitido()) {
				if(rules.ehMesmaUnidade(usu, usuSessao).isPermitido()) {
					msg.setPermitido(true);
					return msg;
				}else {
					msg.setMensagem("Somente é possível excluir um Usuário caso o usuário registrado seja da mesma Unidade.");
					return msg;
				}
			}else {
				msg.setMensagem("Somente é possível excluir um Usuário caso o usuário registrado seja Presidente, Superitendente, Diretor, Gerente ou Coordenador.");
				return msg;
			}
		}
	}
}