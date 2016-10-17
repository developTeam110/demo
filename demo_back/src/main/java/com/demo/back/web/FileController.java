package com.demo.back.web;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.demo.common.constant.ErrorCode;
import com.demo.common.util.FileUtil;
import com.demo.common.util.ImageUtil;
import com.demo.common.vo.Result;


@Controller
@RequestMapping("admin/file")
public class FileController extends BaseController {

	@ResponseBody
	@RequestMapping(value = "image/upload", method = { RequestMethod.GET, RequestMethod.POST })
	public Object uploadIamge(Model model, MultipartFile file, HttpServletRequest request) {
		Result result = new Result();

        //String path = request.getContextPath();

		if (file == null || file.getSize() == 0) {
			result.setErrorCode(ErrorCode.PARAM_IS_BLANK);
			return result;
		}

		if (!ImageUtil.isAllowedSuffix(FileUtil.getFileSuffix(file.getOriginalFilename()))) {
			result.setErrorCode(ErrorCode.PARAM_IMAGE_SUFFIX_INVALID);
			return result;
		}

		File localImageFile = new File(getImageStoreDirectory(parentDir, date));
		if (!localImageFile.exists()) {
			localImageFile.mkdirs();
		}

		localImageFile = new File(dir, createRandomName(file.getOriginalFilename()));

		if (localImageFile == null) {
			result.setCode(-1);
			result.setMsg("文件类型不符合要求");

			return CrossDomainUtil.transferToCrossDomainString(null, result);
		}

		//持久到本地
		try {
			file.transferTo(localImageFile);
		} catch (Exception e) {
			logger.error("create image file failed!", e);
		}

		String diskPath = localImageFile.getAbsolutePath();
		logger.info("create image file successful! path is [{}]", diskPath);

		result.setCode(1);
		result.setMsg("成功");
		result.addDatas("type", type);
		//图片压缩存储
		if (type == null) {
			type = CommonConstants.IMAGE_UPLOAD_TYPE_HEAD_IMAGE;
		}

		if (type.equals(CommonConstants.IMAGE_UPLOAD_TYPE_HEAD_IMAGE)) {
			//头像缩放存储一份
			int size = SystemConfig.FILE_USER_HEAD_IMAGE_COMPRESS_SIZE.getIntegerValue();
			ImageFileWraper wraper = ImageUtil.compress(new ImageFileWraper(localImageFile, size));

			if (wraper != null)
				result.addDatas("path" + size, FileUtil.imageHttpWholePath(wraper.getNewImageFile().getAbsolutePath()));
		}// 
		else if (type.equals(CommonConstants.IMAGE_UPLOAD_TYPE_VIDEO_PREVIEW)) {
			int width = SystemConfig.FILE_VIDEO_PREVIEW_IMAGE_COMPRESS_WIDTH.getIntegerValue();
			int height = SystemConfig.FILE_VIDEO_PREVIEW_IMAGE_COMPRESS_HEIGHT.getIntegerValue();
			ImageFileWraper wraper = ImageUtil.compress(new ImageFileWraper(localImageFile, width, height));

			if (wraper != null)
				result.addDatas("path" + type, FileUtil.imageHttpWholePath(wraper.getNewImageFile().getAbsolutePath()));
		}// 
		else if (type.equals(CommonConstants.IMAGE_UPLOAD_TYPE_CONSOLE) && isCompress != null && isCompress) {//后台上传图片是否压缩
			int size = SystemConfig.FILE_USER_HEAD_IMAGE_COMPRESS_SIZE.getIntegerValue();
			ImageFileWraper wraper = ImageUtil.compress(new ImageFileWraper(localImageFile, size));

			if (wraper != null)
				result.addDatas("path" + size, FileUtil.imageHttpWholePath(wraper.getNewImageFile().getAbsolutePath()));
		}

		ImageFileWraper wraper = null;
		if (type.equals(CommonConstants.IMAGE_UPLOAD_TYPE_DEFAULT) == false) {
			//非默认情况，原文件压缩存储
			wraper = ImageUtil.compress(new ImageFileWraper(localImageFile));
		}
		String path = FileUtil.imageHttpWholePath(localImageFile.getAbsolutePath());
		result.addDatas("path", path);

		//图片存储入图片表
		Image image = new Image();
		image.setUsername(loginedUser.getUsername());
		image.setPhysicsUrl(localImageFile.getAbsolutePath());
		image.setBrowseUrl(path);
		image.setType(type);
		imageService.saveImage(image);

		if (wraper != null) {
			result.addDatas("h", wraper.getHeight());
			result.addDatas("w", wraper.getWidth());
		}

		return CrossDomainUtil.transferToCrossDomainString(null, result);
        return result;
	}
}
