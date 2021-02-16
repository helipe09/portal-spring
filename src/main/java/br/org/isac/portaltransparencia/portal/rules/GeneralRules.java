package br.org.isac.portaltransparencia.portal.rules;

import org.springframework.stereotype.Service;

import br.org.isac.portaltransparencia.portal.entity.Mensagem;
import br.org.isac.portaltransparencia.portal.entity.TipoFuncaoUsuarioEnum;
import br.org.isac.portaltransparencia.portal.entity.Usuario;
import br.org.isac.portaltransparencia.portal.util.ParametrosIsacSede;

@Service
public class GeneralRules {
	
	public Mensagem isPresidente(Usuario usuSessao) {
		
		Mensagem msg = new Mensagem();
		msg.setPermitido(false);
		
		if(usuSessao.getIdFuncaoAtual() == TipoFuncaoUsuarioEnum.PRESIDENTE.getId()) {
			msg.setPermitido(true);
		}else {
			msg.setMensagem("O usuário registrado não tem a função: "+ TipoFuncaoUsuarioEnum.PRESIDENTE.getNome());
		}
		
		return msg;
	}
	
	public Mensagem isDiretor(Usuario usuSessao) {
		
		Mensagem msg = new Mensagem();
		msg.setPermitido(false);
		
		if(usuSessao.getIdFuncaoAtual() == TipoFuncaoUsuarioEnum.DIRETOR.getId()) {
			msg.setPermitido(true);
		}else {
			msg.setMensagem("O usuário registrado não tem a função: "+ TipoFuncaoUsuarioEnum.DIRETOR.getNome());
		}
		
		return msg;
	}
	
	public Mensagem isSuperindente(Usuario usuSessao) {
		
		Mensagem msg = new Mensagem();
		msg.setPermitido(false);
		
		if(usuSessao.getIdFuncaoAtual() == TipoFuncaoUsuarioEnum.SUPERINTENDENTE.getId()) {
			msg.setPermitido(true);
		}else {
			msg.setMensagem("O usuário registrado não tem a função: "+ TipoFuncaoUsuarioEnum.SUPERINTENDENTE.getNome());
		}
		
		return msg;
	}	
	
	public Mensagem isGerente(Usuario usuSessao) {
		
		Mensagem msg = new Mensagem();
		msg.setPermitido(false);
		
		if(usuSessao.getIdFuncaoAtual() == TipoFuncaoUsuarioEnum.GERENTE.getId()) {
			msg.setPermitido(true);
		}else {
			msg.setMensagem("O usuário registrado não tem a função: "+ TipoFuncaoUsuarioEnum.GERENTE.getNome());
		}
		
		return msg;
	}
	
	public Mensagem isCoordenador(Usuario usuSessao) {
		
		Mensagem msg = new Mensagem();
		msg.setPermitido(false);
		
		if(usuSessao.getIdFuncaoAtual() == TipoFuncaoUsuarioEnum.COORDENADOR.getId()) {
			msg.setPermitido(true);
		}else {
			msg.setMensagem("O usuário registrado não tem a função: "+ TipoFuncaoUsuarioEnum.COORDENADOR.getNome());
		}
		
		return msg;
	}
	
	public Mensagem isAssessor(Usuario usuSessao) {
		
		Mensagem msg = new Mensagem();
		msg.setPermitido(false);
		
		if(usuSessao.getIdFuncaoAtual() == TipoFuncaoUsuarioEnum.ASSESSOR.getId()) {
			msg.setPermitido(true);
		}else {
			msg.setMensagem("O usuário registrado não tem a função: "+ TipoFuncaoUsuarioEnum.ASSESSOR.getNome());
		}
		
		return msg;
	}
	
	public Mensagem ehMesmaUnidade(Usuario usu, Usuario usuSessao) {
		if(usu.getIdUnidadeAtual() == usuSessao.getIdUnidadeAtual()) {
			return new Mensagem(true, "");
		}else {
			return new Mensagem(false, "Usuário registrado é de Unidade diferente do usuário informado.");
		}
	}
	
	public Mensagem usuariosTemMesmoId(Usuario usu, Usuario usuSessao) {
		if(usu.getId() == usuSessao.getId()) {
			return new Mensagem(true, "");
		}else {
			return new Mensagem(false, "Usuário registrado é o mesmo usuário informado");
		}
	}
	
	public Mensagem usuarioSessaoEhDaUnidade(Integer idUnidade, Usuario usuSessao) {
		if(idUnidade == usuSessao.getIdUnidadeAtual()) {
			return new Mensagem(true, "");
		}else {
			return new Mensagem(false, "Usuário registrado não pertence a Unidade informada.");
		}
	}
	
	public Mensagem isSuperUsuario(Usuario usuSessao) {
		
		if(isISACSede(usuSessao).isPermitido()) {
			return new Mensagem(true, "");
		}
		
		if(isPresidente(usuSessao).isPermitido() || isDiretor(usuSessao).isPermitido() || isSuperindente(usuSessao).isPermitido()) {
			return new Mensagem(true, "");
		}else {
			return new Mensagem(false, "");
		}
	}
	
	public Mensagem isISACSede(Usuario usuSessao) {
		if(usuSessao.getCnpjUnidade().equals(ParametrosIsacSede.cnpjSede)) {
			return new Mensagem(true, "");
		}else {
			return new Mensagem(false, "");
		}
	}
	
	public Mensagem isCompliance(Usuario usuSessao) {
		if(usuSessao.getIdFuncaoAtual() ==  TipoFuncaoUsuarioEnum.COMPLIANCE.getId()) {
			return new Mensagem(true, "");
		}else {
			return new Mensagem(false, "");
		}
	}
}
