package com.jinva.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.opensymphony.xwork2.util.ValueStack;

public class PopoverButtonTag extends ComponentTagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 330177058083313255L;
	
	private String id;
	
	private String style;
	
	private String popoverContent;
	
	private String popoverTrigger;
	
	private String popoverTitle;
	
	private String popoverPlacement;
	
	private String imgUrl;
	
	private String content;
	
	public Component getBean(ValueStack stack, HttpServletRequest req,
			HttpServletResponse res) {
		return new PopoverButtonBean(stack);
	}

	protected void populateParams() {
		PopoverButtonBean popoverButtonBean = (PopoverButtonBean) getComponent();
		popoverButtonBean.setId(id);
		popoverButtonBean.setStyle(style);
		popoverButtonBean.setContent(content);
		popoverButtonBean.setImgUrl(imgUrl);
		popoverButtonBean.setPopoverContent(popoverContent);
		popoverButtonBean.setPopoverPlacement(popoverPlacement);
		popoverButtonBean.setPopoverTitle(popoverTitle);
		popoverButtonBean.setPopoverTrigger(popoverTrigger);
		super.populateParams();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPopoverContent() {
		return popoverContent;
	}

	public void setPopoverContent(String popoverContent) {
		this.popoverContent = popoverContent;
	}

	public String getPopoverTrigger() {
		return popoverTrigger;
	}

	public void setPopoverTrigger(String popoverTrigger) {
		this.popoverTrigger = popoverTrigger;
	}

	public String getPopoverTitle() {
		return popoverTitle;
	}

	public void setPopoverTitle(String popoverTitle) {
		this.popoverTitle = popoverTitle;
	}

	public String getPopoverPlacement() {
		return popoverPlacement;
	}

	public void setPopoverPlacement(String popoverPlacement) {
		this.popoverPlacement = popoverPlacement;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}


}
