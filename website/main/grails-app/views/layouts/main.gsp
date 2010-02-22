<%--
  Template for content administration pages.
  User: Leanne
  Date: Jan 24, 2010, 2:00:21 PM
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<!------------------------------------------------------------------------------
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
  - If not, see http://www.gnu.org/licenses/.
  -
  - BT plc, hereby disclaims all copyright interest in the program
  - “Samye Content Management System” written by Leanne Northrop.
------------------------------------------------------------------------------->
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.samye.dzong.london.media.Image" %>
<html>
  <head>
    <title><g:message code="title" default="Kagyu Samye Dzong London"/>: <g:layoutTitle/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <![if gte IE 7]>
    <link rel="stylesheet" type="text/css" media="screen, projection" href="${resource(dir: 'css/site', file: 'screen.css')}"/>
    <lsd:cssTheme app="${application}"/>
    <![endif]>

    <!--[if IE 6]><link rel="stylesheet" type="text/css" href="${resource(dir: 'css/site', file: 'ie6.css')}" media="screen" /><![endif]-->
    <!--[if IE 7]><link rel="stylesheet" type="text/css" href="${resource(dir: 'css/site', file: 'ie.css')}" media="screen" /><![endif]-->

    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon"/>

    <g:layoutHead/>
  </head>
  <body class="${controllerName}">
    <div class="container_16">
      <div class="grid_16">
        <h1 id="branding">
          <g:set var="logo" value="${Image.findByName('Logo')}"/>
          <g:if test="${logo}">
            <img src="${createLink(controller: 'image', action: 'src', id: logo.id)}" title="${logo.name}" alt="${logo.name}"/>
          </g:if>
          <g:message code="title" default="Kagyu Samye Dzong London"/>
        </h1>
      </div>
      <div class="clear"></div>

      <div class="grid_16">
        <lsdc:nav current="${controllerName}"/>
      </div>
      <div class="clear"></div>

      <div class="grid_16">
        <h2 id="page-heading"><g:layoutTitle/></h2>
      </div>
      <div class="clear"></div>

      <div class="grid pagecontent">
        <g:layoutBody/>
      </div>
      <div class="clear"></div>


      <div class="clear"></div>
      <div class="grid_16" id="site_info">
        <div class="box">
          <g:set var="year"><g:formatDate format="yyyy" date="${new Date()}"/></g:set>
          <g:message code="footer.copyright" args="${[year]}"/> <g:message code="title" default="Kagyu Samye Dzong London"/> |
          <g:link controller="home" action="contactUs"><g:message code="footer.contact.us"/></g:link> |
          <g:link controller="home" action="changeCssTheme"><g:message code="footer.change.theme"/></g:link> |
          <g:link controller="home" action="siteMap"><g:message code="footer.site.map"/></g:link> |
          <g:link controller="home" action="aboutThisSite"><g:message code="footer.about.this.site"/></g:link>
        </div>
      </div>
      <div class="clear"></div>

    </div>
  </body>
</html>
