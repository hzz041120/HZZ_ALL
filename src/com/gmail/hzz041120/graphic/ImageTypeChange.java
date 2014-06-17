package com.gmail.hzz041120.graphic;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.spi.IIORegistry;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.imageio.plugins.jpeg.JPEG;
import com.sun.imageio.plugins.jpeg.JPEGImageReaderSpi;

/**
 * ͼƬ��ʽת��
 * 
 * @author zhongzhou.hanzz 2013��12��3�� ����5:39:53
 */
public class ImageTypeChange {

    private static final Log logger = LogFactory.getLog(ImageTypeChange.class);
    static {
        IIORegistry.getDefaultInstance().registerServiceProvider(new TIFFImageReaderSpi());
        IIORegistry.getDefaultInstance().registerServiceProvider(new TIFFImageWriterSpi());
    }

    /**
     * ��С��ת����ʽ
     * 
     * @param srcPathԴ·��
     * @param destPathĿ��·��
     * @param formate �ļ���ʽ
     * @return
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static boolean transfer2JPEG(InputStream srcImg, String destPath) throws FileNotFoundException, IOException {
        if (srcImg == null || srcImg.available() == 0) {
            return false;
        }

        boolean flag = false;
        File destFile = new File(destPath);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdir();
        }

        ImageInputStream iis = ImageIO.createImageInputStream(srcImg);
        if (iis == null) {
            logger.error("can't parse this img stream! just supported jpg/gif/png/bmp/tif! please check");
            return false;
        }
        final JPEGImageReaderSpi jpegImageReaderSpi = new JPEGImageReaderSpi();
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(destFile);
            if (jpegImageReaderSpi.canDecodeInput(iis)) {
                BufferedImage src = ImageIO.read(iis); // �����ļ�
                // ������ļ���
                flag = ImageIO.write(src, JPEG.suffixes[0], output);
            } else {
                BufferedImage src = ImageIO.read(iis); // �����ļ�
                int width = src.getWidth();
                int height = src.getHeight();
                Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
                BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                // ����
                g.drawImage(image, 0, 0, null);
                g.dispose();
                // ������ļ���
                flag = ImageIO.write(tag, JPEG.suffixes[0], output);
            }
        } finally {
            output.close();
        }

        return flag;
    }
}
