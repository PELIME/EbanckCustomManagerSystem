package com.pelime.ecms.modules.sys.service;

import com.pelime.ecms.modules.sys.dao.SysRoleDao;
import com.pelime.ecms.modules.sys.dao.SysUserDao;
import com.pelime.ecms.modules.sys.entity.SysRoleEntity;
import com.pelime.ecms.modules.sys.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class SysUserService {
    @Autowired
    SysUserDao sysUserDao;

    @Autowired
    SysRoleDao sysRoleDao;

    public SysUserEntity createOrUpdateUser(SysUserEntity userEntity){
        return sysUserDao.save(userEntity);
    }

    public void deleteUser(SysUserEntity userEntity){
        sysUserDao.delete(userEntity);
    }

    public void deleteUserById(Long id){
        sysUserDao.deleteById(id);
    }

    public SysRoleEntity createOrUpdateRole(SysRoleEntity roleEntity){
        return sysRoleDao.save(roleEntity);
    }

    public void deleteRole(SysRoleEntity roleEntity){
        sysRoleDao.delete(roleEntity);
    }

    public void deleteRoleById(Long id){
        sysRoleDao.deleteById(id);
    }

    @Transactional
    public void deleteRoleByRoleName(String roleName){
        //获取该用户
        SysRoleEntity roleEntity=sysRoleDao.findByRoleName(roleName);
        List<SysUserEntity> users=roleEntity.getUsers();
        for(SysUserEntity userTmp : users){
            userTmp.getRoles().removeIf((u) -> {
                return u.getRoleName().equals(roleName);
            });
            sysUserDao.save(userTmp);
        }
        roleEntity.getUsers().clear();
        sysRoleDao.saveAndFlush(roleEntity);
        sysRoleDao.deleteByRoleName(roleName);
    }

    public void register(SysUserEntity userEntity){
        try {
            sysUserDao.save(userEntity);
        }catch (Exception e){
            throw e;
        }
    }

    public SysRoleEntity findUserByRoleName(String roleName){
        return sysRoleDao.findByRoleName(roleName);
    }

    public List<SysRoleEntity> findRoleByNames(List<String> roleNames){
        return sysRoleDao.findAllByRoleNameIn(roleNames);
    }

    public SysUserEntity findUserByName(String username){
        return sysUserDao.findByUsername(username);
    }
}
