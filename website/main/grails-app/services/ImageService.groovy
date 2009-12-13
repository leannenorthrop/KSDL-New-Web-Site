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
                println ("cropping ${imagetype}")
                return imageTool.getBytes(imagetype)
            } else {
                if (file instanceof String) {
                    try {
                        return new File(file).readBytes()
                    } catch(error) {
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

    def thumbnail(image) {
        try {
            def imageTool = new ImageTool()
            imageTool.load(image)
            imageTool.square()
            imageTool.swapSource()
            imageTool.thumbnailSpecial(75, 75, 3, 2)
            return imageTool.getBytes("JPEG")
        } catch (error) {
            log.error(error)
        }
    }
}
