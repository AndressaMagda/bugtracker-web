package br.com.triadworks.bugtracker.controller.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.triadworks.bugtracker.dao.UsuarioDao;
import br.com.triadworks.bugtracker.modelo.Usuario;

@FacesConverter(forClass=Usuario.class)
public class UsuarioConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if(value == null || value.isEmpty())
			return null;
		Integer id = new Integer(value);
		
		UsuarioDao dao = context.getApplication().
				evaluateExpressionGet(context, "#{usuarioDao}", UsuarioDao.class);
		Usuario usuario = dao.busca(id);
		
		return usuario;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value == null) return null;
		
		Usuario usuario = (Usuario) value;
		
		return usuario.getId().toString();
	}

}
