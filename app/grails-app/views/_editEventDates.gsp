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

<%@ page import="org.joda.time.TimeOfDay;org.samye.dzong.london.media.Image;org.samye.dzong.london.venue.Venue;org.samye.dzong.london.community.Teacher;org.samye.dzong.london.users.ShiroUser" contentType="text/html;charset=UTF-8" %>
<g:set var="currentEventDate"><g:formatDate format="dd-MM-yyyy" date="${firstDate.startDate}"/></g:set>
<g:set var="currentEndEventDate"><g:formatDate format="dd-MM-yyyy" date="${firstDate.endDate}"/></g:set>
<fieldset>
    <legend>Dates</legend> 
        
    <g:radioGroup name="dateList[0].ruleType" 
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
        <g:set var="multipleDateList" value="${firstDate.ruleType == 'several' ? event.dateList : []}"/>        
        <g:render template="/clone" model="[propval: 'startDate',labelCode:'event.date',listName:'dateList',nextId:multipleDateList.size(),list:multipleDateList]"/>
    </div>
    
    <div id="between" style="display:none;height:20em;">
         <g:message code="event.between.orig"/> <g:textField name="startingOnDate" class="ui-corner-all" style="display: inline;width:10em" minlength="4" value="${currentEventDate}"/>          
         <g:message code="event.and"/> <g:textField name="endingOnDate" class="ui-corner-all" style="display: inline;width:10em" minlength="4" value="${currentEndEventDate}"/>  
        <g:render template="/scheduleRule" model="[prop: 'between',rule:firstDate]"/>
    </div>
          
    <div id="always" style="display:none;height:20em;">
        <g:message code="event.startingfrom"/> <g:textField name="startingFromDate" class="ui-corner-all" style="display: inline;width:10em" minlength="4" value="${currentEventDate}"/>          
        <g:render template="/scheduleRule" model="[prop:'always',rule:firstDate]"/>
    </div>

</fieldset>

<g:javascript>   
    var start = "${currentEventDate}".split("-");
    var end = "${currentEndEventDate}".split("-");     
</g:javascript>

