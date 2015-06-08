package br.com.maxmedia.dao;

import java.io.Serializable;

import javax.inject.Named;

import br.com.maxmedia.entity.UserEntity;

@Named
public class UserDao extends GenericDAO<UserEntity> implements Serializable {

	private static final long serialVersionUID = 5323643192930155411L;

	public UserDao() {
		super(UserEntity.class);
	}

}
