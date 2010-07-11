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

<p class="details">
    <label for="thing"><g:message code="${labelCode}"/></label>
    <g:textField name="thing" class="ui-corner-all ${propval}" style="display: inline;width:10em" minlength="4" value=""/>                     
    <button class="add" type="button">+</button>
</p>        
<g:each var="number" in="${list}" status="i">
<p class="existing">
    <input type="hidden" name='${listName}[${i}]._deleted' id='${listName}[${i}]._deleted' value='false'/>
    ${number}
    <button class="remove existing" type="button">-</button>
</p>      
</g:each>
<p id="${propval}Template" style="display:none;visibility:hidden;">
    <input type="hidden" name="_deleted" value="false">     
    <input type="hidden" name="${propval}">                        
    <button name="button" class="remove new" type="button">-</button>
</p>

<g:javascript>            
    var nextId = ${nextId};           
    var removeNewHandler = function() {
        $(this).parent().remove();  
    };
    
    var removeExistingHandler = function() {
        var deleteMe = $(this).parent().find(':hidden')
        deleteMe.val('true')
        $(this).parents("form").append(deleteMe)
        $(this).parent().remove();  
    };
    
    var addNew = function(templateSelector,namePrefix,selector) {                                                                
      var clone = $(templateSelector).clone(true)
      clone.find(':hidden').each(function(index) {
          var currName = $(this).attr('name');
          if (currName != "button") {
              var value = $(selector + ' input.'+currName).val()
              $(this).val(value)
          }
          $(this).attr('name', namePrefix + '[' + nextId + '].' + currName);
      });
      clone.removeAttr('id')
      clone.removeAttr('style')
      $(templateSelector).parent().append(clone);  
      nextId++;
      return clone;
    };
    
    $("button.new").click(removeNewHandler); 
    $("button.existing").click(removeExistingHandler);    
    $("p.details button.add").click(function() {
       var name = $('p.details input').val();            
       var newHtml = addNew('#${propval}Template', '${listName}','p.details'); 
       newHtml.find('button').before(name);
    });             
</g:javascript>