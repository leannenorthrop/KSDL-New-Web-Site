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

class ImageService {
    def imageTool
    boolean transactional = false

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
            return imageTool.getHeight() <= 75 && imageTool.getWidth() <= 75
        } catch (error) {
            log.error(error)
        }		
	}
	
	def profileThumbnail(file,type) {
        try {
            def imageTool = new ImageTool()
            imageTool.load(file)
            imageTool.saveOriginal()
            if (imageTool.getHeight() > 75 || imageTool.getWidth() > 75) {
				imageTool.square()
	            imageTool.swapSource()
	            imageTool.thumbnailSpecial(75, 75, 3, 2)
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
            if (imageTool.getHeight() > 85 || imageTool.getWidth() > 85) {
				imageTool.square()
	            imageTool.swapSource()
	            imageTool.thumbnailSpecial(85, 85, 3, 2)
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
