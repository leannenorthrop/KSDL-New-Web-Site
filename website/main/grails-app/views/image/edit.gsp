
<%@ page import="org.samye.dzong.london.media.Image" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="content-admin" />
        <title>Kagyu Samye Dzong London: Edit Image</title>
    </head>
    <body>
        <div class="content">
            <g:form name="imageform" action="update" method="post" enctype="multipart/form-data">
                <h1>Edit Image Details</h1>
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
                <fieldset>
                    <label for="title">Name</label>
                    <input type="text" id="name" name="name" class="${hasErrors(bean:articleInstance,field:'name','errors')}" value="${fieldValue(bean:imageInstance,field:'name')}"/>
                </fieldset>
                <fieldset>
                    <label for="title">Tags</label>
                    <textarea cols="5" rows="5" id="tags" name="tags" class="${hasErrors(bean:articleInstance,field:'tags','errors')}">${imageInstance.tags.join(",")}</textarea>
                </fieldset>
                <a class="submit" onClick="document.imageform.submit();">Save Changes &raquo;</a>
            </g:form>
            <img src="${createLink(controller:'image', action:'src', id:imageInstance.id)}"/>
        </div>
    </body>
</html>
