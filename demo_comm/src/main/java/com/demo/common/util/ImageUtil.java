package com.ifa.common.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/** 
 * REVIEW
 * @Description:图片工具类 
 * @author mengjie.liu@baidao.com mengjie.liu
 * @date 2016年4月21日 上午9:39:20 
 *  
 */

public class ImageUtil {

	private static final String imgPattern = "<img\\s*[^>]*\\s*src=[\\\"|'](.*?)[\\\"|']\\s*[^>]*>";
	private static final Pattern patternForImgTag = Pattern.compile(imgPattern, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
	private static final CommonLogger logger = CommonLogger.getInstance(ImageUtil.class);

	/**
	 * 
	 * REVIEW
	 * @Description:判断是否为img标签 
	 * @param imgTag img标签
	 * @return    
	 * @author mengjie.liu@baidao.com  mengjie.liu  
	 * @date 2016年4月21日 上午9:40:30
	 */
	public static Boolean isImgTag(String imgTag) {
		Matcher matcher = patternForImgTag.matcher(imgTag);
		return matcher.matches();
	}

	/**
	 * 
	 * REVIEW
	 * @Description:判断字符串是否包含img标签 
	 * @param content
	 * @return    
	 * @author mengjie.liu@baidao.com  mengjie.liu  
	 * @date 2016年4月21日 上午9:45:01
	 */
	public static Boolean includeImgTag(String content) {
		Matcher matcherForTag = patternForImgTag.matcher(content);
		return matcherForTag.find();
	}

	/**
	 * 
	 * REVIEW
	 * @Description: 按wraper描述信息对图片进行压缩存储
	 * @param wraper    
	 * @author huan.wang@baidao.com  wanghuan  
	 * @date 2015年7月16日 下午3:21:41
	 */
	public static ImageFileWraper compress(ImageFileWraper wraper) {
		Image image = null;
		try {
			image = ImageIO.read(wraper.getImageFile());
		} catch (Exception e) {
			logger.error("read image file error", e);
			return null;
		}

		// 判断图片格式是否正确
		if (image.getWidth(null) == -1) {
			return null;
		}

		Integer width = wraper.getWidth();
		Integer height = wraper.getHeight();

		//未指定尺寸，则原文件尺寸压缩
		if (width == null || width == 0 || height == null || height == 0) {
			width = image.getWidth(null);
			height = image.getHeight(null);
		}

		//写文件
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(wraper.getNewImageFile());

			if (wraper.getImageFile().getName().toLowerCase().endsWith(".png")) {
				ImageIO.write(generateImageWithPng(wraper, image, width, height), "png", out);
			}//
			else {
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				encoder.encode(generateImageWithJpeg(wraper, image, width, height));
			}
		} catch (FileNotFoundException e) {
			logger.error("file not found when do image compress", e);
		} catch (ImageFormatException e) {
			logger.error("image format error when do image compress", e);
		} catch (IOException e) {
			logger.error("read image file error when write image", e);
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				logger.error("close stream when finished write image", e);
			}
		}

		wraper.setHeight(height);
		wraper.setWidth(width);
		return wraper;
	}

	private static BufferedImage generateImageWithPng(ImageFileWraper wraper, Image image, Integer width, Integer height) {
		//构建新的图片
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		//将原图放大或缩小后画下来:并且保持png图片放大或缩小后背景色是透明的而不是黑色
		Graphics2D g2d = bufferedImage.createGraphics();
		bufferedImage = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
		g2d.dispose();
		g2d = bufferedImage.createGraphics();
		Image from = image.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
		g2d.drawImage(from, 0, 0, null);
		g2d.dispose();

		return bufferedImage;
	}

	private static BufferedImage generateImageWithJpeg(ImageFileWraper wraper, Image image, Integer width, Integer height) {
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		bufferedImage.getGraphics().drawImage(image.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);

		return bufferedImage;
	}
}
