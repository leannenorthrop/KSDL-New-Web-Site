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
<span class="existing">
${link}
<input type="hidden" name='links[${index}]._deleted' value='false'/>
<input type="hidden" name='links[${index}].position' value='${index}'/>        
<button class="remove" type="button">-</button>
<button class="up" type="button">Up</button>
<button class="down" type="button">Down</button>
</span>