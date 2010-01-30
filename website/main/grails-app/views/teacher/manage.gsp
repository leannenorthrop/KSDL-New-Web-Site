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

    <h1 class="ui-widget-header"><g:message code="manage.teachers.title"/></h1>
    <g:if test="${flash.message && !flash.isError}">
      <p class="ui-widget ui-state-highlight ui-corner-all">
        <strong><span class="ui-icon ui-icon-info" style="display: inline-block"></span><g:message code="info"/></strong><g:message code="${flash.message}" args="${flash.args}" default="${flash.default}"/>
      </p>
    </g:if>
    <g:elseif test="${flash.message && flash.isError}">
      <g:set var="errorsList"><g:renderErrors bean="${teacher}" as="list"></g:renderErrors></g:set>
      <div class="ui-widget ui-state-error ui-corner-all">
        <strong>
          <span class="ui-icon ui-icon-alert" style="display: inline-block"></span><g:message code="alert"></g:message>
        </strong>
        <g:message code="${flash.message}" args="${flash.args}" default="${flash.default}"></g:message>
        ${errorsList}
      </div>
    </g:elseif>
    <div id="teacher-tabs">
      <ul>
        <li><a href="ajaxUnpublishedTeachers${listMaxParam}"><g:message code="teacher.unpublished"/></a></li>
        <li><a href="ajaxPublishedTeachers${listMaxParam}"><g:message code="teacher.published"/></a></li>
        <li><a href="ajaxDeletedTeachers${listMaxParam}"><g:message code="teacher.deleted"/></a></li>
      </ul>
    </div>
  </body>
</html>

