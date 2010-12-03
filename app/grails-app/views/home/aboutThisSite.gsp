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
    <title>About This Site</title>
    <meta name="layout" content="main">
  </head>
  <body>
    <div class="grid_16">
      <h3>Problems using this site?</h3>
      <p>&nbsp;</p>

      <h3>Credits</h3>
      <p>
        Content and design on this web-site is maintained by Kagyu Samye Dzong London Web group:
      <ul>
        <g:each var="i" in="${users}">
          <li>
          <g:if test="${i.profile?.image && i.profile?.mimeType?.endsWith('png')}">
            <img src="${createLink(controller: 'profile', action: 'src', id: i.id)}" title="${i.profile?.publicName}" alt="${i.profile?.publicName}" class="pngImg" style="max-width:100%"/>
          </g:if>
          <g:else>
            <img src="${createLink(controller: 'profile', action: 'src', id: i.id)}" title="${i.profile?.publicName}" alt="${i.profile?.publicName}" style="max-width:100%"/>
          </g:else>
          <span>${i}</span>
          </li>
        </g:each>
      </ul>
    </p>
    <p>
      The web-site is developed by:
    <ul>
      <g:each var="i" in="${developers}">
        <li>
        <g:if test="${i.profile?.image && i.profile?.mimeType?.endsWith('png')}">
          <img src="${createLink(controller: 'profile', action: 'src', id: i.id)}" title="${i.profile?.publicName}" alt="${i.profile?.publicName}" class="pngImg" style="max-width:100%"/>
        </g:if>
        <g:else>
          <img src="${createLink(controller: 'profile', action: 'src', id: i.id)}" title="${i.profile?.publicName}" alt="${i.profile?.publicName}" style="max-width:100%"/>
        </g:else>
        <span>${i}</span>
        </li>
      </g:each>
    </ul>
  </p>
  <p>
    The web-site is hosted by <a href="http://www.webintegrations.co.uk/">Web Integrations</a>.
  </p>
  <p>
    Many thanks go to the creators and maintainers of:
  <ul>
    <g:each var="i" in="${['Java', 'Groovy','Grails','jQuery','MooTools','Fluid 960','IconShock','TextMate','IntelliJ','Tomcat','Apache HTTPD']}">
      <li>${i}</li>
    </g:each>
  </ul>
</p>
</div>
</body>
</html>