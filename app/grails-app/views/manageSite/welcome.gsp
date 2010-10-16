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
  Template for content administration welcome page.
  User: Leanne Northrop
  Date: Sep 26, 2010, 16:07:21
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <meta name="layout" content="content-admin" />
        <title><g:message code="manage.home.title"/></title>
    </head>
    <body>
        <h1>Welcome!</h1>
        <p>
            This area of the web-site allows Kagyu Samye Dzong London web-group volunteers to create and manage information displayed on the site. If you would like to help please register for an account using the form below.
        </p>
        <g:form id="register" name="register" controller="auth" action="register">
            <fieldset>
            <legend><g:message code="register.title"/></legend>
                <p>
                  <label for="username"><g:message code="log.in.un"/></label>
                  <g:textField name="username" value="${username}" class="required ui-corner-all"/>
                </p>
                <p>
                  <label for="password"><g:message code="log.in.pw"/></label>
                  <g:passwordField name="password" value="" class="required ui-corner-all"/>
                </p>
                <p>
                  <label for="passwordAgain"><g:message code="log.in.pw"/></label>
                  <g:passwordField name="passwordAgain" value="" class="required ui-corner-all"/>
                </p>
                <p class="last">&nbsp;</p>
                <g:set var="submitBtnLabel"><g:message code="register.btn"/></g:set>
                <g:submitButton name="regsubmitbtn" value="${submitBtnLabel}" id="regsubmitbtn" class="ui-corner-all"/>
            </fieldset>
        </g:form>        
    </body>
</html>
