package br.com.triadworks.bugtracker.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.triadworks.bugtracker.dao.UsuarioDao;
import br.com.triadworks.bugtracker.modelo.Usuario;

@Service("autenticador")
public class AutenticadorImpl implements Autenticador {
	
	private UsuarioDao usuarioDAO;
	
	@Autowired
	public AutenticadorImpl(UsuarioDao usuarioDao) {
		this.usuarioDAO = usuarioDao;
	}

	@Override
	public Usuario autentica(String login, String senha) {
		Usuario usuario = usuarioDAO.buscaPor(login, senha);
		return usuario;
	}

}