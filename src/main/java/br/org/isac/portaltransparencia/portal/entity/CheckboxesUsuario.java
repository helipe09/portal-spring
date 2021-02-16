package br.org.isac.portaltransparencia.portal.entity;

import java.util.List;

public class CheckboxesUsuario {
	
	private List<Usuario> usuarios;
	
	public CheckboxesUsuario() {
		super();
	}

	public CheckboxesUsuario(List<Usuario> usuarios) {
		super();
		this.usuarios = usuarios;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public void addUsuario(Usuario usuario) {
		this.usuarios.add(usuario);
	}
}
