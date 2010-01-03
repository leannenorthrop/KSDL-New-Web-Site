<%@ page import="org.samye.dzong.london.community.Article" %>
<html>
    <head>
        <meta name="layout" content="content-admin" />
        <title>Manage Articles</title>
        <link rel="stylesheet" href="${resource(dir:'css/redmond',file:'jquery-ui-1.7.2.custom.css')}" media="screen, projection" />
        <g:javascript library="jquery"/>
        <g:javascript src="jquery/jquery-ui-1.7.2.custom.min.js"/>        
        <g:javascript>
            var currentTabIndex = 0;
            var currentTabDiv;
            $(function() {
                $('a.step').live('click', function() {
                    $("#articles-tabs").tabs('url', currentTabIndex, this.href);                
                    $(currentTabDiv).load(this.href);
                    return false;
                });
                $('a.nextLink').live('click', function() {
                    $("#articles-tabs").tabs('url', currentTabIndex, this.href);
                    $(currentTabDiv).load(this.href);
                    return false;
                });        
                $('a.prevLink').live('click', function() {
                    $(currentTabDiv).load(this.href);
                    return false;
                });     
                $('a.sortable').live('click', function() {
                    $(currentTabDiv).load(this.href);
                    return false;
                });
                $("#articles-tabs").tabs({
                    fx: { opacity: 'toggle' },
                    select: function(event, ui) {
                        currentTabDiv = $(ui.panel);
                        currentTabIndex = $("#articles-tabs").tabs('option', 'selected');
                    },
                    load: function(event, ui) {
                        currentTabDiv = $(ui.panel);
                        currentTabIndex = $("#articles-tabs").tabs('option', 'selected');
                    }
                });                
            });
        </g:javascript>
    </head>
    <body>
        <div class="jquery-ui content">
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <div id="articles-tabs">
                <ul>
                    <li><a href="ajaxUnpublishedArticles">Unpublished</a></li>
                    <li><a href="ajaxPublishedArticles">Published</a></li>
                    <li><a href="ajaxArchivedArticles">Archived</a></li>
                    <li><a href="ajaxDeletedArticles">Deleted</a></li>
                </ul>                                              
            </div>
        </div>
    </body>
</html>
