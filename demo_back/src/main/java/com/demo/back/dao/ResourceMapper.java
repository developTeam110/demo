package com.demo.back.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.demo.back.po.Resource;
import com.demo.back.po.Role;
import com.demo.back.po.User;
import com.demo.common.vo.Page;

public interface ResourceMapper {

	int save(Resource resource);

	int updateById(Resource resource);

	int updateSelectiveById(Resource resource);

	User getById(Long id);

	List<Role> getListByCondition(@Param("page") Page<User> page, @Param("resource") Resource resource);

	int getCountByCondition(@Param("page") Page<User> page, @Param("resource") Resource resource);
}
