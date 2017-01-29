package br.com.triadworks.bugtracker.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.triadworks.bugtracker.modelo.Usuario;
import br.com.triadworks.bugtracker.service.Autenticador;
import br.com.triadworks.bugtracker.util.FacesUtil;

@Controller
@Scope("request")
public class LoginBean {
	private String login;
	private String senha;

	// @ManagedProperty obriga a existência do método setter do atributo
	private UsuarioWeb usuarioWeb;
	
	private Autenticador autenticador;
	
	private FacesUtil facesUtil;
	
	@Autowired
	public LoginBean(UsuarioWeb usuarioWeb, Autenticador autenticador, FacesUtil facesUtil) {
		this.usuarioWeb = usuarioWeb;
		this.autenticador = autenticador;
		this.facesUtil = facesUtil;
	}

	public String logar() {
		Usuario usuario = this.autenticador.autentica(login, senha);
		if (usuario != null) {
			this.usuarioWeb.loga(usuario); // preenche o usuário na sessão
			return "/pages/dashboard?faces-redirect=true";
		}

		this.facesUtil.adicionaMensagemDeErro("Login ou senha inválido.");

		// Indica ao JSF para não criar uma nova árvore de componentes e
		// continuar trabalhando com a árvore que já está em memória.
		// Retornar uma string vazia ("") cria uma nova árvore.
		return null;

	}
	
	public String sair(){
		this.usuarioWeb.desloga();
		
		return "/login?faces-redirect=true";
	}

	// Getters e setters
	public String getLogin() {
		return login;
	}

	public String getSenha() {
		return senha;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setUsuarioWeb(UsuarioWeb usuarioWeb) {
		this.usuarioWeb = usuarioWeb;
	}
	
	public void setAutenticador(Autenticador autenticador) {
		this.autenticador = autenticador;
	}

}
