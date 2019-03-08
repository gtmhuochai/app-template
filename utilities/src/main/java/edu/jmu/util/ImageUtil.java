package edu.jmu.util;

import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author LiuJiaJun
 * @date 2018/9/30 9:51
 */
public class ImageUtil {


    /**
     * 生成颜色
     *
     * @param color String
     * @return 返回颜色信息
     */
    public static Color getColor(String color) {
        if (org.springframework.util.StringUtils.isEmpty(color) || color.length() < 7) {
            return null;
        }
        int r = Integer.parseInt(color.substring(1, 3), 16);
        int g = Integer.parseInt(color.substring(3, 5), 16);
        int b = Integer.parseInt(color.substring(5), 16);
        return new Color(r, g, b);
    }

    /**
     * 获取网络文件的inputStream
     *
     * @param baseUrl url
     * @return 返回InputStream
     */
    public static InputStream getInputStream(String baseUrl) {
        if (org.springframework.util.StringUtils.isEmpty(baseUrl)) {
            return null;
        }
        try {
            URL url = new URL(baseUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(6000);
            connection.setReadTimeout(6000);
            int code = connection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == code) {
                return connection.getInputStream();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage addWaterMark(String path, String targetPath, String content, Color color, Font font) throws IOException {
        File file = ResourceUtils.getFile("classpath:" + path);
        Image read = ImageIO.read(file);
        int width = read.getWidth(null);
        int height = read.getHeight(null);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        //以下两行代码实为了背景透明
        image = graphics.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        graphics.dispose();
        graphics = image.createGraphics();
        // 设置对线段的锯齿状边缘处理
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.drawImage(read, 0, 0, width, height, null);
        graphics.setColor(color);
        graphics.setFont(font);
//        int x = width - 2 * getWatermarkLength(content,graphics);
//        int y = height - 2 * getWatermarkLength(content,graphics);
        int x = 0;
        int y = height / 2;
        graphics.drawString(content, x, y);
        graphics.dispose();
        return image;

//        Thumbnails.of(image).size(36,211).toOutputStream();

//        FileOutputStream fileOutputStream = new FileOutputStream(targetPath);
//        ImageIO.write(image,"png",fileOutputStream);
//        fileOutputStream.flush();
//        fileOutputStream.close();
//        System.out.println("添加水印完成");
    }

    public static int getWatermarkLength(String content, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(content.toCharArray(), 0, content.length());
    }

    /**
     * 图片裁剪,带有坐标
     *
     * @param bufferedImage 图片流
     * @param startX        左上角X坐标
     * @param startY        左上角Y坐标
     * @param endX          右下角X坐标
     * @param endY          右下角Y坐标
     * @return
     */
    public static BufferedImage cutImage(BufferedImage bufferedImage, int startX, int startY, int endX, int endY) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        //如果坐标点不正确，返回null
        if (startX > width || endX > width || startX > endX) {
            return null;
        }
        if (startY > height || endY > height || startY > endY) {
            return null;
        }
        if (startX == -1) {
            startX = 0;
        }
        if (startY == -1) {
            startY = 0;
        }
        if (endX == -1) {
            endX = width - 1;
        }
        if (endY == -1) {
            endY = height - 1;
        }
        BufferedImage result = new BufferedImage(endX - startX, endY - startY, 4);
        for (int x = startX; x < endX; ++x) {
            for (int y = startY; y < endY; ++y) {
                int rgb = bufferedImage.getRGB(x, y);
                result.setRGB(x - startX, y - startY, rgb);
            }
        }
        return result;
    }

    /**
     * 不传坐标，按照图片的一半裁剪
     * @param bufferedImage
     * @return
     */
    public static BufferedImage cutImage(BufferedImage bufferedImage) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        //按照图片的一半大小裁剪
        int startX = width / 4;
        int startY = height / 4;
        int endX = width / 4 * 3;
        int endY = height / 4 * 3;
        BufferedImage result = new BufferedImage(endX - startX, endY - startY, 4);
        for (int x = startX; x < endX; ++x) {
            for (int y = startY; y < endY; ++y) {
                int rgb = bufferedImage.getRGB(x, y);
                result.setRGB(x - startX, y - startY, rgb);
            }
        }
        return result;
    }
}
