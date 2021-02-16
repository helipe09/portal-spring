package br.org.isac.portaltransparencia.portal.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

@Entity
@Table(name = "pt_estado_arquivo")
public class EstadoArquivo {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="idEstado")
	private Integer idEstado;
	
	@Column(name="idArquivo")
	private Integer idArquivo;
	
	@Column(name="idUsuarioGravador")
	private Integer idUsuarioGravador;
	
	@Column(name = "tsInicio")
	private Timestamp tsInicio;
	
	@Column(name = "tsFim")
	private Timestamp tsFim;
	
	@Size(min=0, max=2000, message="A justificativa não pode ter mais que 2.000 caracteres.")
	@Column(name = "observacao")
	private String observacao;
	
	@Transient
	private Usuario usuarioGravador;
	
	@Transient
	private String nomeEstado;
	
	public String getNomeEstado() {
		return nomeEstado;
	}

	public void setNomeEstado(String nomeEstado) {
		this.nomeEstado = nomeEstado;
	}

	public Usuario getUsuarioGravador() {
		return usuarioGravador;
	}

	public void setUsuarioGravador(Usuario usuarioGravador) {
		this.usuarioGravador = usuarioGravador;
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

	public Integer getIdArquivo() {
		return idArquivo;
	}

	public void setIdArquivo(Integer idArquivo) {
		this.idArquivo = idArquivo;
	}

	public Integer getIdUsuarioGravador() {
		return idUsuarioGravador;
	}

	public void setIdUsuarioGravador(Integer idUsuarioGravador) {
		this.idUsuarioGravador = idUsuarioGravador;
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

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
}