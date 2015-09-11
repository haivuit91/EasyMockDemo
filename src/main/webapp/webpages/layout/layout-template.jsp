<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!doctype html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en" dir="ltr"> <![endif]-->
<!--[if IE 7]> <html class="no-js lt-ie9 lt-ie8" lang="en" dir="ltr"> <![endif]-->
<!--[if IE 8]> <html class="no-js lt-ie9" lang="en" dir="ltr"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js" dir="ltr" lang="en"><!--<![endif]-->
<head>
	<title><s:text name="system.title" /></title>
	
	<meta charset="UTF-8"/>
	<meta content="Bitcoin market" name="description"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta content="yes" name="apple-mobile-web-app-capable"/>
		
	<!-- CSS ================ -->
	<link href="<s:url value='../assets/css/bootstrap.min.css' />" rel="stylesheet" type="text/css"/>
	<link href="<s:url value='../assets/css/theme.reset.min.css' />" rel="stylesheet" type="text/css"/>
	<link href="<s:url value='../assets/css/style.css' />" rel="stylesheet" type="text/css"/>

	<!-- Style Switcher DEMO only ================ -->
	<link href="<s:url value='../assets/css/switcher.css' />" rel="stylesheet" media="screen" type="text/css"/>
	
	<link rel="stylesheet" type="text/css" media="print" href="<s:url value='../assets/css/print.css' />" />
	<link href="<s:url value='../assets/component/validate_engine/validationEngine.jquery.css' />"  rel="stylesheet" type="text/css" media="screen" />
	<link href="<s:url value='../assets/component/validate_engine/validationEngineIE.jquery.css' />" rel="stylesheet" type="text/css" media="screen" />
	<link href="<s:url value='../assets/custom/main_style.css' />" rel="stylesheet" type="text/css" media="screen" />
	<!-- Icons ================ put your icons and favicons below -->
	<!--http://mathiasbynens.be/notes/touch-icons-->
	<link rel="icon" href="../images/favicon.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="../images/favicon.ico" type="image/x-icon" />
	
	<!-- Open Graph for facebook http://graph.facebook.com/[UserName] replace [UserName] with yours and get your fb:admis content information where the XXXX goes.
	================================================== -->

	<meta content="" property="og:title"/>
	<meta content="" property="og:type"/>
	<meta content="" property="og:url"/>
	<meta content="" property="og:image"/>
	<meta content="" property="og:site_name"/>
	<meta content="XXXXX" property="fb:admins"/>
	<meta content="en_us" property="og:locale"/>
	<meta content=" " property="og:description"/>
	
	<!-- JS JQuery in the head you can use ajax if you want ================ -->
	<script src="<s:url value='../assets/js/jquery-1.9.1.min.js'/>" type="text/javascript"></script>
	<script src="<s:url value='../assets/js/jquery.min.map.js'/>" type="text/javascript"></script>
	<!--[if gt IE 8]><!-->
		<%-- <script src="<s:url value='../assets/js/sequence-greater-than-ie-8.jquery-min.js'/>" type="text/javascript"></script> --%>
	<!--<![endif]-->
	<!--[if lte IE 8]><script src="<s:url value='../assets/js/sequence-less-than-ie-8.jquery-min.js'/>"></script><!--<![endif]-->
	
	<!-- Add to HEAD after style sheet http://modernizr.com/docs/#installing  ================ -->
	<script src="<s:url value='../assets/js/modernizr.custom.js?v=2.6.2'/>" type="text/javascript"></script>
	<!-- Style Switcher  ================ -->
	<script src="<s:url value='../assets/js/switcheroo.js'/>" type="text/javascript"></script>
	
</head>
<body>
	
		<!-- HEADER -->
		<tiles:insertAttribute name="header" />
		<!-- MENU -->
		<tiles:insertAttribute name="head-menu" />
		<div id="page" class="clearfix">
			<div class="container main-content">
			<!-- MAIN BODY -->
				<tiles:insertAttribute name="body" />
			 </div>
			 
			<tiles:insertAttribute name="footer" />
		</div>
	<!-- JS General Site COMBINE AND COMPRESS WHEN YOU FIGURE OUT WHAT YOU WANT TO USE comes with unpacked versions ================ --> 
	<script src="<s:url value='../assets/js/theme-menu.js' />" type="text/javascript"></script><!-- menu --> 
	<script src="<s:url value='../assets/js/jquery.easing-1.3.min.js' />" type="text/javascript"></script><!-- easing --> 
	<script src="<s:url value='../assets/js/bootstrap.min.js' />" type="text/javascript"></script><!-- bootstrap / custom.js loads --> 
	<script src="<s:url value='../assets/js/jquery.easytabs.min.js' />" type="text/javascript"></script><!-- tabs/testimonials custom.js / loads --> 
	<script src="<s:url value='../assets/js/slide-to-top-accordion.min.js' />" type="text/javascript"></script><!-- slide to top accordion toggle / custom.js loads --> 
	<script src="<s:url value='../assets/js/bootstrap-progressbar.min.js' />" type="text/javascript"></script><!-- progress bar loading in page --> 

	<!-- Fancy Box and Isotope ================ --> 
	<script src="<s:url value='../assets/js/jquery.isotope.min.js' />" type="text/javascript"></script><!--filter masonry script AND loading--> 
	<script src="<s:url value='../assets/js/fancybox/source/custom-fancybox-combined.js' />" defer type="text/javascript"></script><!--all fancy box buttons, media, helpers, thumbs AND loading--> 

	<!-- Sliders ================ --> 
	<script src="<s:url value='../assets/js/jquery.flexslider.min.js' />" type="text/javascript"></script><!--flexslider (twitter, blog, portfolio, full width) AND loading --> 
	<script src="<s:url value='../assets/js/lemmon-slider.min.js' />" type="text/javascript"></script><!-- variable width image slider AND loading --> 
	
	<script src="<s:url value='../assets/component/validate_engine/jquery.validationEngine-en.js' />" type="text/javascript" ></script>
	<script src="<s:url value='../assets/component/validate_engine/jquery.validationEngine.js' />" type="text/javascript" ></script>
	<script src="<s:url value='../assets/component/jquery.confirm.min.js' />" type="text/javascript" ></script>
	
	<!-- HighStock -->
	<script src="<s:url value='../assets/js/highstock/highstock.js' />" type="text/javascript" ></script>
	<script src="<s:url value='../assets/js/highstock/data.js' />" type="text/javascript" ></script>
	<script src="<s:url value='../assets/js/highstock/exporting.js' />" type="text/javascript" ></script>
	
	<!-- Jsviewer -->
	<script src="<s:url value='../assets/component/jsviewer/jsrender.min.js' />" type="text/javascript" ></script>
	<script src="<s:url value='../assets/component/jsviewer/jquery.observable.min.js' />" type="text/javascript" ></script>
	<script src="<s:url value='../assets/component/jsviewer/jsviews.min.js' />" type="text/javascript" ></script>
	
	<!--initialize scripts / custom scripts--> 
	<script src="<s:url value='../assets/js/custom.js' />" type="text/javascript"></script>
	
	<!-- Custom by me -->
	<script src="<s:url value='../assets/custom/mainjs.js' />" type="text/javascript"></script>
</body>
</html>