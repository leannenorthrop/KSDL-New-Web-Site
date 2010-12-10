<div class="box">
    <h3><g:link controller="room" action="home"><g:message code="facilities" default="Facilities"/></g:link></h3>
    <ul class="menu">
      <li class="menuitem">
        <ul>
          <g:if test="${venues}">
            <g:each var="venue" in="${venues}">
              <li class="menuitem"><g:link controller="room" action="venue" id="${venue?.id}" class="menuitem">${venue}</g:link>
              <g:if test="${venue.rooms}">
                <ul>
                  <g:findAll var="room" in="${venue.rooms}" expr="it.publishState == 'Published'">
                    <li class="menuitem"><g:link controller="room" action="view" id="${room.id}" class="menuitem">${room}</g:link></li>
                  </g:findAll>
                </ul>
              </g:if>
              </li>
            </g:each>          
          </g:if>
        </ul>
      </li>
      <li class="menuitem">
        <ul>
          <g:if test="${allArticles}">
            <g:each var="article" in="${allArticles}">
              <li class="menuitem"><g:link controller="room" action="information" id="${article.id}" class="menuitem">${article?.title}</g:link></li>
            </g:each>
          </g:if>
        </ul>
      </li>
    </ul>
</div>