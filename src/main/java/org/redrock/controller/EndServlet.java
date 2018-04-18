package org.redrock.controller;

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

@WebServlet(value = "/EndServlet")
public class EndServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String score=req.getParameter("score");
        HttpSession session=req.getSession();
        String openid= String.valueOf(session.getAttribute("openid"));
        try {
            // 更新用户的分数
            DBCPHelper.updateScore(openid,score);
            ResultSet resultSet=DBCPHelper.selectUserInfoAndRank(openid);
            if (resultSet.next()) {
                String json = "{ \"statu\": 200 ," +
                        " \"msg\": \"成功\" ,  " +
                        "\"data\": {" +
                        "     \"nickname\": \"" + resultSet.getString("nickname") + "\" ," +
                        "      \"rank\": " + resultSet.getString("rank") +" ,"+
                        "       \"share\": 0" +" ,"+
                        "       \"count\": " + resultSet.getInt("count") +" ,"+
                        "       \"imgurl\":\"" + resultSet.getString("headimgurl") + "\"" +
                        "        }" +
                        "}";
                System.out.println(json);
                resp.getWriter().print(json);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
