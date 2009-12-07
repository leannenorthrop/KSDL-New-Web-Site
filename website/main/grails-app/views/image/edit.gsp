
<%@ page import="org.samye.dzong.london.media.Image" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="content-admin" />
        <title>Kagyu Samye Dzong London: Edit Image</title>
    </head>
    <body>
        <div class="menuBar">
            <span class="menuButton"><g:link class="home" controller="manageSite" action="index">Home</g:link></span>
            <shiro:hasAnyRole in="['Author']"><span class="menuButton"><g:link class="list" controller="article" action="manage" params="[offset:0,max:10]">Articles</g:link></span></shiro:hasAnyRole>
            <shiro:hasAnyRole in="['Author']"><span class="menuButton"><g:link class="list" controller="image" action="manage" params="[offset:0,max:10]">Images</g:link></span></shiro:hasAnyRole>
        </div>
        <div class="body">
            <h1>Edit Image</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${imageInstance}">
            <div class="errors">
                <g:renderErrors bean="${imageInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post"  enctype="multipart/form-data">
                <input type="hidden" name="id" value="${imageInstance?.id}" />
                <input type="hidden" name="version" value="${imageInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">Name:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:imageInstance,field:'name','errors')}">
                                    <input type="text" id="name" name="name" value="${fieldValue(bean:imageInstance,field:'name')}"/>
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="image">Image:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:imageInstance,field:'image','errors')}">
                                    <input type="file" id="image" name="image" />
                                </td>
                            </tr>

                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" value="Update" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
