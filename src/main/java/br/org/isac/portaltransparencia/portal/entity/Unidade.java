package br.org.isac.portaltransparencia.portal.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "pt_unidade")
public class Unidade {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "nome")
	@NotNull(message="O nome da unidade não pode ser nula.")
	@NotBlank(message="O nome da unidade não pode ficar sem preenchimento.")
	@Size(min=3, max=100, message="O nome da unidade não pode ter menos de 3 e mais que 100 caracteres.")
	private String nome;
	
	@Column(name = "endereco")
	@NotNull(message="O endereço da unidade não pode ser nula.")
	@NotBlank(message="O endereço da unidade não pode ficar sem preenchimento.")
	@Size(min=3, max=500, message="O endereço da unidade não pode ter menos de 3 e mais que 500 caracteres.")
	private String endereco;
	
	@Column(name = "cep")
	private String cep;
	
	@Column(name = "uf")
	private String uf;
	
	@Column(name = "cidade")
	private String cidade;
	
	@Column(name = "telefone")
	private String telefone;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "codigoCnes")
	private String codigoCnes;
	
	@Column(name = "trataCovid")
	private Integer trataCovid;
	
	@Column(name = "tsRegistro")
	private Timestamp tsRegistro;
	
	@Column(name = "tipoUnidade")
	private Integer tipoUnidade;
	
	@Column(name = "idEstadoAtual")
	private Integer idEstadoAtual;
	
	@Column(name = "resumo")
	private String resumo;
	
	@Column(name = "urlLogo")
	private String urlLogo;
	
	@Column(name = "cnpj")
	private String cnpj;
	
	@Column(name = "tipoHierarquia")
	private String tipoHierarquia;
	
	@Column(name = "idArquivoImagem")
	private Integer idArquivoImagem;
	
	@Column(name = "idArquivoOrganograma")
	private Integer idArquivoOrganograma;
	
	@Column(name = "idContratante")
	private Integer idContratante;
	
	@Column(name = "urlFacebook")
	private String urlFacebook;
	
	@Column(name = "urlInstagram")
	private String urlInstagram;
	
	@Column(name = "urlSite")
	private String urlSite;
	
	@Transient
	private TipoEstadoUnidadeEnum tipoEstadoAtual;
	
	@Transient 
	private TipoUnidade tipoUnidadeObjeto;
	
	@Transient
	private Integer idImagem;
	
	@Transient
	private String cnpjFormatado;
	
	@Transient
	private String cepFormatado;
	
	public String getCepFormatado() {
		return cepFormatado;
	}

	public void setCepFormatado(String cepFormatado) {
		this.cepFormatado = cepFormatado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUrlFacebook() {
		return urlFacebook;
	}

	public void setUrlFacebook(String urlFacebook) {
		this.urlFacebook = urlFacebook;
	}

	public String getUrlInstagram() {
		return urlInstagram;
	}

	public void setUrlInstagram(String urlInstagram) {
		this.urlInstagram = urlInstagram;
	}

	public String getUrlSite() {
		return urlSite;
	}

	public void setUrlSite(String urlSite) {
		this.urlSite = urlSite;
	}

	public Integer getIdContratante() {
		return idContratante;
	}

	public void setIdContratante(Integer idContratante) {
		this.idContratante = idContratante;
	}

	public Integer getIdArquivoImagem() {
		return idArquivoImagem;
	}

	public void setIdArquivoImagem(Integer idArquivoImagem) {
		this.idArquivoImagem = idArquivoImagem;
	}

	public Integer getIdArquivoOrganograma() {
		return idArquivoOrganograma;
	}

	public void setIdArquivoOrganograma(Integer idArquivoOrganograma) {
		this.idArquivoOrganograma = idArquivoOrganograma;
	}

	public String getTipoHierarquia() {
		return tipoHierarquia;
	}

	public void setTipoHierarquia(String tipoHierarquia) {
		this.tipoHierarquia = tipoHierarquia;
	}
	
	public String getCnpjFormatado() {
		return cnpjFormatado;
	}

	public void setCnpjFormatado(String cnpjFormatado) {
		this.cnpjFormatado = cnpjFormatado;
	}

	public Integer getIdImagem() {
		return idImagem;
	}

	public void setIdImagem(Integer idImagem) {
		this.idImagem = idImagem;
	}

	public String getResumo() {
		return resumo;
	}

	public void setResumo(String resumo) {
		this.resumo = resumo;
	}

	public String getUrlLogo() {
		return urlLogo;
	}

	public void setUrlLogo(String urlLogo) {
		this.urlLogo = urlLogo;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public TipoEstadoUnidadeEnum getTipoEstadoAtual() {
		return tipoEstadoAtual;
	}

	public void setTipoEstadoAtual(TipoEstadoUnidadeEnum tipoEstadoAtual) {
		this.tipoEstadoAtual = tipoEstadoAtual;
	}

	public TipoUnidade getTipoUnidadeObjeto() {
		return tipoUnidadeObjeto;
	}

	public void setTipoUnidadeObjeto(TipoUnidade tipoUnidadeObjeto) {
		this.tipoUnidadeObjeto = tipoUnidadeObjeto;
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

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCodigoCnes() {
		return codigoCnes;
	}

	public void setCodigoCnes(String codigoCnes) {
		this.codigoCnes = codigoCnes;
	}

	public Integer getTrataCovid() {
		return trataCovid;
	}

	public void setTrataCovid(Integer trataCovid) {
		this.trataCovid = trataCovid;
	}

	public Timestamp getTsRegistro() {
		return tsRegistro;
	}

	public void setTsRegistro(Timestamp tsRegistro) {
		this.tsRegistro = tsRegistro;
	}

	public Integer getTipoUnidade() {
		return tipoUnidade;
	}

	public void setTipoUnidade(Integer tipoUnidade) {
		this.tipoUnidade = tipoUnidade;
	}

	public Integer getIdEstadoAtual() {
		return idEstadoAtual;
	}

	public void setIdEstadoAtual(Integer idEstadoAtual) {
		this.idEstadoAtual = idEstadoAtual;
	}
}