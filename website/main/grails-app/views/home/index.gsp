<html>
  <head>
    <title>Welcome</title>
    <meta name="layout" content="main">
    <feed:meta kind="rss" version="2.0" controller="feed" action="news"/>
    <feed:meta kind="rss" version="2.0" controller="feed" action="meditation"/>
    <feed:meta kind="rss" version="2.0" controller="feed" action="community"/>
    <feed:meta kind="rss" version="2.0" controller="feed" action="wellbeing"/>

  </head>
  <body>
    <div class="col1_80_Percent">
      <div class="events box">
        <h2>Forthcoming Events</h2>
        <ol class="box">
          <g:each in="${events}" status="i" var="event">
            <li class="group">
              <h3>${event?.title}</h3>
              <h4><joda:format style="M-" date="${event?.eventDate}"/> (<joda:format style="-S" date="${event?.startTime?.toLocalTime()}"/> ${fieldValue(bean: event, field: "eventDuration")})</h4>
              <h5><g:link controller="teacher" action="view" id="${event?.leader?.id}"><g:message code="teacher.title.${event?.leader?.title}"/> ${event?.leader?.name}</g:link></h5>

              <g:if test="${event.image}">
                <img src="${createLink(controller: 'image', action: 'thumbnail', id: event.image.id)}" title="${event.image.name}" alt="${event.image.name}"/>
              </g:if>
              <p>${event.summary.encodeAsTextile()} <g:link controller="event" action="view" id="${event.id}">Read More...</g:link></p>
            </li>
          </g:each>
        </ol>
      </div>
    </div>
    <div class="col2_20_Percent">
      <div class="box">
        <h2>News <span class="amp">&amp;</span> Goings On</h2>
        <ol>
          <g:each in="${newsArticles}" status="i" var="articleInstance">
            <li>
              ${articleInstance.title} <g:link controller="news" action="view" id="${articleInstance.id}">Read More...</g:link>
            </li>
          </g:each>
        </ol>
      </div>
      <div class="box">
        <h2>Gallery</h2>
      </div>
      <lsdc:cloud/>
    </div>
    <div class="col1_50_Percent">
      <ol class="box">
        <g:each in="${meditationArticles}" status="i" var="articleInstance">
          <li>
            <h2>${articleInstance.title}</h2>
            <g:if test="${articleInstance.image}">
              <img src="${createLink(controller: 'image', action: 'thumbnail', id: articleInstance.image.id)}" title="${articleInstance.image.name}" alt="${articleInstance.image.name}"/>
            </g:if>
            <p>${articleInstance.summary.encodeAsTextile()} <g:link action="view" id="${articleInstance.id}">Read More...</g:link></p>
          </li>
        </g:each>
      </ol>
    </div>
    <div class="col2_50_Percent">
      <ol class="box">
        <g:each in="${communityArticles}" status="i" var="articleInstance">
          <li>
            <h2>${articleInstance.title}</h2>
            <g:if test="${articleInstance.image}">
              <img src="${createLink(controller: 'image', action: 'thumbnail', id: articleInstance.image.id)}" title="${articleInstance.image.name}" alt="${articleInstance.image.name}"/>
            </g:if>
            <p>${articleInstance.summary.encodeAsTextile()} <g:link action="view" id="${articleInstance.id}">Read More...</g:link></p>
          </li>
        </g:each>
      </ol>
    </div>
    <div class="col1_50_Percent">
      <ol class="box">
        <g:each in="${buddhismArticles}" status="i" var="articleInstance">
          <li>
            <h2>${articleInstance.title}</h2>
            <g:if test="${articleInstance.image}">
              <img src="${createLink(controller: 'image', action: 'thumbnail', id: articleInstance.image.id)}" title="${articleInstance.image.name}" alt="${articleInstance.image.name}"/>
            </g:if>
            <p>${articleInstance.summary.encodeAsTextile()} <g:link action="view" id="${articleInstance.id}">Read More...</g:link></p>
          </li>
        </g:each>
      </ol>
    </div>
    <div class="col2_50_Percent">
      <ol class="box">
        <g:each in="${wellbeingArticles}" status="i" var="articleInstance">
          <li>
            <h2>${articleInstance.title}</h2>
            <g:if test="${articleInstance.image}">
              <img src="${createLink(controller: 'image', action: 'thumbnail', id: articleInstance.image.id)}" title="${articleInstance.image.name}" alt="${articleInstance.image.name}"/>
            </g:if>
            <p>${articleInstance.summary.encodeAsTextile()} <g:link action="view" id="${articleInstance.id}">Read More...</g:link></p>
          </li>
        </g:each>
      </ol>
    </div>
  </body>
</html>
