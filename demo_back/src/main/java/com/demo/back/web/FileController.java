package com.demo.back.web;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private final static Logger logger = LoggerFactory.getLogger(FileController.class);

	@RequestMapping(value = "toUpload", method = { RequestMethod.GET })
	public String toUpdate(Model model, HttpServletRequest request) {
		return "file/updateFile";
	}

	@ResponseBody
	@RequestMapping(value = "image/upload", method = { RequestMethod.GET, RequestMethod.POST })
	public Object uploadIamge(Model model, MultipartFile file, HttpServletRequest request) {
		Result result = new Result();

		if (file == null || file.getSize() == 0) {
			result.setErrorCode(ErrorCode.PARAM_IS_BLANK);
			return result;
		}

		String fileSuffix = FileUtil.getFileSuffix(file.getOriginalFilename());
		if (!ImageUtil.isAllowedSuffix(fileSuffix)) {
			result.setErrorCode(ErrorCode.PARAM_IMAGE_SUFFIX_INVALID);
			return result;
		}

		try {
			Date currentDate = new Date();
			File localImageFile = new File(ImageUtil.getImageStorePath(currentDate));
			if (!localImageFile.exists()) {
				localImageFile.mkdirs();
			}
			localImageFile = new File(localImageFile, ImageUtil.generateRandomName() + fileSuffix);

			file.transferTo(localImageFile);//持久到本地

//			String diskPath = localImageFile.getAbsolutePath();
			//图片存储入图片表
//			Image image = new Image();
//			image.setUsername(loginedUser.getUsername());
//			image.setPhysicsUrl(localImageFile.getAbsolutePath());
//			image.setBrowseUrl(path);
//			image.setType(type);
//			imageService.saveImage(image);

			result.setErrorCode(ErrorCode.SUCCESS);
		} catch (Exception e) {
			logger.error("uploadIamge occurs system error caused by: ", e);
		}

		return result;
	}
}
