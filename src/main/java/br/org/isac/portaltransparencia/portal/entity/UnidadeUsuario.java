package br.org.isac.portaltransparencia.portal.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "pt_unidade_usuario")
public class UnidadeUsuario {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="idUnidade")
	private Integer idUnidade;
	
	@Column(name="idUsuario")
	private Integer idUsuario;
	
	@Column(name="idUsuarioGravador")
	private Integer idUsuarioGravador;
	
	@Column(name = "tsInicio")
	private Timestamp tsInicio;
	
	@Column(name = "tsFim")
	private Timestamp tsFim;
	
	@Column(name = "observacao")
	private String observacao;
	
	@Transient
	private String nomeUnidade;
	
	public String getNomeUnidade() {
		return nomeUnidade;
	}

	public void setNomeUnidade(String nomeUnidade) {
		this.nomeUnidade = nomeUnidade;
	}

	public Integer getIdUsuarioGravador() {
		return idUsuarioGravador;
	}

	public void setIdUsuarioGravador(Integer idUsuarioGravador) {
		this.idUsuarioGravador = idUsuarioGravador;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdUnidade() {
		return idUnidade;
	}

	public void setIdUnidade(Integer idUnidade) {
		this.idUnidade = idUnidade;
	}

	public Timestamp getTsInicio() {
		return tsInicio;
	}

	public void setTsInicio(Timestamp tsInicio) {
		this.tsInicio = tsInicio;
	}

	public Timestamp getTsFim() {
		return tsFim;
	}

	public void setTsFim(Timestamp tsFim) {
		this.tsFim = tsFim;
	}
}