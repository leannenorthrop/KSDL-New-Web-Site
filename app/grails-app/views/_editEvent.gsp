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

<g:set var="firstDate" value="${event.dateList[0] ?: event.dateList.get(0)}"/>
<g:render template="/editEventDetails" model="[event: event, firstDate:firstDate]"/>

<g:render template="/editEventDates" model="[event: event, firstDate:firstDate]"/>

<g:render template="/editEventPrices" model="[event: event]"/>

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

<jq:jquery>
    var bindDateParams = function() {
        var startTimeHour = $("#startTimeHour").val();
        var startTimeMin = $("#startTimeMin").val();
        var endTimeHour = $("#endTimeHour").val();
        var endTimeMin = $("#endTimeMin").val();  
        var startDate = $("#startDate").val();
        var type = $("input[name$=ruleType]:checked").val();  
        var timefields = ''; 
        if (type != 'several') {                  
            $("#several p input:hidden").each(function(index) {
                var n = $(this).attr('name');
                if (n.match('^dateList') && !n.match('_deleted')) {
                    $(this).remove();
                } else if (n.match('_deleted')) {
                    if (n.match("0")) {
                        $(this).val('false');
                    } else {
                        $(this).val('true');
                    }
                }
            }); 
            if (type == 'once') {                                
                var selected = $("#singleDate").datepicker( 'getDate' );
                var year = $.datepicker.formatDate('yy', selected);
                var month = $.datepicker.formatDate('mm', selected);
                var day = $.datepicker.formatDate('dd', selected);                                
                
                timefields += '<input type="hidden" name="dateList[0].startDate_year" value="' + year + '">';                                              
                timefields += '<input type="hidden" name="dateList[0].startDate_month" value="' + month + '">';                                              
                timefields += '<input type="hidden" name="dateList[0].startDate_day" value="' + day + '">';                                                                              
                timefields += '<input type="hidden" name="dateList[0].isRule" value="false">';   
                timefields += '<input type="hidden" name="dateList[0].endDate_year" value="' + year + '">';                                              
                timefields += '<input type="hidden" name="dateList[0].endDate_month" value="' + month + '">';                                              
                timefields += '<input type="hidden" name="dateList[0].endDate_day" value="' + day + '">';
                
                timefields += '<input type="hidden" name="dateList[0].startTimeHour" value="' + startTimeHour + '">';   
                timefields += '<input type="hidden" name="dateList[0].startTimeMin" value="' + startTimeMin + '">';                                              
                timefields += '<input type="hidden" name="dateList[0].endTimeHour" value="' + endTimeHour + '">';                                              
                timefields += '<input type="hidden" name="dateList[0].endTimeMin" value="' + endTimeMin + '">';                

                $("#once").append(timefields);
            }      
        } else {           
            var thedates = [];
                                    
            // For each of newly added numbers add to date array                        
            $("#several p input:hidden").each(function(index) {
                var n = $(this).attr('name');
                if (n.match('^dateList') && n.match('startDate')) {
                    var s = $(this).val();
                    thedates.push(s);
                }
            });

            var startIndexOfNewItems = ${event.dateList.size()};
            $("#several p input:hidden").each(function(index) {
                try {
                    var n = $(this).attr('name');
                    if (n.match('^dateList') && n.match('_deleted')) {
                        var i = n.substring(9, n.indexOf(']'));
                        if (i >= startIndexOfNewItems){
                            var selected = thedates[i-startIndexOfNewItems].split('-');
                            var year = selected[2];
                            var month = selected[1];
                            var day = selected[0];                    
                            timefields += '<input type="hidden" name="dateList[' + i + '].startTimeHour" value="' + startTimeHour + '">';   
                            timefields += '<input type="hidden" name="dateList[' + i + '].startTimeMin" value="' + startTimeMin + '">';                                              
                            timefields += '<input type="hidden" name="dateList[' + i + '].endTimeHour" value="' + endTimeHour + '">';                                              
                            timefields += '<input type="hidden" name="dateList[' + i + '].endTimeMin" value="' + endTimeMin + '">';
                            timefields += '<input type="hidden" name="dateList[' + i + '].startDate_year" value="' + year + '">';                                              
                            timefields += '<input type="hidden" name="dateList[' + i + '].startDate_month" value="' + month + '">';                                              
                            timefields += '<input type="hidden" name="dateList[' + i + '].startDate_day" value="' + day + '">';                                                                              
                            timefields += '<input type="hidden" name="dateList[' + i + '].isRule" value="false">';   
                            timefields += '<input type="hidden" name="dateList[' + i + '].endDate_year" value="' + year + '">';                                              
                            timefields += '<input type="hidden" name="dateList[' + i + '].endDate_month" value="' + month + '">';                                              
                            timefields += '<input type="hidden" name="dateList[' + i + '].endDate_day" value="' + day + '">';
                            if (i > 0) {
                                timefields += '<input type="hidden" name="dateList[' + i + '].ruleType" value="several">';
                            }                    
                            $(this).remove();
                        }
                    }
                } catch(error) {
                    
                }
            });                    
            $("#several").append(timefields);            
        }
        return true;
    };

    var removeFields = function(){
        $("#additionalPriceHiddenFields").remove();
        $("#additionalPriceFields").remove();
        $("#priceTemplate").remove(); 
        $("[name^=rule]").remove();
    };
    var container = $("#jserrors");
    $("form").validate({
        onfocusout: false,
    	errorContainer: container,
    	errorLabelContainer: $("ol", container),
    	wrapper: 'li',
        submitHandler: function(form) {
            if (bindDateParams()) {
                removeFields();
                form.submit();
            }
            return true;
        }		
    });    
</jq:jquery>

