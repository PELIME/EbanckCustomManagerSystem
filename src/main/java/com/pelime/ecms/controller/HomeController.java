package com.pelime.ecms.controller;

import com.pelime.ecms.modules.sys.entity.SysRoleEntity;
import com.pelime.ecms.modules.sys.entity.SysUserEntity;
import com.pelime.ecms.modules.sys.service.SysMenuService;
import com.pelime.ecms.modules.sys.shiro.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    SysMenuService sysMenuService;

    @GetMapping({"/","/index"})
    public String index(Model model,@RequestParam(value = "role",defaultValue = "-1") Long role){
        SysUserEntity user=ShiroUtils.getUserEntity();
        model.addAttribute("homeActive",true);
        if(user!=null){
            model.addAttribute("user",user);
            model.addAttribute("roles",user.getRoles());
            if(role!=-1){
                List<SysRoleEntity> roles= user.getRoles();
                for(SysRoleEntity r : roles){
                    if(r.getRoleId()==role){
                        user.setActiveRole(r);
                    }
                }
            }
            model.addAttribute("activeRole",user.getActiveRole());
            model.addAttribute("menuString",sysMenuService.getMenuHtml(user.getActiveRole().getRoleName(),"首页"));
        }
        //获取该角色菜单

        return "index";
    }
    @GetMapping("/index/welcome")
    public String welcome(){
        return "home/welcome";
    }

    @GetMapping("index-tmp")
    public  String test(){
        return "index-tmp";
    }
    @GetMapping("test")
    public  String test1(){
        return "test";
    }


    @RequestMapping("/unauthorized")
    public String unauthorized(){
        return "unauthorized";
    }
}
