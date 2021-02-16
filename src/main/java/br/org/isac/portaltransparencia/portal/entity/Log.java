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
@Table(name = "pt_log")
public class Log {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "idRegistro")
	private Integer idRegistro;
	
	@Column(name = "tipoRegistro")
	private Integer tipoRegistro;
	
	@Column(name = "idUsuario")
	private Integer idUsuario;
	
	@Column(name = "dataHora")
	private Timestamp dataHora;
	
	@Column(name = "observacao")
	@Size(max=1000)
	private String observacao;
	
	@Column(name = "tipoLog")
	@Size(max=20)
	private String tipoLog;

	@Column(name = "ip")
	@Size(max=20)
	private String ip;
	
	@Column(name = "secao")
	@Size(max=100)
	private String secao;
	
	@Transient
	private Usuario responsavel;
	
	public Log() {
		
	}
	
	public Log(Integer idRegistro, Integer tipoRegistro, Integer idUsuario, Timestamp dataHora,
			@Size(max = 1000) String observacao, @Size(max = 20) String tipoLog, @Size(max = 15) String ip,
			@Size(max = 20) String secao) {
		super();
		this.idRegistro = idRegistro;
		this.tipoRegistro = tipoRegistro;
		this.idUsuario = idUsuario;
		this.dataHora = dataHora;
		this.observacao = observacao;
		this.tipoLog = tipoLog;
		this.ip = ip;
		this.secao = secao;
	}
	
	public Usuario getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Usuario responsavel) {
		this.responsavel = responsavel;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdRegistro() {
		return idRegistro;
	}

	public void setIdRegistro(Integer idRegistro) {
		this.idRegistro = idRegistro;
	}

	public Integer getTipoRegistro() {
		return tipoRegistro;
	}

	public void setTipoRegistro(Integer tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Timestamp getDataHora() {
		return dataHora;
	}

	public void setDataHora(Timestamp dataHora) {
		this.dataHora = dataHora;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getTipoLog() {
		return tipoLog;
	}

	public void setTipoLog(String tipoLog) {
		this.tipoLog = tipoLog;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getSecao() {
		return secao;
	}

	public void setSecao(String secao) {
		this.secao = secao;
	}
}
