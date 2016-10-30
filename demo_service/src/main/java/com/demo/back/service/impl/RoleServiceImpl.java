package com.demo.back.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.back.dao.RoleMapper;
import com.demo.back.po.Role;
import com.demo.back.service.RoleService;
import com.demo.common.constant.ErrorCode;
import com.demo.common.exception.BusinessException;
import com.demo.common.util.StringUtil;
import com.demo.common.vo.Page;
import com.google.common.base.Preconditions;


@Service(value="roleService")
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleMapper roleMapper;

	@Override
	public void checkRoleParam(Role role) {
		Preconditions.checkArgument(role != null, "role is null.");

		String code = role.getCode();
		if (StringUtil.isEmpty(code)) {
			throw new BusinessException(ErrorCode.PARAM_CODE_NOT_EMPTY);
		}

		String name = role.getName();
		if (StringUtil.isEmpty(name)) {
			throw new BusinessException(ErrorCode.PARAM_NAME_NOT_EMPTY);
		}
	}

	@Override
	public int saveRole(Role role) {
		this.checkRoleParam(role);

		Date currentDate = new Date();
		role.setCreateTime(currentDate);
		role.setUpdateTime(currentDate);
		return roleMapper.save(role);
	}

	@Override
	public int deleteByIds(Long[] ids) {
		return roleMapper.deleteByIds(ids);
	}

	@Override
	public int updateRoleById(Role role) {
		this.checkRoleParam(role);
		Preconditions.checkArgument(role.getId() != null, "id is null.");

		role.setUpdateTime(new Date());
		return roleMapper.updateById(role);
	}

	@Override
	public int updateRoleSelectiveById(Role role) {
		Preconditions.checkArgument(role != null, "role is null.");
		Preconditions.checkArgument(role.getId() != null, "id is null.");

		role.setUpdateTime(new Date());
		return roleMapper.updateSelectiveById(role);
	}

	@Override
	public Role getRoleById(Long id) {
		return roleMapper.getById(id);
	}

	@Override
	public Page<Role> getPageByCondition(Page<Role> page, Role role) {
		int count = roleMapper.getCountByCondition(page, role);
		page.setTotal(count);
		if (count != 0) {
			List<Role> roleList = roleMapper.getListByCondition(page, role);
			page.setRows(roleList);
		}
		return page;
	}

}
