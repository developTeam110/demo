package com.demo.common.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.common.util.PropertiesUtil;
import com.demo.common.util.StringUtil;

public class InitSystemListener implements ServletContextListener {

	private final static Logger logger = LoggerFactory.getLogger(InitSystemListener.class);
	private static final String CONFIG_LOCATION_PARAM = "initConfigLocation";

	@Override
	public void contextDestroyed(ServletContextEvent event) {

	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		//服务器容器的context对象
		ServletContext servletContext = event.getServletContext();
		logger.info("initialize system properties");

		//context对象中的配置参数，在web.xml中配置
		String configFile = servletContext.getInitParameter(CONFIG_LOCATION_PARAM);

		if (StringUtil.isNotEmpty(configFile)) {
			String[] cfs = configFile.split(",");
			List<String> configFileList = new ArrayList<String>();
			for (String cf : cfs) {
				configFileList.add(cf);
			}

			//加载property文件到SystemProperties的静态实体对象
			PropertiesUtil.loadPropertyFiles(configFileList);
			logger.info("load all system properties files [{}]", configFileList);
		}
	}

}
