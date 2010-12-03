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
<html>
  <head>
    <title><g:message code="footer.site.map"/></title>
    <meta name="layout" content="main">
  </head>
  <body>
    <div class="grid_16 class" id="site-map">
      <ul class="menu">
        <li>
        <g:link controller="home" action="index" class="menuitem"><g:message code="home"/></g:link>
        <ul class="submenu">
          <li>
          <g:link controller="home" action="slideshow" class="menuitem"><g:message code="link.home.slideshow"/></g:link>
          </li>
          <li>
          <g:link controller="home" action="feed"><g:message code="link.home.feed"/></g:link>
          </li>
          <li>
          <g:link controller="home" action="calendars" class="menuitem"><g:message code="link.home.calendars"/></g:link>
          </li>
        </ul>
        </li>
        <li>
        <g:link controller="aboutUs" action="index" class="menuitem"><g:message code="aboutUs"/></g:link>
        <ul class="submenu">
          <li>
          <g:link controller="aboutUs" action="visiting" class="menuitem"><g:message code="footer.visit.us"/></g:link>
          </li>
          <li>
          <g:link controller="aboutUs" action="contactUs" class="menuitem"><g:message code="footer.contact.us"/></g:link>
          </li>
          <li>
          <g:link controller="aboutUs" action="teachers" class="menuitem"><g:message code="teacher.heading.title"/></g:link>
          </li>
        </ul>
        </li>
        <li>
        <g:link controller="news" action="home" class="menuitem"><g:message code="news"/></g:link>
        <ul class="submenu">
          <li>
          <g:link controller="news" action="current" class="menuitem"><g:message code="news.current.title"/></g:link>
          </li>
          <li>
          <g:link controller="news" action="archived" class="menuitem"><g:message code="news.archived.title"/></g:link>
          </li>
        </ul>
        </li>
        <li>
        <g:link controller="event" action="home" class="menuitem"><g:message code="event"/></g:link>
        <ul class="submenu">
          <li>
          <g:link controller="event" action="current" class="menuitem"><g:message code="event.this.month"/></g:link>
          </li>
          <li>
          <g:link controller="event" action="future" class="menuitem"><g:message code="home.events"/></g:link>
          </li>
          <li>
          <g:link controller="event" action="regular" class="menuitem"><g:message code="event.regular"/></g:link>
          </li>
          <li>
          <g:link controller="event" action="calendar" class="menuitem"><g:message code="event.regular"/></g:link>
          </li>
        </ul>
        </li>
        <li>
        <g:link controller="meditation" action="home" class="menuitem"><g:message code="meditation"/></g:link>
        <ul class="submenu">
          <li>
          <g:link controller="meditation" action="all" class="menuitem"><g:message code="meditation.articles.title"/></g:link>
          </li>
          <li>
          <g:link controller="meditation" action="events" class="menuitem"><g:message code="event.meditation"/></g:link>
          </li>
          <li>
          <g:link controller="meditation" action="slideshow" class="menuitem"><g:message code="link.meditation.slideshow"/></g:link>
          </li>
        </ul>
        </li>
        <li>
        <g:link controller="community" action="home" class="menuitem"><g:message code="community"/></g:link>
        <ul class="submenu">
          <li>
          <g:link controller="community" action="list" class="menuitem"><g:message code="community.articles.heading"/></g:link>
          </li>
          <li>
          <g:link controller="community" action="events" class="menuitem"><g:message code="event.community"/></g:link>
          </li>
        </ul>
        </li>
        <li>
        <g:link controller="wellbeing" action="home" class="menuitem"><g:message code="wellbeing"/></g:link>
        <ul class="submenu">
          <li>
          <g:link controller="wellbeing" action="list" class="menuitem"><g:message code="wellbeing.articles.heading"/></g:link>
          </li>
          <li>
          <g:link controller="wellbeing" action="events" class="menuitem"><g:message code="event.wellbeing"/></g:link>
          </li>
        </ul>
        </li>
        <li>
        <g:link controller="buddhism" action="home" class="menuitem"><g:message code="buddhism"/></g:link>
        <ul class="submenu">
          <li>
          <g:link controller="buddhism" action="list" class="menuitem"><g:message code="buddhism.articles.title"/></g:link>
          </li>
          <li>
          <g:link controller="buddhism" action="events" class="menuitem"><g:message code="event.buddhism"/></g:link>
          </li>
          <li>
          <g:link controller="buddhism" action="slideshow" class="menuitem"><g:message code="link.buddhism.slideshow"/></g:link>
          </li>
          <li>
          <g:link controller="buddhism" action="teachers" class="menuitem"><g:message code="teacher.heading.title"/></g:link>
          </li>
        </ul>
        </li>
      </ul>
    </div>
  </body>
</html>
