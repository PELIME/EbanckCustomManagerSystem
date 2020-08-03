package com.pelime.ecms.modules.ebank.controller;

import com.alibaba.fastjson.JSONObject;
import com.pelime.ecms.modules.ebank.service.EbankUserService;
import com.pelime.ecms.modules.ebank.util.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ebank")
public class EbankController {
    @Autowired
    EbankUserService ebankUserService;
    @PostMapping("/upexcel")
    public JSONObject upexcel(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) throws IOException {
        String name=file.getOriginalFilename();
        JSONObject res = new JSONObject();
        if(name.length()<5|| !name.substring(name.length()-4).equals(".xls")){
            res.put("code",-1);
            res.put("message","文件格式错误，请上传xls文件");
            return res;
        }
        List<Object> list = ExcelUtils.excelToShopIdList(file.getInputStream());
        Map<String,String> nameAttrPair=new HashMap<>();
        nameAttrPair.put("数据日期","updateTime");
        nameAttrPair.put("机构号","deptNum");
        nameAttrPair.put("核心客户号","userId");
        nameAttrPair.put("姓名","name");
        nameAttrPair.put("身份证","idCardNumber");
        nameAttrPair.put("手机号码","phone");
        nameAttrPair.put("注册日期","registerTime");
        nameAttrPair.put("认证等级","grade");
        nameAttrPair.put("最近登录日期","lastLoginTime");
        nameAttrPair.put("最近动账日期","lastEffectTime");
        ebankUserService.compareAndSaveAll(list,nameAttrPair);
        res.put("code","0");
        res.put("message","处理成功");
        return res;
    }
}

