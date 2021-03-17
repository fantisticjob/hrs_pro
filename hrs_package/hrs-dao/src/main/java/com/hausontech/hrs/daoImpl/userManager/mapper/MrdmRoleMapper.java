package com.hausontech.hrs.daoImpl.userManager.mapper;

import org.springframework.stereotype.Repository;

import com.hausontech.hrs.bean.userManager.MrdmRole;
@Repository
public interface MrdmRoleMapper {
	
    /**
     * 通过id删除本条角色信息
     * @param id roleId
     * @return	int
     */
    int deleteByPrimaryKey(String id);

    /**
     * 插入角色信息
     * @param role对象
     * @return
     */
    int insert(MrdmRole record);

    /**
     * 插入不为空的角色信息
     * @param role对象
     * @return
     */
    int insertSelective(MrdmRole record);

    /**
     * 通过roleId找到所有角色信息
     * @param id roleId
     * @return role对象
     */
    MrdmRole selectByPrimaryKey(String id);

    /**
     * 修改不为空的角色信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(MrdmRole record);

    /**
     * 修改角色信息
     * @param record
     * @return
     */
    int updateByPrimaryKey(MrdmRole record);
}