package br.org.isac.portaltransparencia.portal.entity;

public enum TipoEstadoUsuarioEnum {
	
	EM_EDICAO(1, "Em edição"), 
	ATIVO(2, "Ativo"), 
	DESLIGADO(3, "Desligado");
	
	
	private Integer id;
	private String nome;
	
	private TipoEstadoUsuarioEnum(Integer id, String nome) {
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
	
	public static TipoEstadoUsuarioEnum recupera(Integer id) {
		if(id == EM_EDICAO.getId()) return EM_EDICAO;
		if(id == ATIVO.getId()) return ATIVO;
		if(id == DESLIGADO.getId()) return DESLIGADO;
		return null;
	}
}
