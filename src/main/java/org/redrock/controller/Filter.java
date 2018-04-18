package org.redrock.controller;

import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;
import org.redrock.bean.User;
import org.redrock.bean.UserToken;
import org.redrock.util.DBCPHelper;
import org.redrock.util.WeChatUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
/* 在访问start.jsp时，从微信获得用户信息，并插入数据库*/
public class Filter implements javax.servlet.Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String AppId="wx50cd7859ecffe7de";
        String Redirect_URL="http://hong.s1.natapp.cc/views/start.jsp";
        String Scope="snsapi_userinfo";
        String Secret="ecec700ca2f0bdc859c5c302457937b8";
        Redirect_URL=URLEncoder.encode(Redirect_URL,"UTF-8");
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpSession session=request.getSession();
        if (session!=null){
            filterChain.doFilter(request,response);
        }
        if (request.getParameter("code")==null){
            // 如果code为空，重定向到微信制定的url
            response.sendRedirect(WeChatUtil.getUrl(AppId,Redirect_URL,Scope));
        }else {
            // 如何code不为空 ，通过传来的code，来得到用户的access_token（与最初的不同）
            UserToken userToken=WeChatUtil.getAccess_token(AppId,request.getParameter("code"),Secret);
            // 通过token，向微信获得用户信息
            User user=WeChatUtil.getUserInfo(userToken.getAccess_token(),userToken.getOpenid());
            System.out.println(user.getNickname());
            try {
                if (!DBCPHelper.isExist(user.getOpenid())) {
                    DBCPHelper.insertUser(user,0,40);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            // 保存session
            session.setAttribute("openid",user.getOpenid());
            session.setAttribute("nickname",user.getNickname());
            session.setAttribute("headimgurl",user.getHeadimgurl());
            filterChain.doFilter(request,response);
        }


    }

    public void destroy() {

    }
}
