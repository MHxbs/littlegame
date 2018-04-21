package org.redrock.controller;


import org.redrock.util.ResultSetUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(value = "/RankServlet")
public class RankServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        System.out.println("RankServlet启动");
        HttpSession session=req.getSession();
        String openid= (String) session.getAttribute("openid");

        String data=ResultSetUtil.getData(openid);
        String json = "{ \"statu\": 200 ," +
                " \"msg\": \"成功\" ,  " +
                "\"data\": " +data+
                "}";
        System.out.println(json);
        resp.getWriter().print(json);


    }
}
