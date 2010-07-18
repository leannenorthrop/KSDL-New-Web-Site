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
  User: Leanne Northrop
  Date: Feb 27, 2010, 5:07:05 PM
--%>

<%@ page import="org.samye.dzong.london.site.Link" contentType="text/html;charset=UTF-8" %>
<g:form name="${type}LinkListForm" action="${type}">
<fieldset>
    <p class="linkOptions">
        <label for="linkname"><g:message code="linkname.label" default="Public Name"/></label>
        <g:textField name="linkname" class="ui-corner-all name" style="display: inline;width:10em" minlength="4" value=""/>    
        <g:select name="linktype" from="${['I', 'E']}" valueMessagePrefix="link.type" class="type"/>
        <span id="E"  style="display:none;">      
            <label for="linkhref"><g:message code="linkname.href" default="Full URL"/></label>
            <g:textField name="linkhref" class="ui-corner-all href" style="display: inline;width:45em" minlength="4" value=""/>                           
        </span>
        <span id="I" style="display:inline;">
            <g:select name="controller" from="${controllers}" valueMessagePrefix="link.controller" class="controller"/>
            <g:select name="action" from="${[]}" class="action"/>        
        </span>
        <button class="add" type="button">+</button>
    </p>
    <ol class="linkMenuList" style="list-style-type:decimal">
    <g:each var="link" in="${links}" status="i">
        <li>
            <g:render template="/linkOptions" model="[link:link,index:i]"/>
        </li>
    </g:each>
    </ol>
    <g:submitButton name="create" class="ui-corner-all" value="${message(code: 'event.save.btn', default: 'Save Changes')}"/>
</fieldset>
</g:form>
<li id="newLinkTemplate" style="display:none;visibility:hidden;">
    <span class="new">
        <input type="hidden" name="_deleted" value="false">     
        <input type="hidden" name="type">                                
        <input type="hidden" name="name">                        
        <input type="hidden" name="href">                            
        <input type="hidden" name="controller">                                
        <input type="hidden" name="action">                                
        <input type="hidden" name="id">                  
        <input type="hidden" name="position">                                        
        <button name="button" class="remove linkNew" type="button">-</button>
        <button name="button" class="up" type="button">Up</button>
        <button name="button" class="down" type="button">Down</button>              
    </span>
</li>
<g:set var="actionoptions">
    var controllerActions = new Object();
<g:each var="action" in="${actions}">
    controllerActions['${action.key}'] = [<g:each var="a" in="${action.value}">{'name':'<g:message code="link.${action.key}.${a}"/>','value':'${a}'},</g:each>''];
</g:each>
</g:set>
<g:javascript> 
    ${actionoptions}
    var nextId = ${links.size()};
    var baseURL = "${resource(dir:'/')}";
    var updatePositions = function() {
        $("ol.linkMenuList input[name$=position]").each(function(index){
            $(this).val(index);
        });
    };
    
    $("#linktype").change(function() {
        $("#linktype option").each(function () {
            var id = $(this).val();
            $("#"+id).toggle();      
            $(".linkOptions span:visible").css("display","inline");      
        });
    });  
    $("#controller").change(function() {
        $("#action option").remove();
        var id = $(this).val();
        var options = controllerActions[id];
        for (var i in options)
        {
          if (options[i] != '') {
              var theoptions = options[i];
              $("#action").append("<option value='" + theoptions['value'] + "'>" + theoptions['name'] + "</option>");
          }
        }
    });
    $("#controller").change();
           
    $("#newLinkTemplate button.remove").click(function() {
        $(this).parent().parent().remove();  
        updatePositions();        
    }); 
    $(".linkMenuList button.remove").click(function() {
        var deleteMe = $(this).parent().find('input[name$=_deleted]');
        deleteMe.val('true');
        var a = deleteMe.detach();
        $(this).parent().parent().parent().parent().append(a);
        var deleteMe = $(this).parent().parent().remove();
        updatePositions();
    });    
    $(".linkOptions button.add").click(function() {
        var clone = $("#newLinkTemplate").clone(true)    
        clone.find(':hidden').each(function(index) {
            var currName = $(this).attr('name');
            if (currName != "button") {
                if (currName == 'position') {
                    $(this).val(nextId);
                } else {
                    var value = $('.linkOptions .'+currName).val()
                    $(this).val(value);
                }
            }
            $(this).attr('name',  'links[' + nextId + '].' + currName);
        });
        clone.removeAttr('id');
        clone.removeAttr('style');
        
        var linkName = $("#linkname").val();
        if ($("#linktype").val() == 'E') {
            clone.prepend("<a href='" + $("#linkhref").val() + "' target='_blank' style='display:inline-block;width:10em;'>" + linkName + "</a>");
        } else {
            clone.prepend("<a href='" + baseURL + $("#controller").val() + "/" + $("#action").val() + "' target='_blank' style='display:inline-block;width:10em;'>" + linkName + "</a>");
        }
        $(".linkMenuList").append(clone);  
        nextId++;
    });   
    
    var up = function() {
        var thisListItem = $(this).parent().parent();
        var previousListItem = thisListItem.prev("li");
        if (previousListItem.length != 0) {
            var tmp = thisListItem.detach();
            tmp.insertBefore(previousListItem);
            tmp.find('input[name$=.position]').each(function(index) {
                var currVal = parseInt($(this).val());
                $(this).val(currVal-1);
            });
            previousListItem.find('input[name$=.position]').each(function(index) {
                var currVal = parseInt($(this).val());
                $(this).val(currVal+1);
            });          
        }
    };
    
    var down = function() {
        var thisListItem = $(this).parent().parent();
        var nextListItem = thisListItem.next("li");
        if (nextListItem.length != 0) {
            var tmp = thisListItem.detach();
            tmp.insertAfter(nextListItem);
            tmp.find('input[name$=.position]').each(function(index) {
                var currVal = parseInt($(this).val());
                $(this).val(currVal+1);
            });
            nextListItem.find('input[name$=.position]').each(function(index) {
                var currVal = parseInt($(this).val());
                $(this).val(currVal-1);
            });            
        }
    };    
    $("ol.linkMenuList button.up").click(up);
    $("#newLinkTemplate button.up").click(up);
    $("ol.linkMenuList button.down").click(down);
    $("#newLinkTemplate button.down").click(down);    
    $("ol.linkMenuList a").each(function() {
        $(this).css("display","inline-block");
        $(this).css("width","10em");        
    });
</g:javascript>