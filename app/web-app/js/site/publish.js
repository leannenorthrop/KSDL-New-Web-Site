/*------------------------------------------------------------------------------
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
  ----------------------------------------------------------------------------*/

/*
    @author Leanne Northrop
    @since November 13, 2010,  11:14 PM
*/
var updatePublishOptions = function() {
    var val = $('#category option:selected').val();
    var name = $('#category option:selected').text();
    var newHomeLabel = homeLabel;
    if (val == 'N') {
        $('#is_featured').hide('slow');
        $('#is_home').show('slow');
    } else if (isEvent) {
        $('#is_featured').show('slow');
        $('#is_home').show('slow');
    } else {
        $('#is_featured').show('slow');
        $('#is_home').show('slow');
        newHomeLabel = newHomeLabel.replace('Section', name);        
    }    
    $('#is_home label span').text(newHomeLabel);
    var newFeaturedLabel = featuredLabel.replace('Section', name);
    $('#is_featured label span').text(newFeaturedLabel);
}

$('#category').change(updatePublishOptions);
updatePublishOptions();
