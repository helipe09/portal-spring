package br.org.isac.portaltransparencia.portal.entity;

public enum TipoPeriodicidadeArquivoEnum {
	
	MENSAL(1, "Mensal"), 
	BIMESTRAL(2, "Bimestral"), 
	TRIMESTRAL(3, "Trimestral"), 
	SEMESTRAL(4, "Semestral"), 
	ANUAL(5, "Anual"), 
	INDETERMINADA(6, "Indeterminada"); 
	
	private Integer id;
	private String nome;
	
	private TipoPeriodicidadeArquivoEnum(Integer id, String nome) {
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
	
	public static TipoPeriodicidadeArquivoEnum recupera(Integer id) {
		if(id == MENSAL.getId()) return MENSAL;
		if(id == BIMESTRAL.getId()) return BIMESTRAL;
		if(id == TRIMESTRAL.getId()) return TRIMESTRAL;
		if(id == SEMESTRAL.getId()) return SEMESTRAL;
		if(id == ANUAL.getId()) return ANUAL;
		if(id == INDETERMINADA.getId()) return INDETERMINADA;
		return null;
	}
}