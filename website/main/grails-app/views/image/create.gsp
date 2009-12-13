<%@ page import="org.samye.dzong.london.media.Image" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="content-admin" />
        <title>Kagyu Samye Dzong London: Add Image</title>
    </head>
    <body>
        <div class="content">
            <g:form name="addimage" action="save" method="post"  enctype="multipart/form-data">
                <h1>Create Image</h1>
                <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
                </g:if>
                <g:hasErrors bean="${imageInstance}">
                <div class="errors">
                    <g:renderErrors bean="${imageInstance}" as="list" />
                </div>
                </g:hasErrors>
                <input type="hidden" name="thumbnail" value=""/>
                <fieldset>
                    <label for="title">Name</label>
                    <input type="text" id="name" name="name" class="${hasErrors(bean:articleInstance,field:'name','errors')}" value="${fieldValue(bean:imageInstance,field:'name')}"/>
                </fieldset>
                <fieldset>
                    <label for="title">Tags</label>
                    <textarea cols="5" rows="5" id="tags" name="tags" class="${hasErrors(bean:articleInstance,field:'tags','errors')}">${imageInstance.tags.join(",")}</textarea>
                </fieldset>
                <fieldset>
                    <label for="title">File <em>May be either a JPG or a PNG file</em></label>
                    <input type="file" id="image" name="image" />
                </fieldset>
                <a class="submit" onClick="document.addimage.submit();">Add Image &raquo;</a>
            </g:form>
        </div>
    </body>
</html>
