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
        <title><g:message code="manage.home.title"/></title>
    </head>
    <body>
        <noscript>
            <div class="ui-widget ui-state-error ui-corner-all">
                <p><span class="ui-icon ui-icon-alert"></span>
                <strong><g:message code="alert"/></strong> <g:message code="manage.js.error"/></p>
            </div>
        </noscript>
        <h2>Getting Started</h2>
        <ul>
            <li><b>Adding Content</b>
                <p>If you have permission as an Author you will be able to create content for the Article and Teacher areas as well as upload and label images. You can write as many items as you wish and once you are happy click the item's Action 'Ready to Publish' action link - this makes the item visible to users with Editor permission to review your content. Use the Category select boxes to determine where on the web-site you want your content to be displayed.</p>
            </li>
            <li><b>Publishing Content</b>
                <p>If you have permission as an Editor you will also see the Article, Teacher and Image areas but you will not be able to add content unless you also have Author permissionship. However you will be able to review content created by all users with Author permission that have used the 'Ready to Publish' action. You can click on items 'Ready to Publish' to review them - once you are happy you can use the 'Publish' action to make the item visible on the Public pages, however you will first be asked to add <em>at least 1</em> Label. Once Publication is complete you will be able to browse to the relevant area and preview the item.</p>
            </li><b>Events</b>
              <p>Before creating events the Leader/Teacher for the event should be added and published.</p>
            </li>
            <li>Across the top of this page is a toolbar which displays the areas of the website you can access. Click on the toolbar to access the management pages for that area. If you have permission you will see Add or Create buttons and Action links. There are no cancel buttons - please either click on the toolbar to navigate around or use your browser's back button.</li>
            <li><b>Images</b>
                <p>A good first place to start is Images. A number of images have been pre-loaded for you but you may 'upload' and store as many PNG or JPG as you wish. Please use short public friendly names for your images because:
                <ol>
                    <li>The name is used as the title and alt value when displayed on the Public pages.</li>
                    <li>You can link to an image within the Content areas using the image name using the following without the quotes "[White Tara](image)" or "[White Tara,right](image)" or "[White Tara,right,50px](image)"</li>
                </ol>
                </p>
                <p><b>Also note</b> Labels are used to control where an Image can be used. If you don't see an Image that you uploaded in a select box then you will need to add a Label to the Image. e.g To allow an image to be used in both the Teacher area and the Event area add the Labels teacher,event.</p>
                <p><b>Hint</b> You may find it useful to keep the Image list open in a separate browser tab or window for Reference.</p>
            </li>
            <l><b>About Us</b>
                <p>The description on the About Us page comes from the 'teacher' with the name Community. This fulfills a dual-purpose in that events led by the Community will link back to the About Us page.</p>
            </l>
            <li><b>Changing Published Items</b>
                <p>It is always possible to change published items. Simply ask someone with Editor permissions to 'Unpublish' the item to allow changes.
            </li>
        </ul>
    </body>
</html>
