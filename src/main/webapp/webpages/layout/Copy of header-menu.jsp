<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags"%>

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
					<div class="nav-collapse collapse">
					<ul class="nav accordmobile pull-right">
						<li><a href="../account/register">Register <i class="e-icon-pencil"></i></a></li>
						<!--begin .dropdown .parent -->
						<li class="dropdown parent"> <a class="dropdown-toggle" href="#" data-toggle="dropdown">Sign In <i class="e-icon-login"></i></a>
							<ul class="dropdown-menu signin"><!--begin .dropdown-menu .signin -->
							
									<li><!--begin list item / everything goes inside the list item-->
											<form method="post" action='<s:url value="/j_spring_security_check" />' name="frm_login" id="frm_login"><!--begin form add your stuff -->
													<input name="j_username" id="userEmail" type="email" maxlength="80" placeholder="Email">
													<input name="j_password" id="password" maxlength="12" type="password" placeholder="Password">
													<label class="checkbox"><input type="checkbox">Remember me </label>
													<button type="button" class="btn custom-btn btn-primary" id="btn_sign_in" onclick ="checkLogin();">Sign in</button>
											</form>
									</li><!--end list item-->
									
									<li class="divider"></li><!--divider -->
									
									<li class="nav-header">Or Sign In with...</li><!--nav-header -->
									
									<li>
										<div class="social clearfix">
											<a class="fc-webicon facebook large round" href="#" title="Facebook"></a>
											<a class="fc-webicon twitter large round" href="#" title="Twitter"></a>
											<a class="fc-webicon googleplus large round" href="#" title="Google"></a>
										</div>
									</li>
									<li class="divider"></li><!--divider -->

									<li><a href="#">Forgot Password</a></li>
									
							</ul><!--close .dropdown-menu .signin -->
						</li><!--close .dropdown .parent -->
						
						<!--begin .dropdown .parent -->
						<li class="dropdown parent"> <a class="dropdown-toggle" href="#" data-toggle="dropdown">Connect <i class="e-icon-comment"></i></a>
							<ul class="dropdown-menu"> <!--begin .dropdown-menu -->
							
								<li class="visible-phone visible-tablet phonenumber"><a href="tel:+8135553333">813.555.3333</a></li>
								<!-- show this number only on phones and tablets -->
								
								<li class="menu-text visible-desktop phonenumber">813.555.3333</li>
								<!-- show this number only on desktop -->

								<li><a href="mailto:contact@longdomaingoeshere.com">email: contact@longdomaingoeshere.com</a></li>
								
								<li class="divider"></li>
								
								<li class="nav-header">Sign Up For News!</li>
								
								<li>
									<form>
										<div class="input-append custom-append">
												<input type="email" placeholder="Email Address" name="email" />
												<button class="btn btn-primary">Sign Up</button>
										</div>
									</form>
								</li>
										
								<li class="divider"></li><!--divider -->
								
								<li class="nav-header">Follow Us!</li><!--nav-header -->
								
								<li>
									<div class="social clearfix">
										<a class="fc-webicon rss round" href="#" title="RSS"></a>
										<a class="fc-webicon facebook round" href="#" title="Facebook"></a>
										<a class="fc-webicon twitter  round" href="#" title="Twitter"></a>
										<a class="fc-webicon skype round" href="#" title="Skype"></a>
										<a class="fc-webicon dribbble round" href="#" title="dribble"></a>
										<a class="fc-webicon linkedin round" href="#" title="Skype"></a>
										<a class="fc-webicon pinterest round" href="#" title="pinterest"></a>
									</div><!-- close div .social-->
								</li>								
							</ul>
						</li><!--close .dropdown .parent -->
						
						<!--begin .dropdown .parent -->
						<li class="dropdown parent"> <a href="#" class="dropdown-toggle" data-toggle="dropdown">Admin <i class="e-icon-cog"></i></a>
								<ul class="dropdown-menu">
										<li class="current-user"> <a href="#"> <img class="avatar" src="#" alt="Christina Arasmo">
												<div class="name">
														<b class="fullname">User Name Goes Here</b><br>
														<small> Edit profile </small>
												</div>
												</a> </li>
										<li class="divider"></li>
										<li><a href="#">Link Here</a></li>
										<li><a href="#">Line Here</a></li>
										<li class="divider"></li>
										<li><a href="#">Help</a></li>
										<li class="divider"></li>
										<li><a href="#">Settings</a></li>
										<li> <a class="js-signout-button" href="#" data-nav="logout">Sign out</a>
												<form class="dropdown-link-form signout-form" id="signout-form" action="/logout" method="POST">
														<input type="hidden" value="" name="" class="">
														<input type="hidden" name="" class="">
														<input type="hidden" name="">
												</form></li>
								</ul><!--close .dropdown-menu -->
						</li><!--close .dropdown .parent -->
						
						<li class="divider-vertical"></li>
						
						<li class="search-wrapper">
						<form action="someaction.php" method="post">
							<div id="search-trigger">
									<i class="e-icon-search"></i>
							</div>
							<input placeholder="search + enter" type="text">
						</form>
						</li>
						</ul><!-- close nav accordmobile-->
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
	</div>
	</div>
</header>