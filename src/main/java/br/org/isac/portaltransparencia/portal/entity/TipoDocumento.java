package br.org.isac.portaltransparencia.portal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "pt_tipo_documento")
public class TipoDocumento {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="nome", nullable = false)
	private String nome;
	
	@Column(name="ativo", nullable = false)
	private String ativo;
	
	@Column(name="grupo")
	private Integer grupo;
	
	@Column(name="periodicidade")
	private Integer periodicidade;
	
	@Column(name="prazoGuardaAnos")
	private Integer prazoGuardaAnos;
	
	@Column(name="localAplicacao")
	private String localAplicacao;
	
	@Transient
	private String nomeGrupo;
	
	@Transient
	private String nomeSelect;
	
	public String getNomeSelect() {
		return nomeSelect;
	}

	public void setNomeSelect(String nomeSelect) {
		this.nomeSelect = nomeSelect;
	}

	public String getNomeGrupo() {
		return nomeGrupo;
	}

	public void setNomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
	}

	public Integer getGrupo() {
		return grupo;
	}

	public void setGrupo(Integer grupo) {
		this.grupo = grupo;
	}

	public Integer getPeriodicidade() {
		return periodicidade;
	}

	public void setPeriodicidade(Integer periodicidade) {
		this.periodicidade = periodicidade;
	}

	public Integer getPrazoGuardaAnos() {
		return prazoGuardaAnos;
	}

	public void setPrazoGuardaAnos(Integer prazoGuardaAnos) {
		this.prazoGuardaAnos = prazoGuardaAnos;
	}

	public String getLocalAplicacao() {
		return localAplicacao;
	}

	public void setLocalAplicacao(String localAplicacao) {
		this.localAplicacao = localAplicacao;
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

	public String getAtivo() {
		return ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}
}
