package com.hausontech.hrs.daoImpl.userManager.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hausontech.hrs.bean.userManager.MrdmUser;
@Repository
public interface MrdmUserMapper {
    /**
     * 通过用户名删除用户信息（不包括对应角色信息）
     * @param userName 用户名
     * @return int
     */
    int deleteByPrimaryKey(String userName);

    /**
     * 插入用户信息（不包括对应角色信息）
     * @param 用户对象
     * @return int
     */
    int insert(MrdmUser record);

//    int insertSelective(MrdmUser record);

    /**
     * 通过用户名查找对应的用户信息（不包括对应角色信息）
     * @param userName 用户名
     * @return	用户对象
     */
    MrdmUser selectByPrimaryKey(String userName);

    /**
     * 修改不为空的用户信息（不包括对应角色信息）
     * @param record 角色对象
     * @return int
     */
    int updateByPrimaryKeySelective(MrdmUser record);

//    int updateByPrimaryKey(MrdmUser record);
    
    /**
     * 查找所有的用户信息（不包括对应角色信息）
     * @return 所有用户对象的list
     */
    List<MrdmUser> selectAll();
    
    /**
     * 查询一共有多少条记录
     * @return 记录数
     */
    int selectCount();
}