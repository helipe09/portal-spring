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
@Table(name = "pt_usuario")
public class Usuario {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "cpf")
	private String cpf;
	
	@Column(name = "senha")
	private String senha;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "tsRegistro")
	private Timestamp tsRegistro;
	
	@Column(name = "tipoUsuario")
	private Integer tipoUsuario;
	
	@Column(name = "curriculo")
	private String curriculo;
	
	@Column(name = "idFuncaoAtual")
	private Integer idFuncaoAtual;
	
	@Column(name = "idUnidadeAtual")
	private Integer idUnidadeAtual;
	
	@Column(name = "idEstadoAtual")
	private Integer idEstadoAtual;
	
	@Column(name = "criptografia")
	private String criptografia;
	
	@Column(name = "idArquivoImagem")
	private Integer idArquivoImagem;
	
	@Column(name = "tsRegistroConcordanciaUsoImagem")
	private Timestamp tsRegistroConcordanciaUsoImagem;
	
	@Transient
	private Unidade unidade;
	
	@Transient
	private TipoEstadoUsuarioEnum tipoEstadoAtual;
	
	@Transient
	private TipoUsuarioEnum tipoUsuarioAtual;
	
	@Transient TipoFuncaoUsuarioEnum funcao;
	
	@Transient
	private String cnpjUnidade;
	
	@Transient
	private String senhaAtual;
	
	@Transient
	private String senhaNova;
	
	@Transient
	private String senhaNovaRepete;
	
	@Transient
	private String cpfFormatado;
	
	public Usuario() {
		super();
	}

	public Usuario(Integer id,String nome,String cpf, Integer idUnidadeAtual, Integer idFuncaoAtual) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.idUnidadeAtual = idUnidadeAtual;
		this.idFuncaoAtual = idFuncaoAtual;
	}
	
	public Integer getIdArquivoImagem() {
		return idArquivoImagem;
	}

	public void setIdArquivoImagem(Integer idArquivoImagem) {
		this.idArquivoImagem = idArquivoImagem;
	}

	public Timestamp getTsRegistroConcordanciaUsoImagem() {
		return tsRegistroConcordanciaUsoImagem;
	}

	public void setTsRegistroConcordanciaUsoImagem(Timestamp tsRegistroConcordanciaUsoImagem) {
		this.tsRegistroConcordanciaUsoImagem = tsRegistroConcordanciaUsoImagem;
	}

	public String getCriptografia() {
		return criptografia;
	}

	public void setCriptografia(String criptografia) {
		this.criptografia = criptografia;
	}

	public String getCnpjUnidade() {
		return cnpjUnidade;
	}

	public void setCnpjUnidade(String cnpjUnidade) {
		this.cnpjUnidade = cnpjUnidade;
	}

	public String getCpfFormatado() {
		return cpfFormatado;
	}

	public void setCpfFormatado(String cpfFormatado) {
		this.cpfFormatado = cpfFormatado;
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public String getSenhaNova() {
		return senhaNova;
	}

	public void setSenhaNova(String senhaNova) {
		this.senhaNova = senhaNova;
	}

	public String getSenhaNovaRepete() {
		return senhaNovaRepete;
	}

	public void setSenhaNovaRepete(String senhaNovaRepete) {
		this.senhaNovaRepete = senhaNovaRepete;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public TipoEstadoUsuarioEnum getTipoEstadoAtual() {
		return tipoEstadoAtual;
	}

	public void setTipoEstadoAtual(TipoEstadoUsuarioEnum tipoEstadoAtual) {
		this.tipoEstadoAtual = tipoEstadoAtual;
	}

	public TipoUsuarioEnum getTipoUsuarioAtual() {
		return tipoUsuarioAtual;
	}

	public void setTipoUsuarioAtual(TipoUsuarioEnum tipoUsuarioAtual) {
		this.tipoUsuarioAtual = tipoUsuarioAtual;
	}

	public TipoFuncaoUsuarioEnum getFuncao() {
		return funcao;
	}

	public void setFuncao(TipoFuncaoUsuarioEnum funcao) {
		this.funcao = funcao;
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getTsRegistro() {
		return tsRegistro;
	}

	public void setTsRegistro(Timestamp tsRegistro) {
		this.tsRegistro = tsRegistro;
	}

	public Integer getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(Integer tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public String getCurriculo() {
		return curriculo;
	}

	public void setCurriculo(String curriculo) {
		this.curriculo = curriculo;
	}

	public Integer getIdFuncaoAtual() {
		return idFuncaoAtual;
	}

	public void setIdFuncaoAtual(Integer idFuncaoAtual) {
		this.idFuncaoAtual = idFuncaoAtual;
	}

	public Integer getIdUnidadeAtual() {
		return idUnidadeAtual;
	}

	public void setIdUnidadeAtual(Integer idUnidadeAtual) {
		this.idUnidadeAtual = idUnidadeAtual;
	}

	public Integer getIdEstadoAtual() {
		return idEstadoAtual;
	}

	public void setIdEstadoAtual(Integer idEstadoAtual) {
		this.idEstadoAtual = idEstadoAtual;
	}
}