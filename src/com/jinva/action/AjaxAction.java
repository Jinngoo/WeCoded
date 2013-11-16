package com.jinva.action;


public class AjaxAction {

//	/**
//     * 
//     */
//	private static final long serialVersionUID = 8308176686963641697L;
//
//	private Map<String, Object> result;
//
//	@Autowired
//	private JinvaService jinvaService;
//	
//	@Autowired
//	private MessageService messageService;
//
//	/**
//	 * 
//	 * @return
//	 */
//	public String loadGroup(){
//		setResult(new HashMap<String, Object>());
//		String id = request.getParameter("id");
//		if(id != null){
//			Group group = jinvaService.get(Group.class, id);
//			result.put("group", group);
//		}
//		return SUCCESS;
//	}
//	
//	/**
//	 * 
//	 * @return
//	 */
//	public String loadRestaurant(){
//		setResult(new HashMap<String, Object>());
//		String id = request.getParameter("id");
//		if(id != null){
//			Restaurant restaurant = jinvaService.get(Restaurant.class, id);
//			result.put("restaurant", restaurant);
//		}
//		return SUCCESS;
//	}
//	
//	/**
//	 * 
//	 * @return
//	 */
//
//	
//	
//
//	
//
//	
//	
//	
//	/**
//	 * 
//	 * @return
//	 */
//	
//	
//	
//	/**
//	 * 
//	 * @return
//	 */
//	public String validateName(){
//		setResult(new HashMap<String, Object>());
//		String name = request.getParameter("name");
//		result.put("access", jinvaService.getUser(name) == null);
//		return SUCCESS;
//	}
//	
//	/**
//	 * 
//	 * @return
//	 * @throws UnsupportedEncodingException 
//	 */
//	public String signup() throws UnsupportedEncodingException{
//		setResult(new HashMap<String, Object>());
//		String name = request.getParameter("name");
//		String password = request.getParameter("password");
//		String nickname = request.getParameter("nickname");
//		nickname = URLDecoder.decode(URLDecoder.decode(nickname, "utf-8"), "utf-8");
//
//		if (jinvaService.getUser(name) != null) {
//			result.put("code", "duplicate");
//		} else {
//			User user = new User(password, name, nickname);
//			if (jinvaService.save(user) == null) {
//				result.put("code", "error");
//			} else {
//				result.put("code", "success");
//			}
//		}
//		return SUCCESS;
//	}
//	
//	/**
//	 * 
//	 * @return
//	 */
//	public String login() {
//		setResult(new HashMap<String, Object>());
//		String name = request.getParameter("username");
//		String pass = request.getParameter("password");
//		User user = jinvaService.getUser(name);
//		if (user == null) {
//			result.put("code", "nouser");
//		} else if (!pass.equals(user.getPassword())) {
//			result.put("code", "wrongpass");
//		} else {
//			session.setAttribute(JinvaConsts.USER_ID, user.getId());
//			session.setAttribute(JinvaConsts.USER_NAME, user.getName());
//			session.setAttribute(JinvaConsts.USER_NICKNAME, user.getNickname());
//			result.put("code", "success");
//		}
//		return SUCCESS;
//	}
//	
//	/**
//	 * 
//	 * @return
//	 */
//	public String logout(){
//		session.setAttribute(JinvaConsts.USER_ID, null);
//		session.setAttribute(JinvaConsts.USER_NAME, null);
//		session.setAttribute(JinvaConsts.USER_NICKNAME, null);
//		return SUCCESS;
//	}
//
//	
//	public String test(){
//		
//		return SUCCESS;
//	}
//	
//	public Map<String, Object> getResult() {
//		return result;
//	}
//
//	public void setResult(Map<String, Object> result) {
//		this.result = result;
//	}

}
