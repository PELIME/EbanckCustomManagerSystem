package com.pelime.ecms.modules.sys.entity;

import javax.persistence.*;
import java.util.List;

@Entity(name = "sys_role")
public class SysRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(nullable = false,columnDefinition = "varchar(40)")
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private List<SysUserEntity> users;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<SysUserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<SysUserEntity> users) {
        this.users = users;
    }
}
