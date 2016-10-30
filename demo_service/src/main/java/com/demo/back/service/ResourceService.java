package com.demo.back.service;

import com.demo.back.po.Resource;
import com.demo.common.vo.Page;

public interface ResourceService {

	void checkResourceParam(Resource role);

	int saveResource(Resource role);

	int deleteByIds(Long[] ids);

	int updateResourceById(Resource role);

	int updateResourceSelectiveById(Resource role);

	Resource getResourceById(Long id);

	Page<Resource> getPageByCondition(Page<Resource> page, Resource role);
}
