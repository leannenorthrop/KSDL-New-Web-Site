
<%@ page import="org.samye.dzong.london.media.Image" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="content-admin" />
        <title>Kagyu Samye Dzong London: Edit Image</title>
    </head>
    <body>
        <div class="content group">
            <g:form name="imageform" action="update" method="post" enctype="multipart/form-data">
                <h1>Edit Image Details</h1>
                <img src="${createLink(controller:'image', action:'src', id:imageInstance.id)}" style="float:right;"/>
                <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
                </g:if>
                <g:hasErrors bean="${imageInstance}">
                <div class="errors">
                    <g:renderErrors bean="${imageInstance}" as="list" />
                </div>
                </g:hasErrors>
                <input type="hidden" name="id" value="${imageInstance?.id}" />
                <input type="hidden" name="version" value="${imageInstance?.version}" />
                <input type="hidden" name="name" value="${imageInstance?.name}" />
                <fieldset>
                    <label for="title">Name</label>
                    <input type="text" id="name" name="name" readonly="true" class="${hasErrors(bean:articleInstance,field:'name','errors')}" value="${fieldValue(bean:imageInstance,field:'name')}"/>
                </fieldset>
                <fieldset>
                    <label for="tags">Tags <em>Separate with commas</em></label>
                    <div id="tags_help">
                        <h4>Suggestions</h4>
                        <ul>
                            <li>news <em>Show on News page</em></li>
                            <li>meditation <em>Show on Mediation page</em></li>
                            <li>meditation advice <em>Show on Meditation page under Advice</em></li>
                            <li>meditation benefits <em>Show on Meditation page under Benefits</em></li>
                        </ul>
                    </div>
                    <textarea cols="5" rows="5" id="tags" name="tags" class="${hasErrors(bean:articleInstance,field:'tags','errors')}">${imageInstance.tags.join(",")}</textarea>
                </fieldset>
                <a class="submit" onClick="document.imageform.submit();">Save Changes &raquo;</a>
            </g:form>
        </div>
    </body>
</html>
