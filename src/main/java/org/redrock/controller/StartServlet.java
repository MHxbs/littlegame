package org.redrock.controller;

import org.redrock.bean.User;
import org.redrock.util.DBCPHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(value = "/StartServlet")
public class StartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session=req.getSession();
        System.out.println("asdsadsad");
        System.out.println("session中的openid ："+session.getAttribute("openid"));
        String openid= (String) session.getAttribute("openid");
        ResultSet resultSet= null;
        try {
            // 根据openid得到用户的信息及排名
            resultSet = DBCPHelper.selectUserInfoAndRank(openid);
            if(resultSet.next()) {
                int count=resultSet.getInt("count")-1;
                String json = "{ \"statu\": 200 ," +
                        " \"msg\": \"成功\" ,  " +
                        "\"data\": {" +
                        "     \"nickname\": \"" + resultSet.getString("nickname") + "\" ," +
                        "      \"rank\": " + resultSet.getString("rank") +" ,"+
                        "       \"share\": 0" +" ,"+
                        "       \"count\": " + count +" ,"+
                        "       \"imgurl\":\"" + resultSet.getString("headimgurl") + "\"" +
                        "        }" +
                        "}";
                System.out.println(json);
                DBCPHelper.updateCount(openid,count);
                resp.getWriter().print(json);
            }else {
                resp.getWriter().print("用户不存在！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
