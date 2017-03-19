package com.demo.back.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.back.po.Resource;
import com.demo.back.service.ResourceService;
import com.demo.common.constant.ErrorCode;
import com.demo.common.exception.BusinessException;
import com.demo.common.vo.Page;
import com.demo.common.vo.Result;

@Controller
@RequestMapping("admin/resource")
public class ResourceController {

	@Autowired
	private ResourceService resourceService;

	@RequestMapping(value = "toList", method = { RequestMethod.GET, RequestMethod.POST })
	public String toResourceList(Model model) {
		return "/resource/resourceList";
	}

	@ResponseBody
	@RequestMapping(value = "list", method = { RequestMethod.GET, RequestMethod.POST })
	public Object getResourceList(HttpServletRequest request, HttpServletResponse response, Page<Resource> paramPage, Resource paramResource) {
		Page<Resource> resultPage = resourceService.getPageByCondition(paramPage, paramResource);
		return resultPage;
	}

	@RequestMapping(value = "toAdd", method = { RequestMethod.GET, RequestMethod.POST })
	public String toAddResource(Model model) {
		return "/resource/addResource";
	}

	@ResponseBody
	@RequestMapping(value = "add", method = { RequestMethod.POST })
	public Object addResource(HttpServletRequest request, Resource resource) {
		Result result = new Result();

		try {
			resourceService.saveResource(resource);
			result.setErrorCode(ErrorCode.SUCCESS);
		} catch (BusinessException e) {
			result.setErrorCode(e.getErrorCode());
		}

		return result;
	}

	@RequestMapping(value = "toEdit", method = { RequestMethod.GET, RequestMethod.POST })
	public String toEditResource(Model model, Long uniqueId) {
		Resource resource = resourceService.getResourceById(uniqueId);
		model.addAttribute("resource", resource);
		return "/resource/editResource";
	}

	@ResponseBody
	@RequestMapping(value = "edit", method = { RequestMethod.POST })
	public Object editResource(HttpServletRequest request, Resource resource) {
		Result result = new Result();

		try {
			resourceService.updateResourceById(resource);
			result.setErrorCode(ErrorCode.SUCCESS);
		} catch (BusinessException e) {
			result.setErrorCode(e.getErrorCode());
		}

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "delete", method = {RequestMethod.GET, RequestMethod.POST })
	public Object deleteResourceList(Long[] uniqueIds) {
		Result result = new Result();
		if (uniqueIds == null) {
			result.setErrorCode(ErrorCode.PARAM_NO_SELECTED_ITEM);
			return result;
		}

		try {
			resourceService.deleteByIds(uniqueIds);
			result.setErrorCode(ErrorCode.SUCCESS);
		} catch (BusinessException e) {
			result.setErrorCode(e.getErrorCode());
		}

		return result;
	}

}
