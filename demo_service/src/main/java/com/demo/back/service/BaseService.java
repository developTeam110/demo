package com.demo.back.service;

import java.util.List;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public abstract class BaseService<T> {
	
	/**
	 * 由子类实现该方法，返回spring注入的mapper对象
	 * @return
	 */
	public abstract Mapper<T> getMapper();
	
	/**
	 * 根据ID查询数据
	 * @param id
	 * @return
	 */
	public T queryById(Long id){
		return getMapper().selectByPrimaryKey(id);
	}
	
	/**
	 * 查询所有数据
	 * @return
	 */
	public List<T> queryAll(){
		return getMapper().select(null);
	}
	
	/**
	 * 根据条件查询数据集合
	 * @param param
	 * @return
	 */
	public List<T> queryListByWhere(T param){
		return getMapper().select(param);
	}

	/**
	 * 查询数量
	 * @param param
	 * @return
	 */
	public Integer queryCount(T param){
		return getMapper().selectCount(param);
	}
	
	/**
	 * 查询数量
	 * @param param
	 * @return
	 */
	public Integer queryCount(){
		return getMapper().selectCount(null);
	}
	/**
	 * 分页查询
	 * @param param 查询条件
	 * @param page 当前页
	 * @param rows 页面大小
	 * @return
	 */
	public PageInfo<T> queryPageListByWhere(T param , Integer page , Integer rows){
		//设置分页参数
		PageHelper.startPage(page, rows);
		List<T> list = this.queryListByWhere(param);
		return new PageInfo<T>(list);
	}
	
	/**
	 * 查询一条记录
	 * @param t
	 * @return
	 */
	public T queryOne(T t){
		return getMapper().selectOne(t);
	}
	
	/**
	 * 新增数据
	 * @param t
	 * @return
	 */
	public Integer save (T t){
		return getMapper().insert(t);
	}
	
	/**
	 * 新增非空字段数据
	 * @param t
	 * @return
	 */
	public Integer saveSelective(T t){
		return this.getMapper().insertSelective(t);
	}
	/**
	 * 根据ID更新数据
	 * @param t
	 * @return
	 */
	public Integer updateById(T t){
		return this.getMapper().updateByPrimaryKey(t);
	}
	
	/**
	 * 根据主键ID更新数据(非空字段)
	 * @param t
	 * @return
	 */
	public Integer updateSelectiveById(T t){
		return this.getMapper().updateByPrimaryKeySelective(t);
	}
	/**
	 * 根据ids删除数据
	 * @param clazz
	 * @param values
	 * @return
	 */
	public Integer deleteByIds(Class<?> clazz , List<Object> values){
		Example example = new Example(clazz);
		example.createCriteria().andIn("id", values);
		return this.getMapper().deleteByExample(example);
	}
	/**
	 * 根据ID删除
	 * @param id
	 * @return
	 */
	public Integer deleteById(Long id){
		return this.getMapper().deleteByPrimaryKey(id);
	}
}
