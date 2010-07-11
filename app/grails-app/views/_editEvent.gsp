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
  Template for editing Events.

  User: Leanne Northrop
  Date: Feb 20, 2010, 3:49:35 PM
--%>

<%@ page import="org.joda.time.TimeOfDay;org.samye.dzong.london.media.Image;org.samye.dzong.london.venue.Venue;org.samye.dzong.london.community.Teacher;org.samye.dzong.london.ShiroUser" contentType="text/html;charset=UTF-8" %>

<div id="jserrors" class="ui-widget ui-state-error ui-corner-all" style="display:none;">
    <strong>
      <span class="ui-icon ui-icon-alert" style="display: inline-block"></span><g:message code="alert"></g:message>
    </strong>
    <ol>
      <li><label for="title" class="error">Please enter a title of at least 5 characters.</label></li>
      <li><label for="summary" class="error">Please add a summary of at least 5 characters.</label></li>
      <li><label for="category" class="error">Please select a category.</label></li>
      <li><label for="leader.id" class="error">Please select a leader.</label></li>
      <li><label for="organizer.id" class="error">Please select an Organizer.</label></li>
    </ol>
</div>

<g:render template="/editEventDetails" model="[event: event, rule:rule]"/>

<g:render template="/editEventDates" model="[event: event, rule:rule]"/>

<g:render template="/editEventPrices" model="[event: event, rule:rule]"/>

<fieldset>
  <legend>Content</legend>
  <g:render template="/contentWithPreview" model="[previewController: 'manageSite',publishableInstance:event]"/>
</fieldset>

<fieldset>
    <g:if test="${showPublication}">
    <legend>Publication Details</legend>
    <g:render template="/publishDetails" model="[articleInstance:event]"/>
    </g:if>
</fieldset>

