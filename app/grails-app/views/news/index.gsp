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
News front page
User: Leanne
Date: Jan 24, 2010, 2:00:21 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <title><g:message code="news.heading"/></title>
  <feed:meta kind="rss" version="2.0" controller="feed" action="news"/>
  <meta name="layout" content="main">
</head>
<body>
  <div class="grid_12">
    <g:render template="/articlelist" model="[articles:homeArticles,controller:'news', action: 'view', total: total,moreAction:'current', heading: 'news.current.title']"/>
  </div>
  <div class="grid_4">
    <g:render template="/shortnewslist" model="[articles: allArticles, heading: 'archive.news.heading']"/>
  </div>
</body>
</html>
