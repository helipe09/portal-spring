package br.org.isac.portaltransparencia.portal.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pt_contratantes")
public class Contratantes {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="nome", nullable = false)
	private String nome;
	
	@Column(name = "cnpj")
	private String cnpj;
	
	@Column(name="ativo", nullable = false)
	private String ativo;
	
	@Column(name = "idArquivoImagem")
	private Integer idArquivoImagem;
	
	@Column(name = "tsRegistro")
	private Timestamp tsRegistro;
	
	@Column(name = "uf")
	private String uf;
	
	@Column(name = "urlSite")
	private String urlSite;
	
	public String getUrlSite() {
		return urlSite;
	}

	public void setUrlSite(String urlSite) {
		this.urlSite = urlSite;
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

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getAtivo() {
		return ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

	public Integer getIdArquivoImagem() {
		return idArquivoImagem;
	}

	public void setIdArquivoImagem(Integer idArquivoImagem) {
		this.idArquivoImagem = idArquivoImagem;
	}

	public Timestamp getTsRegistro() {
		return tsRegistro;
	}

	public void setTsRegistro(Timestamp tsRegistro) {
		this.tsRegistro = tsRegistro;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}
}