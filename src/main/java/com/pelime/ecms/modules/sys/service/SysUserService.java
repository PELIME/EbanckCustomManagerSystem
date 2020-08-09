package com.pelime.ecms.modules.sys.service;

import com.pelime.ecms.modules.sys.dao.SysRoleDao;
import com.pelime.ecms.modules.sys.dao.SysUserDao;
import com.pelime.ecms.modules.sys.entity.SysRoleEntity;
import com.pelime.ecms.modules.sys.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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



    public void register(SysUserEntity userEntity){
        sysUserDao.save(userEntity);
    }



    public SysUserEntity findUserByName(String username){
        return sysUserDao.findByUsername(username);
    }

    public Page<SysUserEntity> queryPageUsers(String key,String value,Integer currentPage,Integer pageSize){
        Pageable pageable= PageRequest.of(currentPage-1,pageSize);
        if(key==null||key.equals("")){
            return sysUserDao.findAll(pageable);
        }
        else {
            if(key.equals("username")){
                return sysUserDao.findAllByUsernameLike(value,pageable);
            }
            else if(key.equals("phone")){
                return sysUserDao.findAllByPhone(value,pageable);
            }
            else {
                return sysUserDao.findAll(pageable);
            }
        }
    }
}
