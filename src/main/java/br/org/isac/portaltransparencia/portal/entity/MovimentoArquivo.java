package br.org.isac.portaltransparencia.portal.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pt_movimento_arquivo")
public class MovimentoArquivo {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="idTipoMovimento")
	private Integer idTipoMovimento;
	
	@Column(name="idArquivo")
	private Integer idArquivo;
	
	@Column(name="idUsuarioGravador")
	private Integer idUsuarioGravador;
	
	@Column(name = "tsInicio")
	private Timestamp tsInicio;
	
	@Column(name = "ip")
	private String ip;
	
	@Column(name = "observacao")
	private String observacao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdTipoMovimento() {
		return idTipoMovimento;
	}

	public void setIdTipoMovimento(Integer idTipoMovimento) {
		this.idTipoMovimento = idTipoMovimento;
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
}