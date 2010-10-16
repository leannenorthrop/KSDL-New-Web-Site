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
  Template for displaying shop menu
  User: Leanne Northrop
  Date: Jun 14, 2010, 3:51:06 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<div id="shopmenu" class="box">
<h3><g:message code="shop.menu.title"/></h3>
${menu?.encodeAsTextile()}
<g:javascript>
var urlBase = '${createLink(controller: "shop", action: "displayAll")}';
$("#shopmenu ul:first").addClass('menu');
$("#shopmenu li span").wrap("<a href='#'/>");
$("#shopmenu li a").addClass('menuitem').each(function() {
    $(this).attr('href', urlBase+'/'+$(this).text());
});

// Style Menu
$('#shopmenu li').css('position','relative');
$(".menu li:has(ul)").addClass('submenu'); 
$(".menu li:has(ul) li:has(ul)").addClass('subsubmenu').removeClass('submenu');
$(".menu li a").addClass('level0');
$(".menu li:has(ul) li a").removeClass('level0').addClass('level1');
$(".menu li:has(ul) li:has(ul) li a").removeClass('level0').removeClass('level1').addClass('level2');
$("#shopmenu li a").addClass('menuitem').each(function() {
    var href = $(this).attr('href');
    $(this).attr('href', href + '?level=' + $(this).attr('class').substring(14));
});

// Insert Toggle Button
$(".submenu").prepend('<span class="toggle_hidden"></span>');
$(".subsubmenu").prepend('<span class="toggle_hidden"></span>');
$('.toggle_hidden').click(function() {
    $(this).toggleClass('toggle_visible').toggleClass('toggle_hidden');
    if ($(this).parent().hasClass('subsubmenu')){
        $(this).parent().children('ul:first').toggle('slow');
    } else {
        $(this).parent().children('ul:first').toggle('slow');
    }
    return false;
});
$('.submenu ul').hide();
$('.subsubmenu ul').hide();
</g:javascript>
</div>