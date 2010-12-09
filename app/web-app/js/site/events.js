/*
 * Copyright © 2010 Leanne Northrop
 *
 * This file is part of Samye Content Management System.
 *
 * Samye Content Management System is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * Samye Content Management System is distributed in the hope that it will be
 * useful,but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Samye Content Management System.
 * If not, see <http://www.gnu.org/licenses/>.
 *
 * BT plc, hereby disclaims all copyright interest in the program
 * “Samye Content Management System” written by Leanne Northrop.
 */
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
        } else {                           
            var year;
            var month;
            var day;    
            if (type == 'between') {                                        
                var selected = $("#between #endingOnDate").datepicker( 'getDate' );
                year = $.datepicker.formatDate('yy', selected);
                month = $.datepicker.formatDate('mm', selected);
                day = $.datepicker.formatDate('dd', selected);
                                    
                timefields += '<input type="hidden" name="dateList[0].endDate_year" value="' + year + '">';                                              
                timefields += '<input type="hidden" name="dateList[0].endDate_month" value="' + month + '">';                                              
                timefields += '<input type="hidden" name="dateList[0].endDate_day" value="' + day + '">';                    
                
                var selected = $("#between #startingOnDate").datepicker( 'getDate' );
                year = $.datepicker.formatDate('yy', selected);
                month = $.datepicker.formatDate('mm', selected);
                day = $.datepicker.formatDate('dd', selected);                    
            } else {
                var selected = $("#always #startingFromDate").datepicker( 'getDate' );
                year = $.datepicker.formatDate('yy', selected);
                month = $.datepicker.formatDate('mm', selected);
                day = $.datepicker.formatDate('dd', selected);
                                    
                timefields += '<input type="hidden" name="dateList[0].endDate_year" value="' + year + '">';                                              
                timefields += '<input type="hidden" name="dateList[0].endDate_month" value="' + month + '">';                                              
                timefields += '<input type="hidden" name="dateList[0].endDate_day" value="' + day + '">';                    
            }
            
            timefields += '<input type="hidden" name="dateList[0].isRule" value="true">';                                   
            timefields += '<input type="hidden" name="dateList[0].startDate_year" value="' + year + '">';                                              
            timefields += '<input type="hidden" name="dateList[0].startDate_month" value="' + month + '">';                                              
            timefields += '<input type="hidden" name="dateList[0].startDate_day" value="' + day + '">';                                                                              
            timefields += '<input type="hidden" name="dateList[0].startTimeHour" value="' + startTimeHour + '">';   
            timefields += '<input type="hidden" name="dateList[0].startTimeMin" value="' + startTimeMin + '">';                                              
            timefields += '<input type="hidden" name="dateList[0].endTimeHour" value="' + endTimeHour + '">';                                              
            timefields += '<input type="hidden" name="dateList[0].endTimeMin" value="' + endTimeMin + '">';
                            
            var tabs = $('#' + type + ' #' + type + 'RuleType').tabs();
            var selected = tabs.tabs('option', 'selected');
            switch(selected)
            {
                // Daily
                case 0:
                  var interval = $('#' + type + 'RuleType #ruledailyinterval option:selected').val();                                    
                  timefields += '<input type="hidden" name="dateList[0].modifierType" value="D">';  
                  timefields += '<input type="hidden" name="dateList[0].modifier" value="">';                        
                  timefields += '<input type="hidden" name="dateList[0].interval" value="' + interval + '">';                                              
                  break;
                  
                // Weekly                    
                case 1:
                  var interval = $('#' + type + 'RuleType #ruleweeklyinterval option:selected').val(); 
                  var modifier = '';
                  $('#' + type + 'Weekly input:checked').each(function(index) {
                      var n = $(this).attr('name');
                      var m = n.substring(n.length-2);
                      modifier += m + ' ';
                  });
                  modifier = modifier.trim();
                  
                  timefields += '<input type="hidden" name="dateList[0].modifierType" value="W">';                                               
                  timefields += '<input type="hidden" name="dateList[0].interval" value="' + interval + '">';                        
                  timefields += '<input type="hidden" name="dateList[0].modifier" value="' + modifier + '">';                       
                  break;
                  
                // Monthly
                case 2:
                  var interval = $('#' + type + 'RuleType #rulemonthlyinterval option:selected').val();                                    
                  var modifier = '';  
                  var names = ['one','two']; 
                  var i = 0;
                  for (i=0; i < names.length; i++) {
                      var name = names[i];
                      var weekInterval = $('#' + type + 'Monthly #rulemonthly' + name + 'interval').val();
                      weekInterval = (weekInterval == 5) ? '1-' : '' + weekInterval + '+';
                      $('#' + type + 'Monthly input:checked').each(function(index) {
                          var n = $(this).attr('name');
                          if (n.match(name)) {
                              var m = n.substring(n.length-2);
                              modifier += weekInterval + ' ' + m + ' ';
                          }
                      });
                  }
                  modifier = modifier.trim();
                                                           
                  timefields += '<input type="hidden" name="dateList[0].modifierType" value="MD">';  
                  timefields += '<input type="hidden" name="dateList[0].interval" value="' + interval + '">';                        
                  timefields += '<input type="hidden" name="dateList[0].modifier" value="' + modifier + '">';                                             
                  break;
                default:
                  break;
            }
            $('#' + type + ' #' + type + 'RuleType').append(timefields); 
        }     
    } else if (type == 'several'){           
        var thedates = [];
                                
        // For each of newly added numbers add to date array                        
        $("#several p input:hidden").each(function(index) {
            var n = $(this).attr('name');
            if (n.match('^dateList') && n.match('startDate')) {
                var s = $(this).val();
                thedates.push(s);
            }
        });

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

$("form").validate({
    onfocusout: false,
    submitHandler: function(form) {
        if (bindDateParams()) {
            removeFields();
            form.submit();
        }
        return true;
    }		
}); 

$("#image\\.id").change(function() {
    var src = $("option:selected", this).val();
    var href = imagehref;
    $("#thumb_image").attr("srcid",src);
    $("#thumb_image").attr("src",href.replace('0',src));
}); 

var defaultDate = new Date(start[2],start[1]-1,start[0]);     // Bug Fix: 6985885
var endDate = new Date(end[2],end[1],end[0]);

$("#singleDate").datepicker({
    showOtherMonths: false,
    dateFormat: 'dd-mm-yy',
    defaultDate: defaultDate,
    numberOfMonths: [1,3],
    hideIfNoPrevNext: true,
    minDate: '0d',
    maxDate: '+3y',
    stepMonths: 3,
    currentText: 'Today'
});

$("#several * #startDate,#between #startingOnDate,#between #endingOnDate,#always #startingFromDate").each(function(index){
      $(this).datepicker({
        dateFormat: 'dd-mm-yy',
        defaultDate: defaultDate,
        minDate: '0d',
        maxDate: '+3y',
        hideIfNoPrevNext: true,
        currentText: 'Today'          
    });
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

$("#startTimeHour").change(function() {
  var stv = $(this).val();
  $("#endTimeHour").attr("value", ++stv);
});

var html = $("#additionalPriceHiddenFields").html();
$("#priceTemplate").append(html);
var html2 = $("#additionalPriceFields").html(); 
$("p.priceDetails button.add").before(html2); 

