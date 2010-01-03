<%@ page import="org.samye.dzong.london.media.Image" %>
<html>
    <head>
        <meta name="layout" content="content-admin" />
        <title>Kagyu Samye Dzong London: Add Image</title>
    </head>
    <body>
        <div class="content group">
            <g:uploadForm name="addimage" action="save">
                <h1>Add Image</h1>
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
                    <label for="title">Name <em>Should be letters a to z, either upper or lower case, and spaces</label>
                    <input type="text" id="name" name="name" class="${hasErrors(bean:articleInstance,field:'name','errors')}" value="${fieldValue(bean:imageInstance,field:'name')}"/>
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
                <fieldset>
                    <label for="title">File <em>May be either a JPG or a PNG file</em></label>
                    <input type="file" id="image" name="image" />
                </fieldset>
                <a class="submit" onClick="document.addimage.submit();">Add Image &raquo;</a>
            </g:uploadForm>
        </div>
    </body>
</html>
