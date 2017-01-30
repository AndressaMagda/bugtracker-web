package br.com.triadworks.bugtracker.service.importacao;

import java.io.InputStream;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.triadworks.bugtracker.dao.UsuarioDao;
import br.com.triadworks.bugtracker.modelo.Usuario;

@Service
public class ImportadorDeUsuariosImpl implements ImportadorDeUsuarios {

	private UsuarioParser parser;
	private UsuarioDao dao;
	private static final Logger logger = Logger.getLogger(ImportadorDeUsuariosImpl.class);

	@Autowired
	public ImportadorDeUsuariosImpl(UsuarioParser parser, UsuarioDao dao) {
		this.parser = parser;
		this.dao = dao;
	}

	/*
	 * Importa os usuários a partir de um arquivo
	 */
	@Transactional
	@Override
	public void importa(InputStream inputStream) {
		Scanner scanner = null;

		try {
			scanner = new Scanner(inputStream);

			while (scanner.hasNextLine()) {
				String linha = scanner.nextLine();
				Usuario usuario = parser.parse(linha);
				try {
					dao.adiciona(usuario);
				} catch (Exception e) {
					//e.printStackTrace();
					logger.error("Erro ao importar usuário " + usuario.getLogin());
				}
			}
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
	}

}
