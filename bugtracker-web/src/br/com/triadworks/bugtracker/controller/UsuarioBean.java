package br.com.triadworks.bugtracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.triadworks.bugtracker.dao.UsuarioDao;
import br.com.triadworks.bugtracker.modelo.Usuario;
import br.com.triadworks.bugtracker.util.FacesUtil;

@Controller
@Scope("request")
public class UsuarioBean {

	private Usuario usuario = new Usuario();
	private List<Usuario> usuarios;
	
	// Pedirá ao container do spring uma instancia válida do usuario dao
	// A anotação Managed Property obriga a ter o método setter do atributo anotado
	private UsuarioDao dao;
	
	private FacesUtil facesUtil;
	
	@Autowired
	public UsuarioBean(UsuarioDao dao, FacesUtil facesUtil) {
		this.dao = dao;
		this.facesUtil = facesUtil;
	}

	public void adiciona() {
		this.dao.adiciona(this.usuario);
		this.usuario = new Usuario();
		this.facesUtil.adicionaMensagemDeSucesso("Usuário adicionado com sucesso!");
	}

	public void lista() {
		this.usuarios = this.dao.lista();
	}
	
	public void remove(Usuario usuario) {
		dao.remove(usuario);
		this.usuarios = this.dao.lista();
		this.facesUtil.adicionaMensagemDeSucesso("Usuário removido com sucesso!");
	}
	
	public void altera(){
		this.dao.atualiza(this.usuario);
		this.facesUtil.adicionaMensagemDeSucesso("Usuário atualizado com sucesso!");
	}
	

	//Getters e setters
	public Usuario getUsuario() {
		return usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void setDao(UsuarioDao dao) {
		this.dao = dao;
	}

}
