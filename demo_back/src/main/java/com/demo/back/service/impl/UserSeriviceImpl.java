package com.demo.back.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.demo.back.dao.UserMapper;
import com.demo.back.po.User;
import com.demo.back.service.UserCacheService;
import com.demo.back.service.UserService;
import com.demo.common.constant.ErrorCode;
import com.demo.common.exception.BusinessException;
import com.demo.common.util.EmailUtil;
import com.demo.common.util.LoginNameUtil;
import com.demo.common.util.PasswordUtil;
import com.demo.common.util.PhoneUtil;
import com.demo.common.util.StringUtil;
import com.demo.common.vo.Page;
import com.google.common.base.Preconditions;

/**
 * @author Administrator
 *
 */
@Service(value="userService")
public class UserSeriviceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserCacheService userCacheService;

	@Override
	public int saveUser(User user) {
		Preconditions.checkArgument(user != null, "user is null.");

		user.setUsername(StringUtil.generateRandomUsernameByUUID());
		user.setInnerFlag(user.getInnerFlag() == null ? false : user.getInnerFlag());
		Date currentDate = new Date();
		user.setCreateTime(currentDate);
		user.setUpdateTime(currentDate);
		return userMapper.save(user);
	}

	
	/**
	 * 校验用户信息参数合法性
	 */
	@Override
	public void checkUserParam(User user) {
		Preconditions.checkArgument(user != null, "user is null.");

		/*密码校验*/
		String password = user.getPassword();
		if (StringUtil.isEmpty(password)) {
			throw new BusinessException(ErrorCode.PARAM_PASSWORD_NOT_EMPTY);
		}

		if (!PasswordUtil.isValid(password)) {
			throw new BusinessException(ErrorCode.PARAM_PASSWORD_INVALID);
		}

		/*昵称校验*/
		String nickname = user.getNickname();
		if (StringUtil.isEmpty(nickname)) {
			throw new BusinessException(ErrorCode.PARAM_NICKNAME_NOT_EMPTY);
		}

		/*登录名校验*/
		String loginString = user.getLoginString();
		if (StringUtil.isNotEmpty(loginString)) {
			if (!LoginNameUtil.isValid(loginString)) {
				throw new BusinessException(ErrorCode.PARAM_LOGIN_NAME_INVALID);
			}

			User paramUser = new User();
			paramUser.setExcludeUsername(user.getUsername());
			paramUser.setLoginString(loginString);
			int count = userMapper.getCountByCondition(null, paramUser);
			if (count > 0) {
				throw new BusinessException(ErrorCode.PARAM_LOGIN_NAME_IS_EXISTED);
			}
		}

		/*邮箱校验*/
		String email = user.getEmail();
		if (StringUtil.isNotEmpty(email)) {
			if (!EmailUtil.isValid(email)) {
				throw new BusinessException(ErrorCode.PARAM_EMAIL_INVALID);
			}

			User paramUser = new User();
			paramUser.setExcludeUsername(user.getUsername());
			paramUser.setEmail(email);
			int count = userMapper.getCountByCondition(null, paramUser);
			if (count > 0) {
				throw new BusinessException(ErrorCode.PARAM_EMAIL_IS_EXISTED);
			}
		}

		/*电话号校验*/
		String phone = user.getPhone();
		if (StringUtil.isNotEmpty(phone)) {
			if (!PhoneUtil.isValid(phone)) {
				throw new BusinessException(ErrorCode.PARAM_EMAIL_INVALID);
			}

			User paramUser = new User();
			paramUser.setExcludeUsername(user.getUsername());
			paramUser.setPhone(PhoneUtil.encodeByAes(phone));
			int count = userMapper.getCountByCondition(null, paramUser);
			if (count > 0) {
				throw new BusinessException(ErrorCode.PARAM_PHONE_IS_EXISTED);
			}
		}
	}

	@Override
	public int updateUserByUsername(User user) {
		Preconditions.checkArgument(user != null, "user is null.");
		Preconditions.checkArgument(StringUtil.isNotEmpty(user.getUsername()), "username is empty.");

		user.setInnerFlag(user.getInnerFlag() == null ? false : user.getInnerFlag());
		Date currentDate = new Date();
		user.setUpdateTime(currentDate);

		int rows = userMapper.updateByUsername(user);
		if (rows > 0) {
			if (User.STATUS.DELETE.code().equals(user.getStatus()) || StringUtil.isNotEmpty(user.getPassword())) { //用户删除或秘密修改后清理相应的缓存信息
				userCacheService.deleteLoginUser(user.getUsername());
				userCacheService.deleteUser(user.getUsername());
			} else { //更新用户缓存
				userCacheService.saveUser(user);
			}
		}

		return rows;
	}


	@Override
	public int updateUserSelectiveByUsername(User user) {
		Preconditions.checkArgument(user != null, "user is null.");
		Preconditions.checkArgument(StringUtil.isNotEmpty(user.getUsername()), "username is empty.");

		user.setUpdateTime(new Date());
		int rows = userMapper.updateSelectiveByUsername(user);
		if (rows > 0) {
			if (User.STATUS.DELETE.code().equals(user.getStatus()) || StringUtil.isNotEmpty(user.getPassword())) { //用户删除或秘密修改后清理相应的缓存信息
				userCacheService.deleteLoginUser(user.getUsername());
				userCacheService.deleteUser(user.getUsername());
			} else { //更新用户缓存
				userCacheService.saveUser(user);
			}
		}

		return rows;
	}

	@Override
	public User getUserByUsername(String username) {
		return userMapper.getByUsername(username);
	}

	@Override
	public User getUserByloginNameAndPwd(String loginName, String pwd) {
		return userMapper.getByloginNameAndPwd(loginName, pwd);
	}

	@Override
	public User getUserFromCacheOrDbByUsername(String username) {
		Preconditions.checkArgument(StringUtil.isNotEmpty(username), "username is empty.");

		User user = userCacheService.getUser(username);
		if (user != null) {
			return user;
		}

		user = userMapper.getByUsername(username);
		if (user != null) {
			userCacheService.saveUser(user);
		}
		return user;
	}

	@Override
	public Page<User> getPageByCondition(Page<User> page, User user) {
		int count = userMapper.getCountByCondition(page, user);
		page.setTotal(count);
		if (count != 0) {
			List<User> userList = userMapper.getListByCondition(page, user);
			if (!CollectionUtils.isEmpty(userList)) {
				for (User userModel : userList) {
					if (StringUtil.isNotEmpty(userModel.getPhone())) {
						userModel.setPhone(PhoneUtil.fuzzy(PhoneUtil.decodeByAes(userModel.getPhone())));
					}
				}
			}
			page.setRows(userList);
		}
		return page;
	}

}
