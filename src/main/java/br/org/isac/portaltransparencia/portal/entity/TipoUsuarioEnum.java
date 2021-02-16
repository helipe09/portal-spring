package br.org.isac.portaltransparencia.portal.entity;

public enum TipoUsuarioEnum {
	
	ADMINISTRADOR(1, "Administrador de Unidade"), 
	USUARIO_PORTAL(2, "Colaborador");
	
	private Integer id;
	private String nome;
	
	private TipoUsuarioEnum(Integer id, String nome) {
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
	
	public static TipoUsuarioEnum recupera(Integer id) {
		if(id == ADMINISTRADOR.getId()) return ADMINISTRADOR;
		if(id == USUARIO_PORTAL.getId()) return USUARIO_PORTAL;
		return null;
	}
}