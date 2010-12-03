/*
 * Copyright © 2010 Leanne Northrop
 *
 * This file is part of Samye Content Management System.
 *
 * Samye Content Management System is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * Samye Content Management System is distributed in the hope that it will be
 * useful,but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Samye Content Management System.
 * If not, see <http://www.gnu.org/licenses/>.
 *
 * BT plc, hereby disclaims all copyright interest in the program
 * “Samye Content Management System” written by Leanne Northrop.
 */

package org.samye.dzong.london.media

import org.samye.dzong.london.media.FlickrService
import org.samye.dzong.london.site.Setting

/**
 * Updates flickr service cache with latest photo-albums from flickr. Executes 2-hourly.
 *
 * @author Leanne Northrop
 * @since 14th November,2010, 16:14
 */
class UpdateFlickrCacheJob {
    def flickrService
    def sessionRequired = false

    static triggers = {
        simple name: '2-hourly', startDelay: (3*60*1000), repeatInterval: (120*60*1000)
    }

    def group = "LSD"

    def execute() {
		def userId = ""
		def flickrUserSetting = Setting.findByName('FlickrUserId')
		if (!flickrUserSetting) {
			userId = '66103625@N00'
			flickrUserSetting = new Setting(name: 'FlickrUserId', value: userId)
			flickrUserSetting.save()
		} else {
			userId = flickrUserSetting.value
		}
		flickrService.refresh(userId)
    }
}
