package com.pelime.ecms.modules.ebank.controller;

import com.pelime.ecms.common.Constant;
import com.pelime.ecms.modules.ebank.entity.EbankUserEntity;
import com.pelime.ecms.modules.ebank.service.EbankUserService;
import com.pelime.ecms.modules.sys.entity.SysUserEntity;
import com.pelime.ecms.modules.sys.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/ebank")
public class EbankPageController {
    @Autowired
    EbankUserService ebankUserService;

    @Autowired
    SysUserService sysUserService;


    @GetMapping("/upload")
    public String upload(){
        return "ebank/upload";
    }
    @GetMapping("/query")
    public String query(){
        return "ebank/query";
    }
    @PostMapping("/query")
    public String query(@RequestParam("key") Integer key, @RequestParam("value") String value, Model model){
        SysUserEntity user= (SysUserEntity)SecurityUtils.getSubject().getPrincipal();
        String department=user.getDepartment();
        if(department==null||department.equals("")){
            return "ebank/query";
        }
        List<EbankUserEntity> users= Constant.isEbankAdmin(user)?ebankUserService.findByKeyValue(key,value):ebankUserService.findByKeyValue(key,value,user.getDepartment());
        if(users==null||users.size()==0){
            return "ebank/query";
        }
        else {
            model.addAttribute("users",users);
            return "ebank/query";
        }
    }
    @GetMapping("/squery")
    public String squery(){
        return "ebank/squery";
    }
    @PostMapping("/squery")
    public String squery(@RequestParam("key") Integer key, @RequestParam("value") String value, Model model){
        SysUserEntity user= (SysUserEntity)SecurityUtils.getSubject().getPrincipal();
        String department=user.getDepartment();
        if(department==null||department.equals("")){
            return "ebank/squery";
        }
        List<EbankUserEntity> users=user.getUserId()==1?ebankUserService.findByKeyValue(key,value):ebankUserService.findByKeyValue(key,value,user.getDepartment());
        if(users==null||users.size()==0){
            return "ebank/squery";
        }
        else {
            model.addAttribute("users",users);
            return "ebank/squery";
        }
    }
}
