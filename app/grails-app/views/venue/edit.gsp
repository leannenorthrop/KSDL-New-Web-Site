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
      $(function() {
        $("#edit").validate();
        $("#addNewNumber").click(function() {
          var type = $('.telephoneDetails select').val()
          var number = $('.telephoneDetails input.number').val()
          var name = $('.telephoneDetails input.name').val()
          var clone = $('#telephoneNumberTemplate').clone(true)
          clone.find(':hidden').each(function(index) {
              var currName = $(this).attr('name');
              if (currName == "type") {
                  $(this).val(type)
              } else if (currName == "number") {
                  $(this).val(number)                  
              } else if (currName == "button") {
                  $(this).before("<input type='hidden' name='telephoneNumbersList[" + nextTelephoneId + "].name' value='" + name + "'/>");
                  $(this).before(name + ' (' + type + '): ' + number);
              }
              $(this).attr('name', 'telephoneNumbersList[' + nextTelephoneId + '].' + currName);
          });
          clone.removeAttr('id')
          clone.removeAttr('style')
          $(this).parent().parent().append(clone);  
          nextTelephoneId++;
        });
        $("button.newnumber").click(function() {
          $(this).parent().remove();  
        }); 
        $("button.existingnumber").click(function() {
            var deleteMe = $(this).parent().find(':hidden')
            deleteMe.val('true')
            $('#edit').append(deleteMe)
            $(this).parent().remove();  
        });               
        $("#addNewEmail").click(function() {
          var type = $('.emailDetails select').val()
          var email = $('.emailDetails input.email').val()
          var name = $('.emailDetails input.name').val()
          var clone = $('#emailTemplate').clone(true)          
          clone.find(':hidden').each(function(index) {
              var currName = $(this).attr('name');
              if (currName == "type") {
                  $(this).val(type)
              } else if (currName == "address") {
                  $(this).val(email)                  
              } else if (currName == "button") {
                  $(this).before("<input type='hidden' name='emailsList[" + nextEmailId + "].name' value='" + name + "'/>");
                  $(this).before(name + ' (' + type + '): ' + email);
              }
              $(this).attr('name', 'emailsList[' + nextEmailId + '].' + currName);
          });
          clone.removeAttr('id')
          clone.removeAttr('style')
          $(this).parent().parent().append(clone);  
          nextEmailId++;
        });
        $("button.newemail").click(function() {
          $(this).parent().remove();  
        }); 
        $("button.existingemail").click(function() {
            var deleteMe = $(this).parent().find(':hidden')
            deleteMe.val('true')
            $('#edit').append(deleteMe)
            $(this).parent().remove();  
        });        
      });
    </g:javascript>
  </head>
  <body>
    <g:form name="edit" method="post" action="update">
      <g:render template="/messageBox" model="[flash: flash]"/>

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
            ${number.name} (<g:message code="contact.${number.type}"/>): ${number.number}
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
            <g:textField name="email" class="ui-corner-all email" style="display: inline;width:20em" minlength="8" email="true" value="somewhere@overtherainbow.com"/>           
            <button id="addNewEmail" class="add" type="button">+</button>
        </p>        
        <g:each var="email" in="${venue.emails}" status="i">
        <p class="emails">
            <input type="hidden" name='emailsList[${i}]._deleted' id='emailsList[${i}]._deleted' value='false'/>
            ${email?.name} (<g:message code="contact.${email?.type}"/>): ${email?.address}
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
        <legend>Addresses</legend>
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
