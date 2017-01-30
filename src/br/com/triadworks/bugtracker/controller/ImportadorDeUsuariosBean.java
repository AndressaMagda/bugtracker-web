package br.com.triadworks.bugtracker.controller;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.triadworks.bugtracker.service.importacao.ImportadorDeUsuarios;
import br.com.triadworks.bugtracker.util.FacesUtil;

@Controller
@Scope("request")
public class ImportadorDeUsuariosBean {
	private Part arquivo;

	private ImportadorDeUsuarios importador;
	private FacesUtil facesUtil;

	@Autowired
	public ImportadorDeUsuariosBean(ImportadorDeUsuarios importador, FacesUtil facesUtil) {
		this.importador = importador;
		this.facesUtil = facesUtil;
	}

	public void importa() {
		try {
			importador.importa(arquivo.getInputStream());
			facesUtil.adicionaMensagemDeSucesso("Arquivo importado com sucesso!!");
		} catch (Exception e) {
			facesUtil.adicionaMensagemDeErro("Não foi possivel importar o arquivo: " + e.getMessage());
		}
	}

	public void valida(FacesContext ctx, UIComponent component, Object value) {
		Part file = (Part) value;
		if (!"text/plain".equals(file.getContentType())) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Tipo de arquivo inválido",
					"O arquivo deve ser do tipo texto.");
			throw new ValidatorException(msg);
		}
	}

	// Getters e Setters
	public Part getArquivo() {
		return arquivo;
	}

	public void setArquivo(Part arquivo) {
		this.arquivo = arquivo;
	}
}
