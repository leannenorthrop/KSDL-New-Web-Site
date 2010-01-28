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
        <title><g:message code="title" default="Kagyu Samye Dzong London"/> <g:layoutTitle /></title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<![if gte IE 7]>
    <link rel="stylesheet" media="screen, projection" href="${resource(dir:'css/site',file:'screen.css')}" />
    <link rel="stylesheet" media="screen, projection" href="${resource(dir:'css/manage',file:'screen.css')}" />
    <lsd:cssTheme app="${application}"/>
<![endif]>
        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <g:javascript library="jquery"/>
        <g:javascript src="jquery/jquery-ui-1.7.2.custom.min.js"/>
        <g:javascript src="jquery/jquery.validate.min.js"/>
        <g:layoutHead />
    </head>
    <body class="contentAdmin" style="min-width:60em">
        <lsdc:header />
        <lsdc:nav current="manageSite"/>
        <lsdc:toolbar controller="${controllerName}" action="${actionName}" id="${id}"/>
        <div class="content">
            <g:layoutBody />
        </div>
        <lsdc:grid />
        <div class="footer">
          <g:set var="year"><g:formatDate format="yyyy" date="${new Date()}"/></g:set>
          <g:message code="footer.copyright" args="${[year]}"/>
        </div>
    </body>
</html>
