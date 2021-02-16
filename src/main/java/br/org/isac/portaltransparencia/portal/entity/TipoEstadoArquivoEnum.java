package br.org.isac.portaltransparencia.portal.entity;

public enum TipoEstadoArquivoEnum {
	
	EM_EDICAO(1, "Em edição"), 
	EM_ANALISE_CONFORMIDADE(2,"Em análise de Conformidade"),
	CONFORME(3, "Documento Conforme"),
	NAO_CONFORME(4, "Documento Não Conforme"),
	PUBLICADO(5, "Publicado"), 
	DESATIVADO(6, "Desativado"), 
	DEVOLVIDO(7, "Devolvido");
	
	private Integer id;
	private String nome;
	
	private TipoEstadoArquivoEnum(Integer id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public static TipoEstadoArquivoEnum recupera(Integer id) {
		if(id == EM_EDICAO.getId()) return EM_EDICAO;
		if(id == PUBLICADO.getId()) return PUBLICADO;
		if(id == DESATIVADO.getId()) return DESATIVADO;
		if(id == EM_ANALISE_CONFORMIDADE.getId()) return EM_ANALISE_CONFORMIDADE;
		if(id == CONFORME.getId()) return CONFORME;
		if(id == NAO_CONFORME.getId()) return NAO_CONFORME;
		if(id == DEVOLVIDO.getId()) return DEVOLVIDO;
		return null;
	}
}
