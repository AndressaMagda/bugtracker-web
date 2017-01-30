package br.com.triadworks.bugtracker.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.triadworks.bugtracker.dao.BugDao;
import br.com.triadworks.bugtracker.dao.UsuarioDao;
import br.com.triadworks.bugtracker.modelo.Bug;
import br.com.triadworks.bugtracker.modelo.Status;
import br.com.triadworks.bugtracker.modelo.Usuario;
import br.com.triadworks.bugtracker.util.FacesUtil;

@Controller
@Scope("request")
public class BugBean {
	private Bug bug = new Bug();

	private List<Bug> bugs = new ArrayList<Bug>();

	private List<Usuario> usuarios;

	private UsuarioDao usuarioDao;

	private BugDao bugDao;

	private FacesUtil facesUtil;

	@Autowired
	public BugBean(UsuarioDao usuarioDao, BugDao bugDao, FacesUtil facesUtil) {
		this.usuarioDao = usuarioDao;
		this.bugDao = bugDao;
		this.facesUtil = facesUtil;
	}

	@PostConstruct
	private void init() {
		this.bug.setResponsavel(new Usuario());
	}

	public void adiciona() {
		this.bugDao.salva(this.bug);

		facesUtil.adicionaMensagemDeSucesso(
				"Bug adicionado com sucesso e " + "assinado para " + this.bug.getResponsavel().getNome() + ".");

		this.bug = new Bug();
	}

	public void lista() {
		this.bugs = this.bugDao.lista();
	}

	public void remove(Bug bug) {
		this.bugDao.remove(bug);
		this.bugs = this.bugDao.lista();
		facesUtil.adicionaMensagemDeSucesso("Bug removido com sucesso!");
	}

	public void altera() {
		this.bugDao.atualiza(this.bug);
		facesUtil.adicionaMensagemDeSucesso("Bug atualizado com sucesso!");
	}

	public List<Status> getTodosOsStatus() {
		return Arrays.asList(Status.values());
	}

	public String carrega() {
		if (this.bug.getId() != null) {
			bug = this.bugDao.busca(this.bug.getId());
			return null;
		}
		this.facesUtil.adicionaMensagemDeErro("Bug inválido ou não encontrado.");
		return "/pages/dashboard";

	}

	// Getters e Setters
	public Bug getBug() {
		return bug;
	}

	public void setBug(Bug bug) {
		this.bug = bug;
	}

	public void setBugDao(BugDao bugDao) {
		this.bugDao = bugDao;
	}

	public List<Bug> getBugs() {
		return bugs;
	}

	public void setBugs(List<Bug> bugs) {
		this.bugs = bugs;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	public List<Usuario> getUsuarios() {

		if (this.usuarios == null)
			this.usuarios = this.usuarioDao.lista();

		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

}
