package com.demo.common.util;

import java.util.UUID;


/**
* REVIEW
* @Description: TODO
* @author jingkun.wang 
* @date 2016年10月13日 上午10:34:53 
* 
*/
public class FileUtil {

	public static final String FILE_SEPRATOR = "/";

	/**
	* REVIEW
	* @Description: 获取文件的后缀
	* @param filename 文件名称
	* @return 后缀信息
	* @author jingkun.wang
	* @date 2016年10月13日 上午10:35:46
	*/
	public static String getFileSuffix(String filename) {
		if (StringUtil.isEmpty(filename)) {
			return null;
		}

		return filename.substring(filename.lastIndexOf(".")).toLowerCase();
	}

	/**
	* REVIEW
	* @Description: 生成随机的文件名
	* @return 10位长度的随机字符串
	* @author jingkun.wang
	* @date 2016年10月13日 上午10:40:02
	*/
	public static String generateRandomName() {
		return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
	}
}
