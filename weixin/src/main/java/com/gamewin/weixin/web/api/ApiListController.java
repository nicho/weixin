/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.gamewin.weixin.web.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gamewin.weixin.util.SHA1;
 

@Controller
@RequestMapping(value = "/api")
public class ApiListController {
	private String Token = "DU2qERxP";  
	
	
	@RequestMapping(method ={ RequestMethod.GET, RequestMethod.POST })
	public void list(HttpServletRequest request, HttpServletResponse response) {
		  System.out.println("进入chat");  
	        boolean isGet = request.getMethod().toLowerCase().equals("get");  
	        if (isGet) {  
	            String signature = request.getParameter("signature");  
	            String timestamp = request.getParameter("timestamp");  
	            String nonce = request.getParameter("nonce");  
	            String echostr = request.getParameter("echostr");  
	            System.out.println(signature);  
	            System.out.println(timestamp);  
	            System.out.println(nonce);  
	            System.out.println(echostr);  
	            access(request, response);  
	        } else {  
	            // 进入POST聊天处理  
	            System.out.println("enter post");  
	           
	        }  
		 
	}
	
	
	/**  
     * 验证URL真实性  
     *   
     * @author morning  
     * @date 2015年2月17日 上午10:53:07  
     * @param request  
     * @param response  
     * @return String  
     */  
    private String access(HttpServletRequest request, HttpServletResponse response) {  
        // 验证URL真实性  
        System.out.println("进入验证access");  
        String signature = request.getParameter("signature");// 微信加密签名  
        String timestamp = request.getParameter("timestamp");// 时间戳  
        String nonce = request.getParameter("nonce");// 随机数  
        String echostr = request.getParameter("echostr");// 随机字符串  
        List<String> params = new ArrayList<String>();  
        params.add(Token);  
        params.add(timestamp);  
        params.add(nonce);  
        // 1. 将token、timestamp、nonce三个参数进行字典序排序  
        Collections.sort(params, new Comparator<String>() {  
            @Override  
            public int compare(String o1, String o2) {  
                return o1.compareTo(o2);  
            }  
        });  
        // 2. 将三个参数字符串拼接成一个字符串进行sha1加密  
        String temp = SHA1.encode(params.get(0) + params.get(1) + params.get(2));  
        if (temp.equals(signature)) {  
            try {  
                response.getWriter().write(echostr);  
                System.out.println("成功返回 echostr：" + echostr);  
                return echostr;  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        System.out.println("失败 认证");  
        return null;  
    }   
   
}
