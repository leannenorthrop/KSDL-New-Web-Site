<g:set var="comments" value="${commentable.comments}"></g:set>
<div id="comments" class="commentable">
	<g:render template="/comments/comment" 
			  collection="${comments}" 
			  var="comment" 
			  model="[noEscape:noEscape]" />
</div>
<div id="addComment" class="addComment">
    <input type="button" 
           onclick="document.getElementById('addCommentContainer').style.display='';return false;" 
           value="${message(code:'comment.add.title',default:'Add a Comment')}"
           class="ui-corner-all cancel"/>
	<div id="addCommentContainer" class="addCommentContainer" style="display:none;margin-top:1em;">
		<div class="addCommentDescription">
			<g:message code="comment.add.description" default=""></g:message>
		</div>
		<a name="commentEditor"></a>
		<g:formRemote name="addCommentForm" url="[controller:'commentable',action:'add']" update="comments">
			<plugin:isAvailable name="grails-ui">
				<gui:richEditor id='commentBody' name="comment.body" value='' width="100%" />
			</plugin:isAvailable>
			<plugin:isNotAvailable name="grails-ui">
				<g:textArea id="commentBody" name="comment.body" /> <br />
			</plugin:isNotAvailable>
			<g:hiddenField name="update" value="comments" />			
			<g:hiddenField name="commentLink.commentRef" value="${commentable.id}" />
			<g:hiddenField name="commentLink.type" value="${commentable.class.name}" />			
			<g:hiddenField name="commentPageURI" value="${request.forwardURI}"></g:hiddenField>
			<g:submitButton name="${g.message(code:'comment.post.button.name', 
											 'default':'Post')}" class="ui-corner-all cancel"></g:submitButton>
		</g:formRemote>
	</div>
</div>