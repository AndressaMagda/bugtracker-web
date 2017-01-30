package br.com.triadworks.bugtracker.service.importacao;

import java.io.InputStream;

public interface ImportadorDeUsuarios {
	public void importa(InputStream inputStream);
}
