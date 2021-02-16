package br.org.isac.portaltransparencia.portal.rules;

import org.springframework.stereotype.Service;

import br.org.isac.portaltransparencia.portal.entity.Mensagem;
import br.org.isac.portaltransparencia.portal.entity.TipoFuncaoUsuarioEnum;
import br.org.isac.portaltransparencia.portal.entity.Usuario;
import br.org.isac.portaltransparencia.portal.util.ParametrosIsacSede;

@Service
public class TipoDocumentoRules {
	
public Mensagem podeAcessarTipos(Usuario usuSessao) {
		
		Mensagem msg = new Mensagem();
		msg.setPermitido(false);
		
		if(usuSessao.getIdFuncaoAtual() == TipoFuncaoUsuarioEnum.COMPLIANCE.getId() && usuSessao.getCnpjUnidade().equals(ParametrosIsacSede.cnpjSede)) {
			msg.setPermitido(true);
		}else {
			msg.setMensagem("Funcionalidade disponível apenas para usuários com a função: "+ TipoFuncaoUsuarioEnum.COMPLIANCE.getNome());
		}
		
		return msg;
	}

}
