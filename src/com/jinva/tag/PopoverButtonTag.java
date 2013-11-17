package com.jinva.tag;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;

public class PopoverButtonTag extends BodyTagSupport  {

    /**
     * 
     */
    private static final long serialVersionUID = 8200410969796473771L;

    private String id;

    private String style;

    private String popoverContent;

    private String popoverTrigger;

    private String popoverTitle;

    private String popoverPlacement;

    private String imgUrl;

    private String content;

    
    @Override
    public int doAfterBody() throws JspException {
        BodyContent bodyContent = getBodyContent();
        JspWriter out = bodyContent.getEnclosingWriter();
        
        if (StringUtils.isEmpty(popoverTrigger)) {
            popoverTrigger = "hover";
        }
        if (StringUtils.isEmpty(popoverPlacement)) {
            popoverPlacement = "bottom";
        }

        StringBuilder buff = new StringBuilder();
        buff.append("<a class=\"btn btn-default jinva_popover_btn\" role=\"button\" ");
        buff.append(" onmouseover=\"initPopover(this)\" ");
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
        buff.append("<img class=\"jinva_popover_btn_body_img\" id=\"img_").append(id).append("\" src=\"").append(imgUrl).append("\" />");
        buff.append("<div class=\"jinva_popover_btn_body_content\">").append(content).append("</div>");
        buff.append("<div class=\"jinva_popover_btn_body_toolbar\">");

        String body = bodyContent.getString();
        if(StringUtils.isNotBlank(body)){
            buff.append(body);
        }
        buff.append("</div></div></a>");

        try {
            out.print(buff.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }

    @Override
    public int doStartTag() throws JspException {
        return EVAL_BODY_BUFFERED;  
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
