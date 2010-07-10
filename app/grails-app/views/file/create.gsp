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
<html>
    <head>
        <meta name="layout" content="content-admin" />
        <title><g:message code="add.file.title"/></title>       
    </head>
    <body>        			                			        
        <g:form name="fileUploader" controller="fileUploader" action="process" method="post" enctype="multipart/form-data" > 
            
            <h1 class="ui-widget-header"><g:message code="add.file.title"/></h1>
            
            <p>You may use this page to upload new files.
               Maximum file size is 12Mb</p>
			<input type='hidden' name='upload' value='attachments' /> 
			<input type='hidden' name='errorAction' value='error' /> 
			<input type='hidden' name='errorController' value='file' /> 
			<input type='hidden' name='successAction' value='install' /> 
			<input type='hidden' name='successController' value='file' /> 

            <fieldset class="last">			
			    <input type='file' name='file' /> 
			</fieldset>
			<input type='submit' name='submit' value='Submit' /> 
		</g:form>
    </body>
</html>