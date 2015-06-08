package br.com.maxmedia.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.maxmedia.entity.UserEntity;
import br.com.maxmedia.exceptions.MyException;
import br.com.maxmedia.facade.UserFacade;
import br.com.maxmedia.helper.MBeanHelper;
import br.com.maxmedia.helper.MessageHelper;

import java.io.Serializable;

@Named("controllerMBean")
@SessionScoped
public class ControllerMBean implements Serializable {

	private static final long serialVersionUID = -3137648429637634429L;
	protected final Logger log = Logger.getLogger(this.getClass().getName());
	private UserEntity user;
	private MessageHelper message;

	@Inject
	private UserFacade userFacade;

	public ControllerMBean() {
	}

	@PostConstruct
	private void init() {
		message = new MessageHelper();
		user = new UserEntity();
	}

	public void gravar() {

		try {
			List<String> erros = userFacade.gravar(user);
			if (erros.isEmpty()) {
				this.setUser(new UserEntity());
				message.sendMessageInfo("Usuário gravado.");
			} else {
				message.sendMessageError(erros);
			}
			
		} catch (MyException e) {
			e.printStackTrace();
			message.sendMessageError(e.getMotivo());
		}
	}

	public List<UserEntity> getUsuarios() {
		List<UserEntity> users = userFacade.findAll();
		return users;
	}

	public void editar() {
		String idUser;
		try {

			idUser = (String) MBeanHelper.getParametroRequest("idUser");
			log.info("Editando: " + idUser);
			try {
				this.setUser(userFacade.findByID(Long.parseLong(idUser)));
			} catch (MyException e) {
				message.sendMessageError(e.getMotivo());
			}

		} catch (IOException e) {
			message.sendMessageError("Não veio o ID");
		}

	}
	
	public void apagar() {
		
		String idUser;
		try {

			idUser = (String) MBeanHelper.getParametroRequest("idUser");
			message.sendMessageInfo("Apagando " + idUser);
			try {
				userFacade.apagar(Long.parseLong(idUser));
			} catch (MyException e) {
				e.printStackTrace();
				message.sendMessageError(e.getMotivo());
			}
		} catch (IOException e) {
			message.sendMessageError("Não veio o ID");
		}
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

}
