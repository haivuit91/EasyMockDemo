package com.vn.ld.btc.fe.util;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.LocaleProvider;
import com.opensymphony.xwork2.TextProvider;
import com.opensymphony.xwork2.TextProviderFactory;
import com.opensymphony.xwork2.util.ValueStack;
import com.vn.ld.api.dto.PagingInfoDTO;


public class PaggerTagObjectInfo extends TagSupport implements TextProvider, LocaleProvider {

	private static final long serialVersionUID = -8064941151850299099L;
	private final static Logger LOGGER = Logger.getLogger(PaggerTagObjectInfo.class);
	private final transient TextProvider textProvider = new TextProviderFactory().createInstance(getClass(), this);
	
	
	private PagingInfoDTO pagingInfo;
	private String navAction;
	private String fromId;
	private String actionName;
	
	public int doStartTag() throws JspException {
		LOGGER.info("doStartTag show pagging information");
		if(pagingInfo == null) {
			return SKIP_BODY;
		}
		
		StringBuffer navBuff = new StringBuffer();
		
		int pageIndex = pagingInfo.getIndexPage();
		long totalPage = pagingInfo.getTotalPage();
		
		if(totalPage <= 1) {
			return SKIP_BODY;
		}
		
		int beginIndex = (pageIndex - 2) > 0 ? (pageIndex - 2 ) : 1;
		int endIndex = beginIndex + 4 > totalPage ? (int) totalPage : beginIndex + 4;
		
		if(totalPage - pageIndex < 2) {
			if(endIndex - beginIndex < 4) {
				beginIndex = endIndex - 4 > 0 ? endIndex - 4 : 1;
			}
		}
		
		navBuff.append("<ul class=\"pagination\">");
		if(pageIndex > 1) {
			navBuff.append("<li><a href=\"javascript:")
					.append(buildNavAction(1))
					.append("\">First</a></li>");
			
			navBuff.append("<li><a href=\"javascript:")
			.append(buildNavAction(pageIndex - 1))
			.append("\">&laquo;</a></li>");
		}
		
		for(long i = beginIndex; i<= endIndex; i++) {
			if(i != pageIndex) {
				navBuff.append("<li><a href=\"javascript:")
			    .append(buildNavAction(i))
			    .append("\">").append(i).append("</a></li>");
			} else {
				navBuff.append("<li class=\"active\"><a href=\"javascript:")
			    .append(buildNavAction(i))
			    .append("\">").append(i).append("</a></li>");
			}
		}
		
		if(pageIndex < totalPage) {
			navBuff.append("<li><a href=\"javascript:")
			.append(buildNavAction(pageIndex + 1))
			.append("\">&raquo;</a></li>");
			
			navBuff.append("<li><a href=\"javascript:")
			.append(buildNavAction(1))
			.append("\">Last</a></li>");
			
		}
		
		navBuff.append("</ul>");
	
		return makePagNav(navBuff);
	}
	
	
	private int makePagNav(StringBuffer navBuffer) {
		try {
			pageContext.getOut().print(navBuffer.toString());
		} catch (Exception e) {
			LOGGER.error("doStartTag() error "+ e, e);
		}
		return SKIP_BODY;
	}
	
	private String buildNavAction(long pageId) {
		String function = "_goTo";
		return (function + "(" +pageId+" , '" + fromId + "','" + actionName + "')");
				
	}
	
	private String buildNavSelected(String name){
		String func = "_selectOffset"; 
		return (func + "(" + name + ", '" + fromId + "', '" + actionName + "')");
	}
	
	/////////////////////
	public PagingInfoDTO getPagingInfo() {
		return pagingInfo;
	}

	public void setPagingInfo(PagingInfoDTO pagingInfo) {
		this.pagingInfo = pagingInfo;
	}

	public String getNavAction() {
		return navAction;
	}

	public void setNavAction(String navAction) {
		this.navAction = navAction;
	}

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}


	@Override
	public Locale getLocale() {
		ActionContext ctx = ActionContext.getContext();
        if (ctx != null) {
            return ctx.getLocale();
        } else {
            LOGGER.debug("Action context not initialized");
            return null;
        }
	}


	@Override
	public boolean hasKey(String key) {
		return textProvider.hasKey(key);
	}


	@Override
	public String getText(String key) {
		return textProvider.getText(key);
	}


	@Override
	public String getText(String key, String defaultValue) {
		return textProvider.getText(key, defaultValue);
	}


	@Override
	public String getText(String key, String defaultValue, String obj) {
		return textProvider.getText(key, defaultValue, obj);
	}


	@Override
	public String getText(String key, List<?> args) {
		return textProvider.getText(key, args);
	}


	@Override
	public String getText(String key, String[] args) {
		return textProvider.getText(key, args);
	}


	@Override
	public String getText(String key, String defaultValue, List<?> args) {
		return textProvider.getText(key, defaultValue, args);
	}


	@Override
	public String getText(String key, String defaultValue, String[] args) {
		return textProvider.getText(key, defaultValue, args);
	}


	@Override
	public String getText(String key, String defaultValue, List<?> args, ValueStack stack) {
		return textProvider.getText(key, defaultValue, args, stack);
	}


	@Override
	public String getText(String key, String defaultValue, String[] args, ValueStack stack) {
		return textProvider.getText(key, defaultValue, args, stack);
	}


	@Override
	public ResourceBundle getTexts(String bundleName) {
		return textProvider.getTexts(bundleName);
	}


	@Override
	public ResourceBundle getTexts() {
		return textProvider.getTexts();
	}
	
}
