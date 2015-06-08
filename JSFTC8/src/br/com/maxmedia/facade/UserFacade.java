package br.com.maxmedia.facade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.maxmedia.dao.UserDao;
import br.com.maxmedia.entity.UserEntity;
import br.com.maxmedia.exceptions.MyException;

@Named
public class UserFacade implements Serializable {

	private static final long serialVersionUID = 3054774149193681526L;

	@Inject
	private UserDao dao;

	public List<UserEntity> findAll() {
		dao.beginTransaction();
		return dao.findAll();
	}

	public UserDao getDao() {
		return dao;
	}

	public void setDao(UserDao dao) {
		this.dao = dao;
	}

	public List<String> gravar(UserEntity user) throws MyException {

		List<String> erros = this.validarUser(user); 
		
		if (erros.isEmpty()) {
			dao.beginTransaction();
			if (user != null) {
				if (user.getId() != null && user.getId() > 0) {
					this.salvarEdicao(user);
				} else {
					this.salvarNovo(user);
				}
				dao.commitAndCloseTransaction();
			} else {
				throw new MyException("usuario nulo");
			}
		} 
		return erros;
	}

	private List<String> validarUser(UserEntity user) {
		List<String> erros = new ArrayList<String>();
		if (user == null) {
			erros.add("Dados inválidos");
		} else {
			if ( user.getFirstName() == null ||  user.getFirstName().isEmpty()) {
				erros.add("Campo nome é obrigatório");
			}
			
			if ( user.getLastName() == null ||  user.getLastName().isEmpty()) {
				erros.add("Campo sobrenome é obrigatório");
			}
		}
		return erros;
	}

	private void salvarNovo(UserEntity user) {
		dao.save(user);
	}

	private void salvarEdicao(UserEntity user) {
		dao.update(user);
	}

	public UserEntity findByID(Long id) throws MyException {
		if (id != null && id > 0) {
			return dao.find(id);
		}
		throw new MyException("ID Não encontrado");
	}

	public void apagar(Long id) throws MyException {
		if (id != null && id > 0) {
			UserEntity user = dao.find(id);
			dao.delete(user);
		}
		throw new MyException("ID Não encontrado");
	}
}
