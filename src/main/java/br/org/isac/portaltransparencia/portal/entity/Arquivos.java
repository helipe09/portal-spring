package br.org.isac.portaltransparencia.portal.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "pt_arquivo")
public class Arquivos {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Lob
    @Column(name="arquivo")
	private byte[] arquivo;
	
	@Column(name="tsRegistro")
	private Timestamp tsRegistro;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="nomeExibicao")
	private String nomeExibicao;
	
	@Size(min=1, max=2000, message="A descrição do arquivo não pode ter menos de 1 e mais que 2.000 caracteres.")
	@Column(name="descricao")
	private String descricao;
	
	@Column(name="idUsuario")
	private Integer idUsuario;
	
	@Column(name="tipoArquivo")
	private Integer tipo;
	
	@Column(name="tamanhoArquivo")
	private String tamanho;
	
	@Column(name="mimetypeArquivo")
	private String mimetype;
	
	@Column(name="idUnidade")
	private Integer idUnidade;
	
	@Column(name="idContratante")
	private Integer idContratante;
	
	@Column(name="idEstadoAtual")
	private Integer idEstadoAtual;
	
	@Column(name="dataProtocolo")
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	@Temporal(TemporalType.DATE)
	private Date dataProtocolo;
	
	@Column(name="mesReferencia")
	private Integer mesReferencia;
	
	@Column(name="anoReferencia")
	private Integer anoReferencia;
	
	@Column(name="visibilidade")
	private String visibilidade;
	
	@Transient
	private String dataProtocoloFormatada;
	
	@Transient
	private String nomeEstadoAtual;
	
	@Transient
	private Unidade unidade;
	
	@Transient
	private TipoDocumento tipoDocumento;
	
	public Arquivos() {
		
	}

	public Arquivos(Integer id, String nome, String nomeExibicao, String descricao,
			Integer idUsuario, Integer tipo, String tamanho, String mimetype, Integer idUnidade, Integer idContratante,
			Integer idEstadoAtual, Date dataProtocolo, Integer mesReferencia, Integer anoReferencia, String visibilidade) {
		super();
		this.id = id;
		//this.tsRegistro = tsRegistro;
		this.nome = nome;
		this.nomeExibicao = nomeExibicao;
		this.descricao = descricao;
		this.idUsuario = idUsuario;
		this.tipo = tipo;
		this.tamanho = tamanho;
		this.mimetype = mimetype;
		this.idUnidade = idUnidade;
		this.idContratante = idContratante;
		this.idEstadoAtual = idEstadoAtual;
		this.dataProtocolo = dataProtocolo;
		this.anoReferencia = anoReferencia;
		this.mesReferencia =  mesReferencia;
		this.visibilidade = visibilidade;
	}
	
	public String getVisibilidade() {
		return visibilidade;
	}

	public void setVisibilidade(String visibilidade) {
		this.visibilidade = visibilidade;
	}

	public Integer getMesReferencia() {
		return mesReferencia;
	}

	public void setMesReferencia(Integer mesReferencia) {
		this.mesReferencia = mesReferencia;
	}

	public Integer getAnoReferencia() {
		return anoReferencia;
	}

	public void setAnoReferencia(Integer anoReferencia) {
		this.anoReferencia = anoReferencia;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public byte[] getArquivo() {
		return arquivo;
	}

	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}

	public Timestamp getTsRegistro() {
		return tsRegistro;
	}

	public void setTsRegistro(Timestamp tsRegistro) {
		this.tsRegistro = tsRegistro;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeExibicao() {
		return nomeExibicao;
	}

	public void setNomeExibicao(String nomeExibicao) {
		this.nomeExibicao = nomeExibicao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getTamanho() {
		return tamanho;
	}

	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public Integer getIdUnidade() {
		return idUnidade;
	}

	public void setIdUnidade(Integer idUnidade) {
		this.idUnidade = idUnidade;
	}

	public Integer getIdContratante() {
		return idContratante;
	}

	public void setIdContratante(Integer idContratante) {
		this.idContratante = idContratante;
	}

	public Integer getIdEstadoAtual() {
		return idEstadoAtual;
	}

	public void setIdEstadoAtual(Integer idEstadoAtual) {
		this.idEstadoAtual = idEstadoAtual;
	}

	public Date getDataProtocolo() {
		return dataProtocolo;
	}

	public void setDataProtocolo(Date dataProtocolo) {
		this.dataProtocolo = dataProtocolo;
	}

	public String getDataProtocoloFormatada() {
		return dataProtocoloFormatada;
	}

	public void setDataProtocoloFormatada(String dataProtocoloFormatada) {
		this.dataProtocoloFormatada = dataProtocoloFormatada;
	}

	public String getNomeEstadoAtual() {
		return nomeEstadoAtual;
	}

	public void setNomeEstadoAtual(String nomeEstadoAtual) {
		this.nomeEstadoAtual = nomeEstadoAtual;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
}