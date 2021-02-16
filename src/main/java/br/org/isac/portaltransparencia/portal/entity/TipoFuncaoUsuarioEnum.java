package br.org.isac.portaltransparencia.portal.entity;

public enum TipoFuncaoUsuarioEnum {
	
	PRESIDENTE(1, "Presidente"), 
	DIRETOR(2, "Diretor"), 
	SUPERINTENDENTE(3, "Superintendente"), 
	GERENTE(4, "Gerente"), 
	COORDENADOR(5, "Coordenador"), 
	ASSESSOR(6, "Assessor"), 
	COMPLIANCE(7, "Compliance"); 
	
	private Integer id;
	private String nome;
	
	private TipoFuncaoUsuarioEnum(Integer id, String nome) {
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
	
	public static TipoFuncaoUsuarioEnum recupera(Integer id) {
		if(id == PRESIDENTE.getId()) return PRESIDENTE;
		if(id == DIRETOR.getId()) return DIRETOR;
		if(id == SUPERINTENDENTE.getId()) return SUPERINTENDENTE;
		if(id == GERENTE.getId()) return GERENTE;
		if(id == COORDENADOR.getId()) return COORDENADOR;
		if(id == ASSESSOR.getId()) return ASSESSOR;
		if(id == COMPLIANCE.getId()) return COMPLIANCE;
		return null;
	}
}