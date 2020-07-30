package com.pelime.ecms.api;

import com.pelime.ecms.common.R;
import com.pelime.ecms.modules.sys.entity.SysRoleEntity;
import com.pelime.ecms.modules.sys.entity.SysUserEntity;
import com.pelime.ecms.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserApi {

    @Autowired
    SysUserService sysUserService;

    /**
     *  创建用户
     * @param username 账号
     * @param password 密码
     * @param email 邮箱
     * @param phone 电话号码
     * @param roles 角色名，多个以逗号分隔
     * @return
     */
    public R createUser(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam("email") String email,
                        @RequestParam("phone") String phone,
                        @RequestParam("roles") String roles){
        try {
            SysUserEntity userEntity=new SysUserEntity();
            userEntity.setUsername(username);
            userEntity.setPassword(password);
            userEntity.setEmail(email);
            userEntity.setPhone(phone);
            userEntity.setSalt("xcx"+username);
            userEntity.setStatus(0);
            List<String> roleList=Arrays.asList(roles.split(","));
            List<SysRoleEntity> rolesEntity=sysUserService.findRoleByNames(roleList);
            if(rolesEntity.size()>0){
                userEntity.setRoles(rolesEntity);
            }
            sysUserService.createOrUpdateUser(userEntity);
            return R.ok("添加成功");
        }catch (Exception e){
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }
}
