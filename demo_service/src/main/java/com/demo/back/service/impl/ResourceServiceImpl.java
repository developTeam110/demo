package com.demo.back.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.back.dao.ResourceMapper;
import com.demo.back.po.Resource;
import com.demo.back.service.ResourceService;
import com.demo.common.constant.ErrorCode;
import com.demo.common.exception.BusinessException;
import com.demo.common.util.StringUtil;
import com.demo.common.vo.Page;
import com.google.common.base.Preconditions;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService{

	@Autowired
	private ResourceMapper resourceMapper;

	@Override
	public void checkResourceParam(Resource resource) {
		Preconditions.checkArgument(resource != null, "role is null.");

		String code = resource.getCode();
		if (StringUtil.isEmpty(code)) {
			throw new BusinessException(ErrorCode.PARAM_CODE_NOT_EMPTY);
		}

		String name = resource.getName();
		if (StringUtil.isEmpty(name)) {
			throw new BusinessException(ErrorCode.PARAM_NAME_NOT_EMPTY);
		}

		String url = resource.getUrl();
		if (StringUtil.isEmpty(url)) {
			throw new BusinessException(ErrorCode.PARAM_URL_NOT_EMPTY);
		}
	}

	@Override
	public int saveResource(Resource resource) {
		this.checkResourceParam(resource);
		Date currentDate = new Date();
		resource.setCreateTime(currentDate);
		resource.setUpdateTime(currentDate);
		return resourceMapper.save(resource);
	}

	@Override
	public int deleteByIds(Long[] ids) {
		return resourceMapper.deleteByIds(ids);
	}

	@Override
	public int updateResourceById(Resource resource) {
		this.checkResourceParam(resource);
		resource.setUpdateTime(new Date());
		return resourceMapper.updateById(resource);
	}

	@Override
	public int updateResourceSelectiveById(Resource resource) {
		resource.setUpdateTime(new Date());
		return resourceMapper.updateSelectiveById(resource);
	}

	@Override
	public Resource getResourceById(Long id) {
		return resourceMapper.getById(id);
	}

	@Override
	public Page<Resource> getPageByCondition(Page<Resource> page, Resource resource) {
		int count = resourceMapper.getCountByCondition(page, resource);
		page.setTotal(count);
		if (count != 0) {
			List<Resource> resourceList = resourceMapper.getListByCondition(page, resource);
			page.setRows(resourceList);
		}
		return page;
	}

}
