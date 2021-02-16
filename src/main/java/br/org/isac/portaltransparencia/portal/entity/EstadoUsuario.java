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
@Table(name = "pt_estado_usuario")
public class EstadoUsuario {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="idEstado")
	private Integer idEstado;
	
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
	private Usuario usuGravador;
	
	@Transient
	private String nomeEstado;
	
	public Usuario getUsuGravador() {
		return usuGravador;
	}

	public void setUsuGravador(Usuario usuGravador) {
		this.usuGravador = usuGravador;
	}

	public String getNomeEstado() {
		return nomeEstado;
	}

	public void setNomeEstado(String nomeEstado) {
		this.nomeEstado = nomeEstado;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
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
