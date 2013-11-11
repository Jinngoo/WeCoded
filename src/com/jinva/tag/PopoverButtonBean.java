package com.jinva.tag;

import java.io.IOException;
import java.io.Writer;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.components.Component;

import com.opensymphony.xwork2.util.ValueStack;

public class PopoverButtonBean extends Component {
	
	private String id;
	
	private String style;

	private String popoverContent;
	
	private String popoverTrigger;
	
	private String popoverTitle;
	
	private String popoverPlacement;
	
	private String imgUrl;
	
	private String content;
	
	private static final Log logger = LogFactory.getLog(PopoverButtonBean.class);
	
	public PopoverButtonBean(ValueStack stack) {
		super(stack);
	}

	@Override
	public boolean usesBody() {
		return true;
	}

	public boolean start(Writer writer) {
		return super.start(writer);
	}

//<a class="btn img_btn" data-content="创建者:&nbsp;&nbsp;${group.ownerName}<br>组简介:&nbsp;&nbsp;${group.introduction}" data-trigger="hover" data-title="[${group.name}]" 
//	<c:if test="${(status.index+1)%3 eq 0 }">data-placement="bottom" </c:if>
//	rel="popover">
//<div class="img_btn_body">
//	<img class="img_btn_body_img" src="${pageContext.request.contextPath}/image/touxiang.jpg" style="" />
//	<div class="img_btn_body_content" style="">
//		<c:out value="${group.name}"/>
//	</div>
//	<div class="img_btn_body_toolbar">
//		<c:if test="${group.ownerId eq sessionScope.user_id }">
//			<i class="icon-cog" style="cursor:pointer;" onclick="editGroup('${group.id}')" title="编辑"></i>
//		</c:if>
//	</div>
//</div>
//</a>
//	<script>
//	$(function () {
//		$('#groupListBody a[rel="popover"]').popover({html:true});
//	});
//</script>
	
	public boolean end(Writer writer, String body) {
		if(StringUtils.isEmpty(popoverTrigger)){
			popoverTrigger = "hover";
		}
		if(StringUtils.isEmpty(popoverPlacement)){
			popoverPlacement = "bottom";
		}
		
			StringBuilder buff = new StringBuilder();
			buff.append("<a class=\"btn jinva_popover_btn\"");
			if (StringUtils.isBlank(id)) {
				id = UUID.randomUUID().toString();
			}
			buff.append(" id=\"").append(id).append("\"");
			
			if(StringUtils.isNotBlank(style)){
				buff.append(" style=\"").append(style).append("\"");
			}
			buff.append(" data-content=\"").append(popoverContent).append("\"");
			buff.append(" data-trigger=\"").append(popoverTrigger).append("\"");
			buff.append(" data-title=\"").append(popoverTitle).append("\"");
			buff.append(" data-placement=\"").append(popoverPlacement).append("\"");
			buff.append(" rel=\"popover\" data-container=\"body\" >");
			
			buff.append("<div class=\"jinva_popover_btn_body\">");
			buff.append("<img class=\"jinva_popover_btn_body_img\" src=\"").append(imgUrl).append("\" />");
			buff.append("<div class=\"jinva_popover_btn_body_content\">").append(content).append("</div>");
			buff.append("<div class=\"jinva_popover_btn_body_toolbar\">");
			if(StringUtils.isNotBlank(body)){
				buff.append(body);
			}
			buff.append("</div></div></a>");
			
			buff.append("<script type=\"text/javascript\">");
			buff.append("$(function () {");
			buff.append("$('#").append(id).append("')").append(".popover({html:true,delay:{show:500}});");
			buff.append("});");
			buff.append("</script>");
		try {
			writer.write(buff.toString());
		} catch (IOException e) {
			logger.error("fail to output end of PopoverButtonBean", e);
		}
		return super.end(writer, "");
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}


}
