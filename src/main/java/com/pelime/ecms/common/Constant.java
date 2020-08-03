package com.pelime.ecms.common;

import com.pelime.ecms.modules.sys.entity.SysRoleEntity;
import com.pelime.ecms.modules.sys.entity.SysUserEntity;

import java.util.List;

public class Constant {
    public static  String SUPER_ADMIN_NAME="超级管理员";
    public static  String EBANK_ADMIN_NAME="手机银行管理员";

    public static boolean isEbankAdmin(SysUserEntity userEntity){
        List<SysRoleEntity> roles=userEntity.getRoles();
        for(SysRoleEntity r : roles){
            if(r.getRoleName().equals(SUPER_ADMIN_NAME)||r.getRoleName().equals(EBANK_ADMIN_NAME)){
                return true;
            }
        }
        return false;
    }
}
