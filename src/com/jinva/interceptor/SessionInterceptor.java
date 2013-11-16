package com.jinva.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jinva.bean.datamodel.User;
import com.jinva.consts.JinvaConsts;


public class SessionInterceptor extends HandlerInterceptorAdapter {

	// private Logger logger = Logger.getLogger(SessionInterceptor.class);

	private static List<String> RESOURCES = new ArrayList<String>();
	static {
	    RESOURCES.add("resource");
	}
	
	private Boolean skipSignIn;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
		String contextPath = request.getContextPath();
		String uri = request.getRequestURI();
		if (StringUtils.isNotBlank(contextPath)) {
			uri = uri.replace(request.getContextPath(), "");
		}

//		if (uri.contains("not-support-html5.html")) {
//			return true;
//		}
		
		String path = uri.startsWith("/") ? uri.substring(1) : uri;
		if (StringUtils.isNotBlank(path)) {
			path = path.contains("/") ? path.substring(0, path.indexOf("/")) : path;
		}
		
		if (RESOURCES.contains(path)) {
            return true;
        }
		
		String userAgent = request.getHeader("user-agent");
		if (userAgent.contains("MSIE")) {
		    if (userAgent.contains("MSIE 5.0") || userAgent.contains("MSIE 6.0") || userAgent.contains("MSIE 7.0")
		            || userAgent.contains("MSIE 8.0")) {
		        response.sendRedirect(request.getContextPath() + "/resource/static/not-support-html5.html");
		        return false;
		    }
		}

		if ("test".equals(path) || "login".equals(path)) {
			return true;
		}

        if (skipSignIn) {
            return true;
        }
		
		User user = (User) request.getSession().getAttribute(JinvaConsts.USER);
		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView mav)
			throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception excptn)
			throws Exception {
	}

    public Boolean getSkipSignIn() {
        return skipSignIn;
    }

    @Value("#{propertiesReader[skipSignIn]}")
    public void setSkipSignIn(Boolean skipSignIn) {
        this.skipSignIn = skipSignIn;
    }

}
