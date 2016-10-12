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
import com.demo.common.vo.Result;


@Controller
@RequestMapping("admin/file")
public class FileController extends BaseController {
	   private String allowSuffix = "jpg,png,gif,jpeg";//允许文件格式
	    private long allowSize = 2L;//允许文件大小
	    private String fileName;
	    private String[] fileNames;
	     
	    public String getAllowSuffix() {
	        return allowSuffix;
	    }
	 
	    public void setAllowSuffix(String allowSuffix) {
	        this.allowSuffix = allowSuffix;
	    }
	 
	    public long getAllowSize() {
	        return allowSize*1024*1024;
	    }
	 
	    public void setAllowSize(long allowSize) {
	        this.allowSize = allowSize;
	    }
	 
	    public String getFileName() {
	        return fileName;
	    }
	 
	    public void setFileName(String fileName) {
	        this.fileName = fileName;
	    }
	 
	    public String[] getFileNames() {
	        return fileNames;
	    }
	 
	    public void setFileNames(String[] fileNames) {
	        this.fileNames = fileNames;
	    }
	 
	    /**
	     * 
	     * <p class="detail">
	     * 功能：重新命名文件
	     * </p>
	     * @author wangsheng
	     * @date 2014年9月25日 
	     * @return
	     */
	    private String getFileNameNew(){
	        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	        return fmt.format(new Date());
	    }

	@RequestMapping(value = "toUpload", method = { RequestMethod.GET })
	public String toUpdate(Model model, HttpServletRequest request) {
		return "file/updateFile";
	}

	@ResponseBody
	@RequestMapping(value = "image/upload", method = { RequestMethod.GET, RequestMethod.POST })
	public Object uploadIamge(Model model, MultipartFile file, HttpServletRequest request) {
		Result result = new Result();
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
        try {
        	String destDir = "/upload/user/";
            int index = 0;
                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
                int length = getAllowSuffix().indexOf(suffix);
                if(length == -1){
                    throw new Exception("请上传允许格式的文件");
                }
                if(file.getSize() > getAllowSize()){
                    throw new Exception("您上传的文件大小已经超出范围");
                }
                String realPath = request.getSession().getServletContext().getRealPath("/");
                File destFile = new File(realPath+destDir);
                if(!destFile.exists()){
                    destFile.mkdirs();
                }
                String fileNameNew = getFileNameNew()+"."+suffix;//
                File f = new File(destFile.getAbsoluteFile()+"\\"+fileNameNew);
                file.transferTo(f);
                f.createNewFile();
                fileNames[index++] =basePath+destDir+fileNameNew;
        } catch (Exception e) {
        	result.setErrorCode(ErrorCode.SYSTEM_INNER_ERROR);
        }

        return result;
	}
}
