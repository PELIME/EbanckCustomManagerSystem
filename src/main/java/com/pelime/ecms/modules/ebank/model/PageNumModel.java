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
        //算出显示的最低页码和最高页码
        if(currentPage<1){
            currentPage=1;
        }
        else if(currentPage>totalPages){
            currentPage=totalPages;
        }
        int min=currentPage-3<0?1:currentPage-3;
        int max=currentPage+3>totalPages?totalPages:currentPage+3;

        List<PageNumModel> result=new LinkedList<>();
        if(min>1){
            result.add(new PageNumModel("<<",min-4>1?min-4:1));
        }
        for(int i=min;i<=max;i++){
            PageNumModel pm=new PageNumModel(String.valueOf(i),i);
            if(i==currentPage){
                pm.setAttr("class='active'");
            }
            result.add(pm);
        }
        if(totalPages>max){
            result.add(new PageNumModel(">>",max+4<totalPages?max+4:totalPages));
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
