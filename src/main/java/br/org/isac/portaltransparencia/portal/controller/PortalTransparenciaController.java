package br.org.isac.portaltransparencia.portal.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.org.isac.portaltransparencia.portal.entity.Arquivos;
import br.org.isac.portaltransparencia.portal.entity.CheckboxesUsuario;
import br.org.isac.portaltransparencia.portal.entity.Contratantes;
import br.org.isac.portaltransparencia.portal.entity.EstadoArquivo;
import br.org.isac.portaltransparencia.portal.entity.EstadoUnidade;
import br.org.isac.portaltransparencia.portal.entity.EstadoUsuario;
import br.org.isac.portaltransparencia.portal.entity.FuncaoUsuario;
import br.org.isac.portaltransparencia.portal.entity.GrupoTipoDocumento;
import br.org.isac.portaltransparencia.portal.entity.Log;
import br.org.isac.portaltransparencia.portal.entity.Mensagem;
import br.org.isac.portaltransparencia.portal.entity.MovimentoArquivo;
import br.org.isac.portaltransparencia.portal.entity.Objeto;
import br.org.isac.portaltransparencia.portal.entity.TipoDocumento;
import br.org.isac.portaltransparencia.portal.entity.TipoEstadoArquivoEnum;
import br.org.isac.portaltransparencia.portal.entity.TipoEstadoUnidadeEnum;
import br.org.isac.portaltransparencia.portal.entity.TipoEstadoUsuarioEnum;
import br.org.isac.portaltransparencia.portal.entity.TipoFuncaoUsuarioEnum;
import br.org.isac.portaltransparencia.portal.entity.TipoLogEnum;
import br.org.isac.portaltransparencia.portal.entity.TipoMovimentoArquivoEnum;
import br.org.isac.portaltransparencia.portal.entity.TipoUnidade;
import br.org.isac.portaltransparencia.portal.entity.TipoUsuarioEnum;
import br.org.isac.portaltransparencia.portal.entity.Unidade;
import br.org.isac.portaltransparencia.portal.entity.UnidadeArquivo;
import br.org.isac.portaltransparencia.portal.entity.UnidadeUsuario;
import br.org.isac.portaltransparencia.portal.entity.UnidadesFederacaoEnum;
import br.org.isac.portaltransparencia.portal.entity.Usuario;
import br.org.isac.portaltransparencia.portal.repository.ArquivoRepository;
import br.org.isac.portaltransparencia.portal.repository.ContratantesRepository;
import br.org.isac.portaltransparencia.portal.repository.EstadoArquivoRepository;
import br.org.isac.portaltransparencia.portal.repository.EstadoUnidadeRepository;
import br.org.isac.portaltransparencia.portal.repository.EstadoUsuarioRepository;
import br.org.isac.portaltransparencia.portal.repository.FuncaoUsuarioRepository;
import br.org.isac.portaltransparencia.portal.repository.GrupoTipoDocumentoRepository;
import br.org.isac.portaltransparencia.portal.repository.LogRepository;
import br.org.isac.portaltransparencia.portal.repository.MovimentoArquivoRepository;
import br.org.isac.portaltransparencia.portal.repository.TipoDocumentoRepository;
import br.org.isac.portaltransparencia.portal.repository.TipoUnidadeRepository;
import br.org.isac.portaltransparencia.portal.repository.UnidadeArquivoRepository;
import br.org.isac.portaltransparencia.portal.repository.UnidadeRepository;
import br.org.isac.portaltransparencia.portal.repository.UnidadeUsuarioRepository;
import br.org.isac.portaltransparencia.portal.repository.UsuarioRepository;
import br.org.isac.portaltransparencia.portal.rules.ArquivoRules;
import br.org.isac.portaltransparencia.portal.rules.ContratanteRules;
import br.org.isac.portaltransparencia.portal.rules.GrupoTipoDocumentoRules;
import br.org.isac.portaltransparencia.portal.rules.TipoDocumentoRules;
import br.org.isac.portaltransparencia.portal.rules.TipoUnidadeRules;
import br.org.isac.portaltransparencia.portal.rules.UnidadeRules;
import br.org.isac.portaltransparencia.portal.rules.UsuarioRules;
import br.org.isac.portaltransparencia.portal.service.GerenciadorDocumentosService;
import br.org.isac.portaltransparencia.portal.util.Conversor;
import br.org.isac.portaltransparencia.portal.util.Criptografia;
import br.org.isac.portaltransparencia.portal.util.Email;
import br.org.isac.portaltransparencia.portal.util.GeradorSenha;
import br.org.isac.portaltransparencia.portal.util.ParametrosIsacSede;
import br.org.isac.portaltransparencia.portal.util.Validador;

@RestController
@RequestMapping("")
public class PortalTransparenciaController {

	@Autowired
	UsuarioRepository usuarioRepo;

	@Autowired
	UnidadeUsuarioRepository unidadeUsuarioRepo;

	@Autowired
	FuncaoUsuarioRepository funcaoUsuarioRepo;

	@Autowired
	EstadoUsuarioRepository estadoUsuarioRepo;

	@Autowired
	UnidadeRepository unidadeRepo;

	@Autowired
	EstadoUnidadeRepository estadoUnidadeRepo;

	@Autowired
	LogRepository logRepo;

	@Autowired
	ArquivoRepository arquivoRepo;

	@Autowired
	EstadoArquivoRepository estadoArquivoRepo;

	@Autowired
	MovimentoArquivoRepository movimentoArquivoRepo;

	@Autowired
	UnidadeArquivoRepository unidadeArquivoRepo;

	@Autowired
	ArquivoRules arquivoRules;

	@Autowired
	UsuarioRules usuarioRules;

	@Autowired
	UnidadeRules unidadeRules;

	@Autowired
	TipoDocumentoRules tipoDocRules;
	
	@Autowired
	GrupoTipoDocumentoRules grupoTipoDocRules;
	
	@Autowired
	TipoUnidadeRules tipoUnidadeRules;

	@Autowired
	GerenciadorDocumentosService gedService;

	@Autowired
	TipoDocumentoRepository tipoDocRepo;
	
	@Autowired
	GrupoTipoDocumentoRepository grupoTipoRepo;
	
	@Autowired
	ContratantesRepository contratantesRepo;
	
	@Autowired
	ContratanteRules contratanteRules;
	
	@Autowired
	TipoUnidadeRepository tipoUnidadeRepo;
	
	@RequestMapping(value = "/abrirIncluirDocsLegado", method = RequestMethod.GET) 
	public ModelAndView abrirIncluirDocsLegado(ModelMap model, HttpSession session) { 

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(null!= usuSessao) {

			gravarLog(new Log(usuSessao.getId(), TipoLogEnum.DOCUMENTO.getId(), usuSessao.getId(), currentTimestamp(), 
					"Página de Inclusão (upload) de arquivos do Portal Antigo", "DOCUMENTO", getIp(), "abrirIncluirDocsLegado"));

			List<Unidade> unidades = unidadeRepo.findAll();
			model.addAttribute("unidades", unidades);
			
			List<TipoDocumento> tipos = tipoDocRepo.findAll();
			model.addAttribute("tipos", tipos);
			
			model.addAttribute("arquivo", new Arquivos());
			
			model.addAttribute("tituloPagina", "Incluir Documentos do Portal Antigo");
			return new ModelAndView("uploadLegado", model);

		}else {
			return preparaRetornoLogin("",model);
		}
	}
	
	@PostMapping("/salvarDocumentosLegado")
	public ModelAndView salvarDocumentosLegado(@RequestParam("files") MultipartFile[] files, @Valid @ModelAttribute("arquivo") Arquivos arquivo, ModelMap model, HttpSession session) {

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(null != usuSessao) {
			
			Integer idUnidade = arquivo.getIdUnidade();
			Integer tipo = arquivo.getTipo();

			try {
				for(MultipartFile file: files) {
					
					if(file.getSize() > 26214000) {
						
						List<Unidade> unidades = unidadeRepo.findAll();
						model.addAttribute("unidades", unidades);
						
						List<TipoDocumento> tipos = tipoDocRepo.findAll();
						model.addAttribute("tipos", tipos);
						
						model.addAttribute("arquivo", new Arquivos());
						
						model.addAttribute("mensagemErro", "Desculpe, ocorreu em erro em algum dos arquivos. Verifique se o tamanho de algum arquivo é maior que 24MB. Observe, caso tenha encaminhado mais de um arquivo, se outros (menores que 24MB) foram salvos.");
						
						model.addAttribute("tituloPagina", "Incluir Documentos do Portal Antigo");
						return new ModelAndView("uploadLegado", model);
						
					}
					
					Arquivos arq = new Arquivos();
					arq.setIdEstadoAtual(TipoEstadoArquivoEnum.PUBLICADO.getId());
					arq.setDataProtocolo(new Date());
					arq.setDescricao(file.getOriginalFilename() + ". Documento extraído do Portal de Transparência para inserção em nova base de dados.");
					arq.setIdUnidade(idUnidade);
					arq.setIdUsuario(usuSessao.getId());
					arq.setTipo(tipo);
					arq.setArquivo(file.getBytes());
					arq.setMimetype(file.getContentType());
					arq.setNome(file.getOriginalFilename());
					arq.setNomeExibicao(file.getOriginalFilename());

					double tam = file.getSize();
					tam = (tam/1024)/1024;
					arq.setTamanho(String.format("%.2f", tam)+ " MB");
					arq.setTsRegistro(currentTimestamp());
					arq.setIdUsuario(usuSessao.getId());
					arq.setVisibilidade("P");

					arquivoRepo.save(arq);

					EstadoArquivo estArq = new EstadoArquivo();
					estArq.setIdArquivo(arq.getId());
					estArq.setIdEstado(TipoEstadoArquivoEnum.PUBLICADO.getId());
					estArq.setIdUsuarioGravador(usuSessao.getId());
					estArq.setTsInicio(currentTimestamp());
					estArq.setObservacao("Documento incluído na situação "+TipoEstadoArquivoEnum.recupera(TipoEstadoArquivoEnum.PUBLICADO.getId()).getNome()+ 
							". Documento publicado no Portal da Transparência - anterior a 01.02.2021.  Inclusão para manutenção do legado de documentos do Portal de Transparência Antigo ");

					estadoArquivoRepo.save(estArq);
				}

				model.addAttribute("mensagemSucesso", "Documento(s) incluído(s) com sucesso. ");
				gravarLog(new Log(usuSessao.getId(), TipoLogEnum.DOCUMENTO.getId(), usuSessao.getId(), currentTimestamp(), 
						"Página de Inclusão (upload) de arquivos do Portal Antigo. Documentos Incluídos", "DOCUMENTO", getIp(), "salvarDocumentosLegado"));

				List<Unidade> unidades = unidadeRepo.findAll();
				model.addAttribute("unidades", unidades);
				
				List<TipoDocumento> tipos = tipoDocRepo.findAll();
				model.addAttribute("tipos", tipos);
				
				model.addAttribute("arquivo", new Arquivos());
				
				model.addAttribute("tituloPagina", "Incluir Documentos do Portal Antigo");
				return new ModelAndView("uploadLegado", model);

			} catch (Exception e) {

				List<Unidade> unidades = unidadeRepo.findAll();
				model.addAttribute("unidades", unidades);
				
				List<TipoDocumento> tipos = tipoDocRepo.findAll();
				model.addAttribute("tipos", tipos);
				
				model.addAttribute("arquivo", new Arquivos());
				
				model.addAttribute("mensagemErro", "Desculpe, ocorreu em erro em algum dos arquivos. Verifique se ele possui proteções ou senhas..");
				
				model.addAttribute("tituloPagina", "Incluir Documentos do Portal Antigo");
				return new ModelAndView("uploadLegado", model);
			}
		}else {
			return preparaRetornoLogin("", model);
		}
	}
	
	private String userLogado(Usuario user) {
		if(user !=null) {
			return user.getNome().split(" ")[0];
		}else {
			return null;
		}
	}

	@GetMapping("/sair")
	public ModelAndView sair(ModelMap model, HttpSession session) {

		Usuario uSessao = recuperarDaSessaoAdmin(session);

		gravarLog(new Log(uSessao.getId(), TipoLogEnum.ACESSO.getId(), uSessao.getId(), currentTimestamp(), 
				"Desconectado da Sessão", "ACESSO", getIp(), "sair"));
		session.invalidate();

		return preparaRetornoLogin("",model);
	}

	@RequestMapping(value = "/abrirAlterarSenhaUsuario", method = RequestMethod.GET) 
	public ModelAndView abrirAlterarSenhaUsuario(ModelMap model, HttpSession session) { 

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(null!= usuSessao) {

			gravarLog(new Log(usuSessao.getId(), TipoLogEnum.USUARIO.getId(), usuSessao.getId(), currentTimestamp(), 
					"Página de Alteração de Senha do Próprio Usuário", "USUARIO", getIp(), "abrirAlterarSenhaUsuario"));

			Optional<Usuario> usuBase = usuarioRepo.findById(usuSessao.getId());
			model.addAttribute("usu", usuBase.get());
			model.addAttribute("userLogado", userLogado(usuSessao));
			model.addAttribute("tituloPagina", "Alterar a própria senha");
			return new ModelAndView("alterarPropriaSenha", model);

		}else {
			return preparaRetornoLogin("",model);
		}
	}
	
	@RequestMapping(value = "/abrirAlterarImagemUsuario", method = RequestMethod.GET) 
	public ModelAndView abrirAlterarImagemUsuario(ModelMap model, HttpSession session) { 

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(null!= usuSessao) {

			gravarLog(new Log(usuSessao.getId(), TipoLogEnum.USUARIO.getId(), usuSessao.getId(), currentTimestamp(), 
					"Página de Alteração da imagem do Próprio Usuário", "USUARIO", getIp(), "abrirAlterarImagemUsuario"));

			Optional<Usuario> usuBase = usuarioRepo.findById(usuSessao.getId());
			model.addAttribute("usuario", usuBase.get());
			model.addAttribute("userLogado", userLogado(usuSessao));
			model.addAttribute("tituloPagina", "Alterar a própria imagem");
			return new ModelAndView("alterarPropriaImagem", model);

		}else {
			return preparaRetornoLogin("",model);
		}
	}

	@PutMapping("/alterarPropriaSenhaUsuario")
	public ModelAndView alterarPropriaSenhaUsuario(@Valid @ModelAttribute("usu") Usuario usu, BindingResult result, ModelMap model, HttpSession session) throws Exception {

		Usuario uSessao = recuperarDaSessaoAdmin(session);

		if(null!= uSessao) {
			Optional<Usuario> usuBase = usuarioRepo.findById(uSessao.getId());
			Usuario usuBase2 = null;
			if(usuBase.isPresent()) {
				usuBase2 = usuBase.get();
			}
			
			String senhaDescritografada = Criptografia.decrypt(usuBase2.getSenha());

			if(senhaDescritografada.equals(usu.getSenhaAtual())) {

				if(usu.getSenhaNova().equals(usu.getSenhaNovaRepete())) {
					if(usu.getSenhaNova().length()>=6 && usu.getSenhaNova().length()<=16) {
						
						String senhaNovaCriptografada = Criptografia.encrypt(usu.getSenhaNova()+"");
						usuBase2.setCriptografia("S");
						usuBase2.setSenha(senhaNovaCriptografada);
						usuarioRepo.save(usuBase2);

						gravarLog(new Log(usuBase2.getId(), TipoLogEnum.USUARIO.getId(), uSessao.getId(), currentTimestamp(), 
								"Alteração da prória senha realizada com sucesso.", "USUARIO", getIp(), "alterarPropriaSenhaUsuario"));

						session.invalidate();
						model.addAttribute("mensagemSucesso", "Alteração da prória Usuário realizada com sucesso. Por favor registre-se novamente");
						model.addAttribute("usu", new Usuario());
						return new ModelAndView("index", model);

					}else {
						gravarLog(new Log(usuBase2.getId(), TipoLogEnum.USUARIO.getId(), uSessao.getId(), currentTimestamp(), 
								"Alteração de Usuário não realizada: Tamanho da Senha diferente do estabelecido.", "USUARIO", getIp(), "alterarPropriaSenhaUsuario"));

						model.addAttribute("userLogado", userLogado(uSessao));
						model.addAttribute("mensagemErro","ALTERAÇÃO NÃO REALIZADA: O tamanho da senha não pode ser menor que 6 ou maior que 16 caracteres.");
						model.addAttribute("usu",usuBase2);
						return new ModelAndView("alterarPropriaSenhaUsuario", model);
					}
				}else {
					gravarLog(new Log(usuBase2.getId(), TipoLogEnum.USUARIO.getId(), uSessao.getId(), currentTimestamp(), 
							"Alteração de Usuário não realizada: A nova senha não é igual nos dois campos.", "USUARIO", getIp(), "alterarPropriaSenhaUsuario"));

					model.addAttribute("userLogado", userLogado(uSessao));
					model.addAttribute("mensagemErro","ALTERAÇÃO NÃO REALIZADA: A nova senha não é igual nos dois campos.");
					model.addAttribute("usu",usuBase2);
					return new ModelAndView("alterarPropriaSenhaUsuario", model);
				}

			}else {
				gravarLog(new Log(usuBase2.getId(), TipoLogEnum.USUARIO.getId(), uSessao.getId(), currentTimestamp(), 
						"Alteração de Usuário não realizada: A senha atual não confere.", "USUARIO", getIp(), "alterarPropriaSenhaUsuario"));

				model.addAttribute("userLogado", userLogado(uSessao));
				model.addAttribute("mensagemErro","ALTERAÇÃO NÃO REALIZADA: A senha atual não confere.");
				model.addAttribute("usu",usuBase2);
				return new ModelAndView("alterarPropriaSenha", model);
			}
		}else {
			return preparaRetornoLogin("Desculpe, para alterar informações de um usuário é necessário se autenticar primeiro.",model);
		}
	}

	@RequestMapping(value = "enviarSenha", method = RequestMethod.POST) 
	public ModelAndView enviarSenha(@Valid @ModelAttribute("usu") Usuario usu, BindingResult result, ModelMap model, HttpSession session) {

		if(null == usu || null == usu.getCpf() || "".equals(usu.getCpf())) {

			gravarLog(new Log(0, TipoLogEnum.ACESSO.getId(), 0, currentTimestamp(), 
					"Solicitação de recuperação de senha por e-mail - CPF não informado", "ACESSO", getIp(), "esqueceuSenha"));
			model.addAttribute("mensagem", "Desculpe, para recuperar a senha é necessário informar seu CPF primeiro.");
			model.addAttribute("usu", new Usuario());
			return new ModelAndView("esqueciSenha", model);
		}

		Optional<Usuario> usuario = usuarioRepo.obterUsuarioPorCPF(usu.getCpf());

		if(usuario.isPresent()) {
			String senhaDescripto = "";
			if(usuario.get().getCriptografia().equals("N")) {
				Usuario usuCripto = usuario.get();
				usuCripto.setSenha(Criptografia.encrypt(usuario.get().getSenha()));
				usuCripto.setCriptografia("S");
				usuarioRepo.save(usuCripto);
				senhaDescripto = usuario.get().getSenha();
			}else {
				senhaDescripto = Criptografia.decrypt(usuario.get().getSenha());
			}

			gravarLog(new Log(0, TipoLogEnum.ACESSO.getId(), 0, currentTimestamp(), 
					"Solicitação de recuperação de senha por e-mail - CPF: "+usuario.get().getCpf(), "ACESSO", getIp(), "esqueceuSenha"));

			String msg = "Prezado(a) "+ usuario.get().getNome()+", <br/><br/>"
					+ "Conforme solicitado, enviamos sua senha para acesso ao Portal de Transparência: "
					+  senhaDescripto+".<br/><br/>"
					+ "Atenciosamente, <br/><br/>"
					+ "ISAC Portal de Transparência."
					+ "<br/><br/>";
			Email.enviar("Portal de Transparência - Recuperação de Senha", msg, usuario.get().getEmail());

			model.addAttribute("mensagemSucesso", "A senha foi encaminhada para o e-mail constante em seu cadastro");
			model.addAttribute("usu", new Usuario());
			return new ModelAndView("index", model);
		}else {
			gravarLog(new Log(0, TipoLogEnum.ACESSO.getId(), 0, currentTimestamp(), 
					"Solicitação de geração de nova senha por e-mail - CPF não localizado na base de dados.", "ACESSO", getIp(), "esqueceuSenha"));

			model.addAttribute("mensagemErro", "Desculpe, para recuperar a senha é necessário informar seu CPF primeiro.");
			model.addAttribute("usu", new Usuario());
			return new ModelAndView("esqueciSenha", model);
		}
	}

	@RequestMapping(value = "esqueciSenha", method = RequestMethod.GET) 
	public ModelAndView esqueciSenha(ModelMap model, HttpSession session) {
		model.addAttribute("mensagemSucesso","Informe o seu CPF para recuperar sua senha. Enviaremos ao e-mail registrado em seu cadastro.");
		model.addAttribute("usu", new Usuario());
		return new ModelAndView("esqueciSenha", model);
	}

	@RequestMapping(value = "", method = RequestMethod.GET) 
	public ModelAndView index(ModelMap model, HttpSession session) {
		
		Usuario usuSessao = recuperarDaSessaoAdmin(session);
		
		if(usuSessao != null) {
			return preparaDashBoard(model, session);
		}else {
			session.invalidate();
			model.addAttribute("usu", new Usuario());
			return new ModelAndView("index", model);
		}
	}

	@RequestMapping(value = "dashboard", method = RequestMethod.GET) 
	public ModelAndView dashboard(ModelMap model, HttpSession session) {
		return preparaDashBoard(model, session);
	}

	private ModelAndView preparaDashBoard(ModelMap model, HttpSession session) {

		Usuario usuarioSessao = recuperarDaSessaoAdmin(session);

		Long arqEdicao = 0L;
		Long arqEmAnalise = 0L;
		Long arqConforme = 0L;
		Long arqNaoConforme = 0L;
		Long arqPublicado = 0L;
		Long arqDevolvido = 0L;
		Long arqDesativado = 0L;

		if(usuarioSessao !=null) {
			if(usuarioSessao.getCnpjUnidade().equals(ParametrosIsacSede.cnpjSede)) {
				model.addAttribute("titulo", "Documentos do Portal: Todas as Unidades");
				
				List<Objeto> lista = gedService.getQuantidadeArquivosPorEstadoVisibilidade("P");
				
				if(lista != null && lista.size()>0) {
					for (Objeto objeto : lista) {
						if(objeto.getTipo() == TipoEstadoArquivoEnum.EM_EDICAO.getId()) arqEdicao = objeto.getQuantidade();
						if(objeto.getTipo() == TipoEstadoArquivoEnum.EM_ANALISE_CONFORMIDADE.getId()) arqEmAnalise = objeto.getQuantidade();
						if(objeto.getTipo() == TipoEstadoArquivoEnum.CONFORME.getId()) arqConforme = objeto.getQuantidade();
						if(objeto.getTipo() == TipoEstadoArquivoEnum.NAO_CONFORME.getId()) arqNaoConforme = objeto.getQuantidade();
						if(objeto.getTipo() == TipoEstadoArquivoEnum.PUBLICADO.getId()) arqPublicado = objeto.getQuantidade();
						if(objeto.getTipo() == TipoEstadoArquivoEnum.DEVOLVIDO.getId()) arqDevolvido = objeto.getQuantidade();
						if(objeto.getTipo() == TipoEstadoArquivoEnum.DESATIVADO.getId()) arqDesativado = objeto.getQuantidade();
					}
				}
				
				
//				List<Objeto> objarqEdicao = gedService.getQuantidadeArquivosPorEstadoVisibilidade(TipoEstadoArquivoEnum.EM_EDICAO.getId(), "P");
//				List<Objeto> objarqEmAnalise = gedService.getQuantidadeArquivosPorEstadoVisibilidade(TipoEstadoArquivoEnum.EM_ANALISE_CONFORMIDADE.getId(), "P");
//				List<Objeto> objarqConforme = gedService.getQuantidadeArquivosPorEstadoVisibilidade(TipoEstadoArquivoEnum.CONFORME.getId(), "P");
//				List<Objeto> objarqNaoConforme = gedService.getQuantidadeArquivosPorEstadoVisibilidade(TipoEstadoArquivoEnum.NAO_CONFORME.getId(), "P");
//				List<Objeto> objarqPublicado = gedService.getQuantidadeArquivosPorEstadoVisibilidade(TipoEstadoArquivoEnum.PUBLICADO.getId(), "P");
//				List<Objeto> objarqDevolvido = gedService.getQuantidadeArquivosPorEstadoVisibilidade(TipoEstadoArquivoEnum.DEVOLVIDO.getId(), "P");
//				List<Objeto> objarqDesativado = gedService.getQuantidadeArquivosPorEstadoVisibilidade(TipoEstadoArquivoEnum.DESATIVADO.getId(), "P");
				
//				if(objarqEdicao.size()>0) arqEdicao = objarqEdicao.get(0).getQuantidade();
//				if(objarqEmAnalise.size()>0) arqEmAnalise = objarqEmAnalise.get(0).getQuantidade();
//				if(objarqConforme.size()>0) arqConforme = objarqConforme.get(0).getQuantidade();
//				if(objarqNaoConforme.size()>0) arqNaoConforme = objarqNaoConforme.get(0).getQuantidade();
//				if(objarqPublicado.size()>0) arqPublicado = objarqPublicado.get(0).getQuantidade();
//				if(objarqDevolvido.size()>0) arqDevolvido = objarqDevolvido.get(0).getQuantidade();
//				if(objarqDesativado.size()>0) arqDesativado = objarqDesativado.get(0).getQuantidade();
				
				//arqEdicao             = gedService.getQuantidadeArquivosPorEstadoVisibilidade(TipoEstadoArquivoEnum.EM_EDICAO.getId(), "P").size();//arquivoRepo.quantidadeArquivosPorEstadoVisibilidadePublica(TipoEstadoArquivoEnum.EM_EDICAO.getId());
				//arqEmAnalise       = gedService.getQuantidadeArquivosPorEstadoVisibilidade(TipoEstadoArquivoEnum.EM_ANALISE_CONFORMIDADE.getId(), "P").size();//arquivoRepo.quantidadeArquivosPorEstadoVisibilidadePublica(TipoEstadoArquivoEnum.EM_ANALISE_CONFORMIDADE.getId());
				//arqConforme        =  gedService.getQuantidadeArquivosPorEstadoVisibilidade(TipoEstadoArquivoEnum.CONFORME.getId(), "P").size();//arquivoRepo.quantidadeArquivosPorEstadoVisibilidadePublica(TipoEstadoArquivoEnum.CONFORME.getId());
				//arqNaoConforme =  gedService.getQuantidadeArquivosPorEstadoVisibilidade(TipoEstadoArquivoEnum.NAO_CONFORME.getId(), "P").size();//arquivoRepo.quantidadeArquivosPorEstadoVisibilidadePublica(TipoEstadoArquivoEnum.NAO_CONFORME.getId());
				//arqPublicado        =  gedService.getQuantidadeArquivosPorEstadoVisibilidade(TipoEstadoArquivoEnum.PUBLICADO.getId(), "P").size();//arquivoRepo.quantidadeArquivosPorEstadoVisibilidadePublica(TipoEstadoArquivoEnum.PUBLICADO.getId());
				//arqDevolvido        =  gedService.getQuantidadeArquivosPorEstadoVisibilidade(TipoEstadoArquivoEnum.DEVOLVIDO.getId(), "P").size();//arquivoRepo.quantidadeArquivosPorEstadoVisibilidadePublica(TipoEstadoArquivoEnum.DEVOLVIDO.getId());
				//arqDesativado      =  gedService.getQuantidadeArquivosPorEstadoVisibilidade(TipoEstadoArquivoEnum.DESATIVADO.getId(), "P").size();//arquivoRepo.quantidadeArquivosPorEstadoVisibilidadePublica(TipoEstadoArquivoEnum.DESATIVADO.getId());
			}else {
				model.addAttribute("titulo", "Documentos do Portal: Minha Unidade");
				
				List<Objeto> lista = gedService.getQuantidadeArquivosPorUnidadeEstadoVisibilidade(usuarioSessao.getIdUnidadeAtual(),"P");
				
				if(lista != null && lista.size()>0) {
					for (Objeto objeto : lista) {
						if(objeto.getTipo() == TipoEstadoArquivoEnum.EM_EDICAO.getId()) arqEdicao = objeto.getQuantidade();
						if(objeto.getTipo() == TipoEstadoArquivoEnum.EM_ANALISE_CONFORMIDADE.getId()) arqEmAnalise = objeto.getQuantidade();
						if(objeto.getTipo() == TipoEstadoArquivoEnum.CONFORME.getId()) arqConforme = objeto.getQuantidade();
						if(objeto.getTipo() == TipoEstadoArquivoEnum.NAO_CONFORME.getId()) arqNaoConforme = objeto.getQuantidade();
						if(objeto.getTipo() == TipoEstadoArquivoEnum.PUBLICADO.getId()) arqPublicado = objeto.getQuantidade();
						if(objeto.getTipo() == TipoEstadoArquivoEnum.DEVOLVIDO.getId()) arqDevolvido = objeto.getQuantidade();
						if(objeto.getTipo() == TipoEstadoArquivoEnum.DESATIVADO.getId()) arqDesativado = objeto.getQuantidade();
					}
				}
				
//				List<Objeto> objarqEdicao = gedService.getQuantidadeArquivosPorUnidadeEstadoVisibilidade(usuarioSessao.getIdUnidadeAtual(), TipoEstadoArquivoEnum.EM_EDICAO.getId(), "P");
//				List<Objeto> objarqEmAnalise = gedService.getQuantidadeArquivosPorUnidadeEstadoVisibilidade(usuarioSessao.getIdUnidadeAtual(), TipoEstadoArquivoEnum.EM_ANALISE_CONFORMIDADE.getId(), "P");
//				List<Objeto> objarqConforme = gedService.getQuantidadeArquivosPorUnidadeEstadoVisibilidade(usuarioSessao.getIdUnidadeAtual(), TipoEstadoArquivoEnum.CONFORME.getId(), "P");
//				List<Objeto> objarqNaoConforme = gedService.getQuantidadeArquivosPorUnidadeEstadoVisibilidade(usuarioSessao.getIdUnidadeAtual(), TipoEstadoArquivoEnum.NAO_CONFORME.getId(), "P");
//				List<Objeto> objarqPublicado = gedService.getQuantidadeArquivosPorUnidadeEstadoVisibilidade(usuarioSessao.getIdUnidadeAtual(), TipoEstadoArquivoEnum.PUBLICADO.getId(), "P");
//				List<Objeto> objarqDevolvido = gedService.getQuantidadeArquivosPorUnidadeEstadoVisibilidade(usuarioSessao.getIdUnidadeAtual(), TipoEstadoArquivoEnum.DEVOLVIDO.getId(), "P");
//				List<Objeto> objarqDesativado = gedService.getQuantidadeArquivosPorUnidadeEstadoVisibilidade(usuarioSessao.getIdUnidadeAtual(), TipoEstadoArquivoEnum.DESATIVADO.getId(), "P");
//				
//				if(objarqEdicao.size()>0) arqEdicao = objarqEdicao.get(0).getQuantidade();
//				if(objarqEmAnalise.size()>0) arqEmAnalise = objarqEmAnalise.get(0).getQuantidade();
//				if(objarqConforme.size()>0) arqConforme = objarqConforme.get(0).getQuantidade();
//				if(objarqNaoConforme.size()>0) arqNaoConforme = objarqNaoConforme.get(0).getQuantidade();
//				if(objarqPublicado.size()>0) arqPublicado = objarqPublicado.get(0).getQuantidade();
//				if(objarqDevolvido.size()>0) arqDevolvido = objarqDevolvido.get(0).getQuantidade();
//				if(objarqDesativado.size()>0) arqDesativado = objarqDesativado.get(0).getQuantidade();
				
//				arqEdicao             = gedService.getQuantidadeArquivosPorUnidadeEstadoVisibilidade(usuarioSessao.getIdUnidadeAtual(), TipoEstadoArquivoEnum.EM_EDICAO.getId(), "P").size();//arquivoRepo.quantidadeArquivosPorEstadoAndUnidadeVisibilidadePublica(TipoEstadoArquivoEnum.EM_EDICAO.getId(), usuarioSessao.getIdUnidadeAtual());
//				arqEmAnalise       = gedService.getQuantidadeArquivosPorUnidadeEstadoVisibilidade(usuarioSessao.getIdUnidadeAtual(), TipoEstadoArquivoEnum.EM_ANALISE_CONFORMIDADE.getId(), "P").size();//arquivoRepo.quantidadeArquivosPorEstadoAndUnidadeVisibilidadePublica(TipoEstadoArquivoEnum.EM_ANALISE_CONFORMIDADE.getId(), usuarioSessao.getIdUnidadeAtual());
//				arqConforme        = gedService.getQuantidadeArquivosPorUnidadeEstadoVisibilidade(usuarioSessao.getIdUnidadeAtual(), TipoEstadoArquivoEnum.CONFORME.getId(), "P").size();//arquivoRepo.quantidadeArquivosPorEstadoAndUnidadeVisibilidadePublica(TipoEstadoArquivoEnum.CONFORME.getId(), usuarioSessao.getIdUnidadeAtual());
//				arqNaoConforme = gedService.getQuantidadeArquivosPorUnidadeEstadoVisibilidade(usuarioSessao.getIdUnidadeAtual(), TipoEstadoArquivoEnum.NAO_CONFORME.getId(), "P").size();//arquivoRepo.quantidadeArquivosPorEstadoAndUnidadeVisibilidadePublica(TipoEstadoArquivoEnum.NAO_CONFORME.getId(), usuarioSessao.getIdUnidadeAtual());
//				arqPublicado        = gedService.getQuantidadeArquivosPorUnidadeEstadoVisibilidade(usuarioSessao.getIdUnidadeAtual(), TipoEstadoArquivoEnum.PUBLICADO.getId(), "P").size();//arquivoRepo.quantidadeArquivosPorEstadoAndUnidadeVisibilidadePublica(TipoEstadoArquivoEnum.PUBLICADO.getId(), usuarioSessao.getIdUnidadeAtual());
//				arqDevolvido        =gedService.getQuantidadeArquivosPorUnidadeEstadoVisibilidade(usuarioSessao.getIdUnidadeAtual(), TipoEstadoArquivoEnum.DEVOLVIDO.getId(), "P").size(); //arquivoRepo.quantidadeArquivosPorEstadoAndUnidadeVisibilidadePublica(TipoEstadoArquivoEnum.DEVOLVIDO.getId(), usuarioSessao.getIdUnidadeAtual());
//				arqDesativado      = gedService.getQuantidadeArquivosPorUnidadeEstadoVisibilidade(usuarioSessao.getIdUnidadeAtual(), TipoEstadoArquivoEnum.DESATIVADO.getId(), "P").size();//arquivoRepo.quantidadeArquivosPorEstadoAndUnidadeVisibilidadePublica(TipoEstadoArquivoEnum.DESATIVADO.getId(), usuarioSessao.getIdUnidadeAtual());
			}
			
			model.addAttribute("arqEdicao", arqEdicao);
			model.addAttribute("arqEmAnalise", arqEmAnalise);
			model.addAttribute("arqConforme", arqConforme);
			model.addAttribute("arqNaoConforme", arqNaoConforme);
			model.addAttribute("arqPublicado", arqPublicado);
			model.addAttribute("arqDevolvido", arqDevolvido);
			model.addAttribute("arqDesativado", arqDesativado);
			
			Long arqEdicaoRestrito = 0L;
			Long arqPublicadoRestrito = 0L;
			Long arqDesativadoRestrito = 0L;
			
			List<Objeto> lista = gedService.getQuantidadeArquivosPorEstadoVisibilidade("R");
			
			if(lista != null && lista.size()>0) {
				for (Objeto objeto : lista) {
					if(objeto.getTipo() == TipoEstadoArquivoEnum.EM_EDICAO.getId()) arqEdicaoRestrito = objeto.getQuantidade();
					if(objeto.getTipo() == TipoEstadoArquivoEnum.PUBLICADO.getId()) arqPublicadoRestrito = objeto.getQuantidade();
					if(objeto.getTipo() == TipoEstadoArquivoEnum.DESATIVADO.getId()) arqDesativadoRestrito = objeto.getQuantidade();
				}
			}
			
//			List<Objeto> objarqEdicaoRestrito = gedService.getQuantidadeArquivosPorEstadoVisibilidade(TipoEstadoArquivoEnum.EM_EDICAO.getId(), "R");
//			
//			if(objarqEdicaoRestrito.size()>0) {
//				arqEdicaoRestrito = objarqEdicaoRestrito.get(0).getQuantidade();
//			}
//			
//			List<Objeto> objarqPublicadoRestrito = gedService.getQuantidadeArquivosPorEstadoVisibilidade(TipoEstadoArquivoEnum.PUBLICADO.getId(), "R");
//			
//			if(objarqPublicadoRestrito.size()>0) {
//				arqPublicadoRestrito = objarqPublicadoRestrito.get(0).getQuantidade();
//			}
//			
//			List<Objeto> objarqDesativadoRestrito = gedService.getQuantidadeArquivosPorEstadoVisibilidade(TipoEstadoArquivoEnum.DESATIVADO.getId(), "R");
//			
//			if(objarqDesativadoRestrito.size()>0) {
//				arqDesativadoRestrito = objarqDesativadoRestrito.get(0).getQuantidade();
//			}
			
			//arqEdicaoRestrito =  gedService.getQuantidadeArquivosPorEstadoVisibilidade(TipoEstadoArquivoEnum.EM_EDICAO.getId(), "R").size();//arquivoRepo.quantidadeArquivosPorEstadoVisibilidadeRestrita(TipoEstadoArquivoEnum.EM_EDICAO.getId());
			//arqPublicadoRestrito =  gedService.getQuantidadeArquivosPorEstadoVisibilidade(TipoEstadoArquivoEnum.PUBLICADO.getId(), "R").get(0).getQuantidade();//arquivoRepo.quantidadeArquivosPorEstadoVisibilidadeRestrita(TipoEstadoArquivoEnum.PUBLICADO.getId());
			//arqDesativadoRestrito =  gedService.getQuantidadeArquivosPorEstadoVisibilidade(TipoEstadoArquivoEnum.DESATIVADO.getId(), "R").size();//arquivoRepo.quantidadeArquivosPorEstadoVisibilidadeRestrita(TipoEstadoArquivoEnum.DESATIVADO.getId());
			
			model.addAttribute("arqEdicaoRestrito", arqEdicaoRestrito);
			model.addAttribute("arqPublicadoRestrito", arqPublicadoRestrito);
			model.addAttribute("arqDesativadoRestrito", arqDesativadoRestrito);
			
			List<Arquivos> arquivosPublicadoRestritos = arquivoRepo.obterArquivosPublicadosSemBlobAreaRestrita();
			model.addAttribute("listaPublicadosRestrito", arquivosPublicadoRestritos);
			
			model.addAttribute("usuariosEdicao", usuarioRepo.quantidadeUsuariosEmEdicao(TipoEstadoUsuarioEnum.EM_EDICAO.getId()));
			
			model.addAttribute("unidadesEdicao", unidadeRepo.quantidadeUnidadesEmEdicao(TipoEstadoUnidadeEnum.EM_EDICAO.getId()));
			
			model.addAttribute("imagensPendentes", 0);

			return new ModelAndView("dashboard", model);
			
		}else {
			return preparaRetornoLogin("", model);
		}
	}

	@RequestMapping(value = "home", method = RequestMethod.GET) 
	public ModelAndView home(ModelMap model, HttpSession session) {
		return preparaDashBoard(model, session);
	}

	@RequestMapping(value = "pre-login", method = RequestMethod.GET) 
	public ModelAndView iniciarLogin(ModelMap model, HttpSession session) {
		model.addAttribute("usu", new Usuario());
		return new ModelAndView("index", model);
	}

	/**LOGIN DE SESSAO NA AREA RESTRITA
	 * @throws Exception **/
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView verificaLogin(@Valid @ModelAttribute("usu") Usuario usu, BindingResult result, ModelMap model, HttpSession session){

		String cpf = usu.getCpf();
		String senha = usu.getSenha();

		String cpfComMascara = cpf;
		//retirar os pontos:
		cpfComMascara = cpfComMascara.replace(".", "");
		//retirar o traço:
		cpfComMascara = cpfComMascara.replace("-", "");

		cpf = cpfComMascara;

		if(null == cpf || null == senha) {
			model.addAttribute("usu", usu);
			model.addAttribute("mensagemErro", "Desculpe, os campos CPF e Senha precisam ser preenchidos");
			return new ModelAndView("index", model);
		}

		if("".equals(cpf) || "".equals(senha)) {
			model.addAttribute("usu", usu);
			model.addAttribute("mensagemErro", "Desculpe, os campos CPF e Senha precisam ser preenchidos");
			return new ModelAndView("index", model);
		}

		usu.setCpf(cpf);
		Optional<Usuario> usuarioNaBase = usuarioRepo.obterUsuarioPorCPF(cpf);

		if(!usuarioNaBase.isPresent()) {
			model.addAttribute("usu", usu);
			model.addAttribute("mensagemErro", "Desculpe, os dados informados não conferem ou o usuário está na situação Em Edição.");
			return new ModelAndView("index", model);
		}

		//encontrou o usuario na base
		if(usuarioNaBase.isPresent()) {

			if(usuarioNaBase.get().getIdEstadoAtual() != TipoEstadoUsuarioEnum.ATIVO.getId()) {
				model.addAttribute("usu", usu);
				model.addAttribute("mensagemErro", "Desculpe, o usuário informado encontra-se na situação Em Edição ou Desligado. Verifique com algum administrador para realizar a ativação deste cadastro.");
				return new ModelAndView("index", model);
			}

			if(null != usuarioNaBase.get().getCpf() && null != usuarioNaBase.get().getSenha() && usuarioNaBase.get().getIdEstadoAtual() == TipoEstadoUsuarioEnum.ATIVO.getId() ) {
				String senhaDescriptografada = "";
				//cpf confere
				if(usuarioNaBase.get().getCpf().equals(usu.getCpf()) ) {
					
					if(null == usuarioNaBase.get().getCriptografia() || usuarioNaBase.get().getCriptografia().equals("N")) {
						if(usuarioNaBase.get().getSenha().equals(usu.getSenha())) {
							Usuario usuBDNovaSenhaCripto = usuarioNaBase.get();
							usuBDNovaSenhaCripto.setSenha(Criptografia.encrypt(usu.getSenha()));
							usuBDNovaSenhaCripto.setCriptografia("S");
							usuarioRepo.save(usuBDNovaSenhaCripto);
							senhaDescriptografada = usu.getSenha();
						}else {
							model.addAttribute("usu", usu);
							model.addAttribute("mensagemErro", "Desculpe, os dados informados não conferem. Verifique se o cadastro do usuário encontra-se na situação Ativo.");
							return new ModelAndView("index", model);
						}
					}else {
						senhaDescriptografada = Criptografia.decrypt(usuarioNaBase.get().getSenha().trim());
					}
					
					
//					String senhaDescriptografada = "";
//					try {
//						senhaDescriptografada = Criptografia.decrypt(usuarioNaBase.get().getSenha().trim());
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						//e.printStackTrace();
//						//se der erro, significa que a senha nao está criptografada
//						if(usuarioNaBase.get().getSenha().equals(usu.getSenha())) {
//							Usuario usuBDNovaSenhaCripto = usuarioNaBase.get();
//							try {
//								usuBDNovaSenhaCripto.setSenha(Criptografia.encrypt(usu.getSenha()));
//							} catch (Exception e1) {
//								// TODO Auto-generated catch block
//								//e1.printStackTrace();
//								model.addAttribute("usu", usu);
//								model.addAttribute("mensagemErro", "Erro na Criptografia da Senha");
//								return new ModelAndView("index", model);
//							}
//							usuarioRepo.save(usuBDNovaSenhaCripto);
//							
//							try {
//								senhaDescriptografada = Criptografia.decrypt(usuBDNovaSenhaCripto.getSenha());
//							} catch (Exception e1) {
//								// TODO Auto-generated catch block
//								//e1.printStackTrace();
//								model.addAttribute("usu", usu);
//								model.addAttribute("mensagemErro", "Erro na descriptografia da Senha");
//								return new ModelAndView("index", model);
//							}
//							
//						}else {
//							model.addAttribute("usu", usu);
//							model.addAttribute("mensagemErro", "Desculpe, os dados informados não conferem. Verifique se o cadastro do usuário encontra-se na situação Ativo.");
//							return new ModelAndView("index", model);
//						}
//					}
					
					if(!senhaDescriptografada.equals(usu.getSenha())) {
						model.addAttribute("usu", usu);
						model.addAttribute("mensagemErro", "Desculpe, os dados informados não conferem. Verifique se o cadastro do usuário encontra-se na situação Ativo.");
						return new ModelAndView("index", model);
					}
					
					usu.setNome(usuarioNaBase.get().getNome());
					usu.setId(usuarioNaBase.get().getId());
					usu.setCpf(usuarioNaBase.get().getCpf());
					usu.setIdFuncaoAtual(usuarioNaBase.get().getIdFuncaoAtual());
					usu.setIdUnidadeAtual(usuarioNaBase.get().getIdUnidadeAtual());
					usu.setFuncao(TipoFuncaoUsuarioEnum.recupera(usuarioNaBase.get().getIdFuncaoAtual()));
					
					if(usuarioNaBase.get().getIdArquivoImagem() ==null) {
						usu.setIdArquivoImagem(0);
					}else {
						usu.setIdArquivoImagem(usuarioNaBase.get().getIdArquivoImagem());
					}
					
					Optional<Unidade> unidadeUsuario = unidadeRepo.findById(usuarioNaBase.get().getIdUnidadeAtual());

					if(unidadeUsuario.isPresent()) {
						usu.setCnpjUnidade(unidadeUsuario.get().getCnpj());
						usu.setUnidade(unidadeUsuario.get());
					}

					salvarNaSessaoAdmin(usu, session);

					gravarLog(new Log(0, TipoLogEnum.ACESSO.getId(), usu.getId(), currentTimestamp(), 
							"Acesso a seção Restrita", "ACESSO", getIp(), "/login"));

					model.addAttribute("userLogado", userLogado(usu));
					model.addAttribute("mensagemSucesso", "Bem vindo ao Portal "+usu.getNome()+"!");
					return preparaDashBoard(model, session);
					//senha nao confere	
				}else {

					model.addAttribute("usu", usu);
					model.addAttribute("mensagemErro", "Desculpe, os dados informados não conferem. Verifique se o cadastro do usuário encontra-se na situação Ativo.");
					return new ModelAndView("index", model);
				}
			}else {

				model.addAttribute("usu", usu);
				model.addAttribute("mensagemErro", "Desculpe, os dados informados não conferem.");
				return new ModelAndView("index", model);
			}
			//nao encontrou o usuario na base	
		}else {

			model.addAttribute("usu", usu);
			model.addAttribute("mensagemErro", "Desculpe, os dados informados não conferem.");
			return new ModelAndView("index", model);
		}
	}

	@RequestMapping(value = "/usuarios", method = RequestMethod.GET)
	public ModelAndView obterTodosUsuarios(ModelMap model, HttpSession session){

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(usuSessao != null) {

			Mensagem podeVerTodos = usuarioRules.podeVerTodosUsuarios(usuSessao);
			List<Usuario> users = new ArrayList<>();

			if(podeVerTodos.isPermitido()) {
				users = usuarioRepo.findAll();
			}else {
				users = usuarioRepo.usuariosUnidade(usuSessao.getIdUnidadeAtual());
			}

			List<Usuario> newUsers = new ArrayList<>();

			for (Usuario usuario : users) {
				usuario = complementaObjeto(usuario, session);
				newUsers.add(usuario);
			}

			gravarLog(new Log(0, TipoLogEnum.USUARIO.getId(), usuSessao.getId(), currentTimestamp(), 
					"Lista de todos os usuários", "USUARIO", getIp(), "/usuarios"));

			model.addAttribute("usuarios", newUsers);
			model.addAttribute("tituloPagina", "Lista de Usuários Cadastrados");
			model.addAttribute("userLogado", userLogado(usuSessao));
			return new ModelAndView("listarUsuarios", model);
		}else {

			return preparaRetornoLogin("Desculpe, para acessar a lista de usuários cadastrados na Área Restrita é necessário estar registrado.", model);
		}
	}

	@RequestMapping(value = "/novoUsuario", method = RequestMethod.GET)
	public ModelAndView novoUsuario(ModelMap model, HttpSession session){

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(usuSessao != null) {

			List<Unidade> unidades =  new ArrayList<>();

			if(!usuSessao.getCnpjUnidade().equals(ParametrosIsacSede.cnpjSede)) {
				Optional<Unidade> unidade = unidadeRepo.findById(usuSessao.getIdUnidadeAtual() );
				if(unidade.isPresent()) {
					unidades.add(unidade.get());
				}
			}else {
				unidades =  unidadeRepo.obterUnidadesPublicadas(TipoEstadoUnidadeEnum.PUBLICADO.getId());
			}

			model.addAttribute("unidades", unidades);

			List<TipoFuncaoUsuarioEnum> funcoes = Arrays.asList(TipoFuncaoUsuarioEnum.values());
			model.addAttribute("funcoes",funcoes);

			gravarLog(new Log(0, TipoLogEnum.USUARIO.getId(), usuSessao.getId(), currentTimestamp(), 
					"Acesso ao formulário de  cadastro de usuários", "USUARIO", getIp(), "/novoUsuario"));

			model.addAttribute("usu", new Usuario());
			model.addAttribute("userLogado", userLogado(usuSessao));
			model.addAttribute("tituloPagina", "Cadastro de novo usuário");
			return new ModelAndView("formNovoUsuario", model);
		}else {
			return preparaRetornoLogin("Desculpe, para acessar o formulário de cadastro de novos usuários na Área Restrita é necessário estar registrado.", model);
		}
	}

	@RequestMapping(value = "/novoDocumento", method = RequestMethod.GET)
	public ModelAndView novoDocumento(ModelMap model, HttpSession session){

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(usuSessao != null) {

			List<Unidade> unidades =  new ArrayList<>();
			
			///String tiposPermitidos ="";

			List<TipoDocumento> tiposArquivos = new ArrayList<>();

			if(!usuSessao.getCnpjUnidade().equals(ParametrosIsacSede.cnpjSede)) {
				Optional<Unidade> unidade = unidadeRepo.findById(usuSessao.getIdUnidadeAtual() );
				
				if(unidade.isPresent()) {
					unidades.add(unidade.get());
				}
				
				//tiposPermitidos = arquivoRules.tiposPermitidosParaUnidade(unidade.get().getTipoUnidade());
				tiposArquivos = tipoDocRepo.findAllTiposUnidades();
						
			}else {
				unidades =  unidadeRepo.obterUnidadesPublicadas(TipoEstadoUnidadeEnum.PUBLICADO.getId());
				tiposArquivos = tipoDocRepo.findAllAtivos().get();
			}

			model.addAttribute("unidades", unidades);
			
			List<TipoDocumento> tiposArquivosNovo = new ArrayList<>();
			
			for(TipoDocumento tip: tiposArquivos) {
				//GrupoTipoDocumento gr = grupoTipoRepo.findById(tip.getGrupo()).get();
				//String nomeSelect =  "<b>Tipo: </b>"+ tip.getNome() + " - <b>Grupo:</b> "+ gr.getNome() + " - <b>Periodicidade: </b>" + Conversor.periodicidadeTipoDocumento(tip.getPeriodicidade()) + " -  <b>Prazo Legal de Guarda(anos):</b> "+tip.getPrazoGuardaAnos();
				String nomeSelect =  tip.getNome();
				tip.setNomeSelect(nomeSelect);
				tiposArquivosNovo.add(tip);
			}
			
			model.addAttribute("tiposArquivos",tiposArquivosNovo);

			gravarLog(new Log(0, TipoLogEnum.DOCUMENTO.getId(), usuSessao.getId(), currentTimestamp(), 
					"Acesso ao formulário de  cadastro de documentos", "DOCUMENTO", getIp(), "/novoDocumento"));

			model.addAttribute("arquivo", new Arquivos());
			model.addAttribute("userLogado", userLogado(usuSessao));
			model.addAttribute("tituloPagina", "Cadastro de novo documento");
			return new ModelAndView("formNovoDocumento", model);
		}else {
			return preparaRetornoLogin("Desculpe, para acessar o formulário de cadastro de novos documentos na Área Restrita é necessário estar registrado.", model);
		}
	}
	
	@PostMapping("/salvarImagemUsuario")
	public ModelAndView salvarImagemUsuario(@RequestParam("avatar") MultipartFile file, ModelMap model, HttpSession session) {
		
		Usuario usuSessao = recuperarDaSessaoAdmin(session);
		
		if(usuSessao != null) {
			Optional<Usuario> usuDB = usuarioRepo.findById(usuSessao.getId());
			Mensagem podeAlterarImagem = usuarioRules.podeAlterarImagemUsuario(usuDB.get(), usuSessao);
			
			if(podeAlterarImagem.isPermitido()) {
				
				//Optional<Usuario> usuDB = usuarioRepo.findById(usu.getId());
				
				if(usuDB.isPresent()) {
					Integer idArqImg = usuDB.get().getIdArquivoImagem();
					Usuario usuEditar = usuDB.get();
					
					if(null!= idArqImg && idArqImg >0) {
						//ja existe
						Optional<Arquivos> arqImgDB = arquivoRepo.findById(idArqImg);
						
						if(arqImgDB.isPresent()) {
							Arquivos arqEditar = arqImgDB.get();
							try {
								arqEditar.setArquivo(file.getBytes());
							} catch (IOException e) {
								// TODO Auto-generated catch block
								//e.printStackTrace();
								model.addAttribute("usuario", usuEditar);
								model.addAttribute("mensagemErro", "Desculpe, ocorreu um erro ao salvar sua imagem na base de dados.");
								return new ModelAndView("alterarPropriaImagem", model);
							}
							arqEditar.setMimetype(file.getContentType());
							arqEditar.setNome(file.getOriginalFilename());
							
							double tam = file.getSize();
							tam = (tam/1024)/1024;
							arqEditar.setTamanho(String.format("%.2f", tam)+ " MB");
							arqEditar.setTsRegistro(currentTimestamp());
							arqEditar.setIdUsuario(usuSessao.getId());
							arqEditar.setIdEstadoAtual(TipoEstadoArquivoEnum.PUBLICADO.getId());
							arqEditar.setDataProtocolo(new Date());
							arqEditar.setTipo(0);
							
							arquivoRepo.save(arqEditar);
							
							usuEditar.setIdArquivoImagem(arqEditar.getId());
						}
						
					}else {
						//nova
						Arquivos arq = new Arquivos();
						try {
							arq.setArquivo(file.getBytes());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
							model.addAttribute("usuario", usuEditar);
							model.addAttribute("mensagemErro", "Desculpe, ocorreu um erro ao salvar sua imagem na base de dados.");
							return new ModelAndView("alterarPropriaImagem", model);
						}
						arq.setMimetype(file.getContentType());
						arq.setNome(file.getOriginalFilename());
						
						double tam = file.getSize();
						tam = (tam/1024)/1024;
						arq.setTamanho(String.format("%.2f", tam)+ " MB");
						arq.setTsRegistro(currentTimestamp());
						arq.setIdUsuario(usuSessao.getId());
						arq.setIdEstadoAtual(TipoEstadoArquivoEnum.PUBLICADO.getId());
						arq.setDataProtocolo(new Date());
						arq.setTipo(0);
						
						arquivoRepo.save(arq);

						EstadoArquivo estArq = new EstadoArquivo();
						estArq.setIdArquivo(arq.getId());
						estArq.setIdEstado(TipoEstadoArquivoEnum.PUBLICADO.getId());
						estArq.setIdUsuarioGravador(usuSessao.getId());
						estArq.setTsInicio(currentTimestamp());
						estArq.setObservacao("Arquivo de imagem do usuário ID: "+usuSessao.getId());

						estadoArquivoRepo.save(estArq);
						
						usuEditar.setIdArquivoImagem(arq.getId());
					}
					
					
					usuEditar.setTsRegistroConcordanciaUsoImagem(currentTimestamp());
					usuarioRepo.save(usuEditar);
					
					gravarLog(new Log(usuEditar.getId(), TipoLogEnum.USUARIO.getId(), usuSessao.getId(), currentTimestamp(), "Alteração de imagem do usuário ", "USUARIO", getIp(), "salvarImagemUsuario"));
					
				}
				model.addAttribute("usuario", usuDB.get());
				model.addAttribute("mensagemSucesso", "Imagem Salva com sucesso!");
				return new ModelAndView("alterarPropriaImagem", model);
			}else {
				model.addAttribute("usuario", usuDB.get());
				model.addAttribute("mensagemErro", podeAlterarImagem.getMensagem());
				return new ModelAndView("alterarPropriaImagem", model);
			}
		}else {
			return preparaRetornoLogin("", model);
		}
	}

	@PostMapping("/salvarNovoDocumento")
	public ModelAndView salvarNovoDocumento(@RequestParam("files") MultipartFile[] files, @Valid @ModelAttribute("arquivo") Arquivos arq,  ModelMap model, HttpSession session) {

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(null != usuSessao) {

			Optional<Unidade> unidade = unidadeRepo.findById(arq.getIdUnidade());

			if(!unidade.isPresent()) {
				List<Unidade> unidades = unidadeRepo.findAll();
				model.addAttribute("unidades", unidades);

				List<TipoDocumento> tiposArquivos = tipoDocRepo.findAllAtivos().get();
				model.addAttribute("tiposArquivos",tiposArquivos);

				gravarLog(new Log(0, TipoLogEnum.DOCUMENTO.getId(), usuSessao.getId(), currentTimestamp(), 
						"Acesso ao formulário de  cadastro de documentos", "DOCUMENTO", getIp(), "/novoDocumento"));

				model.addAttribute("arquivo", new Arquivos());
				model.addAttribute("userLogado", userLogado(usuSessao));
				model.addAttribute("tituloPagina", "Cadastro de novo documento");
				model.addAttribute("mensagemErro", "Desculpe, não foi possível localizar a unidade pelo id informado: "+arq.getIdUnidade());
				return new ModelAndView("formNovoDocumento", model);
			}

			try {
				for(MultipartFile file: files) {
					
					if(file.getSize() > 26214000) {
						
						List<Unidade> unidades = unidadeRepo.findAll();
						model.addAttribute("unidades", unidades);

						List<TipoDocumento> tiposArquivos = tipoDocRepo.findAllAtivos().get();
						model.addAttribute("tiposArquivos",tiposArquivos);

						gravarLog(new Log(0, TipoLogEnum.DOCUMENTO.getId(), usuSessao.getId(), currentTimestamp(), 
								"Acesso ao formulário de  cadastro de documentos. Erro: Arquivo maior que 24MB", "DOCUMENTO", getIp(), "/novoDocumento"));

						model.addAttribute("arquivo", new Arquivos());
						model.addAttribute("userLogado", userLogado(usuSessao));
						model.addAttribute("tituloPagina", "Cadastro de novo documento");
						model.addAttribute("mensagemErro", "Desculpe, O tamanho máximo do arquivo não pode ultrapassar 24MB.");
						return new ModelAndView("formNovoDocumento", model);
						
					}
					
					arq.setArquivo(file.getBytes());
					arq.setMimetype(file.getContentType());
					arq.setNome(file.getOriginalFilename());

					double tam = file.getSize();
					tam = (tam/1024)/1024;
					arq.setTamanho(String.format("%.2f", tam)+ " MB");
					arq.setTsRegistro(currentTimestamp());
					arq.setIdUsuario(usuSessao.getId());
					arq.setIdEstadoAtual(TipoEstadoArquivoEnum.EM_EDICAO.getId());

					arq.setDataProtocolo(Conversor.converteStringEmDate(arq.getDataProtocoloFormatada()));

					arquivoRepo.save(arq);

					EstadoArquivo estArq = new EstadoArquivo();
					estArq.setIdArquivo(arq.getId());
					estArq.setIdEstado(TipoEstadoArquivoEnum.EM_EDICAO.getId());
					estArq.setIdUsuarioGravador(usuSessao.getId());
					estArq.setTsInicio(currentTimestamp());
					estArq.setObservacao("Documento incluído na situação "+TipoEstadoArquivoEnum.recupera(TipoEstadoArquivoEnum.EM_EDICAO.getId()).getNome());

					estadoArquivoRepo.save(estArq);
				}

				model.addAttribute("mensagemSucesso", "Documento incluído com sucesso. Fluxo de aprovação iniciado. Verifique a próxima etapa.");
				return preparaDetalharDocumento(arq, session, model);

			} catch (Exception e) {

				List<Unidade> unidades = unidadeRepo.findAll();
				model.addAttribute("unidades", unidades);

				List<TipoDocumento> tiposArquivos = tipoDocRepo.findAllAtivos().get();
				model.addAttribute("tiposArquivos",tiposArquivos);

				gravarLog(new Log(0, TipoLogEnum.DOCUMENTO.getId(), usuSessao.getId(), currentTimestamp(), 
						"Acesso ao formulário de  cadastro de documentos", "DOCUMENTO", getIp(), "/novoDocumento"));

				model.addAttribute("arquivo", new Arquivos());
				model.addAttribute("userLogado", userLogado(usuSessao));
				model.addAttribute("tituloPagina", "Cadastro de novo documento");
				model.addAttribute("mensagemErro", "Desculpe, ocorreu em erro em o arquivo. Verifique se ele possui proteções, senhas ou mesmo se o tamanho é superior a 16 MB.");
				return new ModelAndView("formNovoDocumento", model);
			}
		}else {
			return preparaRetornoLogin("", model);
		}
	}

	@RequestMapping(value = "/novaUnidade", method = RequestMethod.GET)
	public ModelAndView novaUnidade(ModelMap model, HttpSession session){

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(usuSessao != null) {

			if(!usuSessao.getCnpjUnidade().equals(ParametrosIsacSede.cnpjSede)) {
				model.addAttribute("mensagemErro", "Desculpe, apenas usuários das unidades ISAC Sede podem incluir unidades no Portal.");
				return preparaDashBoard(model, session);
			}

			List<UnidadesFederacaoEnum> ufs = Arrays.asList(UnidadesFederacaoEnum.values());
			model.addAttribute("ufs",ufs);

			List<TipoUnidade> tipos = tipoUnidadeRepo.obterTiposUnidadesAtivos(); //Arrays.asList(TipoUnidadeEnum.values());
			model.addAttribute("tipos",tipos);
			
			List<Contratantes> contratantes = contratantesRepo.findAll();
			model.addAttribute("contratantes",contratantes);

			gravarLog(new Log(0, TipoLogEnum.UNIDADE.getId(), usuSessao.getId(), currentTimestamp(), 
					"Acesso ao formulário de  cadastro de unidades", "UNIDADE", getIp(), "/novaUnidade"));

			model.addAttribute("unidade", new Unidade());
			model.addAttribute("userLogado", userLogado(usuSessao));
			model.addAttribute("tituloPagina", "Cadastro de nova unidade");
			return new ModelAndView("formNovaUnidade", model);
		}else {
			return preparaRetornoLogin("Desculpe, para acessar o formulário de cadastro de novos usuários na Área Restrita é necessário estar registrado.", model);
		}
	}

	@RequestMapping(value = "/usuarios/porUnidade/{idUnidade}", method = RequestMethod.GET)
	public ResponseEntity<?> obterTodosUsuariosPorUnidade(@PathVariable(value = "idUnidade") Integer idUnidade, HttpSession session){

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(usuSessao != null) {

			Optional<List<UnidadeUsuario>> users = unidadeUsuarioRepo.recuperaTodosUsuarioDeUmaUnidade(idUnidade);

			if(users.isPresent()) {
				List<Usuario> usuariosUnidade = new ArrayList<>();

				for (UnidadeUsuario uu : users.get()) {
					Optional<Usuario> usuario = usuarioRepo.findById(uu.getIdUsuario());
					if(usuario.isPresent()) {
						Usuario u = usuario.get(); 
						u = complementaObjeto(u, session);
						usuariosUnidade.add(u);
					}
				}

				return new ResponseEntity<List<Usuario>>(usuariosUnidade, HttpStatus.OK);

			}else {
				return new ResponseEntity<Mensagem>(new Mensagem(false, "Informações sobre Unidades e usuários não localizadas na base de dados."), HttpStatus.NOT_FOUND);
			}
		}else {
			return new ResponseEntity<Mensagem>(new Mensagem(false, "Usuário não está registrado. Por favor se autentique novamente."), HttpStatus.FORBIDDEN);
		}
	}

	@RequestMapping(value = "/usuario/porCPF/{cpf}", method = RequestMethod.GET)
	public ResponseEntity<?> obterUsuarioPorCPF(@PathVariable(value = "cpf") String cpf, HttpSession session){

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(usuSessao !=null) {
			Optional<Usuario> usu = usuarioRepo.obterUsuarioPorCPF(cpf);

			if(usu.isPresent()) {
				Usuario user = complementaObjeto(usu.get(), session);
				gravarLog(new Log(user.getId(), TipoLogEnum.USUARIO.getId(), usuSessao.getId(), currentTimestamp(), 
						"Sucesso na pesquisa de usuário por CPF: "+cpf, "USUARIO", getIp(), "/usuario/porCPF/"));
				return new ResponseEntity<Usuario>(user, HttpStatus.OK);
			}else {
				gravarLog(new Log(0, TipoLogEnum.USUARIO.getId(), usuSessao.getId(), currentTimestamp(), 
						"Insucesso na pesquisa de usuário por CPF: "+cpf, "USUARIO", getIp(), "/usuario/porCPF/"));
				return new ResponseEntity<Mensagem>(new Mensagem(false, "Informações sobre Unidades e usuários não localizadas na base de dados."), HttpStatus.NOT_FOUND);
			}
		}else {
			return new ResponseEntity<Mensagem>(new Mensagem(false, "Usuário não está registrado. Por favor se autentique novamente."), HttpStatus.FORBIDDEN);
		}
	}

	@RequestMapping(value = "/usuario/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> obterUsuarioPorId(@PathVariable(value = "id") Integer id, HttpSession session){

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(usuSessao !=null) {

			Optional<Usuario> usu = usuarioRepo.findById(id);
			if(usu.isPresent()) {
				Usuario user = complementaObjeto(usu.get(), session);
				gravarLog(new Log(user.getId(), TipoLogEnum.USUARIO.getId(), usuSessao.getId(), currentTimestamp(), 
						"Sucesso na pesquisa de usuário por ID: "+id, "USUARIO", getIp(), "/usuario(GET)"));
				return new ResponseEntity<Usuario>(user, HttpStatus.OK);
			}else {
				gravarLog(new Log(0, TipoLogEnum.USUARIO.getId(), usuSessao.getId(), currentTimestamp(), 
						"Insucesso na pesquisa de usuário por ID: "+id, "USUARIO", getIp(), "/usuario(GET)"));
				return new ResponseEntity<>(new Mensagem(false, "Usuário não localizado na base de dados."), HttpStatus.NOT_FOUND);
			}
		}else {
			return new ResponseEntity<>(new Mensagem(false, "Usuário não está registrado. Por favor se autentique novamente."), HttpStatus.FORBIDDEN);
		}
	}

	@RequestMapping(value = "/salvarNovoUsuario", method =  RequestMethod.PUT)
	public ModelAndView salvarNovoUsuario(@Valid @ModelAttribute("usu") Usuario usuario, BindingResult result, ModelMap model, HttpSession session) throws Exception{

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(usuSessao != null) {

			String cpfComMascara = usuario.getCpf();
			//retirar os pontos:
			cpfComMascara = cpfComMascara.replace(".", "");
			//retirar o traço:
			cpfComMascara = cpfComMascara.replace("-", "");
			
			if(!Validador.isCPF(cpfComMascara)) {
				
				List<Unidade> unidades =  new ArrayList<>();

				if(!usuSessao.getCnpjUnidade().equals(ParametrosIsacSede.cnpjSede)) {
					Optional<Unidade> unidade = unidadeRepo.findById(usuSessao.getIdUnidadeAtual() );
					if(unidade.isPresent()) {
						unidades.add(unidade.get());
					}
				}else {
					unidades =  unidadeRepo.obterUnidadesPublicadas(TipoEstadoUnidadeEnum.PUBLICADO.getId());
				}

				model.addAttribute("unidades", unidades);

				List<TipoFuncaoUsuarioEnum> funcoes = Arrays.asList(TipoFuncaoUsuarioEnum.values());
				model.addAttribute("funcoes",funcoes);
				
				model.addAttribute("mensagemErro", "Desculpe, o CPF informado não é válido. Verifique o número informado.");
				model.addAttribute("userLogado", userLogado(usuSessao));
				model.addAttribute("usu", usuario);
				model.addAttribute("tituloPagina", "Cadastro de Usuários");
				return new ModelAndView("formNovoUsuario", model);
			}

			Optional<Usuario> usuBdComMesmoCPF = usuarioRepo.obterUsuarioPorCPF(cpfComMascara);

			if(usuBdComMesmoCPF.isPresent()) {

				model.addAttribute("userLogado", userLogado(usuSessao));
				model.addAttribute("mensagemErro", "Não foi possível incluir o novo usuário: Já existe um usuário com este CPF cadastrado.");
				model.addAttribute("usu", usuario);
				return new ModelAndView("formNovoUsuario", model);
			}

			Optional<Unidade> unidadePropostaUsuario = unidadeRepo.findById(usuario.getIdUnidadeAtual()); 

			if(unidadePropostaUsuario.isPresent()) usuario.setCnpjUnidade(unidadePropostaUsuario.get().getCnpj());

			Mensagem podeIncluir = usuarioRules.podeIncluir(usuSessao, usuario);

			if(podeIncluir.isPermitido()) {

				usuario.setCpf(cpfComMascara);
				usuario.setTsRegistro(currentTimestamp());
				
				String senhaCriptografada = Criptografia.encrypt(GeradorSenha.aleatorio()+"");
				
				usuario.setSenha(senhaCriptografada);
				usuario.setCriptografia("S");
				usuario.setIdEstadoAtual(TipoEstadoUsuarioEnum.EM_EDICAO.getId());
				usuarioRepo.save(usuario);
				Usuario user = complementaObjeto(usuario, session);

				EstadoUsuario estado = new EstadoUsuario();
				estado.setIdEstado(TipoEstadoUsuarioEnum.EM_EDICAO.getId());
				estado.setIdUsuario(usuario.getId());
				estado.setObservacao("Usuário criado na situação Em Edição.");
				estado.setIdUsuarioGravador(usuSessao.getId());
				estado.setTsInicio(currentTimestamp());
				estadoUsuarioRepo.save(estado);

				FuncaoUsuario funcao = new FuncaoUsuario();
				funcao.setIdFuncao(usuario.getIdFuncaoAtual());
				funcao.setIdUsuarioGravador(usuSessao.getId());
				funcao.setIdUsuario(usuario.getId());
				funcao.setIdUnidade(usuario.getIdUnidadeAtual());
				funcao.setObservacao("Usuário registrado com a função "+ TipoFuncaoUsuarioEnum.recupera(usuario.getIdFuncaoAtual()));
				funcao.setTsInicio(currentTimestamp());

				funcaoUsuarioRepo.save(funcao);

				UnidadeUsuario unidade = new UnidadeUsuario();
				unidade.setIdUnidade(usuario.getIdUnidadeAtual());
				unidade.setIdUsuario(usuario.getId());
				unidade.setIdUsuarioGravador(usuSessao.getId());
				unidade.setObservacao("Unidade registrada para o usuário");
				unidade.setTsInicio(currentTimestamp());

				unidadeUsuarioRepo.save(unidade);

				String msg = "Prezado(a) "+ usuario.getNome()+", <br/><br/>"
						+ "Bem vindo ao Portal de Transparência do ISAC!<br/><br/>"
						+ "Criamos uma senha para você utilizar junto com seu CPF "+usuario.getCpf()+" e realizar o acesso ao Portal. "
						+ "Anote ai: "+ Criptografia.decrypt(usuario.getSenha())+".<br/><br/>"
						+ "Atenciosamente, <br/><br/>"
						+ "Portal de Transparência do ISAC."
						+ "<br/><br/>";
				Email.enviar("Portal da Transparência ISAC - Bem vindo!", msg, usuario.getEmail());

				gravarLog(new Log(user.getId(), TipoLogEnum.USUARIO.getId(), usuSessao.getId(), currentTimestamp(), 
						"Inclusão de Usuário - Id: "+usuario.getId(), "USUARIO", getIp(), "/salvarNovoUsuario"));

				model.addAttribute("mensagemSucesso", "Novo usuário incluído com sucesso");
				return preparaListarUsuarios(model, session);
			}else {

//				List<Usuario> users = usuarioRepo.findAll();
//				List<Usuario> newUsers = new ArrayList<>();
//
//				for (Usuario us : users) {
//					us = complementaObjeto(us, session);
//					newUsers.add(us);
//				}
				model.addAttribute("userLogado", userLogado(usuSessao));
				model.addAttribute("usu", usuario);
				model.addAttribute("tituloPagina", "Cadastro de Usuários");
				model.addAttribute("mensagemErro", podeIncluir.getMensagem());
				return new ModelAndView("formNovoUsuario", model);
			}
		}else {
			return preparaRetornoLogin("", model);
		}
	}

	@RequestMapping(value = "/alteraSituacaoUsuario", method =  RequestMethod.GET)
	public ModelAndView alterarSituacaoUsuario(@Validated @ModelAttribute("id") Integer idUsuario, @ModelAttribute("novaSituacao") Integer idSituacaoNova, ModelMap model, HttpSession session){

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(usuSessao != null) {
			Optional<Usuario> oldUsuario = usuarioRepo.findById(idUsuario);

			if(oldUsuario.isPresent()) {

				if(idSituacaoNova == TipoEstadoUsuarioEnum.EM_EDICAO.getId()) {
					Mensagem podeAlterarEdicao = usuarioRules.podeColocarEmEdicao(oldUsuario.get(), usuSessao);

					if(podeAlterarEdicao.isPermitido()) {
						executaAlteracaoSituacaoUsuario(oldUsuario.get(), usuSessao, idSituacaoNova);
						model.addAttribute("mensagemSucesso", "Alteração realizada com sucesso.");
						return preparaDetalharUsuario(idUsuario, session, model);
					}else {
						model.addAttribute("mensagemErro", podeAlterarEdicao.getMensagem());
						return preparaDetalharUsuario(idUsuario, session, model);
					}
				}else if(idSituacaoNova == TipoEstadoUsuarioEnum.ATIVO.getId()) {
					Mensagem podeAlterarAtivo = usuarioRules.podeAtivar(oldUsuario.get(), usuSessao);

					if(podeAlterarAtivo.isPermitido()) {
						executaAlteracaoSituacaoUsuario(oldUsuario.get(), usuSessao, idSituacaoNova);
						model.addAttribute("mensagemSucesso", "Alteração realizada com sucesso.");
						return preparaDetalharUsuario(idUsuario, session, model);
					}else {
						model.addAttribute("mensagemErro", podeAlterarAtivo.getMensagem());
						return preparaDetalharUsuario(idUsuario, session, model);
					}
				}else if(idSituacaoNova == TipoEstadoUsuarioEnum.DESLIGADO.getId()) {
					Mensagem podeAlterarDesligado = usuarioRules.podeDesligar(oldUsuario.get(), usuSessao);

					if(podeAlterarDesligado.isPermitido()) {
						executaAlteracaoSituacaoUsuario(oldUsuario.get(), usuSessao, idSituacaoNova);
						model.addAttribute("mensagemSucesso", "Alteração realizada com sucesso.");
					}else {
						model.addAttribute("mensagemErro", podeAlterarDesligado.getMensagem());
						return preparaDetalharUsuario(idUsuario, session, model);
					}
				}else {
					model.addAttribute("mensagemErro", "Desculpe o id informado para alteração do usuário não permitiu localizar informações na base de dados. Por favor tente novamente.");
					return preparaListarUsuarios(model, session);
				}
			}else {
				return preparaListarUsuarios(model, session);
			}
		}else {
			return preparaRetornoLogin("", model);
		}

		return null;
	}

	private ModelAndView preparaRetornoLogin(String mensagem, ModelMap model) {
		model.addAttribute("usu", new Usuario());
		model.addAttribute("mensagemErro", (mensagem.equals("")?"Usuário não está registrado. Por favor se autentique novamente.":mensagem));
		return new ModelAndView("index", model);
	}

	private ModelAndView preparaListarUsuarios(ModelMap model, HttpSession session) {
		List<Usuario> users = usuarioRepo.findAll();
		List<Usuario> newUsers = new ArrayList<>();

		for (Usuario usuario : users) {
			usuario = complementaObjeto(usuario, session);
			newUsers.add(usuario);
		}

		model.addAttribute("userLogado", userLogado(recuperarDaSessaoAdmin(session)));
		model.addAttribute("usuarios", newUsers);
		model.addAttribute("tituloPagina", "Lista de Usuários Cadastrados");
		return new ModelAndView("listarUsuarios", model);
	}
	
	private ModelAndView preparaListarContratantes(ModelMap model, HttpSession session) {
		gravarLog(new Log(0, TipoLogEnum.CONTRATANTE.getId(), recuperarDaSessaoAdmin(session).getId(), currentTimestamp(), 
				"Lista de Contratantes", "CONTRATANTE", getIp(), "listarContratantes"));
		model.addAttribute("contratantes", contratantesRepo.findAll());
		model.addAttribute("tituloPagina", "Lista de Contratantes");
		return new ModelAndView("listarContratantes", model);
	}

	private Usuario executaAlteracaoSituacaoUsuario(Usuario oldUsuario, Usuario usuSessao, Integer idSituacaoNova) {
		Usuario novo = oldUsuario;
		novo.setIdEstadoAtual(idSituacaoNova);
		
		if(null == novo.getCriptografia() || novo.getCriptografia().equals("N")) {
			String senhaCriptografada = Criptografia.encrypt(novo.getSenha());
			novo.setSenha(senhaCriptografada);
			novo.setCriptografia("S");
		}
		
		usuarioRepo.save(novo);

		Optional<EstadoUsuario> estadoAtual = estadoUsuarioRepo.recuperaEstadoAtualUsuario(novo.getId());

		if(estadoAtual.isPresent()) {
			EstadoUsuario estadoA = estadoAtual.get();
			estadoA.setTsFim(currentTimestamp());
			estadoUsuarioRepo.save(estadoA);

			EstadoUsuario novoEstado = new EstadoUsuario();
			novoEstado.setIdEstado(idSituacaoNova);
			novoEstado.setIdUsuario(novo.getId());
			novoEstado.setIdUsuarioGravador(usuSessao.getId());
			novoEstado.setObservacao("Alteração de situação para "+TipoEstadoUsuarioEnum.recupera(idSituacaoNova));
			novoEstado.setTsInicio(currentTimestamp());

			estadoUsuarioRepo.save(novoEstado);
		}

		gravarLog(new Log(novo.getId(), TipoLogEnum.USUARIO.getId(), usuSessao.getId(), currentTimestamp(), 
				"Alteração de Situação de Usuário. De: "+estadoAtual.get().getIdEstado()+ " para "+ idSituacaoNova, "USUARIO", getIp(), "alteraSituacaoUsuario"));
		return novo;
	}

	@RequestMapping(value = "/alterarUsuario", method =  RequestMethod.PUT)
	public ModelAndView alterarUsuario(@Valid @ModelAttribute("usuario") Usuario usuario, HttpSession session, ModelMap model){

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(usuSessao != null) {

			Optional<Unidade> unidadeProposta = unidadeRepo.findById(usuario.getIdUnidadeAtual());

			if(unidadeProposta.isPresent()) usuario.setCnpjUnidade(unidadeProposta.get().getCnpj());

			Mensagem podeAlterar = usuarioRules.podeAlterar(usuario, usuSessao);

			if(podeAlterar.isPermitido()) {
				Optional<Usuario> oldUsuario = usuarioRepo.findById(usuario.getId());
				if(oldUsuario.isPresent()){

					String cpfComMascara = usuario.getCpf();
					//retirar os pontos:
					cpfComMascara = cpfComMascara.replace(".", "");
					//retirar o traço:
					cpfComMascara = cpfComMascara.replace("-", "");
					
					if(!Validador.isCPF(cpfComMascara)) {
						model.addAttribute("mensagemErro", "Desculpe, o CPF informado não é válido. Verifique o número informado.");
						model.addAttribute("usu", usuario);
						model.addAttribute("tituloPagina", "Cadastro de Usuários");
						return preparaAlterarUsuario(usuario.getId(), session, model);
					}
					
					if(null == oldUsuario.get().getCriptografia() || oldUsuario.get().getCriptografia().equals("N")) {
						usuario.setSenha(Criptografia.encrypt(oldUsuario.get().getSenha()));
						usuario.setCriptografia("S");
					}
					
					usuario.setCpf(cpfComMascara);
					usuario.setTsRegistro(oldUsuario.get().getTsRegistro());
					usuario.setIdEstadoAtual(oldUsuario.get().getIdEstadoAtual());
					usuario.setId(oldUsuario.get().getId());
					usuario.setSenha(oldUsuario.get().getSenha());
					usuarioRepo.save(usuario);

					Usuario user = complementaObjeto(usuario, session);

					Optional<EstadoUsuario> estadoAtual = estadoUsuarioRepo.recuperaEstadoAtualUsuario(usuario.getId());
					Optional<FuncaoUsuario> funcaoAtual = funcaoUsuarioRepo.recuperaFuncaoAtualUsuario(usuario.getId());
					Optional<UnidadeUsuario> unidadeAtual = unidadeUsuarioRepo.recuperaUnidadeAtualUsuario(usuario.getId());

					if(estadoAtual.get().getIdEstado() != user.getIdEstadoAtual()) {
						estadoAtual.get().setTsFim(currentTimestamp());
						estadoUsuarioRepo.save(estadoAtual.get());

						EstadoUsuario estado = new EstadoUsuario();
						estado.setIdEstado(user.getIdEstadoAtual());
						estado.setIdUsuario(usuario.getId());
						estado.setObservacao("Usuário alterado para a situação "+ TipoFuncaoUsuarioEnum.recupera(user.getIdEstadoAtual()));
						estado.setIdUsuarioGravador(usuSessao.getId());
						estado.setTsInicio(currentTimestamp());
						estadoUsuarioRepo.save(estado);
					}

					if(funcaoAtual.get().getIdFuncao() != user.getIdFuncaoAtual()) {
						funcaoAtual.get().setTsFim(currentTimestamp());
						funcaoUsuarioRepo.save(funcaoAtual.get());

						FuncaoUsuario funcao = new FuncaoUsuario();
						funcao.setIdFuncao(usuario.getIdFuncaoAtual());
						funcao.setIdUsuarioGravador(usuSessao.getId());
						funcao.setIdUsuario(usuario.getId());
						funcao.setIdUnidade(usuario.getIdUnidadeAtual());
						funcao.setObservacao("Usuário alterado para a função "+ TipoFuncaoUsuarioEnum.recupera(usuario.getIdFuncaoAtual()));
						funcao.setTsInicio(currentTimestamp());

						funcaoUsuarioRepo.save(funcao);
					}

					if(unidadeAtual.get().getIdUnidade() != user.getIdUnidadeAtual()) {
						unidadeAtual.get().setTsFim(currentTimestamp());
						unidadeUsuarioRepo.save(unidadeAtual.get());

						UnidadeUsuario unidade = new UnidadeUsuario();
						unidade.setIdUnidade(user.getIdUnidadeAtual());
						unidade.setIdUsuario(user.getId());
						unidade.setIdUsuarioGravador(usuSessao.getId());
						unidade.setObservacao("Nova Unidade registrada para o usuário");
						unidade.setTsInicio(currentTimestamp());

						unidadeUsuarioRepo.save(unidade);
					}

					gravarLog(new Log(user.getId(), TipoLogEnum.USUARIO.getId(), usuSessao.getId(), currentTimestamp(), 
							"Alteração de Usuário - Id: "+usuario.getId(), "USUARIO", getIp(), "/usuario(PUT)"));
					model.addAttribute("mensagemSucesso", "Informações alteradas com sucesso.");
					return preparaDetalharUsuario(usuario.getId(), session, model);
				}else {
					model.addAttribute("mensagemErro", "Usuário informado não foi localizado na base de dados");
					return preparaDetalharUsuario(usuario.getId(), session, model);
				}
			}else {
				model.addAttribute("mensagemErro", podeAlterar.getMensagem());
				return preparaDetalharUsuario(usuario.getId(), session, model);
			}
		}else {
			return preparaRetornoLogin("", model);
		}
	}

	@RequestMapping(value = "/alterarDocumentoCompliance", method =  RequestMethod.PUT)
	public ModelAndView alterarDocumentoCompliance(@RequestParam("files") MultipartFile[] files, @Valid @ModelAttribute("arquivo") Arquivos arquivo, HttpSession session, ModelMap model){

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(usuSessao != null) {

			Optional<Arquivos> arqDB = arquivoRepo.findById(arquivo.getId());

			if(arqDB.isPresent()) {
				Mensagem podeAlterar = arquivoRules.podeAlterar(arqDB.get(), usuSessao);

				if(podeAlterar.isPermitido()) {

					Arquivos arqNovo = arqDB.get();

					arqNovo.setNomeExibicao(arquivo.getNomeExibicao());
					//arqNovo.setPeriodicidade(arquivo.getPeriodicidade());
					arqNovo.setDataProtocolo(Conversor.converteStringEmDate(arquivo.getDataProtocoloFormatada()));

					arquivoRepo.save(arqNovo);

					gravarLog(new Log(arqNovo.getId(), TipoLogEnum.DOCUMENTO.getId(), usuSessao.getId(), currentTimestamp(), 
							"Alterados dados pelo Compliance: Nome, Prazo Final e Periodicidade", "DOCUMENTO", getIp(), "alterarDocumentoCompliance"));

					model.addAttribute("mensagemSucesso", "Informações alteradas com sucesso.");
					return preparaDetalharDocumento(arqNovo, session, model);

				}else {
					model.addAttribute("mensagemErro", podeAlterar.getMensagem());
					return preparaDetalharDocumento(arqDB.get(), session, model);
				}

			}else {
				model.addAttribute("mensagemErro", "Desculpe, não foi possível localizar o documento. Id "+arquivo.getId());
				return preparaDashBoard(model, session);
			}
		}else {
			return preparaRetornoLogin("", model);
		}
	}

	@RequestMapping(value = "/alterarDocumento", method =  RequestMethod.PUT)
	public ModelAndView alterarDocumento(@RequestParam("files") MultipartFile[] files, @Valid @ModelAttribute("arquivo") Arquivos arquivo, HttpSession session, ModelMap model){

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(usuSessao != null) {

			Optional<Arquivos> arqDB = arquivoRepo.findById(arquivo.getId());

			if(arqDB.isPresent()) {
				Mensagem podeAlterar = arquivoRules.podeAlterar(arqDB.get(), usuSessao);

				if(podeAlterar.isPermitido()) {

					boolean passouNovoArquivo = false;

					try {
						for(MultipartFile file: files) {

							if( file.getSize() <= 0 ) {
								continue;
							}
							
							if(file.getSize() > 26214000) {
								model.addAttribute("mensagem", "Desculpe, O tamanho máximo do arquivo não pode ultrapassar 24MB.");
								return preparaDetalharDocumento(arqDB.get(), session, model);
							}

							arquivo.setArquivo(file.getBytes());
							arquivo.setMimetype(file.getContentType());
							arquivo.setNome(file.getOriginalFilename());
							//arquivo.setNomeExibicao(nomeExibicao);	
							double tam = file.getSize();
							tam = (tam/1024)/1024;
							arquivo.setTamanho(String.format("%.2f", tam)+ " MB");
							arquivo.setTsRegistro(currentTimestamp());
							arquivo.setIdUsuario(usuSessao.getId());
							arquivo.setDataProtocolo(Conversor.converteStringEmDate(arquivo.getDataProtocoloFormatada()));

							arquivoRepo.save(arquivo);

							passouNovoArquivo = true;

						}
					} catch (IOException e) {
						model.addAttribute("mensagemErro", "Desculpe, ocorreu em erro em o arquivo. Verifique se ele possui proteções, senhas ou mesmo se o tamanho é superior a 16 MB.");	
						return preparaDetalharDocumento(arquivo, session, model);
					}

					if(!passouNovoArquivo) {
						Arquivos novoArquivo = arqDB.get();
						novoArquivo.setNomeExibicao(arquivo.getNomeExibicao());
						novoArquivo.setDescricao(arquivo.getDescricao());
						novoArquivo.setIdUnidade(arquivo.getIdUnidade());
						novoArquivo.setIdUsuario(usuSessao.getId());
						//novoArquivo.setPeriodicidade(arquivo.getPeriodicidade());
						//novoArquivo.setPrazoAvaliacao(arquivo.getPrazoAvaliacao());
						novoArquivo.setDataProtocolo(Conversor.converteStringEmDate(arquivo.getDataProtocoloFormatada()));
						novoArquivo.setTipo(arquivo.getTipo());

						arquivoRepo.save(novoArquivo);
					}

					model.addAttribute("mensagemSucesso", "Documento alterado com sucesso.");
					Optional<Arquivos> arqFinal = arquivoRepo.findById(arquivo.getId());
					return preparaDetalharDocumento(arqFinal.get(), session, model);

				}else {
					model.addAttribute("mensagem", podeAlterar.getMensagem());
					return preparaDetalharDocumento(arqDB.get(), session, model);
				}

			}else {
				model.addAttribute("mensagemErro", "Desculpe, não foi possível localizar o documento. Id "+arquivo.getId());
				return preparaDashBoard(model, session);
			}
		}else {
			return preparaRetornoLogin("", model);
		}
	}

	@RequestMapping(value = "/excluirUsuario", method = RequestMethod.GET)
	public ModelAndView excluirUsuario(@Valid @ModelAttribute("idExcluir") Integer id, HttpSession session, ModelMap model){

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(usuSessao != null) {
			Optional<Usuario> usuario = usuarioRepo.findById(id);

			if(usuario.isPresent()){
				Optional<List<UnidadeUsuario>> unidades = unidadeUsuarioRepo.recuperaTodasUnidadesUsuario(usuario.get().getId());
				Optional<List<FuncaoUsuario>> funcoes = funcaoUsuarioRepo.recuperaTodasFuncoesUsuario(usuario.get().getId());
				Optional<List<EstadoUsuario>> estados = estadoUsuarioRepo.recuperaTodosEstadosUsuario(usuario.get().getId());

				Mensagem podeExcluir = usuarioRules.podeExcluir(usuario.get(), usuSessao, estados.get());

				if(podeExcluir.isPermitido()) {
					String log = "Usuário id: "+id + " - "+usuario.get().getNome() + " excluído por CPF: "+ usuSessao.getCpf();
					for (UnidadeUsuario un: unidades.get()) {
						unidadeUsuarioRepo.delete(un);
					}

					for (FuncaoUsuario f: funcoes.get()) {
						funcaoUsuarioRepo.delete(f);
					}

					for (EstadoUsuario e: estados.get()) {
						estadoUsuarioRepo.delete(e);
					}

					usuarioRepo.delete(usuario.get());

					gravarLog(new Log(id, TipoLogEnum.USUARIO.getId(), usuSessao.getId(), currentTimestamp(), 
							log, "USUARIO", getIp(), "/excluirUsuario"));
					model.addAttribute("mensagemSucesso", "Usuário excluído com sucesso.");
					return preparaListarUsuarios(model, session); 
				}else {
					model.addAttribute("mensagemErro", podeExcluir.getMensagem());
					return preparaDetalharUsuario(id, session, model);
				}
			}else {
				model.addAttribute("mensagemErro", "Usuário informado não foi localizado na base de dados.");
				return preparaDetalharUsuario(id, session, model);
			}
		}else {
			return preparaRetornoLogin("", model);
		}
	}

	private Usuario complementaObjeto(Usuario usuario, HttpSession session) {
		usuario.setCpfFormatado(Conversor.formataCPF(usuario.getCpf()));
		usuario.setTipoEstadoAtual(TipoEstadoUsuarioEnum.recupera(usuario.getIdEstadoAtual()));
		usuario.setTipoUsuarioAtual(TipoUsuarioEnum.recupera(usuario.getTipoUsuario()));
		usuario.setFuncao(TipoFuncaoUsuarioEnum.recupera(usuario.getIdFuncaoAtual()));
		usuario.setUnidade(obterUnidadePorId(usuario.getIdUnidadeAtual()));
		return usuario;
	}

	@RequestMapping(value = "/detalharUsuario", method = RequestMethod.GET)
	public ModelAndView detalharUsuario(@Validated @ModelAttribute("id") Integer id, ModelMap model, HttpSession session){

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(usuSessao != null) {
			return preparaDetalharUsuario(id, session, model);
		}else {
			model.addAttribute("userLogado", userLogado(usuSessao));
			model.addAttribute("mensagemErro", "Desculpe, para acessar a lista de unidades cadastradas na Área Restrita é necessário estar registrado.");
			model.addAttribute("usu", new Usuario());
			return new ModelAndView("index", model);
		}
	}

	@RequestMapping(value = "/abrirAlterarUsuario", method = RequestMethod.GET)
	public ModelAndView abrirAlterarUsuario(@Validated @ModelAttribute("id") Integer id, ModelMap model, HttpSession session){

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(usuSessao != null) {
			return preparaAlterarUsuario(id, session, model);
		}else {
			return preparaRetornoLogin("", model);
		}
	}
	
	@RequestMapping(value = "/abrirAlterarContratante", method = RequestMethod.GET)
	public ModelAndView abrirAlterarContratante(@Validated @ModelAttribute("id") Integer id, ModelMap model, HttpSession session){

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(usuSessao != null) {
			return preparaAlterarContratante(id, session, model);
		}else {
			return preparaRetornoLogin("", model);
		}
	}

	@RequestMapping(value = "/abrirAlterarDocumento", method = RequestMethod.GET)
	public ModelAndView abrirAlterarDocumento(@Validated @ModelAttribute("id") Integer id, ModelMap model, HttpSession session){

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(usuSessao != null) {
			return preparaAlterarDocumento(id, session, model, "alterarDocumento");
		}else {
			return preparaRetornoLogin("", model);
		}
	}

	@RequestMapping(value = "/abrirAlterarDocumentoCompliance", method = RequestMethod.GET)
	public ModelAndView abrirAlterarDocumentoCompliance(@Validated @ModelAttribute("id") Integer id, ModelMap model, HttpSession session){

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(usuSessao != null) {
			return preparaAlterarDocumento(id, session, model, "alterarDocumentoCompliance");
		}else {
			return preparaRetornoLogin("", model);
		}
	}

	@RequestMapping(value = "/abrirAlterarUnidade", method = RequestMethod.GET)
	public ModelAndView abrirAlterarUnidade(@Validated @ModelAttribute("id") Integer id, ModelMap model, HttpSession session){

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(usuSessao != null) {
			return preparaAlterarUnidade(id, session, model);
		}else {
			return preparaRetornoLogin("", model);
		}
	}

	private ModelAndView preparaAlterarUsuario(Integer id, HttpSession session, ModelMap model) {

		Optional<Usuario> usuario = usuarioRepo.findById(id);

		if(usuario.isPresent()) {
			Usuario usuarioDB = usuario.get();
			usuarioDB = complementaObjeto(usuarioDB, session);
			model.addAttribute("usuario", usuarioDB);

			List<Unidade> unidades = unidadeRepo.obterUnidadesPublicadas(TipoEstadoUnidadeEnum.PUBLICADO.getId());
			model.addAttribute("unidades", unidades);

			List<TipoFuncaoUsuarioEnum> tiposFuncoes = Arrays.asList(TipoFuncaoUsuarioEnum.values());
			model.addAttribute("funcoes",tiposFuncoes);

			model.addAttribute("tituloPagina", "Informações do Usuário "+usuarioDB.getNome());
			return new ModelAndView("alterarUsuario", model);

		}else {
			return preparaListarUsuarios(model, session);
		}
	}
	
	private ModelAndView preparaAlterarContratante(Integer id, HttpSession session, ModelMap model) {

		Optional<Contratantes> contratante = contratantesRepo.findById(id);

		if(contratante.isPresent()) {
			Contratantes contrDB = contratante.get();
			//usuarioDB = complementaObjeto(usuarioDB, session);
			model.addAttribute("contratante", contrDB);

//			List<Unidade> unidades = unidadeRepo.obterUnidadesPublicadas(TipoEstadoUnidadeEnum.PUBLICADO.getId());
//			model.addAttribute("unidades", unidades);
//
//			List<TipoFuncaoUsuarioEnum> tiposFuncoes = Arrays.asList(TipoFuncaoUsuarioEnum.values());
//			model.addAttribute("funcoes",tiposFuncoes);
			
			List<UnidadesFederacaoEnum> ufs = Arrays.asList(UnidadesFederacaoEnum.values());
			model.addAttribute("ufs",ufs);

			model.addAttribute("tituloPagina", "Informações do Contratante "+contrDB.getNome());
			return new ModelAndView("alterarContratante", model);

		}else {
			return preparaListarUsuarios(model, session);
		}
	}

	private ModelAndView preparaAlterarDocumento(Integer id, HttpSession session, ModelMap model, String pagina) {

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(usuSessao != null) {
			Optional<Arquivos> arquivo = arquivoRepo.findById(id);

			if(arquivo.isPresent()) {
				Arquivos arqDB = arquivo.get();
				arqDB.setDataProtocoloFormatada(Conversor.converteDateEmString(arqDB.getDataProtocolo()));

				arqDB.setTipoDocumento(tipoDocRepo.findById(arqDB.getTipo()).get());
				arqDB.setNomeEstadoAtual(TipoEstadoArquivoEnum.recupera(arqDB.getIdEstadoAtual()).getNome());
				//arqDB.setNomePeriodicidade(TipoPeriodicidadeArquivoEnum.recupera(arqDB.getPeriodicidade()).getNome());

				List<Unidade> unidades =  new ArrayList<>();
				//String tiposPermitidos = "";
				List<TipoDocumento> tiposArquivos = new ArrayList<>();//tipoDocRepo.findAllAtivos().get();

				if(!usuSessao.getCnpjUnidade().equals(ParametrosIsacSede.cnpjSede)) {
					Optional<Unidade> unidade = unidadeRepo.findById(usuSessao.getIdUnidadeAtual() );
					if(unidade.isPresent()) {
						unidades.add(unidade.get());
					}
					
					//tiposPermitidos = arquivoRules.tiposPermitidosParaUnidade(unidade.get().getTipoUnidade());
					tiposArquivos = tipoDocRepo.findAllTiposUnidades();
					
					
				}else {
					unidades =  unidadeRepo.obterUnidadesPublicadas(TipoEstadoUnidadeEnum.PUBLICADO.getId());
					tiposArquivos = tipoDocRepo.findAllAtivos().get();
				}

				model.addAttribute("unidades", unidades);

				
				List<TipoDocumento> tiposArquivosNovo = new ArrayList<>();
				
				for(TipoDocumento tip: tiposArquivos) {
					//GrupoTipoDocumento gr = grupoTipoRepo.findById(tip.getGrupo()).get();
					//String nomeSelect =  "<b>Tipo: </b>"+ tip.getNome() + " - <b>Grupo:</b> "+ gr.getNome() + " - <b>Periodicidade: </b>" + Conversor.periodicidadeTipoDocumento(tip.getPeriodicidade()) + " -  <b>Prazo Legal de Guarda(anos):</b> "+tip.getPrazoGuardaAnos();
					String nomeSelect =  tip.getNome();
					tip.setNomeSelect(nomeSelect);
					tiposArquivosNovo.add(tip);
				}
				
				model.addAttribute("tiposArquivos",tiposArquivosNovo);

				gravarLog(new Log(0, TipoLogEnum.DOCUMENTO.getId(), usuSessao.getId(), currentTimestamp(), 
						"Acesso ao formulário de  alteração de documentos", "DOCUMENTO", getIp(), "/abrirAlterarArquivo"));

				model.addAttribute("codigoUnidade", arqDB.getIdUnidade());
				model.addAttribute("arquivo", arqDB);
				model.addAttribute("userLogado", userLogado(usuSessao));
				model.addAttribute("tituloPagina", "Alteração de documento");
				return new ModelAndView(pagina, model);

			}else {
				model.addAttribute("mensagemErro", "Desculpe, não foi possível identificar o documento a partir do id informado. Id do documento: "+id);
				return preparaDashBoard(model, session);
			}
		}else {
			return preparaRetornoLogin("", model);
		}
	}

	@RequestMapping(value = "/gerenciadorDocumentos", method = RequestMethod.GET) 
	public ModelAndView gerenciadorDocumentos(ModelMap model, HttpSession session) {
		Usuario usu = recuperarDaSessaoAdmin(session);
		if(null != usu) {
			model.addAttribute("tituloPagina", "Gerenciador de Documentos");

			List<TipoDocumento> listaTipos = tipoDocRepo.findAllTipos();//Arrays.asList(TipoArquivoEnum.values());
			model.addAttribute("tiposArquivos", listaTipos);

			List<Arquivos> novosArquivos = new ArrayList<>();

			List<Objeto> qtdePorTipo = null;

			if(usu.getCnpjUnidade().equals(ParametrosIsacSede.cnpjSede)) {
				qtdePorTipo = gedService.getQuantidadeArquivosPorTipo();
			}else {
				qtdePorTipo = gedService.getQuantidadeArquivosPorTipoAndUnidade(usu.getIdUnidadeAtual());
			}

			List<Objeto> tiposFinal = new ArrayList<>();

			for (TipoDocumento tipos : listaTipos) {
				Objeto o = new Objeto();
				o.setNome(tipos.getNome());
				o.setTipo(tipos.getId());
				for(Objeto obj: qtdePorTipo) {
					if(obj.getTipo() == tipos.getId()) {
						o.setQuantidade(obj.getQuantidade());
					}
				}

				if(null == o.getQuantidade()) {
					o.setQuantidade(0L);
				}

				tiposFinal.add(o);
			}

			model.addAttribute("tiposArquivosMenu", tiposFinal);
			model.addAttribute("arquivos", novosArquivos);

			return new ModelAndView("gerenciadorDocumentos", model);
		}else {
			return preparaRetornoLogin("", model);
		}		
	}

	@RequestMapping(value = "/pesquisaDocumentosPorTipo", method = RequestMethod.GET) 
	public ModelAndView pesquisaDocumentosPorTipo(@Valid @ModelAttribute("tipo") Integer tipo, BindingResult result, ModelMap model, HttpSession session) {
		Usuario usu = recuperarDaSessaoAdmin(session);
		if(null != usu) {
			model.addAttribute("tituloPagina", "Gerenciador de Documentos");


			List<TipoDocumento> listaTipos = tipoDocRepo.findAllTipos();//Arrays.asList(TipoArquivoEnum.values());
			model.addAttribute("tiposArquivos", listaTipos);

			List<Objeto> qtdePorTipo = null;

			if(usu.getCnpjUnidade().equals(ParametrosIsacSede.cnpjSede)) {
				qtdePorTipo = gedService.getQuantidadeArquivosPorTipo();
			}else {
				qtdePorTipo = gedService.getQuantidadeArquivosPorTipoAndUnidade(usu.getIdUnidadeAtual());
			}

			List<Objeto> tiposFinal = new ArrayList<>();

			for (TipoDocumento tipos : listaTipos) {
				Objeto o = new Objeto();
				o.setNome(tipos.getNome());
				o.setTipo(tipos.getId());
				for(Objeto obj: qtdePorTipo) {
					if(obj.getTipo() == tipos.getId()) {
						o.setQuantidade(obj.getQuantidade());
					}
				}

				if(null == o.getQuantidade())o.setQuantidade(0L);

				tiposFinal.add(o);
			}

			model.addAttribute("tiposArquivosMenu", tiposFinal);

			List<Arquivos> resultadoArquivosPorTipo = arquivoRepo.obterArquivosPorTipoSemBlob(tipo);//arquivoRepo.obterArquivosPorTipo(tipo);
			List<Arquivos> arquivos = new ArrayList<>();

			if(resultadoArquivosPorTipo != null && resultadoArquivosPorTipo.size()>0) {

				for (Arquivos arq : resultadoArquivosPorTipo) {
					arq.setTipoDocumento(tipoDocRepo.findById(arq.getTipo()).get());
					arq.setNomeEstadoAtual(TipoEstadoArquivoEnum.recupera(arq.getIdEstadoAtual()).getNome());
					Optional<Unidade> unidade = unidadeRepo.findById(arq.getIdUnidade()); 
					if(unidade.isPresent()) arq.setUnidade(unidade.get());

					arquivos.add(arq);
				}

				model.addAttribute("arquivos", arquivos);
			}

			return new ModelAndView("gerenciadorDocumentos", model);
		}else {
			return preparaRetornoLogin("", model);
		}		
	}

	private ModelAndView preparaAlterarUnidade(Integer id, HttpSession session, ModelMap model) {

		Optional<Unidade> unidade = unidadeRepo.findById(id);

		if(unidade.isPresent()) {
			Unidade unidadeDB = unidade.get();
			unidadeDB = complementaObjeto(unidadeDB);
			model.addAttribute("unidade", unidadeDB);

			List<UnidadesFederacaoEnum> ufs = Arrays.asList(UnidadesFederacaoEnum.values());
			model.addAttribute("ufs",ufs);

			List<TipoUnidade> tipos = tipoUnidadeRepo.obterTiposUnidadesAtivos();//List<TipoUnidadeEnum> tipos = Arrays.asList(TipoUnidadeEnum.values());
			model.addAttribute("tipos",tipos);

			List<Contratantes> contratantes = contratantesRepo.findAll();
			model.addAttribute("contratantes",contratantes);
			
			model.addAttribute("tituloPagina", "Alteração de informações da  Unidade "+unidadeDB.getNome());
			return new ModelAndView("alterarUnidade", model);

		}else {
			return preparaListarUnidades("", model);
		}
	}

	private ModelAndView preparaDetalharUsuario(Integer id, HttpSession session, ModelMap model) {

		Optional<Usuario> usuario = usuarioRepo.findById(id);

		if(usuario.isPresent()) {
			Usuario usuarioDB = usuario.get();
			usuarioDB = complementaObjeto(usuarioDB, session);
			model.addAttribute("usuario", usuarioDB);

			List<Unidade> unidades = unidadeRepo.findAll();
			model.addAttribute("unidades", unidades);

			List<TipoFuncaoUsuarioEnum> tiposFuncoes = Arrays.asList(TipoFuncaoUsuarioEnum.values());
			model.addAttribute("funcoes",tiposFuncoes);

			Optional<List<EstadoUsuario>> estados = estadoUsuarioRepo.recuperaTodosEstadosUsuario(usuarioDB.getId());
			if(estados.isPresent()) {
				List<EstadoUsuario> estadosNovos = new ArrayList<>();

				for(EstadoUsuario est: estados.get()) {
					Optional<Usuario> ug = usuarioRepo.findById(est.getIdUsuarioGravador());
					est.setUsuGravador(ug.get());
					est.setNomeEstado(TipoEstadoUsuarioEnum.recupera(est.getIdEstado()).getNome());
					estadosNovos.add(est);
				}

				model.addAttribute("estados",estadosNovos);
			}

			Optional<List<FuncaoUsuario>> funcoes = funcaoUsuarioRepo.recuperaTodasFuncoesUsuario(usuarioDB.getId());
			if(funcoes.isPresent()) {
				List<FuncaoUsuario> funcNovos = new ArrayList<>();

				for(FuncaoUsuario  est: funcoes.get()) {
					Optional<Unidade> unidade = unidadeRepo.findById(est.getIdUnidade());
					est.setNomeUnidade(unidade.get().getNome());
					est.setNomeFuncao(TipoFuncaoUsuarioEnum.recupera(est.getIdFuncao()).getNome());
					funcNovos.add(est);
				}
				model.addAttribute("funcoesUsuario",funcNovos);
			}

			Optional<List<UnidadeUsuario>> unidadesUsuario = unidadeUsuarioRepo.recuperaTodasUnidadesUsuario(usuarioDB.getId());
			List<UnidadeUsuario> unidadesUsuarioNew = new ArrayList<>();
			if(unidadesUsuario.isPresent()) {
				for(UnidadeUsuario u :unidadesUsuario.get() ) {
					Optional<Unidade> und = unidadeRepo.findById(u.getIdUnidade());
					if(und.isPresent()) {
						u.setNomeUnidade(und.get().getNome());
					}
					unidadesUsuarioNew.add(u);
				}
				model.addAttribute("unidadesUsuario",unidadesUsuarioNew);
			}

			model.addAttribute("userLogado", userLogado(recuperarDaSessaoAdmin(session)));
			model.addAttribute("tituloPagina", "Informações do Usuário "+usuarioDB.getNome());
			return new ModelAndView("detalharUsuario", model);

		}else {
			model.addAttribute("mensagemErro", "Desculpe, não foi possível localizar nenhum usuário com o id "+id);
			return preparaListarUsuarios(model, session);
		}
	}

	private ModelAndView preparaDetalharDocumento(Arquivos arq, HttpSession session, ModelMap model) {

		arq.setDataProtocoloFormatada(Conversor.converteDateEmString(arq.getDataProtocolo()));
		arq.setTipoDocumento(tipoDocRepo.findById(arq.getTipo()).get());
		arq.setNomeEstadoAtual(TipoEstadoArquivoEnum.recupera(arq.getIdEstadoAtual()).getNome());
		//arq.setNomePeriodicidade(TipoPeriodicidadeArquivoEnum.recupera(arq.getPeriodicidade()).getNome());
		arq.setArquivo(null);
		model.addAttribute("arquivo", arq);
		model.addAttribute("usuarioSessao", recuperarDaSessaoAdmin(session));

		List<Unidade> unidades = unidadeRepo.findAll();
		model.addAttribute("unidades", unidades);

		List<TipoDocumento> tiposArquivos = tipoDocRepo.findAllAtivos().get();
		List<TipoDocumento> tiposArquivosNovo = new ArrayList<>();
		
		for(TipoDocumento tip: tiposArquivos) {
			GrupoTipoDocumento gr = grupoTipoRepo.findById(tip.getGrupo()).get();
			String nomeSelect =  "<b>Tipo: </b>"+ tip.getNome() + " - <b>Grupo:</b> "+ gr.getNome() + " - <b>Periodicidade: </b>" + Conversor.periodicidadeTipoDocumento(tip.getPeriodicidade()) + " -  <b>Prazo Legal de Guarda(anos):</b> "+tip.getPrazoGuardaAnos();
			tip.setNomeSelect(nomeSelect);
			tiposArquivosNovo.add(tip);
		}
		model.addAttribute("tiposArquivos",tiposArquivosNovo);

		Optional<List<EstadoArquivo>> estados = estadoArquivoRepo.recuperaTodosEstadosArquivo(arq.getId());
		if(estados.isPresent()) {
			List<EstadoArquivo> estadosNovos = new ArrayList<>();

			for(EstadoArquivo est: estados.get()) {
				Optional<Usuario> ug = usuarioRepo.findById(est.getIdUsuarioGravador());
				est.setUsuarioGravador(ug.get());
				est.setNomeEstado(TipoEstadoArquivoEnum.recupera(est.getIdEstado()).getNome());
				estadosNovos.add(est);
			}

			model.addAttribute("estados",estadosNovos);
		}

		if(arq.getIdEstadoAtual() == TipoEstadoArquivoEnum.EM_ANALISE_CONFORMIDADE.getId()) {
			List<Usuario> usuariosUnidade = usuarioRepo.usuariosUnidade(arq.getIdUnidade());
			CheckboxesUsuario checks = new CheckboxesUsuario(usuariosUnidade);
			model.addAttribute("formulario", checks);
		}

		model.addAttribute("codigoUnidade", arq.getIdUnidade());
		model.addAttribute("userLogado", userLogado(recuperarDaSessaoAdmin(session)));
		model.addAttribute("tituloPagina", "Informações do Documento "+arq.getNomeExibicao());
		return new ModelAndView("detalharDocumento", model);
	}

	@RequestMapping(value = "/detalharUnidade", method = RequestMethod.GET)
	public ModelAndView detalharUnidade(@Validated @ModelAttribute("id") Integer id, ModelMap model, HttpSession session){

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(usuSessao != null) {
			Optional<Unidade> unidade = unidadeRepo.findById(id);

			if(unidade.isPresent()) {
				return preparaDetalharUnidade(unidade.get(), model, session);
			}else {
				return preparaListarUnidades("Desculpe, não foi possível localizar nenhuma unidade com o id "+id, model);
			}
		}else {
			return preparaRetornoLogin("", model);
		}
	}

	@RequestMapping(value = "/detalharDocumento", method = RequestMethod.GET)
	public ModelAndView detalharDocumento(@Validated @ModelAttribute("id") Integer id, ModelMap model, HttpSession session){

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(usuSessao != null) {

			Optional<Arquivos> arquivo = arquivoRepo.findById(id);

			if(arquivo.isPresent()) {
				return preparaDetalharDocumento(arquivo.get(), session, model);
			}else {
				return preparaListarUnidades("Desculpe, não foi possível localizar nenhum arquivo com o id "+id, model);
			}
		}else {
			return preparaRetornoLogin("", model);
		}
	}

	private ModelAndView preparaListarUnidades(String mensagem, ModelMap model) {
		List<Unidade> unidades = unidadeRepo.findAll();
		List<Unidade> unidadesNew = new ArrayList<>();

		for (Unidade unid : unidades) {
			unid = complementaObjeto(unid);
			unidadesNew.add(unid);
		}

		model.addAttribute("unidades", unidadesNew);
		model.addAttribute("mensagemSucesso", mensagem);
		model.addAttribute("tituloPagina", "Lista de Unidades Cadastradas");
		return new ModelAndView("listarUnidades", model);
	}

	private ModelAndView preparaDetalharUnidade(Unidade unidade, ModelMap model, HttpSession session) {

//		Optional<Arquivos> arqImagem = arquivoRepo.imagemUnidade(unidade.getId());
//
//		if(arqImagem.isPresent()) {
//			unidade.setIdImagem(arqImagem.get().getId());
//			unidade.setUrlLogo("downloadArquivo?id="+arqImagem.get().getId());
//		}else {
//			unidade.setIdImagem(0);
//		}

		Unidade unidadeDB = unidade;
		unidadeDB = complementaObjeto(unidadeDB);
		model.addAttribute("unidade", unidadeDB);

		List<UnidadesFederacaoEnum> ufs = Arrays.asList(UnidadesFederacaoEnum.values());
		model.addAttribute("ufs",ufs);

		List<TipoUnidade> tipos = tipoUnidadeRepo.findAll();//List<TipoUnidadeEnum> tipos = Arrays.asList(TipoUnidadeEnum.values());
		model.addAttribute("tipos",tipos);
		
		List<Contratantes> contratantes = contratantesRepo.findAll();
		model.addAttribute("contratantes",contratantes);

//		Optional<Arquivos> arqUnidade = arquivoRepo.imagemUnidade(unidadeDB.getId());
//
//		if(arqUnidade.isPresent()) {
//			unidadeDB.setUrlLogo("downloadArquivo?id="+arqUnidade.get().getId());
//		}

		Optional<List<EstadoUnidade>> estados = estadoUnidadeRepo.recuperaTodosEstadosUnidade(unidadeDB.getId());
		if(estados.isPresent()) {
			List<EstadoUnidade> estadosNovos = new ArrayList<>();

			for(EstadoUnidade est: estados.get()) {
				Optional<Usuario> ug = usuarioRepo.findById(est.getIdUsuarioGravador());
				if(ug.isPresent()) {
					est.setUsuGravador(ug.get());
				}

				est.setNomeEstado(TipoEstadoUnidadeEnum.recupera(est.getIdEstado()).getNome());
				estadosNovos.add(est);
			}

			model.addAttribute("estados",estadosNovos);
		}

		List<Arquivos> arquivos = arquivoRepo.obterArquivosPorUnidadeSemBlob(unidadeDB.getId());
		List<Arquivos> arquivosNovos = new ArrayList<>();
		if(arquivos!= null && arquivos.size()>0) {

			for (Arquivos arq : arquivos) {
				arq.setArquivo(null);
				arq.setTipoDocumento(tipoDocRepo.findById(arq.getTipo()).get());
				arq.setNomeEstadoAtual(TipoEstadoArquivoEnum.recupera(arq.getIdEstadoAtual()).getNome());
				//arq.setNomePeriodicidade(TipoPeriodicidadeArquivoEnum.recupera(arq.getPeriodicidade()).getNome());
				arquivosNovos.add(arq);
			}

			model.addAttribute("arquivos",arquivosNovos);
		}

		Optional<List<Usuario>> administradores = usuarioRepo.administradoresUnidade(TipoUsuarioEnum.ADMINISTRADOR.getId(), unidadeDB.getId());
		List<Usuario> administradoresNew =new ArrayList<>();

		if(administradores.isPresent()) {

			for(Usuario u: administradores.get()) {
				u.setFuncao(TipoFuncaoUsuarioEnum.recupera(u.getIdFuncaoAtual()));
				administradoresNew.add(u);
			}

			model.addAttribute("administradores",administradoresNew);
		}
		model.addAttribute("userLogado", userLogado(recuperarDaSessaoAdmin(session)));
		model.addAttribute("tituloPagina", "Informações da Unidade "+unidadeDB.getNome());
		return new ModelAndView("detalharUnidade", model);
	}

	@RequestMapping(value = "/listarDocumentos", method = RequestMethod.GET)
	public ModelAndView listarDocumentos(@ModelAttribute("idEstado") Integer idEstado,  @ModelAttribute("visibilidade") String  visibilidade, ModelMap model, HttpSession session){

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(usuSessao != null) {

			List<Arquivos> arquivos = null;
			
			if(visibilidade.equals("P")) {
				if(usuSessao.getCnpjUnidade().equals(ParametrosIsacSede.cnpjSede)) {
					arquivos = arquivoRepo.obterArquivosPorEstadoAreaPublica(idEstado);
				}else {
					arquivos = arquivoRepo.obterArquivosPorEstadoAndUnidadeAreaPublica(idEstado, usuSessao.getIdUnidadeAtual());
				}
			}else {
				arquivos = arquivoRepo.obterArquivosPorEstadoAreaRestrita(idEstado);
			}

			List<Arquivos> arquivosNovos = new ArrayList<>();
			if(arquivos != null && arquivos.size()>0) {

				for (Arquivos arq : arquivos) {
					arq.setTipoDocumento(tipoDocRepo.findById(arq.getTipo()).get());
					arq.setNomeEstadoAtual(TipoEstadoArquivoEnum.recupera(arq.getIdEstadoAtual()).getNome());
					//arq.setNomePeriodicidade(TipoPeriodicidadeArquivoEnum.recupera(arq.getPeriodicidade()).getNome());
					arquivosNovos.add(arq);
				}

				model.addAttribute("arquivos",arquivosNovos);
			}

			model.addAttribute("userLogado", userLogado(recuperarDaSessaoAdmin(session)));
			model.addAttribute("tituloPagina", "Documento na situação "+TipoEstadoArquivoEnum.recupera(idEstado).getNome());
			model.addAttribute("ocultarBotao", "S");
			return new ModelAndView("listarDocumentos", model);
		}else {
			return preparaRetornoLogin("", model);
		}
	}

	@RequestMapping(value = "/unidades", method = RequestMethod.GET)
	public ModelAndView obterTodasUnidades(ModelMap model, HttpSession session){

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(usuSessao != null) {

			Mensagem podeVerTodas = unidadeRules.podeVerTodasUnidades(usuSessao);
			List<Unidade> unidades = new ArrayList<>();
			if(podeVerTodas.isPermitido()) {
				unidades = unidadeRepo.findAll();
			}else {
				Optional<Unidade> unid = unidadeRepo.findById(usuSessao.getIdUnidadeAtual());
				if(unid.isPresent()) {
					unidades.add(unid.get());
				}
			}

			List<Unidade> unidadesNew = new ArrayList<>();

			for (Unidade unidade : unidades) {
				unidade = complementaObjeto(unidade);
				unidadesNew.add(unidade);
			}

			gravarLog(new Log(0, TipoLogEnum.UNIDADE.getId(), usuSessao.getId(), currentTimestamp(), 
					"Lista de todas as unidades", "UNIDADE", getIp(), "/unidades"));

			model.addAttribute("unidades", unidadesNew);
			model.addAttribute("userLogado", userLogado(usuSessao));
			model.addAttribute("tituloPagina", "Lista de Unidades Cadastradas");
			return new ModelAndView("listarUnidades", model);
		}else {
			return preparaRetornoLogin("Desculpe, para acessar a lista de unidades cadastradas na Área Restrita é necessário estar registrado.", model);
		}
	}

	@RequestMapping(value = "/unidades/porTipo/{tipo}", method = RequestMethod.GET)
	public ResponseEntity<?> obterTodasUnidadesPorTipo(@PathVariable(value = "tipo") Integer tipo, HttpSession session){

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(usuSessao != null) {
			Optional<List<Unidade>> unidades = unidadeRepo.obterUnidadesPorTipo(tipo);

			if(unidades.isPresent()) {
				return new ResponseEntity<List<Unidade>>(unidades.get(), HttpStatus.OK);
			}else {
				return new ResponseEntity<Mensagem>(new Mensagem(false, "Informações sobre Unidadades não localizadas na base de dados."), HttpStatus.NOT_FOUND);
			}
		}else {
			return new ResponseEntity<Mensagem>(new Mensagem(false, "Usuário não registrado. Favor registrar-se novamente."), HttpStatus.FORBIDDEN);
		}
	}

	@RequestMapping(value = "/unidade/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> obterUnidadePorId(@PathVariable(value = "id") Integer id, HttpSession session){
		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(usuSessao != null) {
			Optional<Unidade> unidade = unidadeRepo.findById(id);
			if(unidade.isPresent()) {
				Unidade unidadeX = complementaObjeto(unidade.get());
				gravarLog(new Log(id, TipoLogEnum.UNIDADE.getId(), usuSessao.getId(), currentTimestamp(), 
						"Sucesso na pesquisa de unidade por ID: "+id, "UNIDADE", getIp(), "/unidade(GET)"));
				return new ResponseEntity<Unidade>(unidadeX, HttpStatus.OK);
			}else {
				gravarLog(new Log(id, TipoLogEnum.UNIDADE.getId(), usuSessao.getId(), currentTimestamp(), 
						"Insucesso na pesquisa de unidade por ID: "+id, "UNIDADE", getIp(), "/unidade(GET)"));
				return new ResponseEntity<Mensagem>(new Mensagem(false, "Unidade não localizada na base de dados."),HttpStatus.NOT_FOUND);
			}
		}else {
			return new ResponseEntity<Mensagem>(new Mensagem(false, "Usuário não registrado. Favor registrar-se novamente."), HttpStatus.FORBIDDEN);
		}
	}

	@RequestMapping(value = "/salvarNovaUnidade", method =  RequestMethod.PUT)
	public ModelAndView salvarNovaUnidade(@RequestParam("files") MultipartFile[] files, @RequestParam("files2") MultipartFile[] files2, @Valid @ModelAttribute("unidade") Unidade unidade, BindingResult result, ModelMap model, HttpSession session){

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(usuSessao != null) {

			String cnpjComMascara = unidade.getCnpj();
			//retirar os pontos:
			cnpjComMascara = cnpjComMascara.replace(".", "");
			//retira a barra:
			cnpjComMascara = cnpjComMascara.replace("/", "");
			//retirar o traço:
			cnpjComMascara = cnpjComMascara.replace("-", "");
			
			if(!Validador.isCNPJ(cnpjComMascara)) {
				model.addAttribute("mensagemErro", "Desculpe, o CNPJ informado não é válido. Favor verificar o número informado.");
				List<UnidadesFederacaoEnum> ufs = Arrays.asList(UnidadesFederacaoEnum.values());
				model.addAttribute("ufs",ufs);

				List<TipoUnidade> tipos = tipoUnidadeRepo.obterTiposUnidadesAtivos();//List<TipoUnidadeEnum> tipos = Arrays.asList(TipoUnidadeEnum.values());
				model.addAttribute("tipos",tipos);

				model.addAttribute("unidade", unidade);
				model.addAttribute("userLogado", userLogado(usuSessao));
				model.addAttribute("tituloPagina", "Cadastro de nova unidade");
				return new ModelAndView("formNovaUnidade", model);
			}

			List<Unidade> unidadeBdComMesmoCNPJ = unidadeRepo.obterUnidadesPorCNPJ(cnpjComMascara);

			if(unidadeBdComMesmoCNPJ != null && unidadeBdComMesmoCNPJ.size()>0) {

				for (Unidade unidade2 : unidadeBdComMesmoCNPJ) {
					if(unidade2.getTipoHierarquia().equals("S") && unidade.getTipoHierarquia().equals("S")) {
						model.addAttribute("mensagem", "Desculpe, já existe uma unidade cadastrada com este CNPJ e como Tipo Hierarquia Principal. Id: "+unidade2.getId());
						List<UnidadesFederacaoEnum> ufs = Arrays.asList(UnidadesFederacaoEnum.values());
						model.addAttribute("ufs",ufs);

						List<TipoUnidade> tipos = tipoUnidadeRepo.obterTiposUnidadesAtivos();//List<TipoUnidadeEnum> tipos = Arrays.asList(TipoUnidadeEnum.values());
						model.addAttribute("tipos",tipos);
						
						List<Contratantes> contratantes = contratantesRepo.findAll();
						model.addAttribute("contratantes",contratantes);

						model.addAttribute("unidade", unidade);
						model.addAttribute("userLogado", userLogado(usuSessao));
						model.addAttribute("tituloPagina", "Cadastro de nova unidade");
						return new ModelAndView("formNovaUnidade", model);
					}
				}
			}

			Mensagem podeIncluir = unidadeRules.podeIncluir(usuSessao);

			if(podeIncluir.isPermitido()) {

				unidade.setCnpj(cnpjComMascara);
				unidade.setIdEstadoAtual(TipoEstadoUnidadeEnum.EM_EDICAO.getId());
				unidade.setTsRegistro(currentTimestamp());
				unidadeRepo.save(unidade);

				Unidade u = complementaObjeto(unidade);

				EstadoUnidade estado = new EstadoUnidade();
				estado.setIdUnidade(u.getId());
				estado.setIdEstado(TipoEstadoUnidadeEnum.EM_EDICAO.getId());
				estado.setIdUsuarioGravador(usuSessao.getId());
				estado.setObservacao("Unidade registrada na situação Em Edição.");
				estado.setTsInicio(currentTimestamp());

				estadoUnidadeRepo.save(estado);

				try {
					for(MultipartFile file: files) 
					{
						if(!file.getContentType().contains("image")) {
							model.addAttribute("mensagemErro", "Desculpe, o arquivo informado não é do tipo Imagem. Favor verificar o arquivo informado.");
							List<UnidadesFederacaoEnum> ufs = Arrays.asList(UnidadesFederacaoEnum.values());
							model.addAttribute("ufs",ufs);

							//List<TipoUnidadeEnum> tipos = Arrays.asList(TipoUnidadeEnum.values());
							List<TipoUnidade> tipos = tipoUnidadeRepo.obterTiposUnidadesAtivos();
							model.addAttribute("tipos",tipos);
							
							List<Contratantes> contratantes = contratantesRepo.findAll();
							model.addAttribute("contratantes",contratantes);

							model.addAttribute("unidade", unidade);
							model.addAttribute("userLogado", userLogado(usuSessao));
							model.addAttribute("tituloPagina", "Cadastro de nova unidade");
							return new ModelAndView("formNovaUnidade", model);
						}
						
						Arquivos arquivo = new Arquivos();
						arquivo.setArquivo(file.getBytes());
						arquivo.setMimetype(file.getContentType());
						arquivo.setNome(file.getOriginalFilename());

						double tam = file.getSize();
						tam = (tam/1024)/1024;
						arquivo.setTamanho(String.format("%.2f", tam)+ " MB");
						arquivo.setTsRegistro(currentTimestamp());
						arquivo.setIdUsuario(usuSessao.getId());
						arquivo.setTipo(0);
						arquivo.setDescricao("Imagem da Unidade "+u.getId());
						arquivo.setIdUnidade(u.getId());
						arquivo.setIdEstadoAtual(TipoEstadoArquivoEnum.PUBLICADO.getId());

						arquivoRepo.save(arquivo);

						EstadoArquivo estadoArquivo = new EstadoArquivo();
						estadoArquivo.setIdArquivo(arquivo.getId());
						estadoArquivo.setIdEstado(TipoEstadoArquivoEnum.PUBLICADO.getId());
						estadoArquivo.setIdUsuarioGravador(usuSessao.getId());
						estadoArquivo.setObservacao("Imagem da Unidade "+u.getId());
						estadoArquivo.setTsInicio(currentTimestamp());

						estadoArquivoRepo.save(estadoArquivo);
						
						u.setIdArquivoImagem(arquivo.getId());
						unidadeRepo.save(u);

					}
				} catch (IOException e) {
					//faz nada
				}
				//imagem do organograma
				try {
					for(MultipartFile fileOrg: files2) 
					{
						if(fileOrg.getSize() <= 0) {
							continue;
						}
						
						if(!fileOrg.getContentType().contains("image")) {
							model.addAttribute("mensagemErro", "Desculpe, o arquivo informado não é do tipo Imagem. Favor verificar o arquivo informado.");
							List<UnidadesFederacaoEnum> ufs = Arrays.asList(UnidadesFederacaoEnum.values());
							model.addAttribute("ufs",ufs);

							List<TipoUnidade> tipos = tipoUnidadeRepo.obterTiposUnidadesAtivos();//List<TipoUnidadeEnum> tipos = Arrays.asList(TipoUnidadeEnum.values());
							model.addAttribute("tipos",tipos);
							
							List<Contratantes> contratantes = contratantesRepo.findAll();
							model.addAttribute("contratantes",contratantes);

							model.addAttribute("unidade", unidade);
							model.addAttribute("userLogado", userLogado(usuSessao));
							model.addAttribute("tituloPagina", "Cadastro de nova unidade");
							return new ModelAndView("formNovaUnidade", model);
						}
						
						Arquivos arquivo = new Arquivos();
						arquivo.setArquivo(fileOrg.getBytes());
						arquivo.setMimetype(fileOrg.getContentType());
						arquivo.setNome(fileOrg.getOriginalFilename());

						double tam = fileOrg.getSize();
						tam = (tam/1024)/1024;
						arquivo.setTamanho(String.format("%.2f", tam)+ " MB");
						arquivo.setTsRegistro(currentTimestamp());
						arquivo.setIdUsuario(usuSessao.getId());
						arquivo.setTipo(0);
						arquivo.setDescricao("Imagem do Organograma da Unidade "+u.getId());
						arquivo.setIdUnidade(u.getId());
						arquivo.setIdEstadoAtual(TipoEstadoArquivoEnum.PUBLICADO.getId());

						arquivoRepo.save(arquivo);

						EstadoArquivo estadoArquivo = new EstadoArquivo();
						estadoArquivo.setIdArquivo(arquivo.getId());
						estadoArquivo.setIdEstado(TipoEstadoArquivoEnum.PUBLICADO.getId());
						estadoArquivo.setIdUsuarioGravador(usuSessao.getId());
						estadoArquivo.setObservacao("Imagem da Unidade "+u.getId());
						estadoArquivo.setTsInicio(currentTimestamp());

						estadoArquivoRepo.save(estadoArquivo);
						
						u.setIdArquivoOrganograma(arquivo.getId());
						unidadeRepo.save(u);

					}
				} catch (IOException e) {
					//faz nada
				}

				gravarLog(new Log(u.getId(), TipoLogEnum.UNIDADE.getId(), usuSessao.getId(), currentTimestamp(), 
						"Inclusão de nova unidade ", "UNIDADE", getIp(), "/salvarNovaUnidade"));
				return preparaListarUnidades("Unidade cadastrada com sucesso", model);
			}else {

				model.addAttribute("mensagemErro", podeIncluir.getMensagem());
				List<UnidadesFederacaoEnum> ufs = Arrays.asList(UnidadesFederacaoEnum.values());
				model.addAttribute("ufs",ufs);

				List<TipoUnidade> tipos = tipoUnidadeRepo.obterTiposUnidadesAtivos();//List<TipoUnidadeEnum> tipos = Arrays.asList(TipoUnidadeEnum.values());
				model.addAttribute("tipos",tipos);
				
				List<Contratantes> contratantes = contratantesRepo.findAll();
				model.addAttribute("contratantes",contratantes);

				model.addAttribute("unidade", unidade);
				model.addAttribute("userLogado", userLogado(usuSessao));
				model.addAttribute("tituloPagina", "Cadastro de nova unidade");
				return new ModelAndView("formNovaUnidade", model);
			}
		}else {
			return preparaRetornoLogin("",model);
		}
	}

	@RequestMapping(value = "/alterarSituacaoUnidade", method =  RequestMethod.GET)
	public ModelAndView alterarSituacaoUnidade(@Validated @ModelAttribute("id") Integer idUnidade, @ModelAttribute("novaSituacao") Integer idSituacaoNova, ModelMap model, HttpSession session){

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(usuSessao != null) {
			Optional<Unidade> oldUnidade = unidadeRepo.findById(idUnidade);

			if(oldUnidade.isPresent()) {

				if(idSituacaoNova == TipoEstadoUnidadeEnum.EM_EDICAO.getId()) {
					Mensagem podeAlterarEdicao = unidadeRules.podeAlterarParaEmEdicao(usuSessao, oldUnidade.get());

					if(podeAlterarEdicao.isPermitido()) {
						executaAlteracaoSituacaoUnidade(oldUnidade.get(), usuSessao, idSituacaoNova);
						model.addAttribute("mensagemSucesso", "Unidade alterada para a Situação Em Edição.");
						return preparaDetalharUnidade(oldUnidade.get(), model, session);
					}else {
						model.addAttribute("mensagemErro", podeAlterarEdicao.getMensagem());
						return preparaDetalharUnidade(oldUnidade.get(), model, session);
					}

				}else if(idSituacaoNova == TipoEstadoUnidadeEnum.PUBLICADO.getId()) {
					Mensagem podeAlterarPublicada = unidadeRules.podeAlterarParaPublicada(usuSessao, oldUnidade.get());

					if(podeAlterarPublicada.isPermitido()) {
						executaAlteracaoSituacaoUnidade(oldUnidade.get(), usuSessao, idSituacaoNova);
						model.addAttribute("mensagemSucesso", "Unidade Publicada. Ficará disponível para exibição no Portal de Transparência e seus documentos já publicados visíveis ao público geral.");
						return preparaDetalharUnidade(oldUnidade.get(), model, session);
					}else {
						model.addAttribute("mensagemErro", podeAlterarPublicada.getMensagem());
						return preparaDetalharUnidade(oldUnidade.get(), model, session);
					}

				}else if(idSituacaoNova == TipoEstadoUnidadeEnum.DESATIVADA.getId()) {
					List<Usuario> usuarios = usuarioRepo.usuariosUnidade(oldUnidade.get().getId());
					Mensagem podeAlterarDesativada = unidadeRules.podeAlterarParaDesativada(usuSessao, oldUnidade.get(), usuarios);

					if(podeAlterarDesativada.isPermitido()) {
						executaAlteracaoSituacaoUnidade(oldUnidade.get(), usuSessao, idSituacaoNova);
						model.addAttribute("mensagemSucesso", "Unidade desativada. Os documentos serão mantidos e vinculados à unidade para pesquisas internas.");
						return preparaDetalharUnidade(oldUnidade.get(), model, session);
					}else {
						model.addAttribute("mensagemErro", podeAlterarDesativada.getMensagem());
						return preparaDetalharUnidade(oldUnidade.get(), model, session);
					}
				}else {
					model.addAttribute("mensagemErro", "O código da situação informada não foi localizada entre as possíveis para o registro de Unidades. Favor verificar.");
					return preparaDetalharUnidade(oldUnidade.get(), model, session);
				}
			}else {
				model.addAttribute("mensagemErro", "Unidade não localizada na base de informações");
				return preparaDetalharUnidade(oldUnidade.get(), model, session);
			}
		}else {
			return preparaRetornoLogin("Usuário não registrado. Favor registrar-se novamente.", model);
		}
	}

	private Unidade executaAlteracaoSituacaoUnidade(Unidade unidade, Usuario usuSessao, Integer idSituacaoNova) {
		Unidade nova = unidade;
		nova.setIdEstadoAtual(idSituacaoNova);

		unidadeRepo.save(nova);

		Optional<EstadoUnidade> estadoAtual = estadoUnidadeRepo.recuperaEstadoAtualUnidade(nova.getId());

		if(estadoAtual.isPresent()) {
			EstadoUnidade estadoA = estadoAtual.get();
			estadoA.setTsFim(currentTimestamp());
			estadoUnidadeRepo.save(estadoA);

			EstadoUnidade novoEstado = new EstadoUnidade();
			novoEstado.setIdEstado(idSituacaoNova);
			novoEstado.setIdUnidade(nova.getId());
			novoEstado.setIdUsuarioGravador(usuSessao.getId());
			novoEstado.setObservacao("Alteração de situação para "+TipoEstadoUnidadeEnum.recupera(idSituacaoNova));
			novoEstado.setTsInicio(currentTimestamp());

			estadoUnidadeRepo.save(novoEstado);
		}

		gravarLog(new Log(nova.getId(), TipoLogEnum.UNIDADE.getId(), usuSessao.getId(), currentTimestamp(), 
				"Alteração de Situação de Unidade. De: "+estadoAtual.get().getIdEstado()+ " para "+ idSituacaoNova, "UNIDADE", getIp(), "/alteraSituacaoUnidade"));

		return nova;
	}

	@RequestMapping(value = "/alterarUnidade", method =  RequestMethod.PUT)
	public ModelAndView alterarUnidade(@RequestParam("files") MultipartFile[] files, @RequestParam("files2") MultipartFile[] files2, @Valid @ModelAttribute("unidade") Unidade unidade, HttpSession session, ModelMap model){

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(usuSessao != null) {

			String cnpjComMascara = unidade.getCnpj();
			//retirar os pontos:
			cnpjComMascara = cnpjComMascara.replace(".", "");
			//retira a barra:
			cnpjComMascara = cnpjComMascara.replace("/", "");
			//retirar o traço:
			cnpjComMascara = cnpjComMascara.replace("-", "");
			
			unidade.setCnpj(cnpjComMascara);

			Optional<Unidade> oldUnidade = unidadeRepo.findById(unidade.getId());

			Mensagem podeAlterar = unidadeRules.podeAlterar(usuSessao, unidade);

			if(podeAlterar.isPermitido()) {
				if(oldUnidade.isPresent()){
					
					if(!Validador.isCNPJ(cnpjComMascara)) {
						model.addAttribute("mensagemErro", "Desculpe, o CNPJ informado não é válido. Favor verificar o número informado.");
						return preparaAlterarUnidade(oldUnidade.get().getId(), session, model);
					}

					List<Unidade> outraUnidadeComMesmoCNPJ = unidadeRepo.obterUnidadesPorCNPJ(cnpjComMascara);

					if(outraUnidadeComMesmoCNPJ != null && outraUnidadeComMesmoCNPJ.size()>0) {

						for (Unidade unidade2 : outraUnidadeComMesmoCNPJ) {
							if(unidade2.getId() != oldUnidade.get().getId()) {
								if(unidade2.getTipoHierarquia().equals("S") && unidade.getTipoHierarquia().equals("S")) {
									//está tentando incluir o cnpj de outra unidade já cadastrada
									model.addAttribute("mensagemErro", "Desculpe, o CNPJ informado nesta tentativa de alteração já pertence a outra unidade cadastrada como Principal. Id: "+unidade2.getId());
									return preparaAlterarUnidade(oldUnidade.get().getId(), session, model);
								}
							}
						}
					}

					//Optional<Arquivos> arq = arquivoRepo.imagemUnidade(unidade.getId());
					Integer idArqImagem = oldUnidade.get().getIdArquivoImagem();
					Integer idArqOrganograma = oldUnidade.get().getIdArquivoOrganograma();
					
					if(null != idArqImagem && idArqImagem > 0) {
						unidade.setIdArquivoImagem(idArqImagem);
					}else {
						unidade.setIdArquivoImagem(0);
					}
					if(null != idArqOrganograma && idArqOrganograma > 0) {
						unidade.setIdArquivoOrganograma(idArqOrganograma);
					}else {
						unidade.setIdArquivoOrganograma(0);
					}

					//imagem da unidade
					try {
						for(MultipartFile file: files) 
						{
							if( file.getSize() <= 0 ) {
								continue;
							}
							
							Arquivos arquivo = new Arquivos();

							if(idArqImagem!=null && idArqImagem>0) {
								arquivo.setId(idArqImagem);
							}
							
							if(!file.getContentType().contains("image")) {
								model.addAttribute("mensagemErro", "Desculpe, o arquivo informado não é do tipo Imagem. Favor verificar o arquivo informado.");
								return preparaAlterarUnidade(oldUnidade.get().getId(), session, model);
							}
							
							arquivo.setArquivo(file.getBytes());
							arquivo.setMimetype(file.getContentType());
							arquivo.setNome(file.getOriginalFilename());

							double tam = file.getSize();
							tam = (tam/1024)/1024;
							arquivo.setTamanho(String.format("%.2f", tam)+ " MB");
							arquivo.setTsRegistro(currentTimestamp());
							arquivo.setIdUsuario(usuSessao.getId());
							arquivo.setTipo(0);
							arquivo.setDescricao("Imagem da Unidade "+unidade.getId());
							arquivo.setIdUnidade(unidade.getId());
							arquivo.setIdEstadoAtual(TipoEstadoArquivoEnum.PUBLICADO.getId());

							arquivoRepo.save(arquivo);

							EstadoArquivo estadoArquivo = new EstadoArquivo();
							estadoArquivo.setIdArquivo(arquivo.getId());
							estadoArquivo.setIdEstado(TipoEstadoArquivoEnum.PUBLICADO.getId());
							estadoArquivo.setIdUsuarioGravador(usuSessao.getId());
							estadoArquivo.setObservacao("Imagem da Unidade "+unidade.getId());
							estadoArquivo.setTsInicio(currentTimestamp());

							estadoArquivoRepo.save(estadoArquivo);
							
							unidade.setIdArquivoImagem(arquivo.getId());
							unidadeRepo.save(unidade);

						}
					} catch (IOException e) {
						// faz nada
					}
					//organograma
					try {
						for(MultipartFile fileOrg: files2) 
						{
							if( fileOrg.getSize() <= 0 ) {
								continue;
							}
							
							Arquivos arquivo = new Arquivos();

							if(idArqOrganograma!=null && idArqOrganograma>0) {
								arquivo.setId(idArqOrganograma);
							}
							
							if(!fileOrg.getContentType().contains("image")) {
								model.addAttribute("mensagemErro", "Desculpe, o arquivo informado não é do tipo Imagem. Favor verificar o arquivo informado.");
								return preparaAlterarUnidade(oldUnidade.get().getId(), session, model);
							}
							
							arquivo.setArquivo(fileOrg.getBytes());
							arquivo.setMimetype(fileOrg.getContentType());
							arquivo.setNome(fileOrg.getOriginalFilename());

							double tam = fileOrg.getSize();
							tam = (tam/1024)/1024;
							arquivo.setTamanho(String.format("%.2f", tam)+ " MB");
							arquivo.setTsRegistro(currentTimestamp());
							arquivo.setIdUsuario(usuSessao.getId());
							arquivo.setTipo(0);
							arquivo.setDescricao("Imagem do Organograma da Unidade "+unidade.getId());
							arquivo.setIdUnidade(unidade.getId());
							arquivo.setIdEstadoAtual(TipoEstadoArquivoEnum.PUBLICADO.getId());

							arquivoRepo.save(arquivo);

							EstadoArquivo estadoArquivo = new EstadoArquivo();
							estadoArquivo.setIdArquivo(arquivo.getId());
							estadoArquivo.setIdEstado(TipoEstadoArquivoEnum.PUBLICADO.getId());
							estadoArquivo.setIdUsuarioGravador(usuSessao.getId());
							estadoArquivo.setObservacao("Imagem do Organograma da Unidade "+unidade.getId());
							estadoArquivo.setTsInicio(currentTimestamp());

							estadoArquivoRepo.save(estadoArquivo);
							
							unidade.setIdArquivoOrganograma(arquivo.getId());
							unidadeRepo.save(unidade);

						}
					} catch (IOException e) {
						// faz nada
					}

					unidade.setIdEstadoAtual(oldUnidade.get().getIdEstadoAtual());
					unidade.setTsRegistro(currentTimestamp());
					unidadeRepo.save(unidade);
					Optional<EstadoUnidade> estadoAtual = estadoUnidadeRepo.recuperaEstadoAtualUnidade(unidade.getId());

					if(estadoAtual.get().getIdEstado() != unidade.getIdEstadoAtual()) {
						estadoAtual.get().setTsFim(currentTimestamp());
						estadoUnidadeRepo.save(estadoAtual.get());

						EstadoUnidade estadoNovo = new EstadoUnidade();
						estadoNovo.setIdUnidade(unidade.getId());
						estadoNovo.setIdUsuarioGravador(usuSessao.getId());
						estadoNovo.setObservacao("Estado da Unidade alterado para "+ TipoEstadoUnidadeEnum.recupera(unidade.getIdEstadoAtual()));
						estadoNovo.setTsInicio(currentTimestamp());
						estadoUnidadeRepo.save(estadoNovo);
					}

					unidade = complementaObjeto(unidade);

					gravarLog(new Log(unidade.getId(), TipoLogEnum.UNIDADE.getId(), usuSessao.getId(), currentTimestamp(), 
							"Alteração de unidade ", "UNIDADE", getIp(), "/alterarUnidade"));

					model.addAttribute("mensagemSucesso", "Informações da Unidade alteradas com sucesso");
					return preparaDetalharUnidade(unidade, model, session);
				}else {
					return preparaListarUnidades("Unidade não localizada na base de dados.", model);
				}
			}else {
				model.addAttribute("mensagemErro", podeAlterar.getMensagem());
				return preparaDetalharUnidade(unidade, model, session);
			}
		}else {
			return preparaRetornoLogin("",model);
		}
	}

	@RequestMapping(value = "/excluirUnidade", method = RequestMethod.GET)
	public ModelAndView excluirUnidade(@Valid @ModelAttribute("idExcluir") Integer id, HttpSession session, ModelMap model){

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(usuSessao != null) {
			Optional<Unidade> unidade = unidadeRepo.findById(id);

			if(unidade.isPresent()){

				String log = "Unidade id: "+id + " - "+unidade.get().getNome() + " excluída por CPF: "+ usuSessao.getCpf();
				Optional<List<EstadoUnidade>> todosEstados = estadoUnidadeRepo.recuperaTodosEstadosUnidade(id);

				Mensagem podeExcluir = unidadeRules.podeExcluir(usuSessao, unidade.get(), todosEstados.get());

				if(podeExcluir.isPermitido()) {

					for(EstadoUnidade est: todosEstados.get()) {
						estadoUnidadeRepo.delete(est);
					}

					unidadeRepo.delete(unidade.get());

					gravarLog(new Log(id, TipoLogEnum.UNIDADE.getId(), usuSessao.getId(), currentTimestamp(), 
							log, "UNIDADE", getIp(), "/excluirUnidade"));

					model.addAttribute("mensagemSucesso", "Unidade excluída com sucesso");
					return preparaListarUnidades("", model);
				}else {
					model.addAttribute("mensagemErro", podeExcluir.getMensagem());
					return preparaDetalharUnidade(unidade.get(), model, session);
				}
			}else {
				return preparaListarUnidades("Unidade não localizada na base de dados.", model);
			}
		}else {
			return preparaRetornoLogin("",model);
		}
	}

	/**FIM DOS METODOS DE UNIDADE*/

	/**ARQUIVOS**/
	@RequestMapping(value = "/arquivos", method = RequestMethod.GET)
	public ResponseEntity<?> obterTodosArquivos(HttpSession session){

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(usuSessao != null) {
			List<Arquivos> arquivos = arquivoRepo.findAll();

			gravarLog(new Log(0, TipoLogEnum.DOCUMENTO.getId(), usuSessao.getId(), currentTimestamp(), 
					"Lista de todos os documentos", "DOCUMENTO", getIp(), "/arquivos (GET)"));

			return new ResponseEntity<List<Arquivos>>(arquivos, HttpStatus.OK);
		}else {
			return new ResponseEntity<Mensagem>(new Mensagem(false, "Usuário não registrado. Favor registrar-se novamente."), HttpStatus.FORBIDDEN);
		}
	}

//	@RequestMapping(value = "/arquivos/porTipo/{tipo}", method = RequestMethod.GET)
//	public ResponseEntity<?> obterArquivosPorTipo(@PathVariable(value = "tipo") Integer tipo, HttpSession session){
//
//		Usuario usuSessao = recuperarDaSessaoAdmin(session);
//
//		if(usuSessao != null) {
//			List<Arquivos> arquivos = arquivoRepo.obterArquivosPorTipoSemBlob(tipo);
//
//			gravarLog(new Log(0, TipoLogEnum.DOCUMENTO.getId(), usuSessao.getId(), currentTimestamp(), 
//					"Lista de todos os documentos por tipo: "+tipo, "DOCUMENTO", getIp(), "/arquivos/porTipo/{tipo}"));
//
//			if(arquivos !=null && arquivos.size()>0) {
//				return new ResponseEntity<List<Arquivos>>(arquivos, HttpStatus.OK);
//			}else {
//				return new ResponseEntity<Mensagem>(new Mensagem(false, "Lista de arquivos por tipo não encontrada na base de dados."), HttpStatus.NOT_FOUND);
//			}
//		}else {
//			return new ResponseEntity<Mensagem>(new Mensagem(false, "Usuário não registrado. Favor registrar-se novamente."), HttpStatus.FORBIDDEN);
//		}
//	}

//	@RequestMapping(value = "/arquivos/porUnidade/{idUnidade}", method = RequestMethod.GET)
//	public ResponseEntity<?> obterArquivosPorUnidade(@PathVariable(value = "idUnidade") Integer idUnidade, HttpSession session){
//
//		Usuario usuSessao = recuperarDaSessaoAdmin(session);
//
//		if(usuSessao != null) {
//			Optional<List<UnidadeArquivo>> arquivosUnidade = unidadeArquivoRepo.obterArquivosPorUnidade(idUnidade);
//			List<Arquivos> arquivos = new ArrayList<>();
//
//			gravarLog(new Log(0, TipoLogEnum.DOCUMENTO.getId(), usuSessao.getId(), currentTimestamp(), 
//					"Lista de todos os documentos por unidade: "+idUnidade, "DOCUMENTO", getIp(), "/arquivos/porTipo/{tipo}"));
//
//			if(arquivosUnidade.isPresent()) {
//
//				for(UnidadeArquivo unArq : arquivosUnidade.get()) {
//					Optional<Arquivos> arq = arquivoRepo.findById(unArq.getIdArquivo());
//					if(arq.isPresent()) {
//						arquivos.add(arq.get());
//					}
//				}
//
//				return new ResponseEntity<List<Arquivos>>(arquivos, HttpStatus.OK);
//			}else {
//				return new ResponseEntity<Mensagem>(new Mensagem(false, "Lista de arquivos por Unidade não encontrada na base de dados."), HttpStatus.NOT_FOUND);
//			}
//		}else {
//			return new ResponseEntity<Mensagem>(new Mensagem(false, "Usuário não registrado. Favor registrar-se novamente."), HttpStatus.FORBIDDEN);
//		}
//	}

//	@RequestMapping(value = "/arquivo/{id}", method = RequestMethod.GET)
//	public ResponseEntity<?> obterArquivoPorId(@PathVariable(value = "id") Integer id, HttpSession session){
//
//		Usuario usuSessao = recuperarDaSessaoAdmin(session);
//
//		if(usuSessao !=null) {
//			Optional<Arquivos> arquivo = arquivoRepo.findById(id);
//
//			if(arquivo.isPresent()) {
//				gravarLog(new Log(0, TipoLogEnum.DOCUMENTO.getId(), usuSessao.getId(), currentTimestamp(), 
//						"Sucesso na pesquisa de arquivo por ID: "+id, "DOCUMENTO", getIp(), "/arquivo/{id}"));
//				return new ResponseEntity<Arquivos>(arquivo.get(), HttpStatus.OK);
//			}else {
//				gravarLog(new Log(0, TipoLogEnum.DOCUMENTO.getId(), usuSessao.getId(), currentTimestamp(), 
//						"Insucesso na pesquisa de arquivo por ID: "+id, "DOCUMENTO", getIp(), "/arquivo/{id}"));
//
//				return new ResponseEntity<Mensagem>(new Mensagem(false, "Arquivo não encontrado na base de dados."), HttpStatus.NOT_FOUND);
//			}
//		}else {
//			return new ResponseEntity<Mensagem>(new Mensagem(false, "Usuário não registrado. Favor registrar-se novamente."), HttpStatus.FORBIDDEN);
//		}
//	}

//	@RequestMapping(value = "/arquivo", method =  RequestMethod.POST)dfgdfg
//	public ResponseEntity<?> salvarNovoArquivo(@RequestParam("files") MultipartFile[] files, @Valid @RequestBody Arquivos arquivo, HttpSession session){
//
//		Usuario usuSessao = recuperarDaSessaoAdmin(session);
//
//		if(usuSessao !=null) {
//
//			try {
//				for(MultipartFile file: files) {
//					arquivo.setArquivo(file.getBytes());
//					arquivo.setMimetype(file.getContentType());
//					arquivo.setNome(file.getOriginalFilename());
//
//					double tam = file.getSize();
//					tam = (tam/1024)/1024;
//					arquivo.setTamanho(String.format("%.2f", tam)+ " MB");
//					arquivo.setTsRegistro(currentTimestamp());
//					arquivo.setIdUsuario(usuSessao.getId());
//
//					arquivoRepo.save(arquivo);
//
//					EstadoArquivo estado = new EstadoArquivo();
//					estado.setIdArquivo(arquivo.getId());
//					estado.setIdEstado(TipoEstadoArquivoEnum.EM_EDICAO.getId());
//					estado.setIdUsuarioGravador(usuSessao.getId());
//					estado.setObservacao("Documento registrado na situação Em Edição.");
//					estado.setTsInicio(currentTimestamp());
//
//					estadoArquivoRepo.save(estado);
//
//					MovimentoArquivo mov = new MovimentoArquivo();
//					mov.setIdArquivo(arquivo.getId());
//					mov.setIdTipoMovimento(TipoMovimentoArquivoEnum.EM_EDICAO.getId());
//					mov.setIdUsuarioGravador(usuSessao.getId());
//					mov.setIp(getIp());
//					mov.setObservacao("Inclusão de documento Em Edição");
//					mov.setTsInicio(currentTimestamp());
//
//					movimentoArquivoRepo.save(mov);
//
//					UnidadeArquivo unidade = new UnidadeArquivo();
//					unidade.setIdArquivo(arquivo.getId());
//					unidade.setIdUnidade(arquivo.getIdUnidade());
//					unidade.setIdUsuarioGravador(usuSessao.getId());
//					unidade.setObservacao("Documento id: "+arquivo.getId() + " vinculado a Unidade id: "+ arquivo.getIdUnidade()+".");
//					unidade.setTsInicio(currentTimestamp());
//
//					unidadeArquivoRepo.save(unidade);
//
//					gravarLog(new Log(arquivo.getId(), TipoLogEnum.DOCUMENTO.getId(), usuSessao.getId(), currentTimestamp(), 
//							"Sucesso na inclusão de novo documento. ID: "+arquivo.getId(), "DOCUMENTO", getIp(), "/arquivo/{POST}"));
//				}
//			} catch (IOException e) {
//				gravarLog(new Log(0, TipoLogEnum.DOCUMENTO.getId(), usuSessao.getId(), currentTimestamp(), 
//						"Insucesso na inclusão de novo documento. ", "DOCUMENTO", getIp(), "/arquivo/{POST}"));
//				return new ResponseEntity<Mensagem>(new Mensagem(false, "Insucesso na inclusão de novo documento."), HttpStatus.NOT_FOUND);
//			}
//
//			return new ResponseEntity<Arquivos>(arquivo, HttpStatus.OK);
//
//		}else {
//			return new ResponseEntity<Mensagem>(new Mensagem(false, "Usuário não registrado. Favor registrar-se novamente."), HttpStatus.FORBIDDEN);
//		}
//	}

	@RequestMapping(value = "/excluirArquivo", method = RequestMethod.GET)
	public ModelAndView excluirArquivo(@Valid @ModelAttribute("idExcluir") Integer id, HttpSession session, ModelMap model){

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(usuSessao !=null) {

			Optional<Arquivos> arq = arquivoRepo.findById(id);

			if(arq.isPresent()) {

				Optional<Unidade> unidadeArquivo = unidadeRepo.findById(arq.get().getIdUnidade());

				Optional<List<EstadoArquivo>> estados = estadoArquivoRepo.recuperaTodosEstadosArquivo(id);

				if(estados.isPresent()) {
					Mensagem podeExcluir = arquivoRules.podeExcluir(arq.get(), estados.get());
					String msgLog = "Arquivo "+arq.get().getNome() + "excluído com sucesso";

					if(podeExcluir.isPermitido()) {

						Optional<List<MovimentoArquivo>> movs = movimentoArquivoRepo.recuperaTodosMovimentosArquivo(id);

						if(movs.isPresent()) {
							for(MovimentoArquivo m : movs.get()) {
								movimentoArquivoRepo.delete(m);
							}
						}

						for(EstadoArquivo e : estados.get()) {
							estadoArquivoRepo.delete(e);
						}

						Optional<List<UnidadeArquivo>> unidades = unidadeArquivoRepo.obterUnidadesPorArquivo(id);

						if(unidades.isPresent()) {
							for(UnidadeArquivo u : unidades.get()) {
								unidadeArquivoRepo.delete(u);
							}
						}

						arquivoRepo.delete(arq.get());

						gravarLog(new Log(0, TipoLogEnum.DOCUMENTO.getId(), usuSessao.getId(), currentTimestamp(), 
								msgLog, "DOCUMENTO", getIp(), "/excluirArquivo"));

						model.addAttribute("mensagemSucesso", "Documento excluído com sucesso.");
						return preparaDetalharUnidade(unidadeArquivo.get(), model, session); //new ResponseEntity<Mensagem>(new Mensagem(true, "Sucesso na exclusão do arquivo"), HttpStatus.OK);
					}else {
						model.addAttribute("mensagemErro", podeExcluir.getMensagem());
						return preparaDetalharDocumento(arq.get(), session, model);//new ResponseEntity<Mensagem>(new Mensagem(false, podeExcluir.getMensagem()), HttpStatus.NOT_FOUND);
					}
				}else {
					model.addAttribute("mensagemErro", "Desculpe, O arquivo informado não foi localizado.");
					return preparaDetalharDocumento(arq.get(), session, model);
				}
			}else {
				model.addAttribute("mensagemErro", "Desculpe, O arquivo informado não foi localizado.");
				return preparaDashBoard(model, session);
			}
		}else {
			return preparaRetornoLogin("", model);
		}
	}

	@RequestMapping(value = "/alteraSituacaoArquivo", method =  RequestMethod.GET)
	public ModelAndView alterarSituacaoArquivo(@Validated @ModelAttribute("idArquivo") Integer idArquivo, 
			@ModelAttribute("idNovaSituacao") Integer idNovaSituacao, 
			@ModelAttribute("justificativaAlteracaoArquivo") String justificativaAlteracaoArquivo, @Validated @ModelAttribute("formulario") CheckboxesUsuario usuarios,
			ModelMap model, HttpSession session){

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(usuSessao != null) {

			Optional<Arquivos> arquivo = arquivoRepo.findById(idArquivo);

			if(arquivo.isPresent()) {

				if(justificativaAlteracaoArquivo == null) {
					model.addAttribute("mensagemErro", "Desculpe, o campo de justificativa precisa ser preenchido");
					return preparaDetalharDocumento(arquivo.get(), session, model);
				}
				if(justificativaAlteracaoArquivo.length()>1500) {
					model.addAttribute("mensagemErro", "Desculpe, o campo de justificativa não deve possuir mais de 1500 caracteres. Foram incluídos "+justificativaAlteracaoArquivo.length());
					return preparaDetalharDocumento(arquivo.get(), session, model);
				}

				String nomeNovoEstado = TipoEstadoArquivoEnum.recupera(idNovaSituacao).getNome();

				if(idNovaSituacao == TipoEstadoArquivoEnum.EM_EDICAO.getId()) {

					Mensagem podeAlterarEdicao = arquivoRules.podeAlterarParaEmEdicao(arquivo.get(), usuSessao);

					if(podeAlterarEdicao.isPermitido()) {
						executaAlteracaoSituacaoArquivo(arquivo.get(), usuSessao, idNovaSituacao, justificativaAlteracaoArquivo);
						model.addAttribute("mensagemSucesso", "Situação do documento alterada com sucesso para "+nomeNovoEstado);
						return preparaDetalharDocumento(arquivo.get(), session, model);
					}else {
						model.addAttribute("mensagemErro", podeAlterarEdicao.getMensagem());
						return preparaDetalharDocumento(arquivo.get(), session, model);
					}

				}else if(idNovaSituacao == TipoEstadoArquivoEnum.EM_ANALISE_CONFORMIDADE.getId()) {

					Mensagem podeAlterarEmAnalise = arquivoRules.podeAlterarParaEmAnalise(arquivo.get(), usuSessao);

					if(podeAlterarEmAnalise.isPermitido()) {
						executaAlteracaoSituacaoArquivo(arquivo.get(), usuSessao, idNovaSituacao, justificativaAlteracaoArquivo);
						Optional<Unidade> unidadeDoArquivo = unidadeRepo.findById(arquivo.get().getIdUnidade());

						montarEnviarEmailParaCompliance(usuSessao, arquivo.get(), unidadeDoArquivo.get());

						model.addAttribute("mensagemSucesso", "Situação do documento alterada com sucesso para "+nomeNovoEstado+". E-mail encaminhado à equipe de Compliance.");
						return preparaDetalharDocumento(arquivo.get(), session, model);
					}else {
						model.addAttribute("mensagemErro", podeAlterarEmAnalise.getMensagem());
						return preparaDetalharDocumento(arquivo.get(), session, model);
					}

				}else if(idNovaSituacao == TipoEstadoArquivoEnum.CONFORME.getId()) {

					Mensagem podeAlterarConforme = arquivoRules.podeAlterarParaConforme(arquivo.get(), usuSessao);

					if(podeAlterarConforme.isPermitido()) {
						executaAlteracaoSituacaoArquivo(arquivo.get(), usuSessao, idNovaSituacao, justificativaAlteracaoArquivo);
						boolean enviouEmail = false;
						if(usuarios != null && usuarios.getUsuarios() != null && usuarios.getUsuarios().size()>0) {
							for (Usuario usu : usuarios.getUsuarios()) {
								if(usu.getId() != null && usu.getId()>0) {
									Optional<Usuario> userEmail = usuarioRepo.findById(usu.getId());
									if(userEmail.isPresent()) {
										montarEnviarEmailCompliance(userEmail.get(), arquivo.get());
										enviouEmail = true;
									}
								}
							}
						}

						model.addAttribute("mensagemSucesso", "Situação do documento alterada com sucesso para "+nomeNovoEstado+". "+(enviouEmail?"E-mail encaminhado aos colaboradores selecionados.":""));
						return preparaDetalharDocumento(arquivo.get(), session, model);
					}else {
						model.addAttribute("mensagemErro", podeAlterarConforme.getMensagem());
						return preparaDetalharDocumento(arquivo.get(), session, model);
					}

				}else if(idNovaSituacao == TipoEstadoArquivoEnum.NAO_CONFORME.getId()) {

					Mensagem podeAlterarNaoConforme = arquivoRules.podeAlterarParaNaoConforme(arquivo.get(), usuSessao);

					if(podeAlterarNaoConforme.isPermitido()) {
						executaAlteracaoSituacaoArquivo(arquivo.get(), usuSessao, idNovaSituacao, justificativaAlteracaoArquivo);
						boolean enviouEmail = false;
						if(usuarios != null && usuarios.getUsuarios() != null && usuarios.getUsuarios().size()>0) {
							for (Usuario usu : usuarios.getUsuarios()) {
								if(usu.getId() != null && usu.getId()>0) {
									Optional<Usuario> userEmail = usuarioRepo.findById(usu.getId());
									if(userEmail.isPresent()) {
										montarEnviarEmailCompliance(userEmail.get(), arquivo.get());
										enviouEmail = true;
									}
								}
							}
						}

						model.addAttribute("mensagemSucesso", "Situação do documento alterada com sucesso para "+nomeNovoEstado+". "+(enviouEmail?"E-mail encaminhado aos colaboradores selecionados.":""));
						return preparaDetalharDocumento(arquivo.get(), session, model);
					}else {
						model.addAttribute("mensagemErro", podeAlterarNaoConforme.getMensagem());
						return preparaDetalharDocumento(arquivo.get(), session, model);
					}

				}else if(idNovaSituacao == TipoEstadoArquivoEnum.PUBLICADO.getId()) {

					Mensagem podeAlterarPublicado = arquivoRules.podeAlterarParaPublicado(arquivo.get(), usuSessao);

					if(podeAlterarPublicado.isPermitido()) {
						executaAlteracaoSituacaoArquivo(arquivo.get(), usuSessao, idNovaSituacao, justificativaAlteracaoArquivo);
						model.addAttribute("mensagemSucesso", "Situação do documento alterada com sucesso para "+nomeNovoEstado);
						return preparaDetalharDocumento(arquivo.get(), session, model);
					}else {
						model.addAttribute("mensagemErro", podeAlterarPublicado.getMensagem());
						return preparaDetalharDocumento(arquivo.get(), session, model);
					}

				}else if(idNovaSituacao == TipoEstadoArquivoEnum.DEVOLVIDO.getId()) {

					Mensagem podeAlterarDevolvido = arquivoRules.podeAlterarParaDevolvido(arquivo.get(), usuSessao);

					if(podeAlterarDevolvido.isPermitido()) {
						executaAlteracaoSituacaoArquivo(arquivo.get(), usuSessao, idNovaSituacao, justificativaAlteracaoArquivo);
						model.addAttribute("mensagemSucesso", "Situação do documento alterada com sucesso para "+nomeNovoEstado);
						return preparaDetalharDocumento(arquivo.get(), session, model);
					}else {
						model.addAttribute("mensagemErro", podeAlterarDevolvido.getMensagem());
						return preparaDetalharDocumento(arquivo.get(), session, model);
					}

				}else if(idNovaSituacao == TipoEstadoArquivoEnum.DESATIVADO.getId()) {
					Mensagem podeAlterarDesativado = arquivoRules.podeAlterarParaDesativado(arquivo.get(), usuSessao);

					if(podeAlterarDesativado.isPermitido()) {
						executaAlteracaoSituacaoArquivo(arquivo.get(), usuSessao, idNovaSituacao, justificativaAlteracaoArquivo);
						model.addAttribute("mensagemSucesso", "Situação do documento alterada com sucesso para "+nomeNovoEstado);
						return preparaDetalharDocumento(arquivo.get(), session, model);
					}else {
						model.addAttribute("mensagemErro", podeAlterarDesativado.getMensagem());
						return preparaDetalharDocumento(arquivo.get(), session, model);
					}

				}else {
					model.addAttribute("mensagemErro", "O código da situação informada não foi localizada entre as possíveis para o registro de Documento. Favor verificar.");
					return preparaDetalharDocumento(arquivo.get(), session, model);
				}
			}else {
				return preparaListarUnidades("Documento não localizado na base de informações", model);
			}
		}else {
			return preparaRetornoLogin("", model);
		}
	}

	private void montarEnviarEmailCompliance(Usuario usu, Arquivos arquivo) {
		String msg = "Prezado "+usu.getNome()+ ", <br/><br/>A equipe de Compliance concluiu a análise do documento "+arquivo.getNomeExibicao()+".<br/><br/>"
				+ "Para verificar, acesse o Portal de Transparência, área Restrita utilizando seu CPF e a senha já encaminhada para seu e-mail cadastrado no Portal.<br/><br/>"
				+ "Atenciosamente, <br/><br/>"
				+ "Portal de Transparência do ISAC."
				+ "<br/><br/>";
		Email.enviar("Portal da Transparência ISAC - Análise de Conformidade Concluída", msg, usu.getEmail());
	}

	private void montarEnviarEmailParaCompliance(Usuario usuSessao, Arquivos arquivo, Unidade unidade) {
		String msg = "Prezados, <br/><br/>"
				+ "O colaborador "+usuSessao.getNome()+ " encaminhou para sua avaliação de conformidade o documento "+arquivo.getNomeExibicao()+" da unidade " + unidade.getNome()+"<br/><br/>"
				+ "Para analisar, acesse o Portal de Transparência, área Restrita utilizando seu CPF e a senha já encaminhada para seu e-mail cadastrado no Portal.<br/><br/>"
				+ "Atenciosamente, <br/><br/>"
				+ "Portal de Transparência do ISAC."
				+ "<br/><br/>";
		Email.enviar("Portal da Transparência ISAC - Documento Disponível para Análise de Conformidade!", msg, ParametrosIsacSede.emailIntegritas);
	}

	@GetMapping("/downloadArquivo")
	public ResponseEntity<?> downloadArquivo(@Valid @ModelAttribute("id") Integer idArquivo, HttpSession session) {

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(usuSessao !=null) {
			Optional<Arquivos> arquivo = arquivoRepo.findById(idArquivo);

			if(arquivo.isPresent()) {
				
				MovimentoArquivo mov = new MovimentoArquivo();
				mov.setIdArquivo(arquivo.get().getId());
				mov.setIdTipoMovimento(TipoMovimentoArquivoEnum.DOWNLOAD.getId());
				mov.setIdUsuarioGravador(usuSessao.getId());
				mov.setIp(getIp());
				mov.setTsInicio(currentTimestamp());
				mov.setObservacao("Download de arquivo da base de dados. Id: "+ arquivo.get().getId() + " por "+ usuSessao.getNome() + " Unidade: "+usuSessao.getIdUnidadeAtual());
				
				if(usuSessao.getCnpjUnidade().equals(ParametrosIsacSede.cnpjSede)) {
					
					movimentoArquivoRepo.save(mov);
					
					return ResponseEntity.ok().contentType(MediaType.parseMediaType(arquivo.get().getMimetype()))
							//.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + arquivo.getNome() + "\"")
							.body(arquivo.get().getArquivo());
				}else {
					if(arquivo.get().getIdUnidade() == usuSessao.getIdUnidadeAtual()) {
						
						movimentoArquivoRepo.save(mov);
						
						return ResponseEntity.ok().contentType(MediaType.parseMediaType(arquivo.get().getMimetype()))
								//.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + arquivo.getNome() + "\"")
								.body(arquivo.get().getArquivo());
					}else {
						ModelMap model = new ModelMap();
						model.addAttribute("mensagemErro", "Desculpe, não foi possível efetuar o download deste documento. "
								+ "Ele não pertence a sua Unidade. Somente usuários da Unidade Isac Sede podem ter acesso a todos os documentos.");
						return new ResponseEntity<ModelAndView>(preparaDetalharDocumento(arquivo.get(), session, new ModelMap()), HttpStatus.NOT_FOUND);
					}
				}
			}else {
				ModelMap model = new ModelMap();
				model.addAttribute("mensagemErro", "Desculpe, não foi possível localizar o documento com o id "+ idArquivo+". Verifique se o mesmo ainda existe.");
				return new ResponseEntity<ModelAndView>(preparaDashBoard(model, session), HttpStatus.NOT_FOUND);
			}
		}else {
			return new ResponseEntity<ModelAndView>(preparaRetornoLogin("", new ModelMap()), HttpStatus.NOT_FOUND);
		}
	}

	private Arquivos executaAlteracaoSituacaoArquivo(Arquivos arquivo, Usuario usuSessao, Integer idSituacaoNova, String justificativa) {

		arquivo.setIdEstadoAtual(idSituacaoNova);

		arquivoRepo.save(arquivo);

		Optional<EstadoArquivo> estadoAtual = estadoArquivoRepo.recuperaEstadoAtualArquivo(arquivo.getId());

		if(estadoAtual.isPresent()) {
			EstadoArquivo estadoA = estadoAtual.get();
			estadoA.setTsFim(currentTimestamp());
			estadoArquivoRepo.save(estadoA);

			EstadoArquivo novoEstado = new EstadoArquivo();
			novoEstado.setIdEstado(idSituacaoNova);
			novoEstado.setIdArquivo(arquivo.getId());
			novoEstado.setIdUsuarioGravador(usuSessao.getId());
			novoEstado.setObservacao("Alteração de situação para "+TipoEstadoArquivoEnum.recupera(idSituacaoNova) + ".  Justificativa: "+justificativa);
			novoEstado.setTsInicio(currentTimestamp());

			estadoArquivoRepo.save(novoEstado);
		}

		MovimentoArquivo mov = new MovimentoArquivo();
		mov.setIdArquivo(arquivo.getId());
		mov.setIdTipoMovimento(TipoMovimentoArquivoEnum.recupera(idSituacaoNova).getId());
		mov.setIdUsuarioGravador(usuSessao.getId());
		mov.setIp(getIp());
		mov.setObservacao("Alteração de Situação de Documento. De: "+estadoAtual.get().getIdEstado()+ " para "+ idSituacaoNova);
		mov.setTsInicio(currentTimestamp());

		movimentoArquivoRepo.save(mov);

		gravarLog(new Log(arquivo.getId(), TipoLogEnum.DOCUMENTO.getId(), usuSessao.getId(), currentTimestamp(), 
				"Alteração de Situação de Documento. De: "+estadoAtual.get().getIdEstado()+ " para "+ idSituacaoNova, "DOCUMENTO", getIp(), "/alteraSituacaoArquivo"));

		return arquivo;
	}


	/**ROTAS PARA OS TIPOS**/

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value = "/tiposEstadosUsuario", method = RequestMethod.GET)
	public ResponseEntity<?> tiposEstadosUsuario(){
		List<TipoEstadoUsuarioEnum> tipos = Arrays.asList(TipoEstadoUsuarioEnum.values());
		return new ResponseEntity<List<TipoEstadoUsuarioEnum>>(tipos, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value = "/tiposEstadosUnidade", method = RequestMethod.GET)
	public ResponseEntity<?> tiposEstadosUnidade(){
		List<TipoEstadoUnidadeEnum> tipos = Arrays.asList(TipoEstadoUnidadeEnum.values());
		return new ResponseEntity<List<TipoEstadoUnidadeEnum>>(tipos, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value = "/tiposEstadosArquivo", method = RequestMethod.GET)
	public ResponseEntity<?> tiposEstadosArquivo(){
		List<TipoEstadoArquivoEnum> tipos = Arrays.asList(TipoEstadoArquivoEnum.values());
		return new ResponseEntity<List<TipoEstadoArquivoEnum>>(tipos, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value = "/tiposArquivos", method = RequestMethod.GET)
	public ResponseEntity<?> tiposArquivos(){
		List<TipoDocumento> tiposArquivos = tipoDocRepo.findAllAtivos().get();
		return new ResponseEntity<List<TipoDocumento>>(tiposArquivos, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value = "/tiposFuncoesUsuario", method = RequestMethod.GET)
	public ResponseEntity<?> tipoFuncoesUsuario(){
		List<TipoFuncaoUsuarioEnum> tipos = Arrays.asList(TipoFuncaoUsuarioEnum.values());
		return new ResponseEntity<List<TipoFuncaoUsuarioEnum>>(tipos, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value = "/tiposUnidades", method = RequestMethod.GET)
	public ResponseEntity<?> tiposUnidades(){
		List<TipoUnidade> tipos = tipoUnidadeRepo.findAll();//List<TipoUnidadeEnum> tipos = Arrays.asList(TipoUnidadeEnum.values());
		return new ResponseEntity<List<TipoUnidade>>(tipos, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value = "/tiposUsuarios", method = RequestMethod.GET)
	public ResponseEntity<?> tiposUsuarios(){
		List<TipoUsuarioEnum> tipos = Arrays.asList(TipoUsuarioEnum.values());
		return new ResponseEntity<List<TipoUsuarioEnum>>(tipos, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value = "/tiposLog", method = RequestMethod.GET)
	public ResponseEntity<?> tiposLog(){
		List<TipoLogEnum> tipos = Arrays.asList(TipoLogEnum.values());
		return new ResponseEntity<List<TipoLogEnum>>(tipos, HttpStatus.OK);
	}

	/**FIM DAS ROTAS PARA OS TIPOS**/

	/******************************************
	 * *****************************************
	 * ROTAS PARA A AREA PUBLICA**********
	 * *****************************************
	 * *****************************************/
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value = "/ap/unidade/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> obterUnidadePorIdAreaPublica(@PathVariable(value = "id") Integer id){

		Optional<Unidade> unidade = unidadeRepo.findById(id);
		if(unidade.isPresent()) {
			Unidade unidadeX = complementaObjeto(unidade.get());
			gravarLog(new Log(id, TipoLogEnum.UNIDADE.getId(), null, currentTimestamp(), 
					"Sucesso na pesquisa de unidade por ID: "+id, "UNIDADE", getIp(), "/ap/unidade/{id}"));
			return new ResponseEntity<Unidade>(unidadeX, HttpStatus.OK);
		}else {
			gravarLog(new Log(id, TipoLogEnum.UNIDADE.getId(), null, currentTimestamp(), 
					"Insucesso na pesquisa de unidade por ID: "+id, "UNIDADE", getIp(), "/ap/unidade/{id}"));
			return new ResponseEntity<>(new Mensagem(false, "Unidade não localizada na base de dados. Verifique se o ID informado corresponde a uma unidade."), HttpStatus.NOT_FOUND);
		}
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value = "/ap/unidades/porTipo/{tipo}", method = RequestMethod.GET)
	public ResponseEntity<?> obterTodasUnidadesPorTipoAreaPublica(@PathVariable(value = "tipo") Integer tipo){

		Optional<List<Unidade>> unidades = unidadeRepo.obterUnidadesPorTipo(tipo);

		if(unidades.isPresent()) {
			return new ResponseEntity<List<Unidade>>(unidades.get(), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(new Mensagem(false, "Unidades não localizadas na base de dados. Verifique se o tipo informado corresponde ao de alguma unidade."), HttpStatus.NOT_FOUND);
		}
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value = "/ap/unidades/todas", method = RequestMethod.GET)
	public ResponseEntity<?> obterTodasUnidadesAreaPublica(){

		List<Unidade> unidades = unidadeRepo.obterUnidadesPublicadas(TipoEstadoUnidadeEnum.PUBLICADO.getId());

		if(unidades != null && unidades.size()>0) {
			return new ResponseEntity<List<Unidade>>(unidades, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(new Mensagem(false, "Unidades não localizadas na base de dados. Verifique se o tipo informado corresponde ao de alguma unidade."), HttpStatus.NOT_FOUND);
		}
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value = "/ap/arquivos/porTipo/{tipo}", method = RequestMethod.GET)
	public ResponseEntity<?> obterArquivosPorTipoAreaPublica(@PathVariable(value = "tipo") Integer tipo){

		List<Arquivos> arquivos = arquivoRepo.obterArquivosPorTipoSemBlobAreaPublica(tipo);
		List<Arquivos> lsArquivos = new ArrayList<>();
		
		if(arquivos != null && arquivos.size()> 0) {
			for (Arquivos arq : arquivos) {
				
				Optional<TipoDocumento> tipoDoc = tipoDocRepo.findById(arq.getTipo());
				if(tipoDoc.isPresent()) {
					arq.setTipoDocumento(tipoDoc.get());
				}
				lsArquivos.add(arq);
			}
		}

		gravarLog(new Log(0, TipoLogEnum.DOCUMENTO.getId(), null, currentTimestamp(), 
				"Lista de todos os documentos por tipo: "+tipo, "DOCUMENTO", getIp(), "/arquivos/porTipo/{tipo}"));

		if(arquivos!=null && arquivos.size()>0) {
			return new ResponseEntity<List<Arquivos>>(lsArquivos, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(new Mensagem(false, "Arquivos não localizados na base de dados. Verifique se o tipo informado corresponde ao de algum arquivo."), HttpStatus.NOT_FOUND);
		}
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value = "/ap/arquivos/porUnidade/{idUnidade}", method = RequestMethod.GET)
	public ResponseEntity<?> obterArquivosPorUnidadeAreaPublica(@PathVariable(value = "idUnidade") Integer idUnidade){

		List<Arquivos> arquivosUnidade = arquivoRepo.obterArquivosPorUnidadeAreaPublicaSemBlobo(idUnidade);
		
		List<Arquivos> lsArquivos = new ArrayList<>();
		
		if(arquivosUnidade != null && arquivosUnidade.size()>0) {
			for (Arquivos arq : arquivosUnidade) {
				
				Optional<TipoDocumento> tipoDoc = tipoDocRepo.findById(arq.getTipo());
				if(tipoDoc.isPresent()) {
					arq.setTipoDocumento(tipoDoc.get());
				}
				
				lsArquivos.add(arq);
			}
		}

		gravarLog(new Log(0, TipoLogEnum.DOCUMENTO.getId(), null, currentTimestamp(), 
				"Lista de todos os documentos por unidade: "+idUnidade, "DOCUMENTO", getIp(), "/arquivos/porTipo/{tipo}"));

		if(arquivosUnidade !=null && arquivosUnidade.size()>0) {
			return new ResponseEntity<List<Arquivos>>(lsArquivos, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(new Mensagem(false, "Arquivos não localizados na base de dados. Verifique se o ID da Unidade informada corresponde a de alguma Unidade válida."), HttpStatus.NOT_FOUND);
		}
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value = "/ap/arquivos/porUnidade/{idUnidade}/{grupo}", method = RequestMethod.GET)
	public ResponseEntity<?> obterArquivosPorUnidadePorGrupoAreaPublica(@PathVariable(value = "idUnidade") Integer idUnidade, @PathVariable(value = "grupo") Integer grupo){
		
		List<TipoDocumento> tipos = tipoDocRepo.findAllTiposPorGrupo(grupo);
		List<Integer> idsTipos = new ArrayList<Integer>();
		if(tipos!=null && tipos.size()>0) {
			
			for(TipoDocumento t: tipos) {
				idsTipos.add(t.getId());
			}
		}else {
			return new ResponseEntity<>(new Mensagem(false, "Tipos de documentos não localizados para o id de grupo informado. "
					+ "Verifique se o ID informado corresponde a de algum grupamento de documentos válido."), HttpStatus.NOT_FOUND);
		}

		List<Arquivos> arquivosUnidade = arquivoRepo.obterArquivosPorUnidadePorGrupoSemBlob(idUnidade, idsTipos);
		
		List<Arquivos> lsArquivos = new ArrayList<>();
		
		if(arquivosUnidade != null && arquivosUnidade.size()>0) {
			for (Arquivos arq : arquivosUnidade) {
				
				Optional<TipoDocumento> tipoDoc = tipoDocRepo.findById(arq.getTipo());
				if(tipoDoc.isPresent()) {
					Optional<GrupoTipoDocumento> gr = grupoTipoRepo.findById(tipoDoc.get().getGrupo());
					TipoDocumento td = tipoDoc.get();
					td.setNomeGrupo(gr.get().getNome());
					arq.setTipoDocumento(td);
				}
				
				lsArquivos.add(arq);
			}
		}

		gravarLog(new Log(0, TipoLogEnum.DOCUMENTO.getId(), null, currentTimestamp(), 
				"Lista de todos os documentos por unidade: "+idUnidade+ " er por grupo: "+grupo, "DOCUMENTO", getIp(), "/ap/arquivos/porUnidade/{idUnidade}/{grupo}"));

		if(arquivosUnidade !=null && arquivosUnidade.size()>0) {
			return new ResponseEntity<List<Arquivos>>(lsArquivos, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(new Mensagem(false, "Arquivos não localizados na base de dados. Verifique se o ID da Unidade informada corresponde a de alguma Unidade válida."), HttpStatus.NOT_FOUND);
		}
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value = "/ap/arquivo/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> obterArquivoPorIdAreaPublica(@PathVariable(value = "id") Integer id){

		Optional<Arquivos> arquivo = arquivoRepo.findById(id);

		if(arquivo.isPresent()) {
			gravarLog(new Log(0, TipoLogEnum.DOCUMENTO.getId(), null, currentTimestamp(), 
					"Sucesso na pesquisa de arquivo por ID: "+id, "DOCUMENTO", getIp(), "/arquivo/{id}"));
			return new ResponseEntity<Arquivos>(arquivo.get(), HttpStatus.OK);
		}else {
			gravarLog(new Log(0, TipoLogEnum.DOCUMENTO.getId(), null, currentTimestamp(), 
					"Insucesso na pesquisa de arquivo por ID: "+id, "DOCUMENTO", getIp(), "/arquivo/{id}"));
			return new ResponseEntity<>(new Mensagem(false, "Arquivo não localizado na base de dados. Verifique se o ID do Arquivo informado corresponde ao de algum arquivo válido."),HttpStatus.NOT_FOUND);
		}
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value = "/ap/arquivo/download/{idArquivo}", method = RequestMethod.GET)
	public ResponseEntity<?> downloadArquivoAreaPublica(@PathVariable(value = "idArquivo") Integer idArquivo) {

		Optional<Arquivos> arquivo = arquivoRepo.findById(idArquivo);
		if(arquivo.isPresent()) {

			if(arquivo.get().getIdEstadoAtual() != TipoEstadoArquivoEnum.PUBLICADO.getId()) {
				return new ResponseEntity<Mensagem>(new Mensagem(false, "Desculpe, o documento com o id "+idArquivo+" não se encontra mais na situação publicado."), HttpStatus.NOT_FOUND);
			}

			MovimentoArquivo mov = new MovimentoArquivo();
			mov.setIdArquivo(idArquivo);
			mov.setIdTipoMovimento(TipoMovimentoArquivoEnum.DOWNLOAD.getId());
			mov.setIdUsuarioGravador(0);
			mov.setIp(getIp());
			mov.setObservacao("Download de Documento (Área Pública). Situação do Documento: "+ TipoEstadoArquivoEnum.recupera(arquivo.get().getIdEstadoAtual())+".");
			mov.setTsInicio(currentTimestamp());

			movimentoArquivoRepo.save(mov);

			gravarLog(new Log(idArquivo, TipoLogEnum.DOCUMENTO.getId(), null, currentTimestamp(), 
					"Download de Documento (na Área Pública) - Situação do Documento: "+ TipoEstadoArquivoEnum.recupera(arquivo.get().getIdEstadoAtual())+"."
					, "DOCUMENTO", getIp(), "/arquivo/download/{idArquivo}"));

			return ResponseEntity.ok().contentType(MediaType.parseMediaType(arquivo.get().getMimetype()))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + arquivo.get().getNome() + "\"")
					.body(arquivo.get().getArquivo());
		}else {
			return new ResponseEntity<Mensagem>(new Mensagem(false, "Documento não localizado na base de informações"), HttpStatus.NOT_FOUND);
		}
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value = "/ap/unidades/porUF/{uf}", method = RequestMethod.GET)
	public ResponseEntity<?> obterTodasUnidadesPorUFAreaPublica(@PathVariable(value = "uf") String uf){

		List<Unidade> unidades = unidadeRepo.obterUnidadesPublicadasPorUF(uf);

		if(unidades!=null && unidades.size()>0) {
			return new ResponseEntity<List<Unidade>>(unidades, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(new Mensagem(false, "Unidades da UF "+uf+"não localizadas na base de dados. Verifique se a UF informada corresponde a de alguma unidade."), HttpStatus.NOT_FOUND);
		}
	}
	
	/**FIM DOS METODOS PARA AS ROTAS PUBLICAS**/

	/**METODOS UTILITARIOS PARA A CLASSE**/
	private Unidade obterUnidadePorId(Integer id){

		Optional<Unidade> unidade = unidadeRepo.findById(id);
		if(unidade.isPresent()) {
			Unidade unidadeX = complementaObjeto(unidade.get());
			return unidadeX;
		}else {
			return null;
		}
	}

	private Unidade complementaObjeto(Unidade unidade) {

//		if(unidade.getUrlLogo()==null) {
//			Optional<Arquivos> arq = arquivoRepo.imagemUnidade(unidade.getId());
//			if(arq.isPresent()) {
//				unidade.setUrlLogo("downloadArquivo?id="+arq.get().getId());
//			}
//		}
		unidade.setCepFormatado(Conversor.formataCEP(unidade.getCep()));
		unidade.setCnpjFormatado(Conversor.formataCNPJ(unidade.getCnpj()));
		unidade.setTipoEstadoAtual(TipoEstadoUnidadeEnum.recupera(unidade.getIdEstadoAtual()));
		
		Optional<TipoUnidade> tipo = tipoUnidadeRepo.findById(unidade.getTipoUnidade());
		if(tipo.isPresent()) {
			unidade.setTipoUnidadeObjeto(tipo.get());
		}
		
		unidade.setTipoUnidadeObjeto(tipoUnidadeRepo.findById(unidade.getTipoUnidade()).get());
		//unidade.setTipoUnidadeEnum(TipoUnidadeEnum.recupera(unidade.getTipoUnidade()));
		return unidade;
	}

	private void salvarNaSessaoAdmin(@RequestParam Usuario usuario, HttpSession session) {
		session.setAttribute("usuario", usuario);
	}

	private Usuario recuperarDaSessaoAdmin(HttpSession session) {
		Usuario loginSessao = (Usuario)session.getAttribute("usuario");
		return loginSessao;
	}

	private Timestamp currentTimestamp() {

		try {
			ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));

			int ano = Integer.parseInt(now.toString().substring(0, 4));
			int mes = Integer.parseInt(now.toString().substring(5, 7));
			int dias = Integer.parseInt(now.toString().substring(8, 10));
			int horas = Integer.parseInt(now.toString().substring(11, 13));
			int minutos = Integer.parseInt(now.toString().substring(14, 16));
			int segundos = Integer.parseInt(now.toString().substring(17, 19));
			int nanoSegundos = Integer.parseInt(now.toString().substring(20, 23));

			LocalDateTime localDateTime = LocalDateTime.of(ano, mes, dias, horas, minutos, segundos, nanoSegundos);
			Timestamp currentTimestamp = Timestamp.valueOf(localDateTime);
			return currentTimestamp;
		}catch (Exception e) {
			return new Timestamp(System.currentTimeMillis());
		}
	}

	private String getIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return "0.0.0.0";
		}
	}

	private void gravarLog(Log log) {
		logRepo.save(log);
	}

	@RequestMapping(value = "/novoTipoDocumento", method = RequestMethod.GET) 
	public ModelAndView novoTipoDocumento(ModelMap model, HttpSession session) {
		Usuario usu = recuperarDaSessaoAdmin(session);
		if(null != usu) {

			Mensagem podeTiposDocs = tipoDocRules.podeAcessarTipos(usu);

			if(!podeTiposDocs.isPermitido()) {
				model.addAttribute("mensagemErro", podeTiposDocs.getMensagem());
				return preparaDashBoard(model, session);
			}
			
			List<GrupoTipoDocumento> grupos = grupoTipoRepo.obterGruposAtivos();
			model.addAttribute("grupos", grupos);
			model.addAttribute("tituloPagina", "Novo Tipo de Documento");
			model.addAttribute("tipo", new TipoDocumento());

			gravarLog(new Log(0, TipoLogEnum.TIPO_DOCUMENTO.getId(), usu.getId(), currentTimestamp(), 
					"Página de Cadastro de Tipo de Documento", "TIPO_DOCUMENTO", getIp(), "novoTipoDocumento"));
			model.addAttribute("userLogado", userLogado(usu));
			return new ModelAndView("novoTipoDocumento", model);
		}else {
			return preparaRetornoLogin("", model);
		}
	}
	
	@RequestMapping(value = "/novoGrupoTipoDocumento", method = RequestMethod.GET) 
	public ModelAndView novoGrupoTipoDocumento(ModelMap model, HttpSession session) {
		Usuario usu = recuperarDaSessaoAdmin(session);
		if(null != usu) {

			Mensagem podeTiposDocs = grupoTipoDocRules.podeAcessarGrupos(usu);

			if(!podeTiposDocs.isPermitido()) {
				model.addAttribute("mensagemErro", podeTiposDocs.getMensagem());
				return preparaDashBoard(model, session);
			}

			model.addAttribute("tituloPagina", "Novo Grupo de Tipo de Documento");
			model.addAttribute("grupo", new GrupoTipoDocumento());

			gravarLog(new Log(0, TipoLogEnum.GRUPO_TIPO.getId(), usu.getId(), currentTimestamp(), 
					"Página de Cadastro de Grupo de Tipo de Documento", "GRUPO_TIPO", getIp(), "novoGrupoTipoDocumento"));
			model.addAttribute("userLogado", userLogado(usu));
			return new ModelAndView("novoGrupoTipoDocumento", model);
		}else {
			return preparaRetornoLogin("", model);
		}
	}
	
	@RequestMapping(value = "/novoTipoUnidade", method = RequestMethod.GET) 
	public ModelAndView novoTipoUnidade(ModelMap model, HttpSession session) {
		Usuario usuSessao = recuperarDaSessaoAdmin(session);
		if(null != usuSessao) {

			Mensagem podeTiposDocs = tipoUnidadeRules.podeAcessarTipos(usuSessao);

			if(!podeTiposDocs.isPermitido()) {
				model.addAttribute("mensagemErro", podeTiposDocs.getMensagem());
				return preparaDashBoard(model, session);
			}

			model.addAttribute("tituloPagina", "Novo Tipo de Unidade");
			model.addAttribute("tipo", new TipoUnidade());

			gravarLog(new Log(0, TipoLogEnum.TIPO_UNIDADE.getId(), usuSessao.getId(), currentTimestamp(), 
					"Página de Cadastro de Tipo de Unidade", "TIPO_UNIDADE", getIp(), "novoTipoUnidade"));
			model.addAttribute("userLogado", userLogado(usuSessao));
			return new ModelAndView("novoTipoUnidade", model);
		}else {
			return preparaRetornoLogin("", model);
		}
	}

	@RequestMapping(value = "/novoContratante", method = RequestMethod.GET) 
	public ModelAndView novoContratante(ModelMap model, HttpSession session) {
		Usuario usu = recuperarDaSessaoAdmin(session);
		if(null != usu) {

			Mensagem podeTiposDocs = contratanteRules.podeIncluir(usu);

			if(!podeTiposDocs.isPermitido()) {
				model.addAttribute("mensagemErro", podeTiposDocs.getMensagem());
				return preparaDashBoard(model, session);
			}

			model.addAttribute("tituloPagina", "Novo Contratante");
			model.addAttribute("contratante", new Contratantes());
			
			List<UnidadesFederacaoEnum> ufs = Arrays.asList(UnidadesFederacaoEnum.values());
			model.addAttribute("ufs",ufs);

			gravarLog(new Log(0, TipoLogEnum.CONTRATANTE.getId(), usu.getId(), currentTimestamp(), 
					"Página de Cadastro de Contratantes", "CONTRATANTE", getIp(), "novoContratante"));
			model.addAttribute("userLogado", userLogado(usu));
			return new ModelAndView("novoContratante", model);
		}else {
			return preparaRetornoLogin("", model);
		}
	}

	@PutMapping("/salvarTipoDocumento")
	public ModelAndView salvarTipoDocumento(@Valid @ModelAttribute("tipo") TipoDocumento tipo, BindingResult result, ModelMap model, HttpSession session) {

		Usuario uSessao = recuperarDaSessaoAdmin(session);

		//validacoes
		if(result.hasErrors()) {
			ModelAndView md = new ModelAndView("novoTipoDocumento");
			List<String> msg = new ArrayList<String>();
			msg.add("Atenção: Observe os erros identificados: <br/>");
			for(ObjectError objError: result.getAllErrors()) {
				msg.add(objError.getDefaultMessage()+"<br/>");//vem das anotacoes na classe
			}

			md.addObject("userLogado", userLogado(uSessao));
			md.addObject("mensagem", msg);
			md.addObject("tipo", tipo);
			return md;
		}

		if(null!= uSessao) {

			Mensagem podeTiposDocs = tipoDocRules.podeAcessarTipos(uSessao);

			if(!podeTiposDocs.isPermitido()) {
				model.addAttribute("mensagemErro", podeTiposDocs.getMensagem());
				return preparaDashBoard(model, session);
			}

			tipoDocRepo.save(tipo);

			gravarLog(new Log(tipo.getId() , TipoLogEnum.TIPO_DOCUMENTO.getId(), uSessao.getId(), currentTimestamp(), 
					"Cadastramento de Tipo de Documento", "TIPO_DOCUMENTO", getIp(), "salvarTipoDocumento"));

			model.addAttribute("userLogado", userLogado(uSessao));
			model.addAttribute("mensagemSucesso","Tipo de Documento cadastrado com sucesso. ");
			model.addAttribute("tipos", tipoDocRepo.findAllAtivos().get());
			model.addAttribute("tituloPagina", "Lista de Tipos de Documentos");
			return new ModelAndView("listarTiposDocumentos", model);
		}else {
			return preparaRetornoLogin("", model);
		}
	}
	
	@PutMapping("/salvarContratante")
	public ModelAndView salvarContratante(@RequestParam("files") MultipartFile[] files, @Valid @ModelAttribute("contratante") Contratantes contratante, BindingResult result, ModelMap model, HttpSession session) {

		Usuario uSessao = recuperarDaSessaoAdmin(session);

		//validacoes
		if(result.hasErrors()) {
			ModelAndView md = new ModelAndView("novoContratante");
			List<String> msg = new ArrayList<String>();
			msg.add("Atenção: Observe os erros identificados: <br/>");
			for(ObjectError objError: result.getAllErrors()) {
				msg.add(objError.getDefaultMessage()+"<br/>");//vem das anotacoes na classe
			}

			md.addObject("userLogado", userLogado(uSessao));
			md.addObject("mensagem", msg);
			md.addObject("contratante", contratante);
			return md;
		}

		if(null!= uSessao) {

			Mensagem podeTiposDocs = contratanteRules.podeIncluir(uSessao);

			if(!podeTiposDocs.isPermitido()) {
				model.addAttribute("mensagemErro", podeTiposDocs.getMensagem());
				return preparaDashBoard(model, session);
			}
			
			String cnpjComMascara = contratante.getCnpj();
			//retirar os pontos:
			cnpjComMascara = cnpjComMascara.replace(".", "");
			//retira a barra:
			cnpjComMascara = cnpjComMascara.replace("/", "");
			//retirar o traço:
			cnpjComMascara = cnpjComMascara.replace("-", "");
			
			if(!Validador.isCNPJ(cnpjComMascara)) {
				model.addAttribute("mensagemErro", "Desculpe, o CNPJ informado não é válido. Favor verificar o número informado.");
				List<UnidadesFederacaoEnum> ufs = Arrays.asList(UnidadesFederacaoEnum.values());
				model.addAttribute("ufs",ufs);

				//List<TipoUnidadeEnum> tipos = Arrays.asList(TipoUnidadeEnum.values());
				//model.addAttribute("tipos",tipos);

				model.addAttribute("contratante", contratante);
				//model.addAttribute("userLogado", userLogado(usuSessao));
				model.addAttribute("tituloPagina", "Cadastro de novo contratante");
				return new ModelAndView("novoContratante", model);
			}
			


			try {
				for(MultipartFile file: files) 
				{
					if(!file.getContentType().contains("image")) {
						model.addAttribute("mensagemErro", "Desculpe, o arquivo informado não é do tipo Imagem. Favor verificar o arquivo informado.");
						List<UnidadesFederacaoEnum> ufs = Arrays.asList(UnidadesFederacaoEnum.values());
						model.addAttribute("ufs",ufs);

						model.addAttribute("contratante", contratante);
						//model.addAttribute("userLogado", userLogado(usuSessao));
						model.addAttribute("tituloPagina", "Cadastro de novo contratante");
						return new ModelAndView("novoContratante", model);
					}
					
					Arquivos arquivo = new Arquivos();
					arquivo.setArquivo(file.getBytes());
					arquivo.setMimetype(file.getContentType());
					arquivo.setNome(file.getOriginalFilename());

					double tam = file.getSize();
					tam = (tam/1024)/1024;
					arquivo.setTamanho(String.format("%.2f", tam)+ " MB");
					arquivo.setTsRegistro(currentTimestamp());
					arquivo.setIdUsuario(uSessao.getId());
					arquivo.setTipo(0);
					arquivo.setDescricao("Imagem do Contratante "+contratante.getId());
					arquivo.setIdContratante(contratante.getId());
					arquivo.setIdEstadoAtual(TipoEstadoArquivoEnum.PUBLICADO.getId());

					arquivoRepo.save(arquivo);

					EstadoArquivo estadoArquivo = new EstadoArquivo();
					estadoArquivo.setIdArquivo(arquivo.getId());
					estadoArquivo.setIdEstado(TipoEstadoArquivoEnum.PUBLICADO.getId());
					estadoArquivo.setIdUsuarioGravador(uSessao.getId());
					estadoArquivo.setObservacao("Imagem do Contratante "+contratante.getId());
					estadoArquivo.setTsInicio(currentTimestamp());

					estadoArquivoRepo.save(estadoArquivo);
					
					contratante.setIdArquivoImagem(arquivo.getId());

				}
			} catch (IOException e) {
				//faz nada
			}
			
			if(contratante.getIdArquivoImagem()== null) contratante.setIdArquivoImagem(0);
			
			contratante.setTsRegistro(currentTimestamp());
			contratantesRepo.save(contratante);
			
			gravarLog(new Log(contratante.getId() , TipoLogEnum.CONTRATANTE.getId(), uSessao.getId(), currentTimestamp(), 
					"Cadastramento de Contratantes", "CONTRATANTE", getIp(), "salvarContratante"));

			model.addAttribute("userLogado", userLogado(uSessao));
			model.addAttribute("mensagemSucesso","Contratante cadastrado com sucesso. ");
			//model.addAttribute("contratantes", contratantesRepo.findAll());
			//model.addAttribute("tituloPagina", "Lista de Contratantes");
			return preparaListarContratantes(model, session);//new ModelAndView("listarContratantes", model);
		}else {
			return preparaRetornoLogin("", model);
		}
	}
	
	@PutMapping("/salvarGrupoTipoDocumento")
	public ModelAndView salvarGrupoTipoDocumento(@Valid @ModelAttribute("grupo") GrupoTipoDocumento gr, BindingResult result, ModelMap model, HttpSession session) {

		Usuario uSessao = recuperarDaSessaoAdmin(session);

		//validacoes
		if(result.hasErrors()) {
			ModelAndView md = new ModelAndView("novoGrupoTipoDocumento");
			List<String> msg = new ArrayList<String>();
			msg.add("Atenção: Observe os erros identificados: <br/>");
			for(ObjectError objError: result.getAllErrors()) {
				msg.add(objError.getDefaultMessage()+"<br/>");//vem das anotacoes na classe
			}

			md.addObject("userLogado", userLogado(uSessao));
			md.addObject("mensagem", msg);
			md.addObject("grupo", gr);
			return md;
		}

		if(null!= uSessao) {

			Mensagem podeTiposDocs = grupoTipoDocRules.podeAcessarGrupos(uSessao);

			if(!podeTiposDocs.isPermitido()) {
				model.addAttribute("mensagemErro", podeTiposDocs.getMensagem());
				return preparaDashBoard(model, session);
			}

			grupoTipoRepo.save(gr);

			gravarLog(new Log(gr.getId() , TipoLogEnum.GRUPO_TIPO.getId(), uSessao.getId(), currentTimestamp(), 
					"Cadastramento de Grupo de Tipo de Documento", "GRUPO_TIPO", getIp(), "salvarGrupoTipoDocumento"));

			model.addAttribute("userLogado", userLogado(uSessao));
			model.addAttribute("mensagemSucesso","Grupo de Tipo de Documento cadastrado com sucesso. ");
			model.addAttribute("grupos", grupoTipoRepo.findAll());
			model.addAttribute("tituloPagina", "Lista de Grupos de Tipos de Documentos");
			return new ModelAndView("listarGruposTiposDocumentos", model);
		}else {
			return preparaRetornoLogin("", model);
		}
	}
	
	@PutMapping("/salvarTipoUnidade")
	public ModelAndView salvarTipoUnidade(@Valid @ModelAttribute("tipo") TipoUnidade tipo, BindingResult result, ModelMap model, HttpSession session) {

		Usuario uSessao = recuperarDaSessaoAdmin(session);

		//validacoes
		if(result.hasErrors()) {
			ModelAndView md = new ModelAndView("novoTipoUnidade");
			List<String> msg = new ArrayList<String>();
			msg.add("Atenção: Observe os erros identificados: <br/>");
			for(ObjectError objError: result.getAllErrors()) {
				msg.add(objError.getDefaultMessage()+"<br/>");//vem das anotacoes na classe
			}

			md.addObject("userLogado", userLogado(uSessao));
			md.addObject("mensagem", msg);
			md.addObject("tipo", tipo);
			return md;
		}

		if(null!= uSessao) {

			Mensagem podeTiposDocs = tipoUnidadeRules.podeAcessarTipos(uSessao);

			if(!podeTiposDocs.isPermitido()) {
				model.addAttribute("mensagemErro", podeTiposDocs.getMensagem());
				return preparaDashBoard(model, session);
			}

			tipoUnidadeRepo.save(tipo);

			gravarLog(new Log(tipo.getId() , TipoLogEnum.TIPO_UNIDADE.getId(), uSessao.getId(), currentTimestamp(), 
					"Cadastramento deTipo de Unidade", "TIPO_UNIDADE", getIp(), "salvarTipoUnidade"));

			model.addAttribute("userLogado", userLogado(uSessao));
			model.addAttribute("mensagemSucesso","Tipo de Unidade cadastrado com sucesso. ");
			model.addAttribute("tipos", tipoUnidadeRepo.findAll());
			model.addAttribute("tituloPagina", "Lista de Tipos de Unidades");
			return new ModelAndView("listarTiposUnidades", model);
		}else {
			return preparaRetornoLogin("", model);
		}
	}

	@PutMapping("/alterarTipoDocumento")
	public ModelAndView alterarTipoDocumento(@Valid @ModelAttribute("tipo") TipoDocumento tipo, BindingResult result, ModelMap model, HttpSession session) {

		Usuario uSessao = recuperarDaSessaoAdmin(session);

		//validacoes
		if(result.hasErrors()) {
			ModelAndView md = new ModelAndView("alterarTipoDocumento");
			List<String> msg = new ArrayList<String>();
			msg.add("Atenção: Observe os erros identificados: <br/>");
			for(ObjectError objError: result.getAllErrors()) {
				msg.add(objError.getDefaultMessage()+"<br/>");//vem das anotacoes na classe
			}

			md.addObject("userLogado", userLogado(uSessao));
			md.addObject("mensagemErro", msg);
			md.addObject("tipo", tipo);
			return md;
		}

		if(null!= uSessao) {

			Mensagem podeTiposDocs = tipoDocRules.podeAcessarTipos(uSessao);

			if(!podeTiposDocs.isPermitido()) {
				model.addAttribute("mensagemErro", podeTiposDocs.getMensagem());
				return preparaDashBoard(model, session);
			}

			tipoDocRepo.save(tipo);

			gravarLog(new Log(tipo.getId() , TipoLogEnum.TIPO_DOCUMENTO.getId(), uSessao.getId(), currentTimestamp(), 
					"Alteração de Tipo de Documento. Id "+tipo.getId(), "TIPO_DOCUMENTO", getIp(), "alterarTipoDocumento"));

			model.addAttribute("userLogado", userLogado(uSessao));
			model.addAttribute("mensagemSucesso","Tipo de Documento cadastrado com sucesso. ");
			model.addAttribute("tipos", tipoDocRepo.findAllTipos());
			model.addAttribute("tituloPagina", "Lista de Tipos de Documentos");
			return new ModelAndView("listarTiposDocumentos", model);
		}else {
			return preparaRetornoLogin("", model);
		}
	}
	
	@PutMapping("/alterarContratante")
	public ModelAndView alterarContratante(@RequestParam("files") MultipartFile[] files, @Valid @ModelAttribute("contratante") Contratantes contratante, BindingResult result, ModelMap model, HttpSession session) {

		Usuario uSessao = recuperarDaSessaoAdmin(session);

		//validacoes
		if(result.hasErrors()) {
			ModelAndView md = new ModelAndView("alterarContratante");
			List<String> msg = new ArrayList<String>();
			msg.add("Atenção: Observe os erros identificados: <br/>");
			for(ObjectError objError: result.getAllErrors()) {
				msg.add(objError.getDefaultMessage()+"<br/>");//vem das anotacoes na classe
			}

			md.addObject("mensagemErro", msg);
			md.addObject("contratante", contratante);
			return md;
		}

		if(null!= uSessao) {

			Mensagem podeTiposDocs = contratanteRules.podeIncluir(uSessao);

			if(!podeTiposDocs.isPermitido()) {
				model.addAttribute("mensagemErro", podeTiposDocs.getMensagem());
				return preparaDashBoard(model, session);
			}

			
			
			try {
				for(MultipartFile file: files) 
				{
					if( file.getSize() <= 0 ) {
						continue;
					}
					
					if(!file.getContentType().contains("image")) {
						model.addAttribute("mensagemErro", "Desculpe, o arquivo informado não é do tipo Imagem. Favor verificar o arquivo informado.");
						List<UnidadesFederacaoEnum> ufs = Arrays.asList(UnidadesFederacaoEnum.values());
						model.addAttribute("ufs",ufs);

						model.addAttribute("contratante", contratante);
						//model.addAttribute("userLogado", userLogado(usuSessao));
						model.addAttribute("tituloPagina", "Cadastro de novo contratante");
						return new ModelAndView("novoContratante", model);
					}
					
					Arquivos arquivo = new Arquivos();
					arquivo.setArquivo(file.getBytes());
					arquivo.setMimetype(file.getContentType());
					arquivo.setNome(file.getOriginalFilename());

					double tam = file.getSize();
					tam = (tam/1024)/1024;
					arquivo.setTamanho(String.format("%.2f", tam)+ " MB");
					arquivo.setTsRegistro(currentTimestamp());
					arquivo.setIdUsuario(uSessao.getId());
					arquivo.setTipo(0);
					arquivo.setDescricao("Imagem do Contratante "+contratante.getId());
					arquivo.setIdContratante(contratante.getId());
					arquivo.setIdEstadoAtual(TipoEstadoArquivoEnum.PUBLICADO.getId());

					arquivoRepo.save(arquivo);

					EstadoArquivo estadoArquivo = new EstadoArquivo();
					estadoArquivo.setIdArquivo(arquivo.getId());
					estadoArquivo.setIdEstado(TipoEstadoArquivoEnum.PUBLICADO.getId());
					estadoArquivo.setIdUsuarioGravador(uSessao.getId());
					estadoArquivo.setObservacao("Imagem do Contratante "+contratante.getId());
					estadoArquivo.setTsInicio(currentTimestamp());

					estadoArquivoRepo.save(estadoArquivo);
					
					contratante.setIdArquivoImagem(arquivo.getId());

				}
			} catch (IOException e) {
				//faz nada
			}
			
			if(contratante.getIdArquivoImagem()== null || contratante.getIdArquivoImagem() <=0) {
				Optional<Contratantes> contratanteBD = contratantesRepo.findById(contratante.getId());
				
				if(contratanteBD.isPresent()) {
					contratante.setIdArquivoImagem(contratanteBD.get().getIdArquivoImagem());
				}
			}
			
			contratantesRepo.save(contratante);
			
			gravarLog(new Log(contratante.getId() , TipoLogEnum.CONTRATANTE.getId(), uSessao.getId(), currentTimestamp(), 
					"Alteração de Contratante. Id "+contratante.getId(), "CONTRATANTE", getIp(), "alterarContratante"));

			model.addAttribute("userLogado", userLogado(uSessao));
			model.addAttribute("mensagemSucesso","Contratante alterado com sucesso. ");
			model.addAttribute("contratantes", contratantesRepo.findAll());
			model.addAttribute("tituloPagina", "Lista de Contratantes");
			return new ModelAndView("listarContratantes", model);
		}else {
			return preparaRetornoLogin("", model);
		}
	}
	
	@PutMapping("/alterarGrupoTipoDocumento")
	public ModelAndView alterarGrupoTipoDocumento(@Valid @ModelAttribute("grupo") GrupoTipoDocumento gr, BindingResult result, ModelMap model, HttpSession session) {

		Usuario uSessao = recuperarDaSessaoAdmin(session);

		//validacoes
		if(result.hasErrors()) {
			ModelAndView md = new ModelAndView("alterarGrupoTipoDocumento");
			List<String> msg = new ArrayList<String>();
			msg.add("Atenção: Observe os erros identificados: <br/>");
			for(ObjectError objError: result.getAllErrors()) {
				msg.add(objError.getDefaultMessage()+"<br/>");//vem das anotacoes na classe
			}

			md.addObject("userLogado", userLogado(uSessao));
			md.addObject("mensagemErro", msg);
			md.addObject("grupo", gr);
			return md;
		}

		if(null!= uSessao) {

			Mensagem podeTiposDocs = grupoTipoDocRules.podeAcessarGrupos(uSessao);

			if(!podeTiposDocs.isPermitido()) {
				model.addAttribute("mensagemErro", podeTiposDocs.getMensagem());
				return preparaDashBoard(model, session);
			}

			grupoTipoRepo.save(gr);

			gravarLog(new Log(gr.getId() , TipoLogEnum.GRUPO_TIPO.getId(), uSessao.getId(), currentTimestamp(), 
					"Alteração de Grupo de Tipo de Documento. Id "+gr.getId(), "GRUPO_TIPO", getIp(), "alterarGrupoTipoDocumento"));

			model.addAttribute("userLogado", userLogado(uSessao));
			model.addAttribute("mensagemSucesso","Grupo de Tipo de Documento alterado com sucesso. ");
			model.addAttribute("grupos", grupoTipoRepo.findAll());
			model.addAttribute("tituloPagina", "Lista de Grupos de Tipos de Documentos");
			return new ModelAndView("listarGruposTiposDocumentos", model);
		}else {
			return preparaRetornoLogin("", model);
		}
	}
	
	@PutMapping("/alterarTipoUnidade")
	public ModelAndView alterarTipoUnidade(@Valid @ModelAttribute("tipo") TipoUnidade tipo, BindingResult result, ModelMap model, HttpSession session) {

		Usuario uSessao = recuperarDaSessaoAdmin(session);

		//validacoes
		if(result.hasErrors()) {
			ModelAndView md = new ModelAndView("alterarTipoUnidade");
			List<String> msg = new ArrayList<String>();
			msg.add("Atenção: Observe os erros identificados: <br/>");
			for(ObjectError objError: result.getAllErrors()) {
				msg.add(objError.getDefaultMessage()+"<br/>");//vem das anotacoes na classe
			}

			md.addObject("userLogado", userLogado(uSessao));
			md.addObject("mensagemErro", msg);
			md.addObject("tipo", tipo);
			return md;
		}

		if(null!= uSessao) {

			Mensagem podeTiposDocs = tipoUnidadeRules.podeAcessarTipos(uSessao);

			if(!podeTiposDocs.isPermitido()) {
				model.addAttribute("mensagemErro", podeTiposDocs.getMensagem());
				return preparaDashBoard(model, session);
			}

			tipoUnidadeRepo.save(tipo);

			gravarLog(new Log(tipo.getId() , TipoLogEnum.TIPO_UNIDADE.getId(), uSessao.getId(), currentTimestamp(), 
					"Alteração deTipo de Unidade. Id "+tipo.getId(), "TIPO_UNIDADE", getIp(), "alterarTipoUnidade"));

			model.addAttribute("userLogado", userLogado(uSessao));
			model.addAttribute("mensagemSucesso","Tipo de Unidade alterado com sucesso. ");
			model.addAttribute("tipos", tipoUnidadeRepo.findAll());
			model.addAttribute("tituloPagina", "Lista de Tipos de Unidade");
			return new ModelAndView("listarTiposUnidades", model);
		}else {
			return preparaRetornoLogin("", model);
		}
	}

	@RequestMapping(value = "/listarTiposDocumentos", method = RequestMethod.GET) 
	public ModelAndView listarTiposDocumentos(ModelMap model, HttpSession session) {
		Usuario usu = recuperarDaSessaoAdmin(session);
		if(null != usu) {

			Mensagem podeTiposDocs = tipoDocRules.podeAcessarTipos(usu);

			if(!podeTiposDocs.isPermitido()) {
				model.addAttribute("mensagemErro", podeTiposDocs.getMensagem());
				return preparaDashBoard(model, session);
			}

			model.addAttribute("tituloPagina", "Lista de Tipos de Documentos");
			List<TipoDocumento> tipos = tipoDocRepo.findAllTipos();
			List<TipoDocumento> tiposNovos = new ArrayList<>();
			
			for(TipoDocumento t: tipos) {
				GrupoTipoDocumento gr = grupoTipoRepo.findById(t.getGrupo()).get();
				t.setNomeGrupo(gr.getNome());
				tiposNovos.add(t);
			}
			
			model.addAttribute("tipos", tiposNovos);
			gravarLog(new Log(0, TipoLogEnum.TIPO_DOCUMENTO.getId(), usu.getId(), currentTimestamp(), 
					"Lista de Tipos de Documentos", "TIPO_DOCUMENTO", getIp(), "listarTiposDocumentos"));
			model.addAttribute("userLogado", userLogado(usu));
			return new ModelAndView("listarTiposDocumentos", model);
		}else {
			return preparaRetornoLogin("", model);
		}		
	}
	
	@RequestMapping(value = "/listarContratantes", method = RequestMethod.GET) 
	public ModelAndView listarContratantes(ModelMap model, HttpSession session) {
		Usuario usu = recuperarDaSessaoAdmin(session);
		if(null != usu) {

//			Mensagem podeTiposDocs = contratanteRules.podeIncluir(usu);
//
//			if(!podeTiposDocs.isPermitido()) {
//				model.addAttribute("mensagemErro", podeTiposDocs.getMensagem());
//				return preparaDashBoard(model, session);
//			}
//
//			model.addAttribute("tituloPagina", "Lista de Contratantes");
//			List<Contratantes> contratantes = contratantesRepo.findAll();
//			model.addAttribute("contratantes", contratantes);
//			gravarLog(new Log(0, TipoLogEnum.CONTRATANTE.getId(), usu.getId(), currentTimestamp(), 
//					"Lista de Contratantes", "CONTRATANTE", getIp(), "listarContratantes"));
			//model.addAttribute("userLogado", userLogado(usu));
			return preparaListarContratantes(model, session);//new ModelAndView("listarContratantes", model);
		}else {
			return preparaRetornoLogin("", model);
		}		
	}
	
	@RequestMapping(value = "/listarGruposTiposDocumentos", method = RequestMethod.GET) 
	public ModelAndView listarGruposTiposDocumentos(ModelMap model, HttpSession session) {
		Usuario usu = recuperarDaSessaoAdmin(session);
		if(null != usu) {

			Mensagem podeTiposDocs = grupoTipoDocRules.podeAcessarGrupos(usu);

			if(!podeTiposDocs.isPermitido()) {
				model.addAttribute("mensagemErro", podeTiposDocs.getMensagem());
				return preparaDashBoard(model, session);
			}

			model.addAttribute("tituloPagina", "Lista de Grupos de Tipos de Documentos");
			List<GrupoTipoDocumento> grupos = grupoTipoRepo.findAll();
			model.addAttribute("grupos", grupos);
			gravarLog(new Log(0, TipoLogEnum.TIPO_DOCUMENTO.getId(), usu.getId(), currentTimestamp(), 
					"Lista de Tipos de Documentos", "TIPO_DOCUMENTO", getIp(), "listarGruposTiposDocumentos"));
			model.addAttribute("userLogado", userLogado(usu));
			return new ModelAndView("listarGruposTiposDocumentos", model);
		}else {
			return preparaRetornoLogin("", model);
		}		
	}
	
	@RequestMapping(value = "/listarTiposUnidades", method = RequestMethod.GET) 
	public ModelAndView listarTiposUnidades(ModelMap model, HttpSession session) {
		Usuario usu = recuperarDaSessaoAdmin(session);
		if(null != usu) {

			Mensagem podeTiposDocs = tipoUnidadeRules.podeAcessarTipos(usu);

			if(!podeTiposDocs.isPermitido()) {
				model.addAttribute("mensagemErro", podeTiposDocs.getMensagem());
				return preparaDashBoard(model, session);
			}

			model.addAttribute("tituloPagina", "Lista de Tipos de Unidades");
			List<TipoUnidade> tipos = tipoUnidadeRepo.findAll();
			model.addAttribute("tipos", tipos);
			gravarLog(new Log(0, TipoLogEnum.TIPO_UNIDADE.getId(), usu.getId(), currentTimestamp(), 
					"Lista de Tipos de Unidades", "TIPO_UNIDADE", getIp(), "listarTiposUnidades"));
			model.addAttribute("userLogado", userLogado(usu));
			return new ModelAndView("listarTiposUnidades", model);
		}else {
			return preparaRetornoLogin("", model);
		}		
	}

	@RequestMapping(value = "/abrirAlterarTipoDocumento", method = RequestMethod.GET) 
	public ModelAndView abrirAlterarTipoDocumento(@Validated @ModelAttribute("id") Integer id, BindingResult result, ModelMap model, HttpSession session) { 

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(null!= usuSessao) {

			gravarLog(new Log(id, TipoLogEnum.TIPO_DOCUMENTO.getId(), usuSessao.getId(), currentTimestamp(), 
					"Página de Alteração de Tipo de Documento", "TIPO_DOCUMENTO", getIp(), "abrirAlterarTipoDocumento"));

			Optional<TipoDocumento> tipo = tipoDocRepo.findById(id);

			if(tipo.isPresent()) {
				List<GrupoTipoDocumento> grupos = grupoTipoRepo.obterGruposAtivos();
				model.addAttribute("grupos", grupos);
				model.addAttribute("tipo", tipo.get());
				model.addAttribute("userLogado", userLogado(usuSessao));
				model.addAttribute("tituloPagina", "Alterar informações do Tipo de Documento");
				return new ModelAndView("alterarTipoDocumento", model);
			}else {
				model.addAttribute("mensagemErro", "Desculpe, não foi possível localizar o tipo de documento pelo ID informado: id: "+id);
				model.addAttribute("tituloPagina", "Lista de Tipos de Documentos");
				List<TipoDocumento> tipos = tipoDocRepo.findAllTipos();
				model.addAttribute("tipos", tipos);
				gravarLog(new Log(0, TipoLogEnum.TIPO_DOCUMENTO.getId(), usuSessao.getId(), currentTimestamp(), 
						"Lista de Tipos de Documentos", "TIPO_DOCUMENTO", getIp(), "listarTiposDocumentos"));
				model.addAttribute("userLogado", userLogado(usuSessao));
				return new ModelAndView("listarTiposDocumentos", model);
			}
		}else {
			return preparaRetornoLogin("", model);
		}
	}
	
	@RequestMapping(value = "/abrirAlterarGrupoTipoDocumento", method = RequestMethod.GET) 
	public ModelAndView abrirAlterarGrupoTipoDocumento(@Validated @ModelAttribute("id") Integer id, BindingResult result, ModelMap model, HttpSession session) { 

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(null!= usuSessao) {

			gravarLog(new Log(id, TipoLogEnum.GRUPO_TIPO.getId(), usuSessao.getId(), currentTimestamp(), 
					"Página de Alteração de Grupo de Tipo de Documento", "GRUPO_TIPO", getIp(), "abrirAlterarGrupoTipoDocumento"));

			Optional<GrupoTipoDocumento> tipo = grupoTipoRepo.findById(id);

			if(tipo.isPresent()) {
				model.addAttribute("grupo", tipo.get());
				model.addAttribute("userLogado", userLogado(usuSessao));
				model.addAttribute("tituloPagina", "Alterar informações do Grupo de Tipo de Documento");
				return new ModelAndView("alterarGrupoTipoDocumento", model);
			}else {
				model.addAttribute("mensagemErro", "Desculpe, não foi possível localizar o grupo de tipo de documento pelo ID informado: id: "+id);
				model.addAttribute("tituloPagina", "Lista de Grupos de Tipos de Documentos");
				List<GrupoTipoDocumento> tipos = grupoTipoRepo.findAll();
				model.addAttribute("tipos", tipos);
				gravarLog(new Log(0, TipoLogEnum.GRUPO_TIPO.getId(), usuSessao.getId(), currentTimestamp(), 
						"Lista de Grupos de Tipos de Documentos", "GRUPO_TIPO", getIp(), "listarGruposTiposDocumentos"));
				model.addAttribute("userLogado", userLogado(usuSessao));
				return new ModelAndView("listarGruposTiposDocumentos", model);
			}
		}else {
			return preparaRetornoLogin("", model);
		}
	}
	
	@RequestMapping(value = "/abrirAlterarTipoUnidade", method = RequestMethod.GET) 
	public ModelAndView abrirAlterarTipoUnidade(@Validated @ModelAttribute("id") Integer id, BindingResult result, ModelMap model, HttpSession session) { 

		Usuario usuSessao = recuperarDaSessaoAdmin(session);

		if(null!= usuSessao) {

			gravarLog(new Log(id, TipoLogEnum.TIPO_UNIDADE.getId(), usuSessao.getId(), currentTimestamp(), 
					"Página de Alteração de Tipo de Unidade", "TIPO_UNIDADE", getIp(), "abrirAlterarTipoUnidade"));

			Optional<TipoUnidade> tipo = tipoUnidadeRepo.findById(id);

			if(tipo.isPresent()) {
				model.addAttribute("tipo", tipo.get());
				model.addAttribute("userLogado", userLogado(usuSessao));
				model.addAttribute("tituloPagina", "Alterar informações do Tipo de Unidade");
				return new ModelAndView("alterarTipoUnidade", model);
			}else {
				model.addAttribute("mensagemErro", "Desculpe, não foi possível localizar o tipo de unidade pelo ID informado: id: "+id);
				model.addAttribute("tituloPagina", "Lista de Tipos de Unidades");
				List<TipoUnidade> tipos = tipoUnidadeRepo.findAll();
				model.addAttribute("tipos", tipos);
				gravarLog(new Log(0, TipoLogEnum.TIPO_UNIDADE.getId(), usuSessao.getId(), currentTimestamp(), 
						"Lista de Grupos de Tipos de Documentos", "TIPO_UNIDADE", getIp(), "listarTiposUnidades"));
				model.addAttribute("userLogado", userLogado(usuSessao));
				return new ModelAndView("listarTiposUnidades", model);
			}
		}else {
			return preparaRetornoLogin("", model);
		}
	}
	
	@RequestMapping(value = "/listarLogs", method = RequestMethod.GET) 
	public ModelAndView listarLogs(ModelMap model, HttpSession session) {
		Usuario usu = recuperarDaSessaoAdmin(session);
		if(null != usu) {

			Mensagem podeTiposDocs = tipoDocRules.podeAcessarTipos(usu);

			if(!podeTiposDocs.isPermitido()) {
				model.addAttribute("mensagemErro", podeTiposDocs.getMensagem());
				return preparaDashBoard(model, session);
			}

			model.addAttribute("tituloPagina", "Logs do Sistema");
			List<Log> logs = logRepo.findAll();
			model.addAttribute("logs", logs);
			gravarLog(new Log(0, TipoLogEnum.LOG.getId(), usu.getId(), currentTimestamp(), 
					"Lista de Logs do Sistema", "LOG", getIp(), "listarLogs"));
			model.addAttribute("userLogado", userLogado(usu));
			return new ModelAndView("listarLogs", model);
		}else {
			return preparaRetornoLogin("", model);
		}		
	}
}