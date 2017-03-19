package com.demo.back.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.demo.back.po.Resource;
import com.demo.common.vo.Page;

public interface ResourceMapper {

	int save(Resource resource);

	int deleteByIds(@Param("ids")Long[] ids);

	int updateById(Resource resource);

	int updateSelectiveById(Resource resource);

	Resource getById(Long id);

	List<Resource> getListByCondition(@Param("page") Page<Resource> page, @Param("resource") Resource resource);

	int getCountByCondition(@Param("page") Page<Resource> page, @Param("resource") Resource resource);
}
