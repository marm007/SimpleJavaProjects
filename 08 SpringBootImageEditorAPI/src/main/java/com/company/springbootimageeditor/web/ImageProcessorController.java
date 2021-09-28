package com.company.springbootimageeditor.web;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletInputStream;

import com.company.springbootimageeditor.web.Exceptions.ImageBadConstraintsException;
import com.company.springbootimageeditor.web.Exceptions.ImageNotFoundException;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

public class ImageProcessorController {

    private Map<String, BufferedImage> imageMap = new HashMap<>();

    private static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    private static byte[] BufferedImageToByteArray(BufferedImage bi) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bi, "jpg", baos);
            baos.flush();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    public ImageProcessorController() {
    }

    public String setImage(ServletInputStream requestEntity) {

        JSONObject jsonObject = new JSONObject();

        try {
            InputStream in = new ByteArrayInputStream(IOUtils.toByteArray(requestEntity));
            BufferedImage bufferedImage = ImageIO.read(in);
            String id = "image_" + imageMap.size();
            imageMap.put(id, bufferedImage);

            jsonObject.put("id", id);
            jsonObject.put("width", bufferedImage.getWidth());
            jsonObject.put("height", bufferedImage.getHeight());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public byte[] getImage(String id) {

        if (!imageMap.containsKey(id))
            throw new ImageNotFoundException(id);

        return BufferedImageToByteArray(imageMap.get(id));
    }

    public String getImageData(String id) {

        if (!imageMap.containsKey(id))
            throw new ImageNotFoundException(id);

        BufferedImage bufferedImage = imageMap.get(id);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("width", bufferedImage.getWidth());
        jsonObject.put("height", bufferedImage.getHeight());

        return jsonObject.toString();
    }

    public byte[] getScaledImage(String id, float percent) {

        if (!imageMap.containsKey(id))
            throw new ImageNotFoundException(id);

        BufferedImage bufferedImage = imageMap.get(id);

        int dWidth = (int) (bufferedImage.getWidth() * (1.0f - percent / 100));
        int dHeight = (int) (bufferedImage.getHeight() * (1.0f - percent / 100));

        BufferedImage scaledImage = new BufferedImage(dWidth, dHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = scaledImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        graphics2D.drawImage(bufferedImage, 0, 0, dWidth, dHeight, null);
        graphics2D.dispose();

        return BufferedImageToByteArray(scaledImage);
    }

    public int[][] getHistogram(String id) {

        if (!imageMap.containsKey(id))
            throw new ImageNotFoundException(id);

        BufferedImage bufferedImage = imageMap.get(id);

        BufferedImage image = deepCopy(bufferedImage);

        int height = bufferedImage.getHeight();
        int width = bufferedImage.getWidth();

        int[][] colors = new int[3][256];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; ++j) {
                Color c = new Color(image.getRGB(j, i));
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();
                colors[0][red]++;
                colors[1][green]++;
                colors[2][blue]++;
            }
        }
        return colors;

    }

    public byte[] getGreyscaleImage(String id) {

        if (!imageMap.containsKey(id))
            throw new ImageNotFoundException(id);

        BufferedImage bufferedImage = imageMap.get(id);

        BufferedImage image = deepCopy(bufferedImage);

        int height = bufferedImage.getHeight();
        int width = bufferedImage.getWidth();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; ++j) {
                Color c = new Color(image.getRGB(j, i));
                int red = (int) (c.getRed() * 0.299);
                int green = (int) (c.getGreen() * 0.587);
                int blue = (int) (c.getBlue() * 0.114);
                Color newColor = new Color(red + green + blue,

                        red + green + blue, red + green + blue);

                image.setRGB(j, i, newColor.getRGB());
            }
        }

        return BufferedImageToByteArray(image);
    }

    public byte[] getCroppedImage(String id, int start, int stop, int width, int height) {

        if (!imageMap.containsKey(id))
            throw new ImageNotFoundException(id);

        BufferedImage bufferedImage = imageMap.get(id);

        if (start >= bufferedImage.getWidth())
            throw new ImageBadConstraintsException("start", ">", "width");
        else if (start <= 0)
            throw new ImageBadConstraintsException("start", "<", "0");
        else if (stop >= bufferedImage.getHeight())
            throw new ImageBadConstraintsException("stop", ">", "height");
        else if (stop <= 0)
            throw new ImageBadConstraintsException("stop", "<", "0");
        else if (width >= bufferedImage.getWidth())
            throw new ImageBadConstraintsException("width", ">", "image width");
        else if (width <= 0)
            throw new ImageBadConstraintsException("width", "<", "0");
        else if (height >= bufferedImage.getHeight())
            throw new ImageBadConstraintsException("height", ">", "image height");
        else if (height <= 0)
            throw new ImageBadConstraintsException("height", "<", "0");

        BufferedImage image = bufferedImage.getSubimage(start, stop, width, height);

        return BufferedImageToByteArray(image);
    }

    public byte[] getBlurImage(String id, int radius) {

        if (!imageMap.containsKey(id))
            throw new ImageNotFoundException(id);

        BufferedImage bufferedImage = imageMap.get(id);

        if (radius >= bufferedImage.getWidth())
            throw new ImageBadConstraintsException("radius", ">", "radius");
        else if (radius <= 0)
            throw new ImageBadConstraintsException("radius", "<", "0");

        int size = radius * 2 + 1;
        float weight = 1.0f / (size * size);
        float[] data = new float[size * size];

        for (int i = 0; i < data.length; i++) {
            data[i] = weight;
        }

        Kernel kernel = new Kernel(size, size, data);
        BufferedImageOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);

        BufferedImage image = op.filter(bufferedImage, null);

        return BufferedImageToByteArray(image);
    }
}
