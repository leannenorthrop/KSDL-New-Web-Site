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
<html>
  <head>
    <title><g:message code="wellbeing"/></title>
    <meta name="layout" content="main">
  </head>
  <body>
    <div class="grid_12">
      <g:render template="/toparticles" model="[articles:homeArticles]"/>
      <g:render template="/eventlist" model="[events: featuredEvents,heading:'event.wellbeing',total:totalAllEvents]"/>
    </div>
    <div class="grid_4">  
      <g:render template="/articlelist" model="[articles:featuredArticles,controller:'wellbeing',action:'view',total:totalAllArticles,moreAction:'list',heading:'wellbeing.articles.heading']"/>
      <g:render template="/articlelist" model="[articles:therapists, heading: 'teacher.category.T', controller: 'wellbeing', action:'therapist']"/>
    </div>
  </body>
</html>
