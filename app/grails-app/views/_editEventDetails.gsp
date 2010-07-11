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
  Template for editing Event details.

  User: Leanne Northrop
  Date: Jun 30, 2010, 23:01
--%>

<%@ page import="org.joda.time.TimeOfDay;org.samye.dzong.london.media.Image;org.samye.dzong.london.venue.Venue;org.samye.dzong.london.community.Teacher;org.samye.dzong.london.ShiroUser" contentType="text/html;charset=UTF-8" %>
<fieldset>
    <legend>Details</legend> 
      <p>
        <label for="title">
            <g:message code="event.title.label"/>
        </label>
        <g:textField name="title" 
                     value="${event?.title}" 
                     class="required ui-corner-all ${hasErrors(bean:event,field:'title','errors')}" 
                     minlength="5"/>
      </p>
      <p>
        <label for="summary">
            <g:message code="event.summary.label"/>
        </label>
        <g:textArea rows="5" 
                    cols="40" 
                    name="summary" 
                    class="required ui-corner-all ${hasErrors(bean:event,field:'summary','errors')}" 
                    value="${fieldValue(bean:event,field:'summary')}" 
                    minlength="5"/>
      </p>
      <p>          
        <label for="image.id">
            <g:message code="event.image.label"/>
        </label>
        <g:set var="noImgLabel"><g:message code="no.img"/></g:set>
        <g:select from="${Image.findAllByTag('event')}" 
                  name="image.id" 
                  value="${event?.image?.id}" 
                  noSelection="${['null':noImgLabel]}" 
                  optionKey="id" 
                  optionValue="name" 
                  class="= ui-corner-all"/>          
      </p>
      <p>
          <label for="category">
              <g:message code="event.category.label"/>
          </label>
          <g:select name="category" 
                    from="${['M','N','C','W','B']}" 
                    value="${event?.category}" 
                    valueMessagePrefix="publish.category" 
                    validate="required:true, minlength:1" 
                    class="required ui-corner-all ${hasErrors(bean:event,field:'category','errors')}"/>    
          
          <label for="venue.id">
              <g:message code="event.venue.label"/>
          </label>
          <g:select from="${Venue.list()}" 
                    name="venue.id" 
                    value="${event?.venue?.id}" 
                    optionKey="id" 
                    optionValue="name" 
                    class="required ui-corner-all"/>                    
      </p>
      <p>
        <label for="leader.id">
            <g:message code="event.leader.label"/>
        </label>
        <g:set var="noSelectionLabel"><g:message code="please.select"/></g:set>
        <g:select from="${Teacher.publishState('Published').list()}" 
                  name="leader.id" 
                  validate="required:true, minlength:1" 
                  value="${event?.leader?.id}" 
                  noSelection="${['null':noSelectionLabel]}" 
                  optionKey="id" 
                  optionValue="name" 
                  class="required ui-corner-all ${hasErrors(bean:event,field:'category','errors')}"/>
        
        <label for="organizer.id">
            <g:message code="event.organizer.label"/>
        </label>
        <g:set var="noSelectionLabel"><g:message code="no.img"/></g:set>
        <g:select from="${ShiroUser.list()}" 
                  name="organizer.id" 
                  value="${event?.organizer?.id}" 
                  noSelection="${['null':noSelectionLabel]}" 
                  optionKey="id"  
                  class="required ui-corner-all ${hasErrors(bean:event,field:'category','errors')}"/>          
      </p>
      <p>                        
        <label for="startTimeHour">
            <g:message code="event.starttime.label" default="Start Time"/>
        </label>
        <g:select name="startTimeHour" 
                  from="${new TimeOfDay().hourOfDay().getMinimumValue()..new TimeOfDay().hourOfDay().getMaximumValue()}" 
                  value="${rule?.startTime?.getHourOfDay()}" 
                  noSelection="${['null':'Select Hour...']}" 
                  class="ui-corner-all ${hasErrors(bean:event,field:'startTime','errors')}" id="starttime"/>
                  
        &nbsp;:&nbsp;
        
        <g:select name="startTimeMin" 
                  from="${[0,10,15,20,30,40,45]}" 
                  value="${rule?.startTime?.getMinuteOfHour()}" 
                  noSelection="${['null':'Select Minute...']}" 
                  class="required ui-corner-all ${hasErrors(bean:event,field:'startTime','errors')}"/>

        <label for="endTimeHour">
            <g:message code="event.endtime.label" default="End Time"/>
        </label>
        <g:select name="endTimeHour" 
                  from="${new TimeOfDay().hourOfDay().getMinimumValue()..new TimeOfDay().hourOfDay().getMaximumValue()}" 
                  value="${rule?.endTime?.getHourOfDay()}" 
                  noSelection="${['null':'Select Hour...']}" 
                  class="ui-corner-all ${hasErrors(bean:event,field:'endTime','errors')}" 
                  id="endtime"/>
                  
        &nbsp;:&nbsp;
        
        <g:select name="endTimeMin" 
                  from="${[0,10,15,20,30,40,45]}" 
                  value="${rule?.endTime?.getMinuteOfHour()}" 
                  noSelection="${['null':'Select Minute...']}" 
                  class="required ui-corner-all ${hasErrors(bean:event,field:'endTime','errors')}"/>
      </p>  
</fieldset>
<g:javascript>   
  $("#starttime").change(function() {
      var stv = $(this).val();
      stv++;
      $("#endtime").attr("value", stv);
  });
</g:javascript>