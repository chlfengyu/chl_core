package com.chl.web.system.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chl.web.system.user.bean.User;
import com.chl.web.system.user.dao.UserDao;

@Service("userService")
public class UserService {
	@Autowired
	private UserDao userDao;

	public User getUser(String loginId) {
		return this.userDao.getUser(loginId);
	}
}
