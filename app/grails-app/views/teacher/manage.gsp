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
  Template for displaying Teachers.
  User: Leanne Northrop
  Date: Jan 27, 2010, 9:40:23 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.samye.dzong.london.community.Teacher" %>
<g:if test="${params.max}">
  <g:set var="listMaxParam" value="?max=${params.max}&sort=name&order=asc"/>
</g:if>
<g:else>
  <g:set var="listMaxParam" value="?sort=name&order=asc"/>
</g:else>
<html>
  <head>
    <meta name="layout" content="content-admin"/>
    <title><g:message code="manage.teachers.title" default="Manage Teachers"/></title>
    <g:javascript>
      var currentTabIndex = 0;
      var currentTabDiv;
      $(function() {
        $('a.step').live('click', function() {
          $("#teacher-tabs").tabs('url', currentTabIndex, this.href);
          $(currentTabDiv).load(this.href);
          return false;
        });
        $('a.nextLink').live('click', function() {
          $("#teacher-tabs").tabs('url', currentTabIndex, this.href);
          $(currentTabDiv).load(this.href);
          return false;
        });
        $('a.prevLink').live('click', function() {
          $("#teacher-tabs").tabs('url', currentTabIndex, this.href);
          $(currentTabDiv).load(this.href);
          return false;
        });
        $('th.sortable a').live('click', function() {
          $("#teacher-tabs").tabs('url', currentTabIndex, this.href);
          $(currentTabDiv).load(this.href);
          return false;
        });
        $("#teacher-tabs").tabs({
          fx: { opacity: 'toggle' },
          select: function(event, ui) {
            currentTabDiv = $(ui.panel);
            currentTabIndex = $("#teacher-tabs").tabs('option', 'selected');
          },
          load: function(event, ui) {
            currentTabDiv = $(ui.panel);
            currentTabIndex = $("#teacher-tabs").tabs('option', 'selected');
          }
        });
      });
    </g:javascript>
  </head>
    <body>
        <form>
            <fieldset>
                <div id="teacher-tabs">
                    <ul>
                        <li><a href="ajaxUnpublishedTeachers${listMaxParam}"><g:message code="teacher.unpublished"/></a></li>
                        <li><a href="ajaxPublishedTeachers${listMaxParam}"><g:message code="teacher.published"/></a></li>
                        <li><a href="ajaxDeletedTeachers${listMaxParam}"><g:message code="teacher.deleted"/></a></li>
                    </ul>
                </div>
                <shiro:hasAnyRole in="${['Author','Admin','EventOrganiser']}">
                    <p class="last">&nbsp;</p>
                    <g:actionSubmit value="${message(code:'add.article.btn')}" action="create" class="ui-corner-all"/>
                </shiro:hasAnyRole>    
            </fieldset>
        </form>
    </body>
</html>

