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
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta name="layout" content="content-admin"/>
    <title><g:message code="log.in" default="Sign In"/></title>
  </head>
  <body>
    <g:if test="${flash.message}">
      <p class="ui-widget ui-state-error ui-corner-all">
        <strong><span class="ui-icon ui-icon-alert" style="display: inline-block"></span><g:message code="alert"/></strong><g:message code="${flash.message}" args="${flash.args}" default="${flash.default}"/></p>
    </g:if>
    <table class="entryForms">
      <tr>
        <td class="ui-widget ui-corner-all signIn">
          <g:form id="signIn" name="signIn" action="signIn">
            <g:hiddenField name="targetUri" value="${targetUri}"/>
            <fieldset>
                <legend><g:message code="log.in" default="Sign In"/></legend>
                <p>
                  <label for="username"><g:message code="log.in.un"/></label>
                  <g:textField class="required ui-corner-all" name="username" value="${username}"/>
                </p>
                <p class="last">
                  <label for="password"><g:message code="log.in.pw"/></label>
                  <g:passwordField class="required ui-corner-all" name="password" value="${password}"/><br/>
                  <g:link action="resetPassword"><g:message code="passwd.reset.msg"/></g:link>
                </p>
                <!--fieldset class="last">
             <label class="rememberMe" for="rememberMe">Remember me?</label> <g:checkBox class="rememberMe" id="rememberMe" name="rememberMe" value="${rememberMe}"/>
                </fieldset-->                
                <g:set var="submitBtnLabel"><g:message code="log.in.btn"/></g:set>
                <g:submitButton name="submitbtn" value="${submitBtnLabel}" id="submitbtn" class="ui-corner-all"/>                
            </fieldset>
          </g:form>
        </td>
        <td class="middle">
          <g:message code="log.in.or"/>
        </td>
        <td class="ui-widget ui-corner-all register">
          <g:form id="register" name="register" action="register">
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
                    <p class="last">
                      <label for="passwordAgain"><g:message code="log.in.pw"/></label>
                      <g:passwordField name="passwordAgain" value="" class="required ui-corner-all"/>
                    </p>
                    <g:set var="submitBtnLabel"><g:message code="register.btn"/></g:set>
                    <g:submitButton name="regsubmitbtn" value="${submitBtnLabel}" id="regsubmitbtn" class="ui-corner-all"/>
                </fieldset>
          </g:form>
        </td>
      </tr>
    </table>
  </body>
</html>
