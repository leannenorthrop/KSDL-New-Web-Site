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
<%@ page import="org.samye.dzong.london.events.Event" %>
<g:if test="${params.max}">
  <g:set var="listMaxParam" value="?max=${params.max}&sort=title&order=asc"/>
</g:if>
<g:else>
  <g:set var="listMaxParam" value="?sort=title&order=asc"/>
</g:else>
<html>
  <head>
    <meta name="layout" content="content-admin"/>
    <title><g:message code="manage.events.title"/></title>
    <g:javascript>
      var currentTabIndex = 0;
      var currentTabDiv;
      $(function() {
        $('a.step').live('click', function() {
          $("#events-tabs").tabs('url', currentTabIndex, this.href);
          $(currentTabDiv).load(this.href);
          return false;
        });
        $('a.nextLink').live('click', function() {
          $("#events-tabs").tabs('url', currentTabIndex, this.href);
          $(currentTabDiv).load(this.href);
          return false;
        });
        $('a.prevLink').live('click', function() {
          $("#events-tabs").tabs('url', currentTabIndex, this.href);
          $(currentTabDiv).load(this.href);
          return false;
        });
        $('th.sortable a').live('click', function() {
          $("#events-tabs").tabs('url', currentTabIndex, this.href);
          $(currentTabDiv).load(this.href);
          return false;
        });
        $("#events-tabs").tabs({
          fx: { opacity: 'toggle' },
          select: function(event, ui) {
            currentTabDiv = $(ui.panel);
            currentTabIndex = $("#events-tabs").tabs('option', 'selected');
          },
          load: function(event, ui) {
            currentTabDiv = $(ui.panel);
            currentTabIndex = $("#events-tabs").tabs('option', 'selected');
          }
        });
      });
    </g:javascript>
  </head>
  <body>

    <h1 class="ui-widget-header"><g:message code="manage.events.title"/></h1>
    <g:if test="${flash.message && !flash.isError}">
      <p class="ui-widget ui-state-highlight ui-corner-all">
        <strong><span class="ui-icon ui-icon-info" style="display: inline-block"></span><g:message code="info"/></strong><g:message code="${flash.message}" args="${flash.args}" default="${flash.default}"/>
      </p>
    </g:if>
    <g:elseif test="${flash.message && flash.isError}">
      <g:set var="errorsList"><g:renderErrors bean="${articleInstance}" as="list"></g:renderErrors></g:set>
      <div class="ui-widget ui-state-error ui-corner-all">
        <strong>
          <span class="ui-icon ui-icon-alert" style="display: inline-block"></span><g:message code="alert"></g:message>
        </strong>
        <g:message code="${flash.message}" args="${flash.args}" default="${flash.default}"></g:message>
        ${errorsList}
      </div>
    </g:elseif>
    <div id="events-tabs">
      <ul>
        <li><a href="ajaxUnpublishedEvents${listMaxParam}"><g:message code="event.unpublished"/></a></li>
        <li><a href="ajaxPublishedEvents${listMaxParam}"><g:message code="event.published"/></a></li>
        <li><a href="ajaxArchivedEvents${listMaxParam}"><g:message code="event.archived"/></a></li>
        <li><a href="ajaxDeletedEvents${listMaxParam}"><g:message code="event.deleted"/></a></li>
      </ul>
    </div>
  </body>
</html>
