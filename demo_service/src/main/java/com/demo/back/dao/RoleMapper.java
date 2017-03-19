package com.demo.back.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.demo.back.po.Role;
import com.demo.common.vo.Page;

public interface RoleMapper {

	int save(Role role);

	int deleteByIds(@Param("ids")Long[] ids);

	int updateById(Role role);

	int updateSelectiveById(Role role);

	Role getById(Long id);

	List<Role> getListByCondition(@Param("page") Page<Role> page, @Param("role") Role role);

	int getCountByCondition(@Param("page") Page<Role> page, @Param("role") Role role);

}
