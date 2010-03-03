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
      <g:textField name="title" value="${event?.title}" class="required ui-corner-all ${hasErrors(bean:event,field:'title','errors')}" minlength="5"/>
    </fieldset>
    <fieldset>
      <label for="summary"><g:message code="event.summary.label"/></label>
      <g:textArea rows="5" cols="40" name="summary" class="required ui-corner-all ${hasErrors(bean:event,field:'summary','errors')}" value="${fieldValue(bean:event,field:'summary')}" minlength="5"/>
    </fieldset>
    <fieldset>
      <label for="category"><g:message code="event.category.label"/></label>
      <g:select name="category" from="${['M','N','C','W','B']}" value="${event?.category}" valueMessagePrefix="publish.category" class="required ui-corner-all ${hasErrors(bean:event,field:'category','errors')}"/>
    </fieldset>
    <fieldset>
      <label for="leader.id"><g:message code="event.leader.label"/></label>
      <g:set var="noSelectionLabel"><g:message code="please.select"/></g:set>
      <g:select from="${org.samye.dzong.london.community.Teacher.publishState('Published').list()}" name="leader.id" value="${event?.leader?.id}" noSelection="${['null':noSelectionLabel]}" optionKey="id" optionValue="name" class="required ui-corner-all"/>
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
    <fieldset>
      <label for="startTimeHour"><g:message code="event.starttime.label" default="Start Time"/></label>
      <g:select name="startTimeHour" from="${new TimeOfDay().hourOfDay().getMinimumValue()..new TimeOfDay().hourOfDay().getMaximumValue()}" value="${rule?.startTime?.getHourOfDay()}" noSelection="${['null':'Select Hour...']}" class="ui-corner-all ${hasErrors(bean:event,field:'startTime','errors')}"/>&nbsp;:&nbsp;
      <g:select name="startTimeMin" from="${[0,10,15,20,30,40,45]}" value="${rule?.startTime?.getMinuteOfHour()}" noSelection="${['null':'Select Minute...']}" class="ui-corner-all ${hasErrors(bean:event,field:'startTime','errors')}"/>
    </fieldset>
    <fieldset>
      <label for="endTimeHour"><g:message code="event.endtime.label" default="End Time"/></label>
      <g:select name="endTimeHour" from="${new TimeOfDay().hourOfDay().getMinimumValue()..new TimeOfDay().hourOfDay().getMaximumValue()}" value="${rule?.endTime?.getHourOfDay()}" noSelection="${['null':'Select Hour...']}" class="ui-corner-all ${hasErrors(bean:event,field:'endTime','errors')}"/>&nbsp;:&nbsp;
      <g:select name="endTimeMin" from="${[0,10,15,20,30,40,45]}" value="${rule?.endTime?.getMinuteOfHour()}" noSelection="${['null':'Select Minute...']}" class="ui-corner-all ${hasErrors(bean:event,field:'endTime','errors')}"/>
    </fieldset>
  </div>

  <h3><a href="#"><g:message code="event.dates"/></a></h3>
  <div>
    <g:hiddenField id="ruletype" name="rule.type" value="0"/>
    <div id="dateTabs">
      <ul>
        <li><a href="#once"><g:message code="event.once.title"/></a></li>
        <li><a href="#regular"><g:message code="event.regular.title"/></a></li>
      </ul>
      <div id="once">
        <div id="eventDatePicker"></div>
        <g:set var="defaultDate"><g:formatDate format="dd-MM-yyyy" date="${rule?.startDate}"/></g:set>
        <g:hiddenField name="eventDate" value="${defaultDate}"/>
      </div>
      <div id="regular">
        <table>
          <tr>
            <td style="width:1em;">
              <label><input type="radio" id="alwaysradio" name="rule.ruleType1" value="always"/><g:message code="always"/></label>
            </td>
            <td style="height:3em;width:18em;">
              <div id="always" style="display:${rule?.isUnbounded() ? 'block' : 'none'}">
                <label><g:message code="from"/>&nbsp;<input name="rule.from" type="text" id="alwaysFrom" style="width:auto"/></label>
              </div>
            </td>
          </tr>
          <tr>
            <td style="width:1em;"><label><input id="between" type="radio" name="rule.ruleType1" value="period"/><g:message code="between"/></label></td>
            <td style="height:3em;width:18em;">
              <div id="period" style="display:${rule?.isBounded() ? 'block' : 'none'}">
                <label><g:message code="from"/>&nbsp;<input type="text" name="rule.from" id="fromDatepicker" style="width:auto"/></label>&nbsp;<label><g:message code="until"/>&nbsp;<input type="text" name="rule.until" id="untilDatepicker" style="width:auto"/></label>
              </div>
            </td>
          </tr>
          <tr>
            <td style="width:1em;"><input type="radio" name="rule.ruleType2" id="dailyradio" value="daily" checked="${rule?.isDaily()}">Daily</input></td>
            <td style="height:2em;width:18em;">
              <div id="daily" style="display:${rule?.isDaily() ? 'block' : 'none'}">
                <g:select name="rule.dailyinterval" from="${1..6}" valueMessagePrefix="day.interval" class="ui-corner-all" value="${rule?.interval}"/>
              </div>
            </td>
          </tr>
          <tr>
            <td style="width:1em;"><input type="radio" name="rule.ruleType2" id="weeklyradio" value="weekly" checked="${rule?.isWeekly()}">Weekly</input></td>
            <td style="height:2em;width:18em;">
              <div id="weekly" style="display:${rule?.isWeekly() ? 'block' : 'none'}">
                <g:select name="rule.weeklyinterval" from="${1..4}" valueMessagePrefix="week.interval" class="ui-corner-all" value="${rule?.interval}"/>
                <g:each in="${['MO','TU','WE','TH','FR','SA','SU']}">
                  <input type="checkbox" name="rule.weekly.${it}" id="ruleweekly${it}"/><g:message code="${it}"/>
                </g:each>
              </div>
            </td>
          </tr>
          <tr>
            <td style="width:1em;"><input type="radio" name="rule.ruleType2" id="monthlyradio" value="monthly" checked="${rule?.isMonthly()}">Monthly</input></td>
            <td style="height:13em;width:18em;">
              <div id="monthly" style="display:${rule?.isMonthly() ? 'block' : 'none'}">
                <g:select name="rule.monthlyinterval" from="${1..12}" valueMessagePrefix="month.interval" class="ui-corner-all" value="${rule?.interval}"/>
                <g:each in="${['one','two']}" var="instance">
                  <fieldset>
                    <g:select name="rule.monthly.${instance}.interval" id="rulemonthly${instance}interval" from="${1..5}" valueMessagePrefix="month" class="ui-corner-all"/>
                    <g:each in="${['MO','TU','WE','TH','FR','SA','SU']}">
                      <input type="checkbox" name="rule.monthly.${instance}.${it}" id="rulemonthly${instance}${it}"/><g:message code="${it}"/>
                    </g:each>
                  </fieldset>
                </g:each>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </div>
  </div>

  <h3><a href="#"><g:message code="event.prices"/></a></h3>
  <div>
    <g:set var="pricelabels" value="${[F: 'full',S: 'subsidize', M: 'mature',O:'other']}"/>
    <g:each var="price" in="${event.prices}" status="i">
      <fieldset>
        <label for="priceList[${i}].price"><g:message code="event.price.${pricelabels[price.category]}"/></label>
        <g:textField name="priceList[${i}].price" value="${price.price}" class="required ui-corner-all ${hasErrors(bean:event,field:'price.${pricelabels[i]}','errors')}" minlength="4" decimal="true"/>
        <g:hiddenField name="priceList[${i}].id" value='${price.id}'/>
        <g:hiddenField name="priceList[${i}]._deleted" value='${false}'/>
        <g:hiddenField name="priceList[${i}].category" value='${price.category}'/>
      </fieldset>
    </g:each>
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
<g:set var="currentEventDate"><g:formatDate format="yyyy" date="${rule?.startDate}"/>,${rule?.startDate?.getMonth()},<g:formatDate format="dd" date="${rule?.startDate}"/></g:set>
<g:set var="currentEndEventDate"><g:formatDate format="yyyy" date="${rule?.endDate}"/>,${rule?.endDate?.getMonth()},<g:formatDate format="dd" date="${rule?.endDate}"/></g:set>
<g:javascript>
      var defaultDate = new Date(${currentEventDate});
      defaultDate.setFullYear(${currentEventDate});
      var endDate = new Date(${currentEndEventDate});
      endDate.setFullYear(${currentEndEventDate});
      $("#alwaysFrom").datepicker({dateFormat: 'dd-mm-yy',defaultDate: defaultDate,minDate: '0d', maxDate: '+3y',hideIfNoPrevNext: true});
      $("#fromDatepicker").datepicker({
          dateFormat: 'dd-mm-yy',
          defaultDate: defaultDate,
          minDate: '0d',
          maxDate: '+3y',
          hideIfNoPrevNext: true,
          onSelect: function(event, ui) {
            var selected = $("#fromDatepicker").datepicker( 'getDate' );
            //selected = $.datepicker.formatDate('dd-mm-yy', selected);
            $('#untilDatepicker').datepicker('option', {minDate: selected});
        }
      });
      $("#untilDatepicker").datepicker({dateFormat: 'dd-mm-yy',defaultDate: endDate,minDate: '0d', maxDate: '+3y',hideIfNoPrevNext: true});
      $("input[name|=rule.ruleType1]").change(function() {
          $("input[name|=rule.ruleType1]").each(function(index) {
            var id = $(this).val();
            $("#" + id).toggle();
          });
      });
      $("input[name|=rule.ruleType2]").change(function() {
          $("input[name|=rule.ruleType2]").each(function(index) {
            var id = $(this).val();
            $("#" + id).hide();
          });
          var id2 = $(this).val();
          $("#" + id2).show();
      });
      $("#accordion").accordion();
      $("#dateTabs").tabs({
          fx: { opacity: 'toggle' },
          select: function(event, ui) {
              var selected = $("#dateTabs").tabs('option', 'selected');
              if (selected == 0) {
                selected = 1;
              } else {
                selected = 0;
              }
              $("#ruletype").attr("value",selected);
            return true;
          }
      });
      $("#eventDatePicker").datepicker({
        showOtherMonths: false,
        dateFormat: 'dd-mm-yy',
        defaultDate: defaultDate,
        numberOfMonths: [3,3],
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
      var defaultFormattedDate = $.datepicker.formatDate('dd-mm-yy', defaultDate);
      var endFormattedDate = $.datepicker.formatDate('dd-mm-yy', endDate);
      $("#alwaysFrom").attr("value",defaultFormattedDate);
      $("#fromDatepicker").attr("value",defaultFormattedDate);
      $("#untilDatepicker").attr("value",endFormattedDate);
  <g:if test="${rule.isRule}">
    $("#dateTabs").tabs('option', 'selected', 1);
  </g:if>
  <g:if test="${rule?.isUnbounded()}">
    $("#alwaysradio").attr("checked","true");
  </g:if>
  <g:else>
    $("#between").attr("checked","true");
  </g:else>
  <g:if test="${rule?.isDaily()}">
    $("#dailyradio").attr("checked","true");
  </g:if>
  <g:elseif test="${rule?.isWeekly()}">
    $("#weeklyradio").attr("checked","true");
    <g:each in="${rule?.getDays()}" var="day">
      $("#ruleweekly${day}").attr("checked","true");
    </g:each>
  </g:elseif>
  <g:else>
    $("#monthlyradio").attr("checked","true");
    <g:each in="${['one','two']}" var="instance" status="index">
      $("#rulemonthly${instance}${rule?.getDays()[index]}").attr("checked","true");
      $("#rulemonthly${instance}interval").attr("value","${rule?.getOffsets()[index]}");
    </g:each>
  </g:else>
</g:javascript>

