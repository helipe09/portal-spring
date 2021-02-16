package br.org.isac.portaltransparencia.portal.entity;

public class Mensagem {

	private boolean permitido;
	private String mensagem;
	
	public Mensagem() {
		
	}
	
	public Mensagem(boolean permitido, String mensagem) {
		super();
		this.permitido = permitido;
		this.mensagem = mensagem;
	}

	public boolean isPermitido() {
		return permitido;
	}

	public void setPermitido(boolean permitido) {
		this.permitido = permitido;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}