<div id="comment${comment.id}" class="comment ui-widget-content ui-corner-all">
  <span style="display:inline-block;">
    <g:if test="${comment?.poster.profile?.image && comment?.poster.profile?.mimeType?.endsWith('png')}">
      <img src="${createLink(controller: 'profile', action: 'src', id: comment?.poster.id)}" title="${comment?.poster.profile?.publicName}" alt="${comment?.poster.profile?.publicName}" class="pngImg"  width="org.samye.dzong.london.site.Setting.findByName('ThumbSize').value" height="org.samye.dzong.london.site.Setting.findByName('ThumbSize').value"/>
    </g:if>
    <g:else>
      <img src="${createLink(controller: 'profile', action: 'src', id: user.id)}" title="${comment?.poster.profile?.publicName}" alt="${comment?.poster.profile?.publicName}"  width="org.samye.dzong.london.site.Setting.findByName('ThumbSize').value" height="org.samye.dzong.london.site.Setting.findByName('ThumbSize').value"/>
    </g:else>
  </span>
  <span style="display:inline-block;">
    <h3>${comment?.poster}</h3>
    <h4><g:formatDate format="dd MMM, yyyy HH:MM a" date="${comment.dateCreated}"/></h4>
    <g:if test="${noEscape}">
      <p>${comment?.body}</p>
    </g:if>
    <g:else>
${comment?.body?.encodeAsTextile()}				
    </g:else>
  </span>
  <span class="clear"></span>
</div>
