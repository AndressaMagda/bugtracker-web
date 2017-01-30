package br.com.triadworks.bugtracker.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.triadworks.bugtracker.modelo.Usuario;

@Transactional
@Repository
public class UsuarioDao {

	@PersistenceContext
	private EntityManager manager;

	public List<Usuario> lista() {
		return manager.createQuery("select u from Usuario u", Usuario.class).getResultList();
	}

	//Com isso, o processo de importação de usuários não será cancelado(rollback) ao tentar 
	//Persistir um usuário duplicado. Com isso sempre que esse método for invocado, o Spring suspenderá
	// a transação atual e criará uma nova independente e ao seu termino, retomará o processamento da
	// transação atual.
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void adiciona(Usuario usuario) {
		manager.persist(usuario);
	}

	public void atualiza(Usuario usuario) {
		manager.merge(usuario);
	}

	public void remove(Usuario usuario) {
		manager.remove(manager.merge(usuario));
	}

	public Usuario busca(Integer id) {
		return manager.find(Usuario.class, id);
	}

	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public Usuario buscaPor(String login, String senha) {
		try {
			return manager.createQuery("select u from Usuario u " + "where u.login = :login and u.senha = :senha",
					Usuario.class).setParameter("login", login).setParameter("senha", senha).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
