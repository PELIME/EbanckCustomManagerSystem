package com.pelime.ecms.modules.sys.entity;


import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Entity(name = "sys_menu")
public class SysMenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    private Long parentId;

    @Transient
    private String parentName;

    /**
     * 菜单名称
     */
    @Column(nullable = false,length = 30)
    private String name;


    /**
     * 展示名称
     */
    @Column(length = 30)
    private String showName;

    /**
     * 菜单URL
     */
    private String url;

    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     */
    private String perms;


    /**
     * 类型     0：目录   1：菜单   2：按钮
     */
    @Column(nullable = false)
    private Integer type;

    /**
     * 菜单图标
     */
    @Column(length = 50)
    private String icon;

    /**
     * 排序
     */
    @Column(columnDefinition="int default 0")
    private Integer orderNum;

    /**
     * 打开方式
     * 类型 0：当前iframe 1:当前标签 2：新标签 3：新窗口
     */
    private Integer openMode;

    @Transient
    private Boolean open;

    @ManyToMany(mappedBy = "menus")
    private List<SysRoleEntity> roles;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getOpenMode() {
        return openMode;
    }

    public void setOpenMode(Integer openMode) {
        this.openMode = openMode;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public List<SysRoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRoleEntity> roles) {
        this.roles = roles;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }
}
