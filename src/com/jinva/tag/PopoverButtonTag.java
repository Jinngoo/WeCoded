package com.jinva.tag;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

public class PopoverButtonTag extends SimpleTagSupport {

    private String id;

    private String style;

    private String popoverContent;

    private String popoverTrigger;

    private String popoverTitle;

    private String popoverPlacement;

    private String imgUrl;

    private String content;

    
    @Override
    public void doTag() throws JspException, IOException {
        if (StringUtils.isEmpty(popoverTrigger)) {
            popoverTrigger = "hover";
        }
        if (StringUtils.isEmpty(popoverPlacement)) {
            popoverPlacement = "bottom";
        }

        StringBuilder buff = new StringBuilder();
        buff.append("<a class=\"btn btn-default jinva_popover_btn\" role=\"button\" ");
        if (StringUtils.isBlank(id)) {
            id = UUID.randomUUID().toString();
        }
        buff.append(" id=\"").append(id).append("\"");

        if (StringUtils.isNotBlank(style)) {
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

        getJspBody().invoke(null);
        // TODO
        // if(StringUtils.isNotBlank(body)){
        // buff.append(body);
        // }

        buff.append("</div></div></a>");

        buff.append("<script type=\"text/javascript\">");
        buff.append("$(function () {");
        buff.append("$('#").append(id).append("')").append(".popover({html:true,delay:{show:500}});");
        buff.append("});");
        buff.append("</script>");
        getJspContext().getOut().print(buff.toString());
    }

    // public Component getBean(ValueStack stack, HttpServletRequest req,
    // HttpServletResponse res) {
    // return new PopoverButtonBean(stack);
    // }
    //
    // protected void populateParams() {
    // PopoverButtonBean popoverButtonBean = (PopoverButtonBean) getComponent();
    // popoverButtonBean.setId(id);
    // popoverButtonBean.setStyle(style);
    // popoverButtonBean.setContent(content);
    // popoverButtonBean.setImgUrl(imgUrl);
    // popoverButtonBean.setPopoverContent(popoverContent);
    // popoverButtonBean.setPopoverPlacement(popoverPlacement);
    // popoverButtonBean.setPopoverTitle(popoverTitle);
    // popoverButtonBean.setPopoverTrigger(popoverTrigger);
    // super.populateParams();
    // }

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
