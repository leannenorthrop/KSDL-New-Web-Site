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
  Template for showing flash messages & errors
  User: Leanne Northrop
  Date: Feb 18, 2010, 1:13:20 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<g:if test="${flash.message && !flash.isError}">
  <p class="ui-widget ui-state-highlight ui-corner-all">
    <strong><span class="ui-icon ui-icon-info" style="display: inline-block"></span><g:message code="info"/></strong><g:message code="${flash.message}" args="${flash.args}" default="${flash.default}"/>
  </p>
</g:if>
<g:elseif test="${(flash.message && flash.isError)}">
  <g:set var="errorsList"><g:renderErrors bean="${flash.args[0]}" as="list"></g:renderErrors></g:set>
  <div class="ui-widget ui-state-error ui-corner-all">
    <strong>
      <span class="ui-icon ui-icon-alert" style="display: inline-block"></span><g:message code="alert"></g:message>
    </strong>
    <g:message code="${flash.message}" args="${flash.args}" default="${flash.default}"></g:message>
    ${errorsList}
  </div>
</g:elseif>
