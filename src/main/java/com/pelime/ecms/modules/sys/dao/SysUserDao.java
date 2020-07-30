package com.pelime.ecms.modules.sys.dao;

import com.pelime.ecms.modules.sys.entity.SysUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserDao extends JpaRepository<SysUserEntity,Long> {

}
