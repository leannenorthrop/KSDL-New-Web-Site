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
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.samye.dzong.london.venue.Venue" %>
<html>
  <head>
    <meta name="layout" content="content-admin"/>
    <title><g:message code="venue.edit.title" args="${[venue?.name]}"/></title>
    <g:javascript>            
      var nextTelephoneId = ${venue.telephoneNumbersList.size()};
      var nextEmailId = ${venue.emails.size()};
      var nextAddressId = ${venue.addresses.size()};            
      $(function() {
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
      });
    </g:javascript>
  </head>
  <body>
    <g:form name="edit" method="post" action="update">

      <g:hiddenField name="id" value="${venue?.id}"/>
      <g:hiddenField name="version" value="${venue?.version}"/>
      <g:hiddenField name="publishState" value="Published"/>
      <g:hiddenField name="deleted" value="${venue?.deleted}"/>
      <g:hiddenField name="displayAuthor" value="${venue?.displayAuthor}"/>
      <g:hiddenField name="displayDate" value="${venue?.displayDate}"/>
      <g:hiddenField name="category" value="V"/>
      <fieldset>
            <legend><g:message code="details"/></legend>
      <p>
        <label for="name"><g:message code="venue.name.label"/></label>
        <g:textField name="name" value="${fieldValue(bean:venue,field:'name')}" class="required ui-corner-all ${hasErrors(bean:venue,field:'name','errors')}" minlength="5"/>
      </p>
      <p>
        <label for="image.id"><g:message code="venue.image.label"/></label>
        <g:set var="noImgLabel"><g:message code="no.img"/></g:set>
        <g:select from="${org.samye.dzong.london.media.Image.findAllByTag('venue')}" name="image.id" value="${venue?.image?.id}" noSelection="${['null':noImgLabel]}" optionKey="id" optionValue="name"/>
      </p>
      <p>
        <label for="facilities"><g:message code="venue.facilities.label"/></label>
        <g:textArea rows="5" cols="40" name="facilities" class="required ui-corner-all ${hasErrors(bean:venue,field:'facilities','errors')}" value="${venue.facilities}" minlength="5"/>
      </p>      
      <p>
        <label for="access"><g:message code="venue.access.label"/></label>
        <g:textArea rows="5" cols="40" name="access" class="required ui-corner-all ${hasErrors(bean:venue,field:'access','errors')}" value="${venue.access}" minlength="5"/>
      </p>      
      </fieldset>
      <fieldset>
        <legend><g:message code="contact.telephone.numbers"/></legend>
        <p class="telephoneDetails">
            <label for="telephoneNumber"><g:message code="contact.telephone.new"/></label>
            <g:select from="${['main','work','home','fax','mobile','other']}" valueMessagePrefix="contact"></g:select> 
            <g:textField name="contactName" class="ui-corner-all name" style="display: inline;width:10em" minlength="4" value="Contact Name"/>           
            <g:textField name="telephoneNumber" class="ui-corner-all number" style="display: inline;width:20em" minlength="8" number="true" value="111111111111"/>           
            <button id="addNewNumber" class="add" type="button">+</button>
        </p>        
        <g:each var="number" in="${venue.telephoneNumbers}" status="i">
        <p class="telephoneNumbers">
            <input type="hidden" name='telephoneNumbersList[${i}]._deleted' id='telephoneNumbersList[${i}]._deleted' value='false'/>
            ${number}
            <button class="remove existingnumber" type="button">-</button>
        </p>      
        </g:each>
        <p id="telephoneNumberTemplate" style="display:none;visibility:hidden;">
            <input type="hidden" name="_deleted" value="false">
            <input type="hidden" name="type">            
            <input type="hidden" name="number">                        
            <button name="button" class="remove newnumber" type="button">-</button>
        </p>        
      </fieldset> 
      <fieldset>
        <legend><g:message code="contact.emails"/></legend>
        <p class="emailDetails">
            <label for="email"><g:message code="contact.email.new"/></label>
            <g:select from="${['main','work','home','other']}" valueMessagePrefix="contact"></g:select> 
            <g:textField name="contactName" class="ui-corner-all name" style="display: inline;width:10em" minlength="4" value="Contact Name"/>           
            <g:textField name="email" class="ui-corner-all address" style="display: inline;width:20em" minlength="8" email="true" value="somewhere@overtherainbow.com"/>           
            <button id="addNewEmail" class="add" type="button">+</button>
        </p>        
        <g:each var="email" in="${venue.emails}" status="i">
        <p class="emails">
            <input type="hidden" name='emailsList[${i}]._deleted' id='emailsList[${i}]._deleted' value='false'/>
            ${email}
            <button class="remove existingemail" type="button">-</button>
        </p>       
        </g:each>        
        <p id="emailTemplate" style="display:none;visibility:hidden;">
            <input type="hidden" name="_deleted" value="false">
            <input type="hidden" name="type">            
            <input type="hidden" name="address">                        
            <button name="button" class="remove newemail" type="button">-</button>
        </p>        
      </fieldset> 
      <fieldset>
        <legend><g:message code="contact.addresses"/></legend>
        <div class="addressDetails">
            <p style="margin-bottom:0;">
                <label for="email"><g:message code="contact.address.new"/></label>
                <g:select from="${['main','work','home','other']}" valueMessagePrefix="contact"></g:select> 
                <g:textField name="contactName" class="ui-corner-all name" style="width:10em" minlength="4" value="Contact Name"/>       
            </p>
            <p style="margin-bottom:0;">
                <label for="placename" style="display:inline-block;width:8em"><g:message code="contact.address.placename"/></label>
                <g:textField name="placename" class="ui-corner-all placeName" style="width:20em" minlength="4"/>
            </p>
            <p style="margin-bottom:0;">
                <label for="streetnumber" style="display:inline-block;width:8em"><g:message code="contact.address.streetnumber"/></label>
                <g:textField name="streetnumber" class="ui-corner-all streetNumber" style="width:20em" minlength="4"/>                
            </p>
            <p style="margin-bottom:0;">
                <label for="line1" style="display:inline-block;width:8em"><g:message code="contact.address.line1"/></label>
                <g:textField name="line1" class="ui-corner-all line1" style="width:20em" minlength="4"/>                
            </p>
            <p style="margin-bottom:0;">
                <label for="line2" style="display:inline-block;width:8em"><g:message code="contact.address.line2"/></label>
                <g:textField name="line2" class="ui-corner-all line2" style="width:20em"/>                
            </p>
            <p style="margin-bottom:0;">
                <label for="posttown" style="display:inline-block;width:8em"><g:message code="contact.address.posttown"/></label>
                <g:textField name="posttown" class="ui-corner-all postRown" style="width:20em"/>                
            </p>
            <p style="margin-bottom:0;">
                <label for="county" style="display:inline-block;width:8em"><g:message code="contact.address.county"/></label>
                <g:textField name="county" class="ui-corner-all county" style="width:20em"/>                
            </p>
            <p style="margin-bottom:0;">
                <label for="country" style="display:inline-block;width:8em"><g:message code="contact.address.country"/></label>
                <g:textField name="country" class="ui-corner-all country" style="width:20em"/>                
            </p> 
            <p style="margin-bottom:0;">
                <label for="postcode" style="display:inline-block;width:8em"><g:message code="contact.address.postcode"/></label>
                <g:textField name="postcode" class="ui-corner-all postCode" style="width:20em"/>                
            </p>                                   
                                                                     
            <button id="addNewAddress" class="add" type="button">+</button>
        </div>   
        <g:each var="address" in="${venue.addresses}" status="i">
        <p class="addresses">
            <input type="hidden" name='addressesList[${i}]._deleted' id='addressesList[${i}]._deleted' value='false'/>
            ${address}
            <button class="remove existingaddress" type="button">-</button>
        </p>       
        </g:each>        
        <p id="addressTemplate" style="display:none;visibility:hidden;">
            <input type="hidden" name="_deleted" value="false">
            <input type="hidden" name="type">            
            <input type="hidden" name="placeName">                        
            <input type="hidden" name="streetNumber">            
            <input type="hidden" name="line1">                        
            <input type="hidden" name="line2"> 
            <input type="hidden" name="postTown">                        
            <input type="hidden" name="county">            
            <input type="hidden" name="country">                        
            <input type="hidden" name="postCode">                                               
            <button name="button" class="remove newemail" type="button">-</button>
        </p>             
      </fieldset>  
      <fieldset>
        <legend>Content</legend>
        <g:render template="/contentWithPreview" model="[previewController: 'manageSite',publishableInstance:venue]"/>
        <p class="last">&nbsp;</p>
        <g:set var="submitBtnLabel"><g:message code="updatearticle.btn"/></g:set>
        <g:submitButton name="submitbtn" value="${submitBtnLabel}" id="submitbtn" class="ui-corner-all"/>
      </fieldset>                    
    </g:form>
  </body>
</html>
