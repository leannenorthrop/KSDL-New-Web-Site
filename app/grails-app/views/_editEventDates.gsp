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
  Template for editing Event dates.

  User: Leanne Northrop
  Date: Jun 30, 2010, 23:05
--%>

<%@ page import="org.joda.time.TimeOfDay;org.samye.dzong.london.media.Image;org.samye.dzong.london.venue.Venue;org.samye.dzong.london.community.Teacher;org.samye.dzong.london.ShiroUser" contentType="text/html;charset=UTF-8" %>
<fieldset>
    <legend>Dates</legend> 
    
    <g:radioGroup name="rule.type" 
                  values="[1,2,3,4]" 
                  labels="['event.once.title','event.several.title','event.regular.dates.title','event.regular.title']" 
                  value="1" >
                  <g:message code="${it.label}" />: ${it.radio}&nbsp;
    </g:radioGroup>
    
    <p></p>
    
    <div id="d1" style="display:none;height:20em;">
      <div id="eventDatePicker"></div>
      <g:set var="defaultDate"><g:formatDate format="dd-MM-yyyy" date="${rule?.startDate}"/></g:set>
      <g:hiddenField name="eventDate" value="${defaultDate}"/>
    </div>
    
    <div id="d2" style="display:none;height:20em;">
    </div>
    
    <div id="d3" style="display:none;height:20em;">
        <div>
          <label for="rule.from">
              <g:message code="from"/>
          </label>
          <input type="text" 
                 name="rule.from" 
                 class="fromDatepicker" 
                 style="width:auto"/>&nbsp;
          
          <label for="rule.until">
              <g:message code="until"/>
          </label>
          <input type="text" 
                 name="rule.until" 
                 class="untilDatepicker" 
                 style="width:auto"/>
        </div>          
         <table style="background:transparent;border:0px;">
              <tr>
                <td style="width:1em;"><input type="radio" name="rule.ruleType2" class="dailyradio" value="daily" checked="${rule?.isDaily()}">Daily</input></td>
                <td style="height:2em;width:18em;">
                  <div class="daily" style="display:${rule?.isDaily() ? 'block' : 'none'}">
                    <g:select name="rule.dailyinterval" from="${1..6}" valueMessagePrefix="day.interval" class="ui-corner-all" value="${rule?.interval}"/>
                  </div>
                </td>
              </tr>
              <tr>
                <td style="width:1em;"><input type="radio" name="rule.ruleType2" class="weeklyradio" value="weekly" checked="${rule?.isWeekly()}">Weekly</input></td>
                <td style="height:2em;width:18em;">
                  <div class="weekly" style="display:${rule?.isWeekly() ? 'block' : 'none'}">
                    <g:select name="rule.weeklyinterval" from="${1..4}" valueMessagePrefix="week.interval" class="ui-corner-all" value="${rule?.interval}"/>
                    <g:each in="${['MO','TU','WE','TH','FR','SA','SU']}">
                      <input type="checkbox" name="rule.weekly.${it}" class="ruleweekly${it}"/><g:message code="${it}"/>
                    </g:each>
                  </div>
                </td>
              </tr>
              <tr>
                <td style="width:1em;"><input type="radio" name="rule.ruleType2" class="monthlyradio" value="monthly" checked="${rule?.isMonthly()}">Monthly</input></td>
                <td style="height:13em;width:18em;">
                  <div class="monthly" style="display:${rule?.isMonthly() ? 'block' : 'none'}">
                    <g:select name="rule.monthlyinterval" from="${1..12}" valueMessagePrefix="month.interval" class="ui-corner-all" value="${rule?.interval}"/>
                    <g:each in="${['one','two']}" var="instance">
                      <p>
                        <g:select name="rule.monthly.${instance}.interval" class="rulemonthly${instance}interval ui-corner-all" from="${1..5}" valueMessagePrefix="month"/>
                        <g:each in="${['MO','TU','WE','TH','FR','SA','SU']}">
                          <input type="checkbox" name="rule.monthly.${instance}.${it}" class="rulemonthly${instance}${it}"/><g:message code="${it}"/>
                        </g:each>
                      </p>
                    </g:each>
                  </div>
                </td>
              </tr>
            </table>        
    </div>      
    <div id="d4" style="display:none;height:20em;">
        <div>
          <label><g:message code="from"/>&nbsp;<input name="rule.from" type="text" class="alwaysFrom" style="width:auto"/></label>
        </div>          
      <table style="background:transparent;border:0px">
        <tr>
          <td style="width:1em;"><input type="radio" name="rule.ruleType2" class="dailyradio" value="daily" checked="${rule?.isDaily()}">Daily</input></td>
          <td style="height:2em;width:18em;">
            <div class="daily" style="display:${rule?.isDaily() ? 'block' : 'none'}">
              <g:select name="rule.dailyinterval" from="${1..6}" valueMessagePrefix="day.interval" class="ui-corner-all" value="${rule?.interval}"/>
            </div>
          </td>
        </tr>
        <tr>
          <td style="width:1em;"><input type="radio" name="rule.ruleType2" class="weeklyradio" value="weekly" checked="${rule?.isWeekly()}">Weekly</input></td>
          <td style="height:2em;width:18em;">
            <div class="weekly" style="display:${rule?.isWeekly() ? 'block' : 'none'}">
              <g:select name="rule.weeklyinterval" from="${1..4}" valueMessagePrefix="week.interval" class="ui-corner-all" value="${rule?.interval}"/>
              <g:each in="${['MO','TU','WE','TH','FR','SA','SU']}">
                <input type="checkbox" name="rule.weekly.${it}" class="ruleweekly${it}"/><g:message code="${it}"/>
              </g:each>
            </div>
          </td>
        </tr>
        <tr>
          <td style="width:1em;"><input type="radio" name="rule.ruleType2" class="monthlyradio" value="monthly" checked="${rule?.isMonthly()}">Monthly</input></td>
          <td style="height:13em;width:18em;">
            <div class="monthly" style="display:${rule?.isMonthly() ? 'block' : 'none'}">
              <g:select name="rule.monthlyinterval" from="${1..12}" valueMessagePrefix="month.interval" class="ui-corner-all" value="${rule?.interval}"/>
              <g:each in="${['one','two']}" var="instance">
                <p>
                  <g:select name="rule.monthly.${instance}.interval" class="rulemonthly${instance}interval ui-corner-all" from="${1..5}" valueMessagePrefix="month"/>
                  <g:each in="${['MO','TU','WE','TH','FR','SA','SU']}">
                    <input type="checkbox" name="rule.monthly.${instance}.${it}" class="rulemonthly${instance}${it}"/><g:message code="${it}"/>
                  </g:each>
                </p>
              </g:each>
            </div>
          </td>
        </tr>
      </table>
    </div>
    <p></p>
</fieldset>
<g:set var="currentEventDate"><g:formatDate format="yyyy" date="${rule?.startDate}"/>,${rule?.startDate?.getMonth()},<g:formatDate format="dd" date="${rule?.startDate}"/></g:set>
<g:set var="currentEndEventDate"><g:formatDate format="yyyy" date="${rule?.endDate}"/>,${rule?.endDate?.getMonth()},<g:formatDate format="dd" date="${rule?.endDate}"/></g:set>
<g:javascript>   
      var defaultDate = new Date(${currentEventDate});
      defaultDate.setFullYear(${currentEventDate});
      var endDate = new Date(${currentEndEventDate});
      endDate.setFullYear(${currentEndEventDate});
      $(".alwaysFrom").datepicker({dateFormat: 'dd-mm-yy',defaultDate: defaultDate,minDate: '0d', maxDate: '+3y',hideIfNoPrevNext: true});
      $(".fromDatepicker").datepicker({
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
      $(".untilDatepicker").datepicker({dateFormat: 'dd-mm-yy',defaultDate: endDate,minDate: '0d', maxDate: '+3y',hideIfNoPrevNext: true});
      $("input[name|=rule.type]").change(function() {
            $("input[name|=rule.type]").each(function(index) {
              var id = $(this).val();
              $("#d" + id).hide();
            });
            var id2 = $(this).val();
            $("#d" + id2).show();            
        });
      $("input[name|=rule.ruleType1]").change(function() {
          $("input[name|=rule.ruleType1]").each(function(index) {
            var id = $(this).val();
            $("." + id).toggle();
          });
      });
      $("input[name|=rule.ruleType2]").change(function() {
          $("input[name|=rule.ruleType2]").each(function(index) {
            var id = $(this).val();
            $("." + id).hide();
          });
          var id2 = $(this).val();
          $("." + id2).show();
      });

      $("#eventDatePicker").datepicker({
        showOtherMonths: false,
        dateFormat: 'dd-mm-yy',
        defaultDate: defaultDate,
        numberOfMonths: [1,3],
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
      $(".alwaysFrom").attr("value",defaultFormattedDate);
      $(".fromDatepicker").attr("value",defaultFormattedDate);
      $(".untilDatepicker").attr("value",endFormattedDate);

  <g:if test="${rule?.isDaily()}">
    $(".dailyradio").attr("checked","true");
  </g:if>
  <g:elseif test="${rule?.isWeekly()}">
    $(".weeklyradio").attr("checked","true");
    <g:each in="${rule?.getDays()}" var="day">
      $("#ruleweekly${day}").attr("checked","true");
    </g:each>
  </g:elseif>
  <g:else>
    $(".monthlyradio").attr("checked","true");
    <g:each in="${['one','two']}" var="instance" status="index">
      $("#rulemonthly${instance}${rule?.getDays()[index]}").attr("checked","true");
      $("#rulemonthly${instance}interval").attr("value","${rule?.getOffsets()[index]}");
    </g:each>
  </g:else>
  var showDatePanel = $("input[name|=rule.type]:checked").val();
  $("#d" + showDatePanel).show();  
</g:javascript>

