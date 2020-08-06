package com.pelime.ecms.modules.ebank.dao;

import com.pelime.ecms.modules.ebank.entity.EbankUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
public interface EbankUserDao extends JpaRepository<EbankUserEntity,String> {
    List<EbankUserEntity> findByName(String name);

    List<EbankUserEntity> findByUserId(String userId);

    List<EbankUserEntity> findByIdCardNumber(String idCardNumber);

    List<EbankUserEntity> findByPhone(String phone);

    List<EbankUserEntity> findByNameAndDeptNum(String name, String deptId);

    List<EbankUserEntity> findByUserIdAndDeptNum(String userId, String deptId);

    List<EbankUserEntity> findByIdCardNumberAndDeptNum(String idCardNumber, String deptId);

    List<EbankUserEntity> findByPhoneAndDeptNum(String phone, String deptId);

    /**
     * 通过管户人员姓名分页查询
     *
     * @param customerManager
     * @param pageable
     * @return
     */
    Page<EbankUserEntity> findAllByCustomerManager(String customerManager, Pageable pageable);

    Page<EbankUserEntity> findAllByLastEffectTimeBetween(Date start, Date end, Pageable pageable);

    Page<EbankUserEntity> findAllByDeptNum(Integer dept,Pageable pageable);

    Page<EbankUserEntity> findAllByDeptNumAndCustomerManager(Integer dept,String customerManager,Pageable pageable);

    Page<EbankUserEntity> findAllByDeptNumIn(Collection<Integer> depts,Pageable pageable);

    Page<EbankUserEntity> findAllByDeptNumInAndCustomerManager(Collection<Integer> depts,String customerManager,Pageable pageable);


    @Query("SELECT  e FROM ebank_user e WHERE e.customerManager = :customerManager AND (e.lastEffectTime is null OR e.lastEffectTime < :start)")
    Page<EbankUserEntity> findAllByCustomerManagerAndLastEffectTimeIsNullOrLastEffectTimeBefore(@Param("customerManager") String customerManager,@Param("start") Date start,Pageable pageable);

    @Query("SELECT  e FROM ebank_user e WHERE e.customerManager = :customerManager AND e.lastEffectTime is not null AND e.lastEffectTime >= :start")
    Page<EbankUserEntity> findAllByCustomerManagerAndLastEffectTimeIsNotNullOrLastEffectTimeAfter(@Param("customerManager") String customerManager,@Param("start") Date start, Pageable pageable);
}
