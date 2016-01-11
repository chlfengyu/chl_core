package com.chl.core.util.properties;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 *
 * @ProjectNmae：util.
 * @ClassName：PropertysUtil
 * @Description： property配置文件工具类
 * @author：chl
 * @crateTime：2013-7-3
 * @editor：
 * @editTime：
 * @editDescription：
 * @version 1.0.0
 */
public class PropertiesUtils extends PropertyPlaceholderConfigurer {

	private static Map<String, String> ctxPropertiesMap;

	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactory, Properties props)
			throws BeansException {
		super.processProperties(beanFactory, props);
		// 读取propertys到ctxPropertiesMap属性中
		ctxPropertiesMap = new HashMap<String, String>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			ctxPropertiesMap.put(keyStr, value);
		}
	}

	/**
	 * 获取propertys值
	 *
	 * @param name
	 *            propertys的key值
	 * @return
	 * @author HuangS
	 * @date 2013-7-3
	 */
	public static String getContextProperty(String name) {
		return ctxPropertiesMap.get(name);
	}

}
