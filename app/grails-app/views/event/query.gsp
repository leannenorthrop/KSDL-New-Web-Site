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
  Template for sending query email.

  User: Leanne Northrop
  Date: Feb 5, 2010,5:51:27 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.samye.dzong.london.events.Event" %>
<html>
  <head>
    <title>${event?.title}</title>
    <meta name="layout" content="main"/>
  </head>
  <body>
      <div class="grid_2">&nbsp;</div>
      <div class="grid_12">
        <g:form action="send" controller="event" id="${event?.id}">
            <fieldset>
                <legend><g:message code="event.query.title"/></legend>
                <p>
                    <label for="email"><g:message code="event.query.your.email"/></label>
                    <input type="text" name="email"/>
                </p>
                <p>
                    <g:set var="subjectline"><g:message code="event.query.email.subjectline" args="${[event?.title]}"/></g:set>
                    <label><g:message code="event.query.email.subject"/></label>
                    <input type="text" name="subject" readonly="true" value="${subjectline}"/>
                </p>
                <p>
                  <label><g:message code="event.query.email.body"/></label>
                  <g:textArea name="body" rows="12" cols="40"/>
                </p>
                <g:submitButton class="button" name="send" value="Send">&nbsp;</g:submitButton>
            </fieldset>
        </g:form>
      </div>
      <div class="grid_2">&nbsp;</div>    
  </body>
</html>
