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

import java.awt.RenderingHints
import java.io.*;
import java.awt.image.renderable.*;
import javax.media.jai.*;
import com.sun.media.jai.codec.*
import javax.media.jai.operator.CompositeDescriptor
import javax.media.jai.operator.ConstantDescriptor
import com.sun.media.jai.codecimpl.JPEGImageEncoder
import java.awt.image.BufferedImage
import java.awt.image.ColorConvertOp
import java.awt.color.ICC_ColorSpace
import java.awt.color.ColorSpace
import java.awt.color.ICC_Profile
import java.awt.Color
import org.samye.dzong.london.site.Setting

class ImageService {
    def imageTool
    boolean transactional = true

    def read(file, type) {
        try {
            def imageTool = new ImageTool()
            imageTool.load(file)
            imageTool.saveOriginal()
            if (imageTool.getHeight() > 640 || imageTool.getWidth() > 640) {
                imageTool.thumbnailSpecial(640, 640, 3, 1)
                def imagetype = type.toLowerCase().indexOf("jpg") >= 0 ? "JPEG" : "PNG";
				imagetype = type.toLowerCase().indexOf("jpeg") >= 0 ? "JPEG" : "PNG";
                return imageTool.getBytes(imagetype)
            } else {
                if (file instanceof String) {
                    try {
                        return new File(file).readBytes()
                    } catch (error) {
                        return ""
                    }
                } else {
                    return file
                }
            }
        } catch (error) {
            log.error(error)
        }
    }

	def isThumbnail(file) {
		try {
            def imageTool = new ImageTool()
            imageTool.load(file)
            imageTool.saveOriginal()
            def thumbSize = Integer.parseInt(Setting.findByName('ThumbSize').value)
            return imageTool.getHeight() <= thumbSize && imageTool.getWidth() <= thumbSize
        } catch (error) {
            log.error(error)
        }		
	}
	
	def profileThumbnail(file,type) {
        try {
            def imageTool = new ImageTool()
            imageTool.load(file)
            imageTool.saveOriginal()
            def thumbSize = Integer.parseInt(Setting.findByName('ThumbSize').value)
            if (imageTool.getHeight() > thumbSize || imageTool.getWidth() > thumbSize) {
				imageTool.square()
	            imageTool.swapSource()
	            imageTool.thumbnailSpecial(thumbSize, thumbSize, 3, 2)
                def imagetype = type.toLowerCase().indexOf("jpg") >= 0 ? "JPEG" : "PNG";
				imagetype = type.toLowerCase().indexOf("jpeg") >= 0 ? "JPEG" : "PNG";
                return imageTool.getBytes(imagetype)	
            } else {
                if (file instanceof String) {
                    try {
                        return new File(file).readBytes()
                    } catch (error) {
                        return ""
                    }
                } else {
                    return file
                }
            }
        } catch (error) {
            log.error(error)
        }		
	}
	
    def thumbnail(image,type) {
        try {
            def imageTool = new ImageTool()
            imageTool.load(image)
            imageTool.saveOriginal()
            def thumbSize = Integer.parseInt(Setting.findByName('ThumbSize').value)
            if (imageTool.getHeight() > thumbSize || imageTool.getWidth() > thumbSize) {
				imageTool.square()
	            imageTool.swapSource()
	            imageTool.thumbnailSpecial(thumbSize, thumbSize, 3, 2)
                def imagetype = type.toLowerCase().indexOf("jpg") >= 0 ? "JPEG" : "PNG";
				imagetype = type.toLowerCase().indexOf("jpeg") >= 0 ? "JPEG" : "PNG";
                return imageTool.getBytes(imagetype)	
            } else {
                return image
            }
        } catch (error) {
            log.error(error)
        }
    }

    def byte[] pngToJpg(byte[] data) {

        ByteArraySeekableStream byteStream = new ByteArraySeekableStream(data);
		PlanarImage img = JAI.create("stream", byteStream);

        BufferedImage bufImage = img.getAsBufferedImage();
        ICC_Profile iccProfile = ICC_Profile.getInstance(ICC_ColorSpace.CS_sRGB);
        ColorSpace cSpace = new ICC_ColorSpace(iccProfile);
        ColorConvertOp op = new ColorConvertOp(bufImage.getColorModel().getColorSpace(), cSpace, null);
        BufferedImage newImage = new BufferedImage(bufImage.getWidth(),bufImage.getHeight(), BufferedImage.OPAQUE);
        def g = newImage.getGraphics()
        g.setColor(Color.WHITE)
        g.fillRect(0,0,bufImage.getWidth(),bufImage.getHeight())
        op.filter(bufImage, newImage);


        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        JAI.create("encode", newImage, bos, "JPEG", null);

        return bos.toByteArray()
    }
}
