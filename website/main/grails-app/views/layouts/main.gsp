%{------------------------------------------------------------------------------
  - Copyright © 2010 Leanne Northrop
  -
  - This file is part of Samye Content Management System.
  -
  - Samye Content Management System is free software: you can redistribute it
  - and/or modify it under the terms of the GNU General Public License as
  - published by the Free Software Foundation, either version 3 of the License,
  - or (at your option) any later version.
  -
  - Samye Content Management System is distributed in the hope that it will be
  - useful,but WITHOUT ANY WARRANTY; without even the implied warranty of
  - MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  - GNU General Public License for more details.
  -
  - You should have received a copy of the GNU General Public License
  - along with Samye Content Management System.
  - If not, see <http://www.gnu.org/licenses/>.
  -
  - BT plc, hereby disclaims all copyright interest in the program
  - “Samye Content Management System” written by Leanne Northrop.
  ----------------------------------------------------------------------------}%

<%--
  Template for content administration pages.
  User: Leanne
  Date: Jan 24, 2010, 2:00:21 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
  <head>
    <title><g:message code="title" default="Kagyu Samye Dzong London"/> <g:layoutTitle/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <!--link rel="stylesheet" media="screen, projection" href="${resource(dir: 'css/themes/pastel', file: 'screen.css')}" /-->
    <![if gte IE 7]>
    <link rel="stylesheet" media="screen, projection" href="${resource(dir: 'css/site', file: 'screen.css')}"/>
    <lsd:cssTheme app="${application}"/>
    <![endif]>
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon"/>
    <g:layoutHead/>
  </head>
    <shiro:isLoggedIn>
      <body class="${controllerName}" style="min-width:85em">
    </shiro:isLoggedIn>
    <shiro:isNotLoggedIn><body class="${controllerName}" style="min-width:70em"></shiro:isNotLoggedIn>
    <lsdc:header/>
    <lsdc:nav current="${controllerName}"/>
    <div id="spinner" class="spinner" style="display:none;">
      <img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/>
    </div>
    <div class="pagecontent group"><g:layoutBody/></div>
    <lsdc:grid/>
    <div class="watermark">
      <span class="one"/>
      <span class="two"/>
      <span class="three"/>
      <span class="four"/>
    </div>
    <div class="services">
      <h1><g:message code="service.header"/></h1>
      <ul>
        <li class="email"><g:link controller="feed" action="index"><g:message code="service.email"/></g:link></li>
        <li class="rss"><g:link controller="feed" action="index"><g:message code="service.rss"/></g:link></li>
        <li class="calendar"><g:link controller="feed" action="index"><g:message code="service.calendar"/></g:link></li>
        <li class="twitter"><a href="http://twitter.com/lsdci"><g:message code="service.twitter"/></a></li>
        <li class="facebook"><g:link controller="feed" action="index"><g:message code="service.facebook"/></g:link></li>
      </ul>
    </div>
    <div class="footer">
      <g:set var="year"><g:formatDate format="yyyy" date="${new Date()}"/></g:set>
      <g:message code="footer.copyright" args="${[year]}"/> |
      <g:link controller="home" action="aboutUs"><g:message code="footer.about.us"/></g:link> |
      <g:link controller="home" action="contactUs"><g:message code="footer.contact.us"/></g:link> |
      <g:link controller="home" action="help"><g:message code="footer.help"/></g:link> |
      <g:link controller="home" action="changeCssTheme"><g:message code="footer.change.theme"/></g:link> |
      <g:link controller="home" action="siteMap"><g:message code="footer.site.map"/></g:link> |
      <g:link controller="home" action="aboutThisSite"><g:message code="footer.about.this.site"/></g:link>
    </div>
  </body>
</html>
