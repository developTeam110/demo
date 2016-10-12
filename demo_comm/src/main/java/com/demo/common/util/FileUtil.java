package com.ifa.common.util;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/** 
 * REVIEW
 * @Description: 
 * @author huan.wang@baidao.com wanghuan
 * @date 2015年7月15日 下午8:02:39 
 *  
 */

public class FileUtil {
	/**
	 * 
	 * REVIEW
	 * @Description: 获取文件后缀，包括“.”
	 * @param filename
	 * @return    
	 * @author huan.wang@baidao.com  wanghuan  
	 * @date 2015年7月15日 下午8:15:28
	 */
	public static String getFileSuffix(String filename) {
		if (StringUtils.hasLength(filename))
			return filename.substring(filename.lastIndexOf(".")).toLowerCase();

		return null;
	}

	/**
	 * 
	 * REVIEW
	 * @Description: 根据文件名，随机生成新的文件名，文件后缀保持一致
	 * @param filename
	 * @return    
	 * @author huan.wang@baidao.com  wanghuan  
	 * @date 2015年7月15日 下午8:11:54
	 */
	public static String createRandomName(String filename) {
		return UUID.randomUUID().toString().replace("-", "").substring(0, 10) + getFileSuffix(filename);
	}

	/**
	 * 
	 * REVIEW
	 * @Description: 此文件是否可作为头像文件上传
	 * @param filename
	 * @return    
	 * @author huan.wang@baidao.com  wanghuan  
	 * @date 2015年7月15日 下午8:19:06
	 */
	public static boolean canHeadImageUpload(String filename) {
		String suffix = getFileSuffix(filename);
		if (suffix == null)
			return false;

		String[] allowSuffies = SystemConfig.FILE_IMAGE_UPLOAD_ALLOW_FORMART.getArrayValue();
		for (String allow : allowSuffies) {
			if (suffix.equals(allow))
				return true;
		}

		return false;
	}

	/**
	 * 
	 * REVIEW
	 * @Description: 根据上传文件，初始化头像存储目录及文件对象
	 * @param file
	 * @return    如果上传的文件不是图片类型，将返回空置
	 * @author huan.wang@baidao.com  wanghuan  
	 * @date 2015年7月15日 下午8:06:36
	 */
	public static File initLocalDiskHeadImageFile(MultipartFile file) {
		return initLocalDiskHeadImageFile(file, null, new Date());
	}

	/**
	 * 
	 * REVIEW
	 * @Description: 根据上传文件，初始化头像存储目录及文件对象
	 * @param file
	 * @param parentDir 创建指定的父存储目录
	 * @return    如果上传的文件不是图片类型，将返回空置
	 * @author huan.wang@baidao.com  wanghuan  
	 * @date 2015年7月16日 下午5:05:45
	 */
	public static File initLocalDiskHeadImageFile(MultipartFile file, String parentDir, Date date) {
		if (!canHeadImageUpload(file.getOriginalFilename())) {
			return null;
		}

		File dir = new File(getImageStoreDirectory(parentDir, date));
		if (dir.exists() == false) {
			dir.mkdirs();
		}

		dir = new File(dir, createRandomName(file.getOriginalFilename()));

		return dir;
	}

	public static String getImageStoreDirectory(String dir) {
		return getImageStoreDirectory(dir, new Date());
	}

	/**
	 * 
	 * REVIEW
	 * @Description: 获取图片存储目录，格式base/yearmonth/day/{dir}/{dayBetween}
	 * @param dir
	 * @param date
	 * @return    
	 * @author huan.wang@baidao.com  wanghuan  
	 * @date 2015年7月16日 下午5:30:17
	 */
	public static String getImageStoreDirectory(String dir, Date date) {
		String basePath = UrlConfig.FILE_USER_HEAD_IMAGE_STORE_BASE_PATH.getValue();
		if (basePath.endsWith(FILE_SEPRATOR)) {
			basePath = basePath.substring(0, basePath.length() - 1);
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DATE);

		return new String(new StringBuilder(basePath).append(FILE_SEPRATOR).append(year).append(month).append(FILE_SEPRATOR).append(day).append(FILE_SEPRATOR).append(dir == null ? "" : dir)
				.append(dir == null ? "" : FILE_SEPRATOR).append(dir == null ? "" : DateUtil.getCountofDates(new Date(), date)));
	}

	/**
	 * 
	 * REVIEW
	 * @Description: 根据磁盘路径换算图片的Http访问地址
	 * @param diskPath
	 * @return    
	 * @author huan.wang@baidao.com  wanghuan  
	 * @date 2015年7月16日 下午1:01:16
	 */
	public static String imageHttpWholePath(String diskPath) {
		if (diskPath == null)
			return null;

		diskPath = diskPath.replace("\\", FILE_SEPRATOR);
		String basePath = UrlConfig.FILE_USER_HEAD_IMAGE_STORE_BASE_PATH.getValue();
		String[] dirs = basePath.split(FILE_SEPRATOR);
		String parentImageDir = null;

		if (dirs.length > 2) {
			parentImageDir = dirs[dirs.length - 1].trim();
			if (parentImageDir.equals(FILE_SEPRATOR)) {
				parentImageDir = dirs[dirs.length - 2];
			}
		} else {
			parentImageDir = dirs[0];
		}

		//硬盘相对存储路径
		String diskStorePath = diskPath.substring(diskPath.indexOf(parentImageDir) + parentImageDir.length());

		String httpBase = UrlConfig.FILE_USER_HEAD_IMAGE_HTTP_MAPPING.getValue();
		if (httpBase.endsWith(FILE_SEPRATOR) && diskStorePath.startsWith(FILE_SEPRATOR)) {
			diskStorePath = diskStorePath.substring(1);
		}
		String httpWholePath = httpBase + diskStorePath;
		return httpWholePath;
	}
}
