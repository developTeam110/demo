package com.demo.back.web;

public class BaseController {
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
	 
}
