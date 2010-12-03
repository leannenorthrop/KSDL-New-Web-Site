%{------------------------------------------------------------------------------
- Copyright © 2010 Leanne Northrop
-
- This file is part of Samye Content Management System.
    -
- Samye Content Management System is free software: you can redistribute it
- and/or modify it under the terms of the GNU General Public License as
- published by the Free Software Foundation, either version 3 of the License,
- or (at your option) any later version.
    -
- Samye Content Management System is distributed in the hope that it will be
- useful,but WITHOUT ANY WARRANTY; without even the implied warranty of
- MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
- GNU General Public License for more details.
    -
- You should have received a copy of the GNU General Public License
- along with Samye Content Management System.
    - If not, see <http://www.gnu.org/licenses/>.
-
- BT plc, hereby disclaims all copyright interest in the program
- “Samye Content Management System” written by Leanne Northrop.
    ----------------------------------------------------------------------------}%
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.samye.dzong.london.site.Setting" %>
<html>
  <head>
    <meta name="layout" content="content-admin-wide" />
    <title><g:message code="theme.select.title"/></title>
  <g:set var="homePageHref" value="${createLink (controller: 'home', action: 'index')}"/>
  <g:javascript>
    $(function() {
        $("input[name=theme]:radio").change(function() {
            var checkedValue = $(':checked').val();
            $("#previewLink").attr("href", '${homePageHref}?theme=' + checkedValue);
            $("#previewFrame").attr("src", '${homePageHref}?theme=' + checkedValue);
        });
        $("#previewLink").click(function() {
            var href = $(this).attr('href')
            $.ajax({url:href,async:true});
        });
    });
  </g:javascript>
</head>
<body>
<g:set var="cssThemesDir" value="${new File(application.getRealPath('/css/themes')).listFiles().findAll {item -> item.isDirectory()}.collect{item -> item.name}}" />
<g:set var="defaultTheme" value="${Setting.findByName('DefaultTheme').value}"/>

<div class="container_16">  
  <div class="grid_4">
    <g:form name="setDefaultTheme" action="save" method="post">
      <fieldset>
        <legend><g:message code="theme.select.title"/></legend>

        <p><g:if test="${Setting.findByName('DefaultTheme').value}">The default theme for new viewers is currently ${Setting.findByName('DefaultTheme').value}.</g:if> You may change the way this website looks for
                  	    new users by selecting any of the options below and pressing the Save Changes button:<br/><br/>

        <g:radioGroup name="theme" labels="${cssThemesDir}" values="${cssThemesDir}" value="${defaultTheme}" >
          <p><g:message code="${it.label}" />: ${it.radio}</p>
        </g:radioGroup>
        </p>

        <p class="last"><a id="previewLink" href="#" target="_blank">Preview in Separate Window</a></p>
        <g:submitButton name="create" class="ui-corner-all" value="${message(code: 'event.save.btn', default: 'Save Changes')}"/>
        <g:actionSubmit value="${message(code:'add.theme.btn')}" action="add" class="ui-corner-all"/>
      </fieldset>
    </g:form>
  </div>
  <div class="grid_12">
    <iframe id="previewFrame" src="${createLink (controller: 'home', action: 'index')}" style="min-height:50em;min-width:65em;width:100%;height:100%;">
      <p>Your browser does not appear to support iframes.</p>
    </iframe>
  </div>
</div> 
</body>
</html>
