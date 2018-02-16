package com.adsizzler.mangolaa.impressions.service.impl

import com.adsizzler.mangolaa.impressions.service.PixelImageService
import groovy.util.logging.Slf4j
import org.springframework.cache.annotation.Cacheable
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service

import javax.imageio.ImageIO

/**
 * Created by ankushsharma on 16/02/18.
 */
@Service
@Slf4j
class PixelImageServiceImpl implements PixelImageService{

    private final ResourceLoader resourceLoader

    PixelImageServiceImpl(ResourceLoader resourceLoader){
        this.resourceLoader = resourceLoader
    }


    @Override
    @Cacheable
    byte[] get1X1Pixel() {
        //Get pixel image as an instance of BufferedImage, and then convert it to byte[]
        def file = resourceLoader.getResource("classpath:/1x1.png").getFile()
        def image = ImageIO.read(file)
        def baos = new ByteArrayOutputStream()
        ImageIO.write(image, "png", baos)
        baos.flush()
        byte[] imageInByte = baos.toByteArray()
        baos.close()
        imageInByte
    }
}
