package com.pelime.ecms.modules.sys.model;

import java.util.List;

public class MenuModel {
    private Integer sort;
    private String name;
    private String url;
    private String openType;
    private List<MenuModel> childrens;

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

    public String getOpenType() {
        return openType;
    }

    public void setOpenType(String openType) {
        this.openType = openType;
    }

    public List<MenuModel> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<MenuModel> childrens) {
        this.childrens = childrens;
    }
}
