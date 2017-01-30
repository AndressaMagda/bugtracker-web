package br.com.triadworks.bugtracker.controller.datamodel;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.triadworks.bugtracker.dao.BugDao;
import br.com.triadworks.bugtracker.modelo.Bug;

@ViewScoped
@ManagedBean
public class BugDataModel extends LazyDataModel<Bug>{

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty("#{bugDao}")
	private BugDao dao;
	
	@PostConstruct
	public void init(){
		//Necessario informar a quantidade de registro que serão paginados
		this.setRowCount(this.dao.contaTodos());
	}
	
	@Override
	public List<Bug> load(int inicio, int quantidade, String campoDeOrdenacao, 
			SortOrder sentidoDeOrdenacao, Map<String, Object> filtros) {

		return dao.listaPaginada(inicio, quantidade);
	}
	
	//Getters e Setters
	
	public void setDao(BugDao dao) {
		this.dao = dao;
	}
}
