package org.redrock.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.redrock.bean.User;
import org.redrock.bean.UserPart;
import org.redrock.util.DBCPHelper;
import org.redrock.util.ResultSetUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
