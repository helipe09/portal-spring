package br.org.isac.portaltransparencia.portal.rules;

import org.springframework.stereotype.Service;

import br.org.isac.portaltransparencia.portal.entity.Mensagem;
import br.org.isac.portaltransparencia.portal.entity.Unidade;
import br.org.isac.portaltransparencia.portal.entity.Usuario;
import br.org.isac.portaltransparencia.portal.util.ParametrosIsacSede;

@Service
public class ContratanteRules {
	
	public Mensagem podeIncluir(Usuario usuSessao) {
		Mensagem msg = new Mensagem();
		msg.setPermitido(false);
		
		if(!usuSessao.getCnpjUnidade().equals(ParametrosIsacSede.cnpjSede)) {
			msg.setMensagem("Somente a Unidade ISAC Sede pode incluir novos contratantes.");
			return msg;
		}else {
			msg.setPermitido(true);
			return msg;
		}
	}
	
	public Mensagem podeAlterar(Usuario usuSessao, Unidade unidade) {
		Mensagem msg = new Mensagem();
		msg.setPermitido(false);
		
		if(!usuSessao.getCnpjUnidade().equals(ParametrosIsacSede.cnpjSede)) {
			msg.setMensagem("Somente a Unidade ISAC Sede pode incluir novos contratantes.");
			return msg;
		}else {
			msg.setPermitido(true);
			return msg;
		}
	}
}