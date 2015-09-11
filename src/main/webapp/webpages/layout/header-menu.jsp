<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="tagCustom" uri="/WEB-INF/taglib/customtag.tld" %>


<!--[if lt IE 7]>
<p class="chromeframe">Your browser is over 12 years old. You are using an <strong>outdated</strong> browser. Please <a href="http://www.google.com/chrome/intl/en/landing_chrome.html">upgrade your browser</a>.</p>
<![endif]--> 

<!-- begin accesibility skip to nav skip content -->
<ul class="visuallyhidden" id="top">
	<li><a href="#nav" title="Skip to navigation" accesskey="n">Skip to navigation</a></li>
	<li><a href="#page" title="Skip to content" accesskey="c">Skip to content</a></li>
</ul>
<!-- begin .header-->
<header class="header clearfix">
	<!-- mobile navigation trigger-->
	<h5 class="mobile_nav"><a href="javascript:void(0)">&nbsp;<span></span> </a> </h5>
	<div id="nav">
		<!-- NAV-BAR -->
		<div class="navbar secondary-menu">
			<div class="container">
				<div class="navbar-inner">
					<div id="topPrice">
						
					</div>
					<div class="nav-collapse collapse" id="menuCreate">
						<tagCustom:MenuInfo />
						<s:if test="loginStatus">
							<ul class="nav accordmobile pull-right"><li><a href="#" id="options-2fa">2FA Options</a></li></ul>
						</s:if>
					</div>
				</div>
			</div>
		</div>
	<!-- End here -->
	
	<!-- Menu bar -->
	<div class="container">
		<div class="navbar primary-menu">
			<div class="navbar-inner">
				<div class="nav-collapse collapse">
					<ul class="nav accordmobile">
					<li ><a href="../order/customer-order"><s:text name="menu.trade" /></a></li>
					<li ><a href="#"><s:text name="menu.new" /></a></li>
					<li class="dropdown parent">
					<a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown"><s:text name="menu.learn" /> <i class="e-icon-down-open-mini"></i></a>
					<ul class="dropdown-menu">
							<li><a href="#"><s:text name="menu.learn.what.bitcoin" /></a></li>
							<li><a href="#"><s:text name="menu.learn.selling.bitcoin" /></a></li>
							<li><a href="#"><s:text name="menu.learn.bitcoin.wallets" /> </a></li>
							<li><a href="#"><s:text name="menu.learn.bitcoin.tranditional" /> </a></li>
					</ul></li>
					<li ><a href="#"><s:text name="menu.faqs" /></a></li>
					<li><a href="#"><s:text name="menu.about.us" /></a></li>
					<li ><a href="#"><s:text name="menu.contact" /></a></li>
					</ul>
				</div>
			</div>
		</div>
		<!-- Logo -->
		<div id="logo">
			<img src="<s:url value='../assets/images/bitvalor-logo-green.png' />"  alt="Bitwalor"/>
		</div> 
	</div>
	</div>
</header>