package com.chl.web.system.user.dao;

import com.chl.web.system.user.bean.User;

public interface UserDao {
	/**
	 * 通过用户ID获取用户
	 * 
	 * @param loginId
	 *            loginId
	 * @return User
	 */
	User getUser(String loginId);
}
