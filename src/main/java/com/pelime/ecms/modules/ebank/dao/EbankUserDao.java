package com.pelime.ecms.modules.ebank.dao;

import com.pelime.ecms.modules.ebank.entity.EbankUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EbankUserDao extends JpaRepository<EbankUserEntity,String> {
    List<EbankUserEntity> findByName(String name);
    List<EbankUserEntity> findByUserId(String userId);
    List<EbankUserEntity> findByIdCardNumber(String idCardNumber);
    List<EbankUserEntity> findByPhone(String phone);

    List<EbankUserEntity> findByNameAndDeptNum(String name,String deptId);
    List<EbankUserEntity> findByUserIdAndDeptNum(String userId,String deptId);
    List<EbankUserEntity> findByIdCardNumberAndDeptNum(String idCardNumber,String deptId);
    List<EbankUserEntity> findByPhoneAndDeptNum(String phone,String deptId);
}
