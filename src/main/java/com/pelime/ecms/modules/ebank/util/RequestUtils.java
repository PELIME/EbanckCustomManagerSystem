package com.pelime.ecms.modules.ebank.util;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {

    public static Boolean isIe(HttpServletRequest request){
        String userAgent=request.getHeader("User-Agent");
        String user=userAgent.toLowerCase();
        if(user.contains("msie")){
            return true;
        }
        else
            return false;
    }
}
