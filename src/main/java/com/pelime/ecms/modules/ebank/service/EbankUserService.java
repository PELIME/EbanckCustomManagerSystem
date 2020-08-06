package com.pelime.ecms.modules.ebank.service;

import com.pelime.ecms.modules.ebank.dao.EbankUserDao;
import com.pelime.ecms.modules.ebank.entity.EbankUserEntity;
import com.pelime.ecms.modules.ebank.util.ExcelUtils;
import com.pelime.ecms.modules.ebank.util.ReflectionUtils;
import com.pelime.ecms.modules.sys.entity.SysUserEntity;
import com.pelime.ecms.modules.sys.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class EbankUserService {

    @Autowired
    EbankUserDao ebankUserDao;

    @Transactional(rollbackFor = Exception.class)
    public Boolean compareAndSaveAll(List<Object> list, Map<String,String> nameAttrPair) {
        if(list.size()<2){
            return false;
        }
        List<String> colNames=(List<String>)list.get(0);
        //将属性与列序号对应起来
        List<String> colAttr=new ArrayList<>(colNames.size());
        for(int i=0;i<colNames.size();i++){
            if(nameAttrPair.containsKey(colNames.get(i))){
                colAttr.add(nameAttrPair.get(colNames.get(i)));
            }
            else
                colAttr.add("");
        }
        List<EbankUserEntity> saveDatas=new ArrayList<>(list.size()-1);
        for(int i=1;i<list.size();i++){
            List<String> rowdata=(List<String>)list.get(i);
            EbankUserEntity entity=new EbankUserEntity();
            for(int j=0;j<rowdata.size();j++){
                if(colAttr.get(j)!=""){
                    String tmp=rowdata.get(j);
                    Object obtmp=rowdata.get(j);
                    if(ExcelUtils.isDateString(tmp,"yyyy/MM/dd")){
                        SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");
                        try {
                            obtmp=new java.sql.Date(fmt.parse(tmp).getTime());
                        } catch (ParseException e) {
                            continue;
                        }
                    }
                    else if(ExcelUtils.isNumber(tmp)){
                        obtmp=Integer.parseInt(tmp);
                    }
                    if(obtmp==null||obtmp.toString().equals(""))
                        continue;
                    ReflectionUtils.setBeanAttr(entity,colAttr.get(j),obtmp);
                }
            }
            if(entity.getUserId()==null){
                continue;
            }
            //保存rowdata
            if(ebankUserDao.findById(entity.getUserId())!=null){
                ebankUserDao.save(entity);
            }
            else {
                ebankUserDao.save(entity);
            }
        }
        return true;
    }

    public List<EbankUserEntity> findByKeyValue(Integer key,String value){
        value=value.trim();
        if(key==0){
            return ebankUserDao.findByName(value);
        }
        else if(key==1){
            return ebankUserDao.findByUserId(value);
        }
        else if(key==2){
            return ebankUserDao.findByIdCardNumber(value);
        }
        else if(key==3){
            return ebankUserDao.findByPhone(value);
        }
        else
            return null;
    }

    public List<EbankUserEntity> findByKeyValue(Integer key, String value, String dept) {
        value=value.trim();
        if (key == 0) {
            return ebankUserDao.findByNameAndDeptNum(value,dept);
        } else if (key == 1) {
            return ebankUserDao.findByUserIdAndDeptNum(value,dept);
        } else if (key == 2) {
            return ebankUserDao.findByIdCardNumberAndDeptNum(value,dept);
        } else if (key == 3) {
            return ebankUserDao.findByPhoneAndDeptNum(value,dept);
        } else
            return null;
    }

    public Page<EbankUserEntity> pagedByDept(Pageable pageable){
        SysUserEntity user= ShiroUtils.getUserEntity();
        String depts=user.getDepartment();
        String[] deptsArr=depts.split(",");
        if(deptsArr.length==1){
           return ebankUserDao.findAllByDeptNum(Integer.parseInt(depts),pageable);
        }
        else {
            List<Integer> deptsNum=new ArrayList<>(deptsArr.length);
            for(String s : deptsArr){
                deptsNum.add(Integer.parseInt(s));
            }
            return ebankUserDao.findAllByDeptNumIn(deptsNum,pageable);
        }
    }

    public int doClaim(String userId){
        SysUserEntity user= ShiroUtils.getUserEntity();
        EbankUserEntity ebankUserEntity=ebankUserDao.findByUserId(userId).get(0);
        if(ebankUserEntity.getCustomerManager()!=null||!ebankUserEntity.getCustomerManager().equals("")){
            return 1;
        }
        ebankUserEntity.setCustomerManager(user.getUsername());
        ebankUserDao.save(ebankUserEntity);
        return 0;
    }
    public int doNotClaim(String userId){
        SysUserEntity user= ShiroUtils.getUserEntity();
        EbankUserEntity ebankUserEntity=ebankUserDao.findByUserId(userId).get(0);
        //如果是管理员可直接脱管
        if(user.getUsername().equals("admin")){
            ebankUserEntity.setCustomerManager(null);
        }
        else {
            if(ebankUserEntity.getCustomerManager()==null||!ebankUserEntity.getCustomerManager().equals(user.getUsername())){
                return 1;
            }
            ebankUserEntity.setCustomerManager(null);
        }
        ebankUserDao.save(ebankUserEntity);
        return 0;
    }
    public Page<EbankUserEntity> pagedByCustomerManager(Pageable pageable){
        SysUserEntity user= ShiroUtils.getUserEntity();
        return ebankUserDao.findAllByCustomerManager(user.getUsername(),pageable);
    }

    public Page<EbankUserEntity> pagedByCustomerManagerBefore(Date start,Pageable pageable){
        SysUserEntity user= ShiroUtils.getUserEntity();
        return ebankUserDao.findAllByCustomerManagerAndLastEffectTimeIsNullOrLastEffectTimeBefore(user.getUsername(),start,pageable);
    }

    public Page<EbankUserEntity> pagedByCustomerManagerAfter(Date start,Pageable pageable){
        SysUserEntity user= ShiroUtils.getUserEntity();
        return ebankUserDao.findAllByCustomerManagerAndLastEffectTimeIsNotNullOrLastEffectTimeAfter(user.getUsername(),start,pageable);
    }
}
