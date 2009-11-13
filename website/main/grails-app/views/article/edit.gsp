
<%@ page import="org.samye.dzong.london.community.Article" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Edit Article</title>
    </head>
    <body>
        <div class="body">
            <h1>Edit Article</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${articleInstance}">
            <div class="errors">
                <g:renderErrors bean="${articleInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <input type="hidden" name="id" value="${articleInstance?.id}" />
                <input type="hidden" name="version" value="${articleInstance?.version}" />
                <input type="hidden" name="publishState" value="${articleInstance?.publishState}" />
                <input type="hidden" name="deleted" value="${articleInstance?.deleted}" />
                <div class="dialog">
                    <table>
                        <tbody>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="title">Title:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:articleInstance,field:'title','errors')}">
                                    <input type="text" id="title" name="title" value="${fieldValue(bean:articleInstance,field:'title')}"/>
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="summary">Summary:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:articleInstance,field:'summary','errors')}">
                                    <textarea rows="5" cols="40" name="summary">${fieldValue(bean:articleInstance, field:'summary')}</textarea>
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="content">Content:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:articleInstance,field:'content','errors')}">
                                    <textarea rows="5" cols="40" name="content">${fieldValue(bean:articleInstance, field:'content')}</textarea>
                                </td>
                            </tr>

                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" value="Update" /></span>
                    <span class="button"><g:link class="list" action="manage">Cancel</g:link></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
