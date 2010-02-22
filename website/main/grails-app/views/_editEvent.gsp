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

<%@ page import="org.joda.time.TimeOfDay" contentType="text/html;charset=UTF-8" %>

<div id="accordion">
  <h3><a href="#"><g:message code="event.details"/></a></h3>
  <div>
    <fieldset>
      <label for="title"><g:message code="event.title.label"/></label>
      <g:textField name="title" value="${fieldValue(bean:event,field:'title')}" class="required ui-corner-all ${hasErrors(bean:event,field:'title','errors')}" minlength="5"/>
    </fieldset>
    <fieldset>
      <label for="summary"><g:message code="event.summary.label"/></label>
      <g:textArea rows="5" cols="40" name="summary" class="required ui-corner-all ${hasErrors(bean:event,field:'summary','errors')}" value="${fieldValue(bean:event,field:'summary')}" minlength="5"/>
    </fieldset>
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
      <g:select from="${org.samye.dzong.london.media.Image.findAllByTag('event')}" name="image.id" value="${event?.image?.id}" noSelection="${['null':noImgLabel]}" optionKey="id" optionValue="name" class="= ui-corner-all"/>
    </fieldset>
    <fieldset>
      <label for="organizer.id"><g:message code="event.organizer.label"/></label>
      <g:set var="noSelectionLabel"><g:message code="no.img"/></g:set>
      <g:select from="${org.samye.dzong.london.ShiroUser.list()}" name="organizer.id" value="${event?.organizer?.id}" noSelection="${['null':noSelectionLabel]}" optionKey="id" optionValue="username" class="ui-corner-all"/>
    </fieldset>
    <fieldset>
      <label for="venue.id"><g:message code="event.venue.label"/></label>
      <g:select from="${org.samye.dzong.london.venue.Venue.list()}" name="venue.id" value="${event?.venue?.id}" optionKey="id" optionValue="name" class="required ui-corner-all"/>
    </fieldset>
  </div>

  <h3><a href="#"><g:message code="event.dates"/></a></h3>
  <div>
    <fieldset>
      <label for="startTimeHour"><g:message code="event.starttime.label" default="Start Time"/></label>
      <g:select name="startTimeHour" from="${new TimeOfDay().hourOfDay().getMinimumValue()..new TimeOfDay().hourOfDay().getMaximumValue()}" value="${event?.startTime?.getHourOfDay()}" noSelection="${['null':'Select Hour...']}" class="ui-corner-all ${hasErrors(bean:event,field:'startTime','errors')}"/>&nbsp;:&nbsp;
      <g:select name="startTimeMin" from="${new TimeOfDay().minuteOfHour().getMinimumValue()..new TimeOfDay().minuteOfHour().getMaximumValue()}" value="${event?.startTime?.getMinuteOfHour()}" noSelection="${['null':'Select Minute...']}" class="ui-corner-all ${hasErrors(bean:event,field:'startTime','errors')}"/>
    </fieldset>
    <fieldset>
      <label for="endTimeHour"><g:message code="event.endtime.label" default="End Time"/></label>
      <g:select name="endTimeHour" from="${new TimeOfDay().hourOfDay().getMinimumValue()..new TimeOfDay().hourOfDay().getMaximumValue()}" value="${event?.endTime?.getHourOfDay()}" noSelection="${['null':'Select Hour...']}" class="ui-corner-all ${hasErrors(bean:event,field:'startTime','errors')}"/>&nbsp;:&nbsp;
      <g:select name="endTimeMin" from="${new TimeOfDay().minuteOfHour().getMinimumValue()..new TimeOfDay().minuteOfHour().getMaximumValue()}" value="${event?.endTime?.getMinuteOfHour()}" noSelection="${['null':'Select Minute...']}" class="ui-corner-all ${hasErrors(bean:event,field:'startTime','errors')}"/>
    </fieldset>
    <fieldset class="last"></fieldset>
    <div id="dateTabs">
      <ul>
        <li><a href="#once"><g:message code="event.once.title"/></a></li>
        <li><a href="#regular"><g:message code="event.regular.title"/></a></li>
        %{--<li><a href="#series"><g:message code="event.series.title"/></a></li>--}%
      </ul>
      <div id="once">
        <fieldset>
          <div id="eventDatePicker"></div>
          <g:set var="defaultDate"><g:formatDate format="dd-MM-yyyy" date="${event?.eventDate}"/></g:set>
          <g:hiddenField name="eventDate" value="${defaultDate}"/>
        </fieldset>
      </div>
      <div id="regular">
        <!-- TODO: add regular dates -->
        <h1>Coming Soon</h1>
      </div>
      %{--<div id="series">
        <h1>Coming Soon</h1>
      </div>--}%
    </div>
  </div>

  <h3><a href="#"><g:message code="event.prices"/></a></h3>
  <div>
    <fieldset>
      <label for="price">Price (coming soon)</label>
      <g:textField name="price" class="ui-corner-all"/>
    </fieldset>
  </div>

  <h3><a href="#"><g:message code="event.description"/></a></h3>
  <div>
    <fieldset>
      <g:render template="/contentWithPreview" model="[previewController: 'manageSite',publishableInstance:event]"/>
    </fieldset>
  </div>

  <g:if test="${showPublication}">
    <h3><a href="#"><g:message code="event.publication.details"/></a></h3>
    <div>
      <g:render template="/publishDetails" model="[articleInstance:event]"/>
    </div>
  </g:if>
</div>
<g:set var="currentEventDate"><g:formatDate format="yyyy" date="${event?.eventDate}"/>,${event?.eventDate?.getMonth()},<g:formatDate format="dd" date="${event?.eventDate}"/></g:set>
<g:javascript>
      $("#accordion").accordion();
      $("#dateTabs").tabs({fx: { opacity: 'toggle' }});
      var d = new Date();
      d.setFullYear(${currentEventDate});
      $("#eventDatePicker").datepicker({
        showOtherMonths: false,
        dateFormat: 'dd-mm-yyyy',
        defaultDate: d,
        numberOfMonths: [2,3],
        hideIfNoPrevNext: true,
        minDate: '0d',
        maxDate: '+3y',
        stepMonths: 6,
        onSelect: function(event, ui) {
          var selected = $("#eventDatePicker").datepicker( 'getDate' );
          selected = $.datepicker.formatDate('dd-mm-yy', selected);
          $('#eventDate').attr('value', selected);
        }
      });
</g:javascript>

