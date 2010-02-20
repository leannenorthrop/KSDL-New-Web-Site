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
<%@ page import="org.joda.time.Minutes; org.joda.time.Hours; org.samye.dzong.london.events.Event;org.joda.time.*" %>
%{--
  Create Event template

  Author: Leanne Northrop
  Date: 29th January, 2010, 17:54
--}%
<html>
  <head>
    <meta name="layout" content="content-admin"/>
    <title><g:message code="event.create.title"/></title>
    <g:javascript>
      $(function() {
        $("#addevent").validate();
        $("#accordion").accordion();
        $("#dateTabs").tabs({fx: { opacity: 'toggle' }});
      });
    </g:javascript>
  </head>
  <body>
    <g:form name="addevent" action="save" method="post">
      <h1 class="ui-widget-header"><g:message code="event.create.title"/></h1>

      <g:render template="/messageBox" model="[flash: flash]"/>

      <g:hiddenField name="publishState" value="Unpublished"/>
      <g:hiddenField name="deleted" value="false"/>
      <g:hiddenField name="displayAuthor" value="false"/>
      <g:hiddenField name="displayDate" value="false"/>
      <g:hiddenField name="isRepeatable" value="false"/>
      <g:hiddenField name="home" value="false"/>
      <g:hiddenField name="featured" value="false"/>

      <div id="accordion">
        <h3><a href="#">Title &amp; Summary</a></h3>
        <div>
          <fieldset>
            <label for="title"><g:message code="event.title.label"/></label>
            <g:textField name="title" value="${fieldValue(bean:event,field:'title')}" class="required ui-corner-all ${hasErrors(bean:event,field:'title','errors')}" minlength="5"/>
          </fieldset>
          <fieldset>
            <label for="summary"><g:message code="event.summary.label"/></label>
            <g:textArea rows="5" cols="40" name="summary" class="required ui-corner-all ${hasErrors(bean:event,field:'summary','errors')}" value="${fieldValue(bean:event,field:'summary')}" minlength="5"/>
          </fieldset>
        </div>

        <h3><a href="#">Details</a></h3>
        <div>
          <fieldset>
            <label for="category"><g:message code="event.category.label"/></label>
            <g:select name="category" from="${['M','N','C','W','B']}" value="${event?.category}" valueMessagePrefix="publish.category" class="required ui-corner-all ${hasErrors(bean:event,field:'title','errors')}"/>
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
            <label for="venue.id"><g:message code="event.venue.label"/></label>
            <g:select from="${org.samye.dzong.london.venue.Venue.list()}" name="venue.id" value="${event?.venue?.id}" optionKey="id" optionValue="name" class="required ui-corner-all"/>
          </fieldset>
        </div>

        <h3><a href="#">Dates</a></h3>
        <div>
          <fieldset>
            <label for="startTimeHour"><g:message code="event.starttime.label" default="Start Time"/></label>
            <g:select name="startTimeHour" from="${new TimeOfDay().hourOfDay().getMinimumValue()..new TimeOfDay().hourOfDay().getMaximumValue()}" value="${event?.startTime?.getHourOfDay()}" noSelection="${['null':'Select Hour...']}" class="ui-corner-all ${hasErrors(bean:event,field:'startTime','errors')}"/>&nbsp;:&nbsp;
            <g:select name="startTimeMin" from="${new TimeOfDay().minuteOfHour().getMinimumValue()..new TimeOfDay().minuteOfHour().getMaximumValue()}" value="${event?.startTime?.getMinuteOfHour()}" noSelection="${['null':'Select Minute...']}" class="ui-corner-all ${hasErrors(bean:event,field:'startTime','errors')}"/>
          </fieldset>
          <fieldset>
            <label for="eventDurationHour"><g:message code="event.eventduration.label" default="Event Duration"/></label>
            <g:select name="eventDurationHour" from="${0..24}" value="${event?.eventDuration?.toStandardHours()?.getHours()}" noSelection="${['null':'Select Hour...']}" class="ui-corner-all ${hasErrors(bean:event,field:'eventDuration','errors')}"/><g:message code="event.duration.hours"/>
            <g:select name="eventDurationMin" from="${[0,5,10,15,20,30,40,45,50,55]}" value="${event?.eventDuration?.toStandardMinutes()?.getMinutes()}" noSelection="${['null':'Select Minute...']}" class="ui-corner-all ${hasErrors(bean:event,field:'eventDuration','errors')}"/><g:message code="event.duration.mins"/>
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
        </div>

        <h3><a href="#">Prices</a></h3>
        <div>
          <fieldset>
            <label for="price">Price (coming soon)</label>
            <g:textField name="price" class="ui-corner-all"/>
          </fieldset>
        </div>

        <h3><a href="#">Full Description</a></h3>
        <div>
          <fieldset>
            <label for="content"><g:message code="event.content.label"/></label>
            <g:textArea rows="35" cols="40" name="content" class="required ui-corner-all ${hasErrors(bean:event,field:'content','errors')}" value="${fieldValue(bean:event,field:'content')}" minlength="5"/>
          </fieldset>
        </div>
      </div>
      <fieldset class="last"></fieldset>
      <g:submitButton name="create" class="ui-corner-all" value="${message(code: 'event.add.btn', default: 'Add event')}"/>
    </g:form>
  </body>
</html>


