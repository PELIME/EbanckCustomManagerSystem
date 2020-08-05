package com.pelime.ecms.modules.ebank.model;

import com.pelime.ecms.modules.ebank.util.ExcelUtils;

import java.util.LinkedList;
import java.util.List;

public class PageNumModel {
    private String show;
    private Integer num;
    private String attr="";

    public PageNumModel(String show, Integer num) {
        this.show = show;
        this.num = num;
    }


    public static List<PageNumModel> bulid(Integer currentPage,Integer totalPages) throws Exception {
        if(currentPage<1||currentPage>totalPages){
            throw new Exception("当前页数错误");
        }
        List<PageNumModel> result=new LinkedList<>();
        if(currentPage!=1||(currentPage-6)>0){
            result.add(new PageNumModel("<<",currentPage-6));
        }
        int showMaxNum=totalPages-currentPage>6?currentPage+5:totalPages;
        PageNumModel first=new PageNumModel(currentPage.toString(),currentPage);
        first.setAttr("class='active'");
        result.add(first);
        for(int i=currentPage+1;i<=showMaxNum;i++){
            result.add(new PageNumModel(String.valueOf(i),i));
        }
        if(totalPages>showMaxNum){
            result.add(new PageNumModel(">>",showMaxNum+1));
        }
        return result;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }
}
