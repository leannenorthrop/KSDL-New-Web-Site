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


<g:set var="multipleDateList" value="${event.dateList.size() > 1 ? event.dateList : []}"/>
<g:set var="defaultDate"><g:formatDate format="dd-MM-yyyy" date="${firstDate.startDate}"/></g:set>
<g:set var="currentEventDate"><g:formatDate format="dd-MM-yyyy" date="${firstDate.startDate}"/></g:set>
<g:set var="currentEndEventDate"><g:formatDate format="dd-MM-yyyy" date="${firstDate.endDate}"/></g:set>

<fieldset>
    <legend>Dates</legend> 
        
    <g:radioGroup name="event.dateList[0].ruleType" 
                  values="['once','several','between','always']" 
                  labels="['event.once.title','event.several.title','event.regular.dates.title','event.regular.title']" 
                  value="${firstDate.ruleType}" >
                  <g:message code="${it.label}" />: ${it.radio}&nbsp;
    </g:radioGroup>
    
    <p></p>
    
    <div id="once" style="display:none;height:20em;">
        <div id="singleDate"></div>    
    </div>
    
    <div id="several" style="display:none;height:20em;">
        <g:render template="/clone" model="[propval: 'startDate',labelCode:'event.date',listName:'dateList',nextId:multipleDateList.size(),list:multipleDateList]"/>
    </div>
    
    <div id="between" style="display:none;height:20em;">
        <g:render template="/scheduleRule" model="[prop: 'between',rule:firstDate]"/>
    </div>
          
    <div id="always" style="display:none;height:20em;">
        <g:render template="/scheduleRule" model="[prop:'always',rule:firstDate]"/>
    </div>

</fieldset>

<g:javascript>   
    var defaultDate = new Date(${currentEventDate});
    defaultDate.setFullYear(${currentEventDate});
    var endDate = new Date(${currentEndEventDate});
    endDate.setFullYear(${currentEndEventDate});

    $("#singleDate").datepicker({
        showOtherMonths: false,
        dateFormat: 'dd-mm-yy',
        defaultDate: defaultDate,
        numberOfMonths: [1,3],
        hideIfNoPrevNext: true,
        minDate: '0d',
        maxDate: '+3y',
        stepMonths: 3
    });
  
    $("input[name$=.ruleType]").change(function() {
        $("input[name$=.ruleType]").each(function(index) {
            var id = $(this).val();
            $("#" + id).hide();
        });
        var id2 = $(this).val();
        $("#" + id2).show();            
    });

    var showDatePanel = $("input[name$=.ruleType]:checked").val();
    $("#" + showDatePanel).show();  
</g:javascript>

