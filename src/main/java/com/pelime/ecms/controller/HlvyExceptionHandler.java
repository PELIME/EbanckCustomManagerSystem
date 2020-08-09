package com.pelime.ecms.controller;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class HlvyExceptionHandler {
    @ExceptionHandler(value= AuthorizationException.class)
    public Object errExceyion(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
        //e.printStackTrace();
        ModelAndView view = new ModelAndView();
        System.out.println("this HlvyExceptionHandler");
        view.addObject("e", e);
        view.addObject("url", request.getRequestURI());
        view.setViewName("/unauthorized");
        return view;
    }
}
