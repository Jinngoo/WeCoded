package com.jinva.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitListener implements ServletContextListener{

	public static String CONTEXT_REAL_PATH;
	
	@Override
	public void contextDestroyed(ServletContextEvent contextEvent) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		CONTEXT_REAL_PATH = contextEvent.getServletContext().getRealPath("/"); 
	}


	
}
