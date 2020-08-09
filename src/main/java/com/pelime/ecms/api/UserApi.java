package com.pelime.ecms.api;

import com.pelime.ecms.common.R;
import com.pelime.ecms.modules.sys.entity.SysRoleEntity;
import com.pelime.ecms.modules.sys.entity.SysUserEntity;
import com.pelime.ecms.modules.sys.service.SysRoleSevice;
import com.pelime.ecms.modules.sys.service.SysUserService;
import com.pelime.ecms.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.WatchService;
import java.util.*;

@RestController
@RequestMapping("/api/user")
@RequiresPermissions("console:user")
public class UserApi {

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysRoleSevice sysRoleSevice;

    @RequestMapping("/query")
    public R queryUser(@RequestParam(value = "key",defaultValue = "") String key,
                       @RequestParam(value = "value",defaultValue = "") String value,
                       @RequestParam(value = "currentPage",defaultValue = "1") Integer currentPage,
                       @RequestParam(value = "pageSize",defaultValue = "20") Integer pageSize){
        try {
            Map<String,Object> result=new HashMap<>();
            Page<SysUserEntity> sysUserEntities=sysUserService.queryPageUsers(key,value,currentPage,pageSize);
            result.put("totalPages",sysUserEntities.getTotalPages());
            result.put("totalElements",sysUserEntities.getTotalElements());
            result.put("currentPage",currentPage);
            result.put("pageSize",pageSize);
            List<SysUserEntity> userEntities=sysUserEntities.getContent();
            userEntities.forEach((u)->{
                List<SysRoleEntity> roles=u.getRoles();
                StringBuilder sb=new StringBuilder();
                roles.forEach((r)->{
                    sb.append(r.getRoleName());
                    sb.append(",");
                });
                String rolesString=sb.substring(0,sb.length()-2);
                u.setRolesString(rolesString);
            });
            result.put("users",userEntities);
            return R.ok(result);
        }catch (Exception e){
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }

    /**
     *  创建用户
     * @param username 账号
     * @param password 密码
     * @param email 邮箱
     * @param phone 电话号码
     * @param roles 角色名，多个以逗号分隔
     * @return
     */
    @RequestMapping("/add")
    public R createUser(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam("email") String email,
                        @RequestParam("phone") String phone,
                        @RequestParam("department") String department,
                        @RequestParam("roles") String roles){
        try {
            SysUserEntity userEntity=new SysUserEntity();
            userEntity.setUsername(username);
            String salt = RandomStringUtils.randomAlphanumeric(20);
            userEntity.setSalt(salt);
            userEntity.setPassword(ShiroUtils.sha256(password, userEntity.getSalt()));
            userEntity.setEmail(email);
            userEntity.setPhone(phone);
            userEntity.setDepartment(department);
            userEntity.setStatus(1);
            userEntity.setCreateTime(new Date());
            List<String> roleList=Arrays.asList(roles.split(","));
            List<SysRoleEntity> rolesEntity=sysRoleSevice.findRoleByNames(roleList);
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

    /**
     * 删除用户
     *  @param id 用户id
     * @return
     */
    @RequestMapping("/delete")
    public R deleteUser(@RequestParam("userId") Long id){
        try {
            sysUserService.deleteUserById(id);
            return R.ok("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }
    @RequestMapping("/updateRoles")
    public R updateUserRoles(@RequestParam("username") String username, @RequestParam("roles") String roles){
        try {
            SysUserEntity userEntity=sysUserService.findUserByName(username);
            userEntity.getRoles().clear();
            List<String> roleList=Arrays.asList(roles.split(","));
            List<SysRoleEntity> rolesEntity=sysRoleSevice.findRoleByNames(roleList);
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
