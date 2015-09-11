package com.vn.ld.btc.fe.business.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.LocaleProvider;
import com.opensymphony.xwork2.TextProvider;
import com.opensymphony.xwork2.TextProviderFactory;
import com.opensymphony.xwork2.util.ValueStack;

public class SystemDataImpl implements TextProvider, LocaleProvider{

	private final transient TextProvider textProvider = new TextProviderFactory().createInstance(getClass(), this);
	
	private static SystemDataImpl instance;
	public SystemDataImpl() {}
	
	
	public static SystemDataImpl getInstance() {
		if(instance == null) {
			instance = new SystemDataImpl();
		}
		return instance;
	}

	/**
	 * Get map disable or not
	 * @return
	 */
	public Map<Integer,String> getmapDisableOrNot(){
		Map<Integer, String> mapDisableOrNot = new HashMap<Integer, String>();
		mapDisableOrNot.put(1, getText(""));
		mapDisableOrNot.put(0, getText("label.disable"));
		return mapDisableOrNot;
	}

	@Override
	public Locale getLocale() {
	  ActionContext ctx = ActionContext.getContext();
        if (ctx != null) {
            return ctx.getLocale();
        } else {
            return null;
        }
	}

	@Override
	public String getText(String arg0, List<?> arg1) {
		return textProvider.getText(arg0, arg1);
	}

	@Override
	public String getText(String arg0, String arg1, List<?> arg2,ValueStack arg3) {
		return textProvider.getText(arg0,arg1,arg2,arg3);
	}

	@Override
	public String getText(String arg0, String arg1, List<?> arg2) {
		return textProvider.getText(arg0, arg1, arg2);
	}

	@Override
	public String getText(String arg0, String arg1, String arg2) {
		return textProvider.getText(arg0,arg1,arg2 );
	}

	@Override
	public String getText(String arg0, String arg1, String[] arg2,ValueStack arg3) {
		return textProvider.getText(arg0,arg1,arg2,arg3);
	}

	@Override
	public String getText(String arg0, String arg1, String[] arg2) {
		return textProvider.getText(arg0,arg1,arg2);
	}

	@Override
	public String getText(String arg0, String arg1) {
		return textProvider.getText(arg0,arg1);
	}

	@Override
	public String getText(String arg0, String[] arg1) {
		return textProvider.getText(arg0, arg1);
	}

	@Override
	public String getText(String arg0) {
		return textProvider.getText(arg0);
	}

	@Override
	public ResourceBundle getTexts() {
		return textProvider.getTexts();
	}

	@Override
	public ResourceBundle getTexts(String resourceBundle) {
		return textProvider.getTexts(resourceBundle);
	}

	@Override
	public boolean hasKey(String key) {
		return textProvider.hasKey(key);
	}

}
