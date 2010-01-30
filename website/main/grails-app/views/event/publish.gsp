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
  Prepare event for publication.
  User: Leanne Northrop
  Date: Jan 30, 2010, 1:30:09 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.joda.time.Minutes; org.joda.time.Hours; org.samye.dzong.london.events.Event;org.joda.time.*" %>
<html>
  <head>
    <meta name="layout" content="content-admin"/>
    <title><g:message code="event.publish.title"/></title>
    <g:javascript>
      $(function() {
        $("#addevent").validate();
        $("#dateTabs").tabs({fx: { opacity: 'toggle' }});
      });
    </g:javascript>
  </head>
  <body>
    <g:form name="addevent" action="publish" method="post">
      <h1 class="ui-widget-header"><g:message code="event.publish.title"/></h1>

      <g:if test="${flash.message && !flash.isError}">
        <p class="ui-widget ui-state-highlight ui-corner-all">
          <strong><span class="ui-icon ui-icon-info" style="display: inline-block"></span><g:message code="info"/></strong><g:message code="${flash.message}" args="${flash.args}" default="${flash.default}"/>
        </p>
      </g:if>
      <g:elseif test="${flash.message && flash.isError}">
        <g:set var="errorsList"><g:renderErrors bean="${event}" as="list"></g:renderErrors></g:set>
        <div class="ui-widget ui-state-error ui-corner-all">
          <strong>
            <span class="ui-icon ui-icon-alert" style="display: inline-block"></span><g:message code="alert"></g:message>
          </strong>
          <g:message code="${flash.message}" args="${flash.args}" default="${flash.default}"></g:message>
          ${errorsList}
        </div>
      </g:elseif>

      <g:hiddenField name="id" value="${event?.id}"/>
      <g:hiddenField name="version" value="${event?.version}"/>
      <g:hiddenField name="publishState" value="Published"/>
      <g:hiddenField name="deleted" value="false"/>
      <g:hiddenField name="displayAuthor" value="false"/>
      <g:hiddenField name="displayDate" value="true"/>
      <g:hiddenField name="isRepeatable" value="false"/>

      <fieldset>
        <label for="title"><g:message code="event.title.label"/></label>
        <g:textField name="title" value="${fieldValue(bean:event,field:'title')}" class="required ui-corner-all ${hasErrors(bean:event,field:'title','errors')}" minlength="5"/>
      </fieldset>
      <fieldset>
        <label for="category"><g:message code="event.category.label"/></label>
        <g:select name="category" from="${event.constraints.category.inList}" value="${event?.category}" valueMessagePrefix="publish.category" class="required ui-corner-all ${hasErrors(bean:event,field:'title','errors')}"/>
      </fieldset>
      <fieldset>
        <label for="startTimeHour"><g:message code="event.starttime.label" default="Start Time"/></label>
        <g:select name="startTimeHour" from="${new TimeOfDay().hourOfDay().getMinimumValue()..new TimeOfDay().hourOfDay().getMaximumValue()}" value="${event?.startTime?.getHourOfDay()}" noSelection="${['null':'Select Hour...']}" class="ui-corner-all ${hasErrors(bean:event,field:'startTime','errors')}"/>&nbsp;:&nbsp;
        <g:select name="startTimeMin" from="${new TimeOfDay().minuteOfHour().getMinimumValue()..new TimeOfDay().minuteOfHour().getMaximumValue()}" value="${event?.startTime?.getMinuteOfHour()}" noSelection="${['null':'Select Minute...']}" class="ui-corner-all ${hasErrors(bean:event,field:'startTime','errors')}"/>
      </fieldset>
      <fieldset>
        <label for="eventDurationHour"><g:message code="event.eventduration.label" default="Event Duration"/></label>
        <g:select name="eventDurationHour" from="${0..24}" value="${event?.eventDuration?.toStandardHours()?.getHours()}" noSelection="${['null':'Select Hour...']}" class="ui-corner-all ${hasErrors(bean:event,field:'eventDuration','errors')}"/><g:message code="event.duration.hours"/>
        <g:select name="eventDurationMin" from="${[0,5,10,15,20,30,40,45,50,55]}" value="${event?.eventDuration?.getMinutes()}" noSelection="${['null':'Select Minute...']}" class="ui-corner-all ${hasErrors(bean:event,field:'eventDuration','errors')}"/><g:message code="event.duration.mins"/>
      </fieldset>
      <fieldset>
        <label for="leader.id"><g:message code="event.leader.label"/></label>
        <g:set var="noSelectionLabel"><g:message code="please.select"/></g:set>
        <g:select from="${org.samye.dzong.london.community.Teacher.list()}" name="leader.id" value="${event?.leader?.id}" noSelection="${['null':noSelectionLabel]}" optionKey="id" optionValue="name" class="required ui-corner-all"/>
      </fieldset>
      <fieldset>
        <label for="image.id"><g:message code="event.image.label"/></label>
        <g:set var="noImgLabel"><g:message code="no.img"/></g:set>
        <g:select from="${org.samye.dzong.london.media.Image.findAllByTag('event')}" name="image.id" value="${event?.image?.id}" noSelection="${['null':noImgLabel]}" optionKey="id" optionValue="name" class="required ui-corner-all"/>
      </fieldset>
      <fieldset>
        <label for="organizer.id"><g:message code="event.organizer.label"/></label>
        <g:set var="noSelectionLabel"><g:message code="no.img"/></g:set>
        <g:select from="${org.samye.dzong.london.ShiroUser.list()}" name="organizer.id" value="${event?.organizer?.id}" noSelection="${['null':noSelectionLabel]}" optionKey="id" optionValue="username" class="required ui-corner-all"/>
      </fieldset>
      <fieldset>
        <label for="price">Price (coming soon)</label>
        <g:textField name="price" class="ui-corner-all"/>
      </fieldset>

      <div id="dateTabs">
        <ul>
          <li><a href="#once"><g:message code="event.once.title"/></a></li>
          <li><a href="#regular"><g:message code="event.regular.title"/></a></li>
          <li><a href="#series"><g:message code="event.series.title"/></a></li>
        </ul>
        <div id="once">
          <fieldset>
            <label for="eventDate"><g:message code="event.eventdate.label" default="Event Date"/></label>
            <joda:datePicker name="eventDate" precision="day" value="${event?.eventDate}" noSelection="['': '']" class="ui-corner-all" years="${2010..2050}"/>
          </fieldset>
        </div>
        <div id="regular">
          <!-- TODO: add regular dates -->
          <h1>Coming Soon</h1>
        </div>
        <div id="series">
          <!-- TODO: add series dates -->
          <h1>Coming Soon</h1>
        </div>
      </div>

      <fieldset>
        <label for="summary"><g:message code="event.summary.label"/></label>
        <g:textArea rows="5" cols="40" name="summary" class="required ui-corner-all ${hasErrors(bean:event,field:'summary','errors')}" value="${fieldValue(bean:event,field:'summary')}" minlength="5"/>
      </fieldset>
      <fieldset>
        <label for="content"><g:message code="event.content.label"/></label>
        <g:textArea rows="35" cols="40" name="content" class="required ui-corner-all ${hasErrors(bean:event,field:'content','errors')}" value="${fieldValue(bean:event,field:'content')}" minlength="5"/>
      </fieldset>
      <fieldset class="last group">
        <label for="tags"><g:message code="event.tag.label"/> <strong><g:message code="event.tag.warning"/></strong></label>
        <textarea cols="5" rows="5" id="tags" name="tags" class="${hasErrors(bean: event, field: 'tags', 'errors')} required" minlength="3">${event.tags.join(",")}</textarea>
        <p class="tags_help">
          <g:message code="event.tag.help"/>
        </p>
      </fieldset>
      <g:submitButton name="create" class="ui-corner-all" value="${message(code: 'event.publish.btn', default: 'Publish')}"/>
    </g:form>
  </body>
</html>


