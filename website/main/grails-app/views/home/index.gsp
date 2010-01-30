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
      <div class="box">
        <h2>Forthcoming Events</h2>

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
