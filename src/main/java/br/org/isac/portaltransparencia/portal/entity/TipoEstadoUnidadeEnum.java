package br.org.isac.portaltransparencia.portal.entity;

public enum TipoEstadoUnidadeEnum {
	
	EM_EDICAO(1, "Em edição"), 
	PUBLICADO(2, "Publicada"), 
	DESATIVADA(3, "Desativada");
	
	
	private Integer id;
	private String nome;
	
	private TipoEstadoUnidadeEnum(Integer id, String nome) {
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
	
	public static TipoEstadoUnidadeEnum recupera(Integer id) {
		if(id == EM_EDICAO.getId()) return EM_EDICAO;
		if(id == PUBLICADO.getId()) return PUBLICADO;
		if(id == DESATIVADA.getId()) return DESATIVADA;
		return null;
	}
}
