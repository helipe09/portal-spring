package br.org.isac.portaltransparencia.portal.entity;

public enum TipoLogEnum {
	
	ACESSO(1, "Acesso à Área Restrita"), 
	USUARIO(2, "Usuário"), 
	DOCUMENTO(3, "Documentos"),
	UNIDADE(4, "Unidades"),
	TIPO_DOCUMENTO(5, "Tipos de Documentos"), 
	LOG(6, "Acesso aos logs do sistema"), 
	GRUPO_TIPO(7, "Grupos de Tipos de Documentos"),
	CONTRATANTE(8, "Contratantes"), 
	TIPO_UNIDADE(9, "Tipos de Unidades") ;
	
	private Integer id;
	private String nome;
	
	private TipoLogEnum(Integer id, String nome) {
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
	
	public static TipoLogEnum recupera(Integer id) {
		if(id == ACESSO.getId()) return ACESSO;
		if(id == USUARIO.getId()) return USUARIO;
		if(id == DOCUMENTO.getId()) return DOCUMENTO;
		if(id == UNIDADE.getId()) return UNIDADE;
		if(id == TIPO_DOCUMENTO.getId()) return TIPO_DOCUMENTO;
		if(id == LOG.getId()) return LOG;
		if(id == GRUPO_TIPO.getId()) return GRUPO_TIPO;
		if(id == CONTRATANTE.getId()) return CONTRATANTE;
		if(id == TIPO_UNIDADE.getId()) return TIPO_UNIDADE;
		return null;
	}
}
