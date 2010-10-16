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

<%@ page import="com.lucastex.grails.fileuploader.UFile" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <meta name="layout" content="content-admin" />
        <title><g:message code="file.manage.title"/></title>
    </head>
    <body>     
        <form> 
            <fieldset>
                <table>
                  <thead>
                    <tr>
                      <th><g:message code="file.name.label"/></th>
                      <th><g:message code="file.size.label"/></th>              
                      <th><g:message code="file.downloads.label"/></th>              
                      <th><g:message code="file.uploaded.label"/></th>
                      <th><g:message code="file.actions.label"/></th>
                    </tr>
                  </thead>
                  <tbody>
                    <g:each in="${files}" status="i" var="file">
                      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        <td>${file?.name}</td>
                        <td><fileuploader:prettysize size="${file.size}" /></td>
                        <td>${file?.downloads}</td>
                        <td><g:formatDate format="dd-MM-yyyy HH:mm" date="${file?.dateUploaded}"/></td>
                        <td>
                            <g:set var="deleteConfirmLabel"><g:message code="file.delete.confirm"/></g:set> 
                            <g:link action="delete" id="${file?.id}" onclick="${deleteConfirmLabel}"><g:message code="file.delete.label"/></g:link>
                            <g:link controller="download" action="index" id="${file?.id}" params="${[id:file?.id]}"><g:message code="file.download.label"/></g:link>
                        </td>
                      </tr>
                    </g:each>
                  </tbody>
                </table>
            
                <g:actionSubmit value="${message(code:'file.add.submit.btn')}" action="create" class="ui-corner-all"/>
            </fieldset>            
        </form>
    </body>
</html>
