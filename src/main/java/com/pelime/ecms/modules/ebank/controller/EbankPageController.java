package com.pelime.ecms.modules.ebank.controller;

import com.pelime.ecms.common.Constant;
import com.pelime.ecms.modules.ebank.entity.EbankUserEntity;
import com.pelime.ecms.modules.ebank.model.PageNumModel;
import com.pelime.ecms.modules.ebank.service.EbankUserService;
import com.pelime.ecms.modules.sys.entity.SysUserEntity;
import com.pelime.ecms.modules.sys.service.SysUserService;
import com.pelime.ecms.modules.sys.shiro.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;
import java.util.Date;
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

    /**
     *  认领客户
     * @param pageNum
     * @param userId
     * @return
     */
    @RequestMapping("/customerClaim")
    public String customerClaim(@RequestParam(name = "pageNum",defaultValue = "1") Integer pageNum,
                                @RequestParam(name = "userId",defaultValue = "") String userId,
                                @RequestParam(name = "key",defaultValue = "0") Integer key,
                                @RequestParam(name = "value",defaultValue = "") String value,
                                Model model) throws Exception {
        SysUserEntity user= ShiroUtils.getUserEntity();
        Sort sort = new Sort(Sort.Direction.DESC, "userId");
        Pageable pageable=PageRequest.of(pageNum-1,20,sort);
        if(userId!=null&&!userId.equals("")){
            ebankUserService.doClaim(userId);
        }
        if(key!=null&&!key.equals("")){
            List<EbankUserEntity> users=user.getUserId()==1?ebankUserService.findByKeyValue(key,value):ebankUserService.findByKeyValue(key,value,user.getDepartment());
            model.addAttribute("users",users);
            int totalPages=1;
            model.addAttribute("totalPages",totalPages);
            model.addAttribute("currentPage",1);
            model.addAttribute("pageShow",PageNumModel.bulid(1,1));
            return "ebank/CustomerManagerConfig";
        }
        Page<EbankUserEntity> entitiesPage=ebankUserService.pagedByDept(pageable);
        model.addAttribute("users",entitiesPage.getContent());
        int totalPages=entitiesPage.getTotalPages();
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",pageNum);
        model.addAttribute("pageShow",PageNumModel.bulid(pageNum,totalPages));
        return "ebank/CustomerManagerConfig";
    }
    @RequestMapping("/myCustomer")
    public String myCustomer(@RequestParam(name = "pageNum",defaultValue = "1") Integer pageNum,
                             @RequestParam(name = "key",defaultValue = "0") Integer key,
                             Model model) throws Exception {
        SysUserEntity user= ShiroUtils.getUserEntity();
        Sort sort = new Sort(Sort.Direction.DESC, "userId");
        Pageable pageable=PageRequest.of(pageNum-1,20,sort);
        if(key==0){
            Page<EbankUserEntity> entitiesPage=ebankUserService.pagedByCustomerManager(pageable);
            model.addAttribute("users",entitiesPage.getContent());
            int totalPages=entitiesPage.getTotalPages();
            model.addAttribute("totalPages",totalPages);
            model.addAttribute("currentPage",pageNum);
            model.addAttribute("pageShow",PageNumModel.bulid(pageNum,totalPages));
        }
        else if(key==1){
            Calendar c = Calendar.getInstance();
            Date now=new Date();
            c.setTime(now);
            c.set(Calendar.DAY_OF_MONTH,1);
            Date start=c.getTime();
            Page<EbankUserEntity> entitiesPage=ebankUserService.pagedByCustomerManagerBefore(start,pageable);
            int totalPages=entitiesPage.getTotalPages();
            model.addAttribute("totalPages",totalPages);
            model.addAttribute("currentPage",pageNum);
            model.addAttribute("pageShow",PageNumModel.bulid(pageNum,totalPages));
        }
        return "ebank/MyCustomer";
    }
}
