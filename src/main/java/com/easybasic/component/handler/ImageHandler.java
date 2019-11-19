package com.easybasic.component.handler;

import com.easybasic.component.Utils.ToolsUtil;
import com.easybasic.component.Utils.TypeConverter;
import com.easybasic.component.model.AutoSizeModel;
import com.easybasic.component.model.ImageInfo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class ImageHandler {

    /**
    * 生成缩略图
    **/
    public static void generateReducePic(BufferedImage image, String path, String filename, int FixWidth, int FixHeight) throws IOException
    {
        if (!ToolsUtil.existsDirectory(path))
        {
            ToolsUtil.forceDirectory(path);
        }
        int width = FixWidth;
        int height = image.getHeight() * width / image.getWidth();

        if (image.getWidth() <= FixWidth && image.getHeight() <= FixHeight)
        {
            //此处如果为jpg，则截屏生成的缩略图为黑屏
            ImageIO.write(image, "png", new File(path +"/"+ filename));
            return;
        }
        if (height > FixHeight)
        {
            height = FixHeight;
            width = image.getWidth() * height / image.getHeight();
        }
        if (height == 0)
        {
            height = 1;
        }
        if (width == 0)
        {
            width = 1;
        }

        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();
        g.drawImage(image.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0,null);
        g.dispose();
        ImageIO.write(bi, ToolsUtil.getFileExtensionWithNoDian(new File(path+"/"+filename)), new File(path +"/"+ filename));
    }

    /**
    * 对已经设定截图区域的图片进行剪裁，并保存在thumbnail文件夹中。
     <param name="folder">图片所在文件夹</param>
     <param name="fileName">图片文件名</param>
     <param name="imageWidth">在剪截界面中实际显示的图片宽度（可能是缩放后的）</param>
     <param name="imageHeight">在剪截界面中实际显示的图片高度（可能是缩放后的）</param>
     <param name="cropWidth"></param>
     <param name="cropHeight"></param>
     <param name="cropX"></param>
     <param name="cropY"></param>
     <param name="fixWidth"> </param>
     <param name="fixHeight"> </param>
     <param name="isCopy">为0时，则覆盖原来图片（重新剪裁，用于图片编辑），1时则另复制一份重新剪裁（主要用于封面的剪裁）</param>
    **/
    public static String thumbnailCrop(String folder, String fileName, int imageWidth, int imageHeight, int cropWidth, int cropHeight, int cropX, int cropY, int fixWidth, int fixHeight, int isCopy) throws IOException
    {
        String imageFile = ToolsUtil.getUploadPath("/" + folder + "/" + fileName);
        if (!ToolsUtil.existsFile(imageFile)) return "";
        //保存原图片
        String oriImageFile = ToolsUtil.getUploadPath("/" + folder + "/") + ToolsUtil.getFileNameWithoutExt(new File(imageFile)) + "_ori" + ToolsUtil.getFileExtension(new File(imageFile));
        String tempImageFile = ToolsUtil.getUploadPath("/" + folder + "/") + ToolsUtil.getFileNameWithoutExt(new File(imageFile)) + "_temp" + ToolsUtil.getFileExtension(new File(imageFile));
        ToolsUtil.copyFile(new File(imageFile), new File(tempImageFile));
        if(ToolsUtil.existsFile(oriImageFile))
        {

        }
        else
        {
            //新上传
            ToolsUtil.copyFile(new File(imageFile), new File(oriImageFile));
        }

        if (isCopy == 1)
        {
            String newimage = ToolsUtil.getUploadPath("/" + folder + "/" + ToolsUtil.randomFileName(imageFile));
            ToolsUtil.copyFile(new File(imageFile),new File(newimage));
            imageFile = newimage;
        }
        BufferedImage image = ImageIO.read(new File(tempImageFile));
        int isCrop = 1;
        Object[] re = cropImage(image, imageWidth, imageHeight, cropWidth, cropHeight, cropX, cropY, fixWidth, fixHeight);
        image =(BufferedImage)re[0];
        isCopy = (int)re[1];
        if (isCrop ==1)
        {
            ImageIO.write(image, ToolsUtil.getFileExtensionWithNoDian(new File(imageFile)), new File(imageFile));
        }
        //生成二个缩略图
        generateReducePic(image, ToolsUtil.getUploadPath("/" + folder + "/reduce"), new File(imageFile).getName(), 400, 400);
        String smallReducePic = ToolsUtil.getFileNameWithoutExt(new File(imageFile)) + "_s" + ToolsUtil.getFileExtension(new File(imageFile));
        generateReducePic(image, ToolsUtil.getUploadPath("/" + folder + "/reduce"), smallReducePic, 80, 80);
        String smallReducePic1 = ToolsUtil.getFileNameWithoutExt(new File(imageFile)) + "_m" + ToolsUtil.getFileExtension(new File(imageFile));
        generateReducePic(image, ToolsUtil.getUploadPath("~/" + folder + "/reduce"), smallReducePic1, 400, 400);
        try
        {
            //删除临时图片
            ToolsUtil.deleteFile(tempImageFile);
        }
        catch (Exception ex)
        {

        }
        return new File(imageFile).getName();
    }


    /**
     对当前图片截图，并删除原图片
     <param name="folder"></param>
     <param name="fileName"></param>
     <param name="imageWidth"></param>
     <param name="imageHeight"></param>
     <param name="cropWidth"></param>
     <param name="cropHeight"></param>
     <param name="cropX"></param>
     <param name="cropY"></param>
     <param name="fixWidth"> </param>
     <param name="fixHeight"> </param>
    **/
    public static String singleThumbnailCrop(String folder, String fileName, int imageWidth, int imageHeight, int cropWidth, int cropHeight, int cropX, int cropY, int fixWidth, int fixHeight) throws IOException
    {
        String imageFile = ToolsUtil.getUploadPath("/" + folder + "/" + fileName);
        if (!ToolsUtil.existsFile(imageFile)) return "";
        String thumbnail = TypeConverter.dateToString(new Date(),"HHmmss") + ToolsUtil.getCalendarInstance(new Date()).get(Calendar.MILLISECOND) + ToolsUtil.getFileExtension(new File(imageFile));
        thumbnail = ToolsUtil.getUploadPath("/" + folder + "/" + thumbnail);
        ToolsUtil.copyFile(new File(imageFile), new File(thumbnail));
        BufferedImage image = ImageIO.read(new File(thumbnail));
        Object[] re = cropImage(image, imageWidth, imageHeight, cropWidth, cropHeight, cropX, cropY, fixWidth, fixHeight);
        image = (BufferedImage) re[0];
        ImageIO.write(image, ToolsUtil.getFileExtensionWithNoDian(new File(imageFile)), new File(imageFile));
        try
        {
            ToolsUtil.deleteFile(thumbnail);
        }
        catch(Exception e)
        { }
        return new File(imageFile).getName();
    }

    /**
     对原图进行剪裁操作，返回剪裁后的图片对象(最大不超过800*800)
     <param name="image"></param>
     <param name="imageWidth">在剪截界面中实际显示的图片宽度（可能是缩放后的）</param>
     <param name="imageHeight">在剪截界面中实际显示的图片高度（可能是缩放后的）</param>
     <param name="cropWidth"></param>
     <param name="cropHeight"></param>
     <param name="cropX"></param>
     <param name="cropY"></param>
     <param name="fixWidth">设置的最大修正宽度，超过则修正</param>
     <param name="fixHeight">设置的最大修正高度，超过则修正</param>
     <param name="isCrop">是否需要剪裁 </param>
    **/
    private static Object[] cropImage(BufferedImage image, int imageWidth, int imageHeight, int cropWidth, int cropHeight, int cropX, int cropY, int fixWidth, int fixHeight)
    {
        int isCrop = 1;
        if (image.getHeight() == cropHeight && image.getWidth() == cropWidth)
        {
            isCrop = 0;
            return new Object[]{image, isCrop};
        }
        if (cropWidth == 0)
        {
            cropWidth = 1;
        }
        if (cropHeight == 0)
        {
            cropHeight = 1;
        }
        float wscale = (float)image.getWidth() / imageWidth;
        float hscale = (float)image.getHeight() / imageHeight;
        float scale = Math.min(wscale, hscale);
        cropWidth = (int)(cropWidth * scale);
        cropHeight = (int)(cropHeight * scale);
        cropX = (int)(cropX*scale);
        cropY = (int)(cropY*scale);
        if (fixWidth != 0 && fixHeight != 0)
        {
            if (cropWidth > fixWidth || cropHeight > fixHeight)
            {
                AutoSizeModel cropSize = initMaxWidthHeight(fixWidth, fixHeight, cropWidth, cropHeight);
                float cscale = (float) cropSize.getWidth()/cropWidth;
                int iw = (int)(image.getWidth()*cscale);
                int ih = (int)(image.getHeight()*cscale);
                image = resizeImage(image, iw, ih);
                cropWidth = cropSize.getWidth();
                cropHeight = cropSize.getHeight();
                cropX = (int)(cropX*cscale);
                cropY = (int)(cropY*cscale);
            }
        }

        if (image.getHeight() == cropHeight && image.getWidth() == cropWidth)
        {
            isCrop = 0;
            return new Object[]{image, isCrop};
        }

        BufferedImage bi = new BufferedImage(cropWidth, cropHeight, BufferedImage.TYPE_INT_RGB);

        for (int x = cropX; x < cropX+cropWidth; ++x) {
            for (int y = cropY; y < cropY+ cropHeight; ++y) {
                int rgb = image.getRGB(x, y);
                bi.setRGB(x - cropX, y - cropY, rgb);
            }
        }
        return new Object[]{bi, isCrop};
    }

    /**
     重新定义图片大小（按比例缩放）
     <param name="image"></param>
     <param name="imageWidth"></param>
     <param name="imageHeight"></param>
     **/
    public static BufferedImage resizeImage(BufferedImage image, int imageWidth, int imageHeight)
    {
        if (image.getHeight() == imageHeight && image.getWidth() == imageWidth)
        {
            return image;
        }

        if (imageWidth == 0)
        {
            imageWidth = 1;
        }
        if (imageHeight == 0)
        {
            imageHeight = 1;
        }

        BufferedImage bi = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();
        g.drawImage(image.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH), 0, 0,null);
        g.dispose();
        return bi;


    }

    /**
     按比例缩放到边框宽高效果，返回缩放后的宽高
     <param name="frameWidth">边框宽度</param>
     <param name="frameHeight">边框高度</param>
     <param name="width">现在的宽度</param>
     <param name="height">现在的高度</param>
    **/
    public static AutoSizeModel initMaxWidthHeight(int frameWidth, int frameHeight, int width, int height)
    {
        int w = frameWidth;
        int h = height * w / width;
        if (h > frameHeight)
        {
            h = frameHeight;
            w = width * h / height;
        }
        AutoSizeModel model = new AutoSizeModel();
        model.setWidth(w);
        model.setHeight(h);
        return model;
    }

    /**
     * 顺时针旋转90度（通过交换图像的整数像素RGB 值）
     **/
    public static BufferedImage rotateClockwise90(BufferedImage bi) {
        int width = bi.getWidth();
        int height = bi.getHeight();
        BufferedImage bufferedImage = new BufferedImage(height, width, bi.getType());
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                bufferedImage.setRGB(height - 1 - j, width - 1 - i, bi.getRGB(i, j));
        return bufferedImage;
    }

    /**
     *逆时针旋转90度（通过交换图像的整数像素RGB 值）
     */
    public static BufferedImage rotateCounterclockwise90(BufferedImage bi) {
        int width = bi.getWidth();
        int height = bi.getHeight();
        BufferedImage bufferedImage = new BufferedImage(height, width, bi.getType());
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                bufferedImage.setRGB(j, i, bi.getRGB(i, j));
        return bufferedImage;
    }

    /**
     * 旋转180度（通过交换图像的整数像素RGB 值）
     * @param bi
     * @return
     */
    public static BufferedImage rotate180(BufferedImage bi) {
        int width = bi.getWidth();
        int height = bi.getHeight();
        BufferedImage bufferedImage = new BufferedImage(width,height,bi.getType());
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                bufferedImage.setRGB( width - i-1,height-j-1, bi.getRGB(i, j));
        return bufferedImage;
    }

    /**
     * 水平翻转
     * @param bi
     * @return
     */
    public static BufferedImage rotateHorizon(BufferedImage bi) {
        int width = bi.getWidth();
        int height = bi.getHeight();
        BufferedImage bufferedImage = new BufferedImage(width,height,bi.getType());
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                bufferedImage.setRGB( width - i-1,j, bi.getRGB(i, j));
        return bufferedImage;
    }

    /**
     * 垂直翻转
     * @param bi
     * @return
     */
    public static BufferedImage rotateVertical(BufferedImage bi) {
        int width = bi.getWidth();
        int height = bi.getHeight();
        BufferedImage bufferedImage = new BufferedImage(width,height,bi.getType());
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                bufferedImage.setRGB(i,height-1-j, bi.getRGB(i, j));
        return bufferedImage;
    }


    /**
     * 另创建thumbnail目录，生成缩略图文件
     * @param filePath
     * @param limit
     * @throws IOException
     */
    public static int[] generateThumbnail(String filePath, int limit) throws IOException
    {
        String path = new File(filePath).getParent() + "/thumbnail";
        String filename = new File(filePath).getName();
        if (!ToolsUtil.existsDirectory(path))
        {
            ToolsUtil.forceDirectory(path);
        }
        BufferedImage image = ImageIO.read(new File(filePath));
        ImageInfo reduceImageInfo = new ImageInfo();
        reduceImageInfo.setWidth(limit);
        reduceImageInfo.setHeight(image.getHeight() * reduceImageInfo.getWidth() / image.getWidth());
        int[] result = new int[2];
        result[0] = image.getWidth();
        result[1] = image.getHeight();
        if (image.getWidth() <= limit && image.getHeight() <= limit)
        {
            String f = path + "/" + filename;
            ImageIO.write(image, ToolsUtil.getFileExtensionWithNoDian(new File(f)), new File(f));
            return result;
        }
        if (reduceImageInfo.getHeight() > limit)
        {
            reduceImageInfo.setHeight(limit);
            reduceImageInfo.setWidth(image.getWidth() * reduceImageInfo.getHeight() / image.getHeight());
        }

        if (reduceImageInfo.getHeight() == 0)
        {
            reduceImageInfo.setHeight(1);
        }
        if (reduceImageInfo.getWidth() == 0)
        {
            reduceImageInfo.setWidth(1);
        }

        BufferedImage bi = new BufferedImage(reduceImageInfo.getWidth(), reduceImageInfo.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();
        g.drawImage(image.getScaledInstance(reduceImageInfo.getWidth(), reduceImageInfo.getHeight(), Image.SCALE_SMOOTH), 0, 0,null);
        g.dispose();
        String ff = path + "/" + filename;
        ImageIO.write(bi, ToolsUtil.getFileExtensionWithNoDian(new File(ff)), new File(ff));
        return result;
    }



}
