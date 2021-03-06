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
  Template for display an event and related items.

  User: Leanne Northrop
  Date: Feb 12, 2010,9:53:27 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" %>

<p class="${propval}Details">
    <label for="thing"><g:message code="${labelCode}"/></label>
    <g:textField name="${propval}" class="ui-corner-all ${propval}" style="display: inline;width:10em" minlength="4" value=""/>                     
    <button class="add" type="button">+</button>
</p>        
<g:each var="thing" in="${list}" status="i">
<p class="${propval}Existing">
    <input type="hidden" name='${listName}[${i}]._deleted' value='false'/>   
    ${thing}
    <button class="remove ${propval}Existing" type="button">-</button>
</p>      
</g:each>
<p id="${propval}Template" style="display:none;visibility:hidden;">
    <input type="hidden" name="_deleted" value="false">     
    <input type="hidden" name="${propval}">                        
    <button name="button" class="remove ${propval}New" type="button">-</button>
</p>

<g:javascript>            
    var ${propval}NextId = ${nextId};           
    var ${propval}RemoveNewHandler = function() {
        $(this).parent().remove();  
    };
    
    var ${propval}RemoveExistingHandler = function() {
        $(this).parent().find(":hidden[name$='_deleted']").each(function(index){
            var clone = $(this).clone(false);
            clone.val('true');
            $(this).parents("form").append(clone);
            $(this).parent().remove();  
        });
    };
    
    var ${propval}AddNew = function(templateSelector,namePrefix,selector) {                                                             
      var clone = $(templateSelector).clone(true)    
      clone.find(':hidden').each(function(index) {
          var currName = $(this).attr('name');
          if (currName != "button") {
              var value = $(selector + ' .'+currName).val()
              $(this).val(value);
          }
          $(this).attr('name', namePrefix + '[' + ${propval}NextId + '].' + currName);
      });
      clone.removeAttr('id')
      clone.removeAttr('style')
      $(templateSelector).parent().append(clone);  
      ${propval}NextId++;
      return clone;
    };
    
    $("button.${propval}New").click(${propval}RemoveNewHandler); 
    $("button.${propval}Existing").click(${propval}RemoveExistingHandler);    
    $("p.${propval}Details button.add").click(function() {
       var name = $('p.${propval}Details input').val();            
       var newHtml = ${propval}AddNew('#${propval}Template', '${listName}','p.${propval}Details'); 
       newHtml.find('button').before(name);
    });             
</g:javascript>
