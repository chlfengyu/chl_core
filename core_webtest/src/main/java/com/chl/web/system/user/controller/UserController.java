/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.chl.web.system.user.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chl.core.util.properties.ObjectUtils;
import com.chl.core.util.properties.RequestUtils;
import com.chl.web.common.model.PageModel;
import com.chl.web.system.user.bean.User;
import com.chl.web.system.user.service.UserService;

/**
 * @author Tera
 *
 */
@Controller
@RequestMapping(value = "/sys/user/")
public class UserController {
	/**
	 * userService
	 */
	@Autowired
	private UserService userService;
	/**
	 * 日志
	 */
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(UserController.class);

	/**
	 * 打开查询列表
	 *
	 * @param request
	 *            request
	 * @param map
	 *            map
	 * @throws Exception
	 *             exception
	 * @return string
	 */
	@RequestMapping("query.do")
	public String sysUserQuery(HttpServletRequest request, ModelMap map)
			throws Exception {
		return "sys/user/sysUserQuery";
	}

	/**
	 * 打开数据列表
	 *
	 * @param request
	 *            request
	 * @param map
	 *            map
	 * @throws Exception
	 *             exception
	 * @return user list
	 */
	@RequestMapping("list.do")
	public String sysUserList(HttpServletRequest request, ModelMap map)
			throws Exception {
		User user = (User) RequestUtils.getRequestBean(User.class, request);
		Map<String, Object> beanMap = null;
		beanMap = ObjectUtils.describe(user);

		PageModel pm = new PageModel();
		pm.init(request, 20, null, user);
		beanMap.put("rowS", pm.getRowS());
		beanMap.put("rowE", pm.getRowE());
		List<User> users = this.userService.getUserPageList(beanMap);
		pm.setData(users);
		map.put("pm", pm);
		return "sys/user/sysUserList";
	}

	@RequestMapping("login")
	public String login() {
		System.out.println("登陆成功！");
		return "success";
	}

}
