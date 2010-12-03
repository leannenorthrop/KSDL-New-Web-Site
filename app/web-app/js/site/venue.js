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

var removeNewHandler = function() {
    $(this).parent().remove();  
};

$("button.newnumber").click(removeNewHandler); 
$("button.newemail").click(removeNewHandler);
$("button.newaddress").click(removeNewHandler);

var removeExistingHandler = function() {
    var deleteMe = $(this).parent().find(':hidden')
    deleteMe.val('true')
    $('#edit').append(deleteMe)
    $(this).parent().remove();  
};

$("button.existingnumber").click(removeExistingHandler);
$("button.existingemail").click(removeExistingHandler);                     
$("button.existingaddress").click(removeExistingHandler);  

$("#edit").validate();

var addNew = function(templateSelector,namePrefix,selector,nextId) {                                                                   
  var clone = $(templateSelector).clone(true)
  clone.find(':hidden').each(function(index) {
      var currName = $(this).attr('name');
      if (currName != "button" && currName != "type") {
          var value = $(selector + ' input.'+currName).val()
          $(this).val(value)
      } else if (currName == "type") {
          var type= $(selector + ' select').val()
          $(this).val(type)
      } 
      else {
          var name = $(selector + ' input.name').val()
          $(this).before("<input type='hidden' name='" + namePrefix + "[" + nextId + "].name' value='" + name + "'/>");
      }
      $(this).attr('name', namePrefix + '[' + nextId + '].' + currName);
  });
  clone.removeAttr('id')
  clone.removeAttr('style')
  $(templateSelector).parent().append(clone);  
  nextId++;
  return clone;
};

$("#addNewNumber").click(function() {
   var name = $('.telephoneDetails input.name').val() + " (" + $('.telephoneDetails select').val() + "): " + $('.telephoneDetails input.number').val();            
   var newHtml = addNew('#telephoneNumberTemplate', 'telephoneNumbersList','.telephoneDetails',nextTelephoneId); 
   newHtml.find('button').before(name)
});

$("#addNewEmail").click(function() {
   var name = $('.emailDetails input.name').val() + " (" + $('.emailDetails select').val() + "): " + $('.emailDetails input.address').val();             
   var newHtml = addNew('#emailTemplate','emailsList','.emailDetails',nextEmailId); 
   newHtml.find('button').before(name)           
});

$("#addNewAddress").click(function() {
   var name = $('.addressDetails input.name').val() + " (" + $('.addressDetails select').val() + "): ";
   $('.addressDetails input').each(function(index) { 
       if (index != 0) {
           name += $(this).val() + ' '
       }
   })
   var newHtml = addNew('#addressTemplate','addressesList','.addressDetails',nextAddressId); 
   newHtml.find('button').before(name)           
});                
