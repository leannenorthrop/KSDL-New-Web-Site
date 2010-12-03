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
Created by IntelliJ IDEA.
    User: northrl
Date: Jan 24, 2010
Time: 2:02:18 PM
To change this template use File | Settings | File Templates.
    --%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <title>Oops! There was a problem with the server.</title>
    <meta name="layout" content="main">
  </head>
  <body>
    <div class="grid_16 pagecontent">
      <h1>Grails Runtime Exception</h1>
      <h3>Error Details</h3>

      <div class="message">
        <strong>Error ${request.'javax.servlet.error.status_code'}:</strong> ${request.'javax.servlet.error.message'}<br/>
        <strong>Servlet:</strong> ${request.'javax.servlet.error.servlet_name'}<br/>
        <strong>URI:</strong> ${request.'javax.servlet.error.request_uri'}<br/>
        <g:if test="${exception}">
          <strong>Exception Message:</strong> ${exception.message?.encodeAsHTML()} <br />
          <strong>Caused by:</strong> ${exception.cause?.message?.encodeAsHTML()} <br />
          <strong>Class:</strong> ${exception.className} <br />
          <strong>At Line:</strong> [${exception.lineNumber}] <br />
          <strong>Code Snippet:</strong><br />
          <div class="snippet">
            <g:each var="cs" in="${exception.codeSnippet}">
${cs?.encodeAsHTML()}<br />
            </g:each>
          </div>
        </g:if>
      </div>
      <g:if test="${exception}">
        <h3>Stack Trace</h3>
        <div class="stack">
          <pre><g:each in="${exception.stackTraceLines}">${it.encodeAsHTML()}<br/></g:each></pre>
        </div>
      </g:if>
    </div>
    <div class="clear"></div>
  </body>
</html>
