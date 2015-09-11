package com.vn.ld.btc.fe.util;

import java.io.IOException;
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
import com.vn.ld.btc.fe.info.WebappUser;
import com.vn.ld.btc.fe.security.UserOnlineUtils;

public class MenuTagObjectInfo extends TagSupport implements TextProvider, LocaleProvider {

	private static final long serialVersionUID = 1472377189160079014L;
	private final static Logger LOGGER = Logger.getLogger(MenuTagObjectInfo.class.getName());
	private final transient TextProvider textProvider = new TextProviderFactory().createInstance(getClass(), this);
	
	public int doStartTag() throws JspException {
		LOGGER.info("doStartTag show menu information");
		try {
			pageContext.getOut().print(createMenuString());
		} catch (IOException e) {
			LOGGER.error("Error show menu information"+e, e);
		}
		LOGGER.info("end show menu information");
		return SKIP_BODY;
	}
	
	public String createMenuString() {
		WebappUser webappUser = UserOnlineUtils.getWebappUser();
		StringBuffer menuBar = new StringBuffer();
		menuBar.append("<ul class='nav accordmobile pull-right'>");
		if(webappUser != null) {
			menuBar.append("<input type='hidden' id='isLogin' value='").append("1").append("' />");
			menuBar.append("<li class='dropdown parent'> <a href='#' class='dropdown-toggle' data-toggle='dropdown'>Admin <i class='e-icon-cog'></i></a>")
				   .append("<ul class='dropdown-menu'>").append("<li class='current-user'> <a href='../profile/index'> <img class='avatar' src='#' alt='Avarta'><div class='name'>")
				   .append("<b class='fullname'>").append(webappUser.getUsername()).append("</b><br><small> Edit profile </small></div></a> </li>")
				   .append("<li class='divider'></li><li><a href='../deposit/index'>").append(getText("menu.customer.deposit")).append("</a></li>")
				   .append("<li><a href='../deposit/history'>").append(getText("menu.customer.deposit.history")).append("</a></li>")
				   .append("<li><a href='../withdrawal/index'>").append(getText("menu.customer.withdrawal")).append("</a></li>")
				   .append("<li><a href='../withdrawal/history'>").append(getText("menu.customer.withdrawal.history")).append("</a></li>")
				   .append("<li><a href='#'>").append(getText("menu.customer.transaction")).append("</a></li>")
				   .append("<li class='divider'></li><li><a href='#'>Help</a></li><li class='divider'></li><li><a href='#'>Settings</a></li><li> <a class='js-signout-button' href='#' onclick='logout_system();' data-nav='logout'>").append(getText("menu.logout"))
				   .append("</a></li></ul></li>");
		} else {
			menuBar.append("<input type='hidden' id='isLogin' value='").append("0").append("' />");
			menuBar.append("<li><a href='../account/register'>").append(getText("button.register")).append("<i class='e-icon-pencil'></i></a></li>")
				   .append("<li class='dropdown parent'> <a class='dropdown-toggle' href='#' data-toggle='dropdown'>").append(getText("button.sign.in")).append("<i class='e-icon-login'></i></a>")
				   .append("<ul class='dropdown-menu signin'><li>")
				   .append("<form method='post' action='/j_spring_security_check").append("name='frm_login' id='jfrm_login'>")
				   .append("<input name='j_username' id='juserEmail' type='email' maxlength='80' placeholder='Email' class='validate[required,custom[email]]'/>")
				   .append("<input name='j_password' id='jpassword' maxlength='18' type='password' placeholder='Password' class='validate[required,minSize[6],custom[onlyLetterNumber]]'/>")
				   .append("<label class='checkbox'><input type='checkbox'>").append(getText("label.customer.remember.me")).append("</label>")
				   .append("<button type='button' class='btn custom-btn btn-primary' id='btn_sign_in' onclick ='checkLogin();'>").append(getText("button.sign.in")).append("</button>")
				   .append("<label id='loginInfo'></label></form></li>")
				   .append("<li class='divider'></li><li class='nav-header'>").append(getText("label.singin.other")).append("</li>")
				   .append("<li><div class='social clearfix'><a class='fc-webicon facebook large round' href='#' title='Facebook'></a>")
				   .append("<a class='fc-webicon twitter large round' href='#' title='Twitter'></a>")
				   .append("<a class='fc-webicon googleplus large round' href='#' title='Google'></a></div></li><li class='divider'></li>")
				   .append("<li><a href='#'>").append(getText("label.forgot.password")).append("</a></li></ul></li>")
				   .append("<li class='dropdown parent'> <a class='dropdown-toggle' href='#' data-toggle='dropdown'>").append(getText("label.connect")).append("<i class='e-icon-comment'></i></a><ul class='dropdown-menu'>")
				   .append("<li class='visible-phone visible-tablet phonenumber'><a href='tel:+").append(getText("system.phone.number")).append("'>").append(getText("system.phone.number")).append("</a></li>")
				   .append("<li class='menu-text visible-desktop phonenumber'>").append(getText("system.phone.number")).append("</li>")
				   .append("<li class='divider'></li>")
				   .append("<li class='nav-header'>").append(getText("label.singup.for.new")).append("</li>")
				   .append("<li>")
				   .append("<form><div class='input-append custom-append'>")
				   .append("<input type='email' placeholder='Email Address' name='emailSignup' />")
				   .append("<button class='btn btn-primary'>").append(getText("button.sign.up")).append("</button></div></form></li>")
				   .append("<li class='nav-header'>").append(getText("label.follow.us")).append("</li>")
				   .append("<li><div class='social clearfix'><a class='fc-webicon facebook large round' href='#' title='Facebook'></a>")
				   .append("<a class='fc-webicon twitter large round' href='#' title='Twitter'></a>")
				   .append("<a class='fc-webicon googleplus large round' href='#' title='Google'></a></div></li></ul></li>");
		}
		
		menuBar.append("<li class='divider-vertical'></li>").append("<li class='search-wrapper'><form  method='post'>")
			   .append("<div id='search-trigger'><i class='e-icon-search'></i></div><input placeholder='search + enter' type='text'></form></li></ul>");
		return menuBar.toString();
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
