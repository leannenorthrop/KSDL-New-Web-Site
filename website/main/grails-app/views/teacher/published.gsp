<html>
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

<g:set var="titleLabel"><g:message code="teacher.name.label"/></g:set>
  <g:set var="lastUpdatedLabel"><g:message code="teacher.last.updated"/></g:set>
  <g:set var="publishedOnLabel"><g:message code="teacher.published.on"/></g:set>
  <g:set var="authorLabel"><g:message code="teacher.author.label"/></g:set>

  <body>
    <table>
      <thead>
        <tr>
          <g:sortableColumn property="title" title="${titleLabel}"/>
          <th><g:message code="teacher.category"/></th>
          <g:sortableColumn property="datePublished" title="${publishedOnLabel}"/>
          <g:sortableColumn property="lastUpdated" title="${lastUpdatedLabel}"/>
          <shiro:hasAnyRole in="['Editor','Administrator']">
            <g:sortableColumn property="author" title="${authorLabel}"/>
          </shiro:hasAnyRole>
          <th><g:message code="teacher.action.label"/></th>
        </tr>
      </thead>
      <tbody>
        <g:each in="${teachers}" status="i" var="teacher">
          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td>
              <g:link action="show" id="${teacher.id}">${teacher}</g:link>
            </td>
            <td>
              <g:each var="tag" in="${teacher.tags}">
                <g:if test="${tag == 'news'}">
                  <g:message code="teacher.category.${tag}"/>
                </g:if>
                <g:elseif test="${tag == 'meditation'}">
                  <g:message code="teacher.category.${tag}"/>
                </g:elseif>
                <g:elseif test="${tag == 'community'}">
                  <g:message code="teacher.category.${tag}"/>
                </g:elseif>
                <g:elseif test="${tag == 'wellbeing'}">
                  <g:message code="teacher.category.${tag}"/>
                </g:elseif>
              </g:each>
            </td>
            <td><g:formatDate format="dd-MM-yyyy HH:mm" date="${teacher?.datePublished}"/></td>
            <td><g:formatDate format="dd-MM-yyyy HH:mm" date="${teacher?.lastUpdated}"/></td>
            <shiro:hasAnyRole in="['Editor','Administrator']">
              <td>${fieldValue(bean: teacher, field: 'author')}</td>
            </shiro:hasAnyRole>
            <td>
              <shiro:hasAnyRole in="['Editor','Administrator']">
                <g:link action="changeState" params="[state:'Unpublished']" id="${teacher.id}"><g:message code="teacher.unpublish.action"/></g:link>
                <g:link action="changeState" params="[state:'Archived']" id="${teacher.id}"><g:message code="teacher.archive.action"/></g:link>
              </shiro:hasAnyRole>
            </td>
          </tr>
        </g:each>
      </tbody>
    </table>
    <div class="manage paginateButtons">
      <g:paginate total="${total}"/>
    </div>
  </body>
</html>
