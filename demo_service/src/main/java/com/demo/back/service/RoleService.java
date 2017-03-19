package com.demo.back.service;

import com.demo.back.po.Role;
import com.demo.common.vo.Page;

public interface RoleService {

	void checkRoleParam(Role role);

	int saveRole(Role role);

	int deleteByIds(Long[] ids);

	int updateRoleById(Role role);

	int updateRoleSelectiveById(Role role);

	Role getRoleById(Long id);

	Page<Role> getPageByCondition(Page<Role> page, Role role);
}
