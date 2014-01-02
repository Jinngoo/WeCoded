package com.jinva.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

public class CarouselPageTag extends TagSupport  {


    /**
     * 
     */
    private static final long serialVersionUID = -4392685757490870243L;

    private String id;

    private String style;

    private String aaZone;
    
    private String pageDataProvider;
    
    private String pageInfoProvider;
    
    private Integer pageNum;
    
    private Integer pageSize;
    
    private String url;
    
    private String initLoad;

    @Override
    public int doStartTag() throws JspException {
//        String contextPath = pageContext.getServletContext().getContextPath();
    
//        String prePageId = "prePage_" + id;
//        String nextPageId = "nextPage_" + id;
        String prePageRealId = "prePageReal_" + id;
        String nextPageRealId = "nextPageReal_" + id;
        
        StringBuilder buff = new StringBuilder();
        buff.append("<div id=\"").append(id).append("\" class=\"carousel slide carouselPage\" ");
        buff.append(" pageDataProvider=\"").append(pageDataProvider).append("\"");
        buff.append(" pageInfoProvider=\"").append(pageInfoProvider).append("\"");
        buff.append(" aaZone=\"").append(aaZone).append("\"");
        buff.append(" url=\"").append(url).append("\"");
        buff.append(" initLoad=\"").append(initLoad).append("\"");
        if(StringUtils.isNotBlank(style)){
            buff.append(" style=\"").append(style).append("\"");
        }
        buff.append(">");//start tag
        
        //翻页显示按钮
        buff.append("<div style=\"width:100%; text-align:right; padding-right:30px;\">");
        buff.append("    <button class=\"button button-rounded button-action button-tiny\" onclick=\"cp_prev('").append(id).append("', '").append(prePageRealId).append("')\" ><i class=\"fa fa-chevron-left fa-lg\"></i></button>");
        buff.append("    &nbsp;&nbsp;&nbsp;&nbsp;");
        buff.append("    <button class=\"button button-rounded button-action button-tiny\" onclick=\"cp_next('").append(id).append("', '").append(nextPageRealId).append("')\" ><i class=\"fa fa-chevron-right fa-lg\"></i></button>");
        buff.append("</div>");
        buff.append("<br/>");
        
        
        //显示内容区域
        buff.append("<div class=\"carousel-inner\">");
        buff.append("  <div class=\"item active\">");
//        buff.append("    <img src=\"" + contextPath + "/resource/image/02.jpg\"/>");
        buff.append("    <div class=\"carousel-caption\"></div>");
        buff.append("  </div>");
        buff.append("  <div class=\"item\">");
//        buff.append("    <img src=\"" + contextPath + "/resource/image/02.jpg\"/>");
        buff.append("    <div class=\"carousel-caption\"></div>");
        buff.append("  </div>");
        buff.append("</div>");
        
        
        //翻页显示按钮
        buff.append("<div style=\"width:100%; text-align:right; padding-right:30px;\">");
        buff.append("    <button class=\"button button-rounded button-action button-tiny\" onclick=\"cp_prev('").append(id).append("', '").append(prePageRealId).append("')\" ><i class=\"fa fa-chevron-left fa-lg\"></i></button>");
        buff.append("    &nbsp;&nbsp;&nbsp;&nbsp;");
        buff.append("    <button class=\"button button-rounded button-action button-tiny\" onclick=\"cp_next('").append(id).append("', '").append(nextPageRealId).append("')\" ><i class=\"fa fa-chevron-right fa-lg\"></i></button>");
        buff.append("</div>");
        
//        buff.append("<a id=\"").append(prePageId).append("\" class=\"carousel-control left carouselPage\" href=\"#\"");
//        buff.append("     onclick=\"cp_prev('").append(id).append("', '").append(prePageRealId).append("')\">");
//        buff.append("  <span class=\"glyphicon glyphicon-chevron-left\"></span>");
//        buff.append("</a>");
//        
//        buff.append("<a id=\"").append(nextPageId).append("\" class=\"carousel-control right\" href=\"#\"");
//        buff.append("     onclick=\"cp_next('").append(id).append("', '").append(nextPageRealId).append("')\">");
//        buff.append("  <span class=\"glyphicon glyphicon-chevron-right\"></span>");
//        buff.append("</a>");
        
        //真是翻页操作按钮
        buff.append("<a id=\"").append(prePageRealId).append("\" class=\"carousel-control left\" href=\"#").append(id).append("\" data-slide=\"prev\" style=\"display:none\"></a>");
        buff.append("<a id=\"").append(nextPageRealId).append("\" class=\"carousel-control right\" href=\"#").append(id).append("\" data-slide=\"next\" style=\"display:none\"></a>");
        
        buff.append("</div>"); //end tag
    
        try {
            pageContext.getOut().print(buff.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.doStartTag();
    }

    
//
//    @Override
//    public int doAfterBody() throws JspException {
//        String contextPath = pageContext.getServletContext().getContextPath();
////        Integer pageSize = getInteger(request, "pageSize", getPageSize());
////        Integer pageNum = getInteger(request, "pageNum", 1);
////        Integer totalCount = getInteger(request, "totalCount", 1);
//        
//        StringBuilder buff = new StringBuilder();
//        buff.append("<div id=\"").append(id).append("\" class=\"carousel slide carouselPage\" ");
//        buff.append(" pageDataProvider=\"").append(pageDataProvider).append("\"");
//        buff.append(" pageInfoProvider=\"").append(pageInfoProvider).append("\"");
//        buff.append(" aaZone=\"").append(aaZone).append("\"");
//        buff.append(" url=\"").append(url).append("\"");
//        if(StringUtils.isNotBlank(style)){
//            buff.append(" style=\"").append(style).append("\"");
//        }
//        buff.append(">");//start tag
//        
//        buff.append("<div class=\"carousel-inner\">");
//        buff.append("  <div class=\"item active\">");
//        buff.append("    <img src=\"" + contextPath + "resource/image/02.jpg\"/>");
//        buff.append("    <div class=\"carousel-caption\"></div>");
//        buff.append("  </div>");
//        buff.append("  <div class=\"item\">");
//        buff.append("    <img src=\"" + contextPath + "resource/image/02.jpg\"/>");
//        buff.append("    <div class=\"carousel-caption\"></div>");
//        buff.append("  </div>");
//        buff.append("</div>");
//        
//        String prePageId = "prePage_" + id;
//        String nextPageId = "nextPage_" + id;
//        String prePageRealId = "prePageReal_" + id;
//        String nextPageRealId = "nextPageReal_" + id;
//        
//        buff.append("<a id=\"").append(prePageId).append("\" class=\"carousel-control left carouselPage\" href=\"#\"");
//        buff.append("     onclick=\"cp_prev('").append(id).append("', '").append(prePageRealId).append("')\">");
//        buff.append("  <span class=\"glyphicon glyphicon-chevron-left\"></span>");
//        buff.append("</a>");
//        
//        buff.append("<a id=\"").append(nextPageId).append("\" class=\"carousel-control right\" href=\"#\"");
//        buff.append("     onclick=\"cp_next('").append(id).append("', '").append(nextPageRealId).append("')\">");
//        buff.append("  <span class=\"glyphicon glyphicon-chevron-right\"></span>");
//        buff.append("</a>");
//        
//        buff.append("<a id=\"").append(prePageRealId).append("\" class=\"carousel-control left\" href=\"#").append(id).append("\" data-slide=\"prev\" style=\"display:none\"></a>");
//        buff.append("<a id=\"").append(nextPageRealId).append("\" class=\"carousel-control right\" href=\"#").append(id).append("\" data-slide=\"next\" style=\"display:none\"></a>");
//        
//        
////        BodyContent bodyContent = getBodyContent();
////        String body = bodyContent.getString();
////        if(StringUtils.isNotBlank(body)){
////            buff.append(body);
////        }
//        
//        buff.append("</div>"); //end tag
//
//        JspWriter out = bodyContent.getEnclosingWriter();
//        try {
//            out.print(buff.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return SKIP_BODY;
//    }

    /* 
      <div id="newsCarousel" class="carousel slide" style="width:870px">
            <!-- Wrapper for slides -->
            <div class="carousel-inner">
                <div class="item active">
                    <img src="${RESOURCE}/image/02.jpg"/>
                    <div class="carousel-caption"></div>
                </div>
                <div class="item">
                    <img src="${RESOURCE}/image/02.jpg"/>
                    <div class="carousel-caption"></div>
                </div>
            </div>
            <!-- Carousel nav -->
            <a id="prePage" class="carousel-control left" href="#" onclick="prev('prePageReal')"><span class="glyphicon glyphicon-chevron-left"></span></a>
            <a id="nextPage" class="carousel-control right" href="#" onclick="next('nextPageReal')"><span class="glyphicon glyphicon-chevron-right"></span></a>
            <a id="prePageReal" class="carousel-control left" href="#newsCarousel" data-slide="prev" style="display:none"></a>
            <a id="nextPageReal" class="carousel-control right" href="#newsCarousel" data-slide="next" style="display:none"></a>
        </div>
     */
    
//    private String getString(ServletRequest request, String name){
//        return (String) request.getAttribute(name);
//    }
//    
//    private Integer getInteger(ServletRequest request, String name){
//        return getInteger(request, name, null);
//    }
    
//    private Integer getInteger(ServletRequest request, String name, Integer defaultValue){
//        Integer value = (Integer) request.getAttribute(name);
//        return value == null ? defaultValue : value;
//    }
    
//    @Override
//    public int doStartTag() throws JspException {
//        return EVAL_BODY_BUFFERED;  
//    }


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

    public String getAaZone() {
        return aaZone;
    }

    public void setAaZone(String aaZone) {
        this.aaZone = aaZone;
    }

    public String getPageDataProvider() {
        return pageDataProvider;
    }

    public void setPageDataProvider(String pageDataProvider) {
        this.pageDataProvider = pageDataProvider;
    }

    public String getPageInfoProvider() {
        return pageInfoProvider;
    }

    public void setPageInfoProvider(String pageInfoProvider) {
        this.pageInfoProvider = pageInfoProvider;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getInitLoad() {
        return initLoad;
    }


    public void setInitLoad(String initLoad) {
        this.initLoad = initLoad;
    }

}
