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
<%@ page import="org.samye.dzong.london.venue.Room" %>
<%@ page import="org.samye.dzong.london.venue.Venue" %>
<html>
  <head>
    <meta name="layout" content="content-admin"/>
    <title><g:message code="venue.create.title"/></title>
    <g:javascript>
      var roomsCount = 0;
      var nextRoomId = roomsCount;

      function removeRoom(elem) {
        var li = $(elem).parent();
        li.remove();
        roomsCount -= 1;
      };

      function addRoom() {
        var namePrefix = "rooms[" + nextRoomId + "]";
        var liElem = $("#roomClone").clone(true);
        liElem.attr({id:"room" + nextRoomId});
        liElem.find('.name input').attr({name: namePrefix + ".name"});
        liElem.find('.image select').attr({name: namePrefix + ".image.name"});
        liElem.find('.description textarea').attr({name: namePrefix + ".description"});
        liElem.find('input.makeRoomPublic').attr({name: namePrefix + ".makePublic"});
        liElem.find('input.deleted').attr({name: namePrefix + ".deleted"});
        liElem.find('input.publish').attr({name: namePrefix + ".publishState"});
        $("#rooms").append(liElem);
        roomsCount += 1;
        nextRoomId += 1;
      }

      $(function() {
        $("#details").tabs();
        $('#addRoom').click(function() {
          addRoom();
        });
      });
    </g:javascript>
  </head>
  <body>
    <g:form action="save" method="post">
      <h1 class="ui-widget-header"><g:message code="venue.create.title"/></h1>

      <g:if test="${flash.message && !flash.isError}">
        <p class="ui-widget ui-state-highlight ui-corner-all">
          <strong><span class="ui-icon ui-icon-info" style="display: inline-block"></span><g:message code="info"/></strong><g:message code="${flash.message}" args="${flash.args}" default="${flash.default}"/>
        </p>
      </g:if>
      <g:elseif test="${flash.isError}">
        <g:set var="errorsList"><g:renderErrors bean="${teacher}" as="list"></g:renderErrors></g:set>
        <div class="ui-widget ui-state-error ui-corner-all">
          <strong>
            <span class="ui-icon ui-icon-alert" style="display: inline-block"></span><g:message code="alert"></g:message>
          </strong>
          <g:message code="${flash.message}" args="${flash.args}" default="${flash.default}"></g:message>
          ${errorsList}
        </div>
      </g:elseif>


      <input type="hidden" name="publishState" value="Unpublished"/>
      <input type="hidden" name="deleted" value="false"/>

      <fieldset>
        <label for="name"><g:message code="venue.name.label"/></label>
        <input type="text" id="name" name="name" value="${fieldValue(bean: venueInstance, field: 'name')}"/>
      </fieldset>
      <fieldset>
        <label for="image.id"><g:message code="venue.image.label"/></label>
        <g:select optionKey="id" from="${org.samye.dzong.london.media.Image.list()}" name="image.id" value="${venueInstance?.image?.name}"></g:select>
      </fieldset>
      <fieldset>
        <label for="description"><g:message code="venue.description.label"/></label>
        <g:textArea name="description" value="${fieldValue(bean:venueInstance,field:'description')}" rows="5" cols="40"/>
      </fieldset>
      <fieldset>
        <label for="facilities"><g:message code="venue.facilities.label"/></label>
        <g:textArea name="facilities" value="${fieldValue(bean:venueInstance,field:'facilities')}" rows="5" cols="40"/>
      </fieldset>
      <fieldset>
        <label for="access"><g:message code="venue.access.label"/></label>
        <g:textArea name="access" value="${fieldValue(bean:venueInstance,field:'access')}" rows="5" cols="40"/>
      </fieldset>
      <div id="details">
        <ul>
          <li><a href="#addresses">Addresses</a></li>
          <li><a href="#telephoneNumbers">Telephone Numbers</a></li>
          <li><a href="#emails">Emails</a></li>
          <li><a href="#visitingUs">Visiting Us</a></li>
          <li><a href="#rooms">Rooms</a></li>
        </ul>
        <div id="addresses" style="min-height: 15em">

        </div>
        <div id="telephoneNumbers" style="min-height: 15em">

        </div>
        <div id="emails" style="min-height: 15em">

        </div>
        <div id="visitingUs" style="min-height: 15em">

        </div>
        <div id="rooms" style="height: 15em">
          <a href="#" id="addRoom"><span class="ui-icon ui-icon-plus"/>Add Room</a>
          <ul id="rooms">
            <g:each in="${venueInstance.rooms}" status="j" var="roomInstance">
              <g:set var="namePrefix" value="${'rooms[' + j + ']'}"/>
              <li>
                <fieldset>
                  <label for="${namePrefix}.name">Name</label>
                  <input type="text" name="${namePrefix}.name" value="${fieldValue(bean: roomInstance, field: 'name')}"/>
                </fieldset>
                <fieldset>
                  <label for="${namePrefix}.image.name">Image</label>
                  <g:select optionKey="id" from="${org.samye.dzong.london.media.Image.list()}" name="${namePrefix}.image.name" value="${venueInstance?.image.name}"></g:select>
                </fieldset>
                <fieldset>
                  <label for="${namePrefix}.description">Description</label>
                  <g:textArea name="${namePrefix}.description" value="${fieldValue(bean:roomInstance,field:'description')}" rows="5" cols="40"/>
                </fieldset>
                <g:checkBox name="${namePrefix}.makePublic" value="${false}"/>
              </li>
            </g:each>
          </ul>
        </div>
      </div>
      <g:set var="submitBtnLabel"><g:message code="venue.create.submit.btn"/></g:set>
      <g:submitButton name="submitbtn" value="${submitBtnLabel}" id="submitbtn" class="ui-corner-all"/>
    </g:form>
    <div style="visibility:hidden;display:none;">
      <li id="roomClone" class="room">
        <fieldset class="name">
          <label>Name</label>
          <input type="text" value=""/>
        </fieldset>
        <fieldset class="description">
          <label>Description</label>
          <textArea rows="5" cols="40">&nbsp;</textarea>
        </fieldset>
        <input type="hidden" class="deleted" value="false"/>
        <input type="hidden" class="publish" value="Unpublished"/>
        <input type="checkBox" class="makeRoomPublic" value="${false}">Make Public</input>
        <a href="#" onClick="removeRoom(this);">Remove Room</a>
      </li>
    </div>
  </body>
</html>
