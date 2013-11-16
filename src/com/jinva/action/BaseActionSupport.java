package com.jinva.action;


/**
 * 
 * @author Administrator
 * 
 */
public class BaseActionSupport{ 
//extends ActionSupport implements
//		ServletRequestAware, ServletContextAware,ServletResponseAware,CodeSupport {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 2894015160348781953L;
//	
//	protected String page;
//
//	public String getPage() {
//		return page;
//	}
//
//	public void setPage(String page) {
//		this.page = page;
//	}
//
//	protected Log logger = LogFactory.getLog(getClass());
//	
//	protected HttpServletRequest request;
//	protected HttpServletResponse response;
//	protected HttpSession session;
//	protected ServletContext application;
//
//	@Override
//	public String execute() throws Exception {
//		// this.session = this.request.getSession();
//		return super.execute();
//	}
//
//	public void setServletContext(ServletContext application) {
//		this.application = application;
//	}
//
//	public void setServletRequest(HttpServletRequest request) {
//		this.request = request;
//		this.session = request.getSession();
//	}
//
//	public void setServletResponse(HttpServletResponse response) {
//		this.response = response;
//	}
//	
//	public HttpServletRequest getRequest() {
//		return request;
//	}
//
//	public ServletContext getApplication() {
//		return application;
//	}
//
//	protected String getUserId() {
//		return (String) session.getAttribute(JinvaConsts.USER_ID);
//	}
//	
//	protected String getUserName() {
//		return (String) session.getAttribute(JinvaConsts.USER_NAME);
//	}
//	
//	/**
//	 * 
//	 * @param key
//	 * @return
//	 */
//	protected boolean hasSessionAttr(String key) {
//		if (StringUtils.isBlank(key)) {
//			return false;
//		}
//		Object attr = session.getAttribute(key);
//		if (attr == null) {
//			return false;
//		}
//		if (attr instanceof String) {
//			return StringUtils.isNotEmpty(key.toString());
//		} else {
//			return true;
//		}
//	}
//
//	@SuppressWarnings("unchecked")
//	protected Object getPojo(Class<?> clazz, String prefix) throws InstantiationException, IllegalAccessException, InvocationTargetException  {
//		Map<String, String[]> params = request.getParameterMap();
//		Map<String, Object> validParams = new HashMap<String, Object>();
//		for(Entry<String, String[]> entry : params.entrySet()){
//			String key = entry.getKey();
//			if(key.contains(prefix)){
//				validParams.put(key.split("_")[1], entry.getValue());
//			}
//		}
//		Object pojo = clazz.newInstance();
//		BeanUtils.populate(pojo, validParams);
//		return pojo;
//	}
//	
//	/**
//	 * 
//	 * @param prefix
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	protected Map<String, Object> getValidParameterMap(String prefix) {
//		Map<String, String[]> params = request.getParameterMap();
//		Map<String, Object> validParams = new HashMap<String, Object>();
//		for(Entry<String, String[]> entry : params.entrySet()){
//			String key = entry.getKey();
//			if(key.contains(prefix)){
//				validParams.put(key.split("_")[1], entry.getValue());
//			}
//		}
//		return validParams;
//	}
//	
//	protected String toString(Object obj) {
//		if (obj == null) {
//			return null;
//		}
//		if (obj instanceof Object[]) {
//			Object[] arr = (Object[]) obj;
//			if (arr.length > 0) {
//				return (String) arr[0];
//			} else {
//				return null;
//			}
//		} else {
//			return (String) obj;
//		}
//	}
//
//	public Map<Integer, String> getOrderProviderStatusCode() {
//		i18n(orderProviderStatusCode);
//		return orderProviderStatusCode;
//	}
//	
//	public Map<Integer, String> getMessageStatusCode() {
//		i18n(messageStatusCode);
//		return messageStatusCode;
//	}
//	
//	private void i18n(Map<Integer, String> codeMap){
//		Iterator<Integer> keys = codeMap.keySet().iterator();
//		while(keys.hasNext()){
//			Integer key = keys.next();
//			String text = getText(codeMap.get(key));
//			if(text != null){
//				codeMap.put(key, text);
//			}
//		}
//	}
//	
//	// /**
//	// * 获得String类型的参数
//	// *
//	// * @param str
//	// * @return
//	// */
//	// public String getParamString(String str) {
//	// return ((String[])
//	// ActionContext.getContext().getParameters().get(str))[0];
//	// }
//	// /**
//	// * 获得int类型的参数
//	// *
//	// * @param str
//	// * @return
//	// */
//	// public Integer getParamInteger(String str) {
//	// String param = ((String[])
//	// ActionContext.getContext().getParameters().get(str))[0];
//	// if (StringUtils.isBlank(param)) {
//	// return null;
//	// }
//	// return Integer.parseInt(param);
//	// }

}
