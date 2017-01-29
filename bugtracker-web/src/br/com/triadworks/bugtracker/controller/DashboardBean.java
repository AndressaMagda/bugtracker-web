package br.com.triadworks.bugtracker.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.triadworks.bugtracker.dao.BugDao;
import br.com.triadworks.bugtracker.modelo.Bug;

@Controller
@Scope("request")
public class DashboardBean {
	private List<Bug> bugs;

	private BugDao dao;
	private UsuarioWeb usuarioWeb;

	@Autowired
	public DashboardBean(BugDao dao, UsuarioWeb usuarioWeb) {
		this.dao = dao;
		this.usuarioWeb = usuarioWeb;
	}

	@PostConstruct
	public void init() {
		// Carrega os bugs do usuário logado sempre que a MB for instanciada
		// pelo container do Spring
		Integer id = usuarioWeb.getUsuario().getId();
		this.bugs = this.dao.getBugsDoUsuario(id);
	}

	// Getters e Setters
	public List<Bug> getBugs() {
		return bugs;
	}
}
