package com.demo.common.util;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
* REVIEW
* @Description: 图片工具类
* @author jingkun.wang 
* @date 2016年10月13日 上午10:42:37 
* 
*/
public class ImageUtil {

	private static final String ALLOWED_IMAGE_SUFFIX = ".bmp|.jpg|.jpeg|.gif|.png";
	private static final String BASE_PATH = "/usr/local/static/uploadstore";

	/**
	* REVIEW
	* @Description: 是否是被允许的后缀
	* @param suffix 后缀名（例：.png）
	* @return 是否被允许
	* @author jingkun.wang
	* @date 2016年10月13日 上午10:46:29
	*/
	public static boolean isAllowedSuffix(String suffix) {
		if (StringUtil.isEmpty(suffix)) {
			return false;
		}

		String[] allowedSuffies = ALLOWED_IMAGE_SUFFIX.split("\\|");
		for (String allow : allowedSuffies) {
			if (suffix.equals(allow))
				return true;
		}

		return false;
	}

	/**
	* REVIEW
	* @Description: 获取图片存储目录路径
	* @param currentDate 当前时间
	* @return 图片存储目录
	* @author jingkun.wang
	* @date 2016年10月13日 上午11:09:29
	*/
	public static String getImageStorePath(Date currentDate) {
		//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path
		String fileSeprator = FileUtil.FILE_SEPRATOR;
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DATE);

		return new String(new StringBuilder(BASE_PATH).append(fileSeprator).append(year).append(fileSeprator).append(month).append(fileSeprator).append(day).append(fileSeprator));
	}

	/**
	* REVIEW
	* @Description: 生成随机的文件名称字符串
	* @return 名称字符串
	* @author jingkun.wang
	* @date 2016年10月13日 上午11:09:29
	*/
	public static String generateRandomName() {
		return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
	}
}
