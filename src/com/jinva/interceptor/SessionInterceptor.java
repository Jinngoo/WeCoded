package com.jinva.interceptor;

import java.net.URLEncoder;
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
		
		//[chrome]         Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.57 Safari/537.36
		//[ie10]           Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)
		//[ie10兼容 ]        Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/6.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E)
		//[ie9]            Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)
		//[ie8]            Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E)
		//[ie7]            Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E)
		//[firefox]        Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0
		//[sougou高速 ]      Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.95 Safari/537.36 SE 2.X MetaSr 1.0
		//[sougou兼容 ]      Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0; SE 2.X MetaSr 1.0)
		//[世界之窗  ]         Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0;  TheWorld 6)
		//[360极速版极速模式  ]  Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31
		//[360极速版兼容模式  ]  Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)
		//[360极速版ie模式 ]   Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)
		//[360安全兼容  ]      Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)
		//[360安全极速  ]      Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.89 Safari/537.1
		
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
//			response.sendRedirect(request.getContextPath() + "/login");
			if (StringUtils.isNotBlank(uri) && !uri.equals("/")) {
                String encodeUri = URLEncoder.encode(URLEncoder.encode(uri, "UTF-8"), "UTF-8");
                response.sendRedirect(request.getContextPath() + "/login/" + encodeUri);
            } else {
                response.sendRedirect(request.getContextPath() + "/login");
            }
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
