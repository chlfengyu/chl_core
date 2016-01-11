package test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import basetest.BaseSpringTest;

import com.chl.web.system.user.bean.User;
import com.chl.web.system.user.dao.UserDao;
import com.chl.web.system.user.service.UserService;

public class UserTest extends BaseSpringTest {
	@Autowired(required = true)
	private UserDao userDao;

	@Autowired
	private UserService userService;

	@Test
	public void getUser() {
		User user = this.userDao.getUser("admin");
		System.out.println(user.getName());

		User user2 = this.userService.getUser("admin");
		System.out.println(user2.getLoginId());
	}
}
