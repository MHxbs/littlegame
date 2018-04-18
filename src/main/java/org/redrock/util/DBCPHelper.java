package org.redrock.util;

import org.redrock.bean.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCPHelper {
    public static void insertUser(User user,int score,int count){
        Connection con=DBCPFactory.getConnection();
        String sql="INSERT INTO users (openid,subscribe,nickname,sex,headimgurl,score,count) VALUES" +
                "(?,?,?,?,?,"+score+","+count+")";
        try {
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setString(1,user.getOpenid());
            pst.setInt(2,user.getSubscribe());
            pst.setString(3,user.getNickname());
            pst.setInt(4,user.getSex());
            pst.setString(5,user.getHeadimgurl());

            int result=pst.executeUpdate();
            if (result==1){
                System.out.println("新用户"+user.getOpenid()+"插入成功");
            }
            pst.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Boolean isExist(String openid) throws SQLException {
        Connection con=DBCPFactory.getConnection();
        String sql="SELECT * FROM users where openid = ?";
        PreparedStatement pst=con.prepareStatement(sql);
        pst.setString(1,openid);
        ResultSet resultSet=pst.executeQuery();

        if (resultSet.next()){
            pst.close();
            con.close();
            return true;
        }else {
            pst.close();
            con.close();
            return false;
        }
    }

    public static ResultSet selectUserInfo(String openid) throws SQLException {
        Connection con=DBCPFactory.getConnection();
        String sql="SELECT * FROM users WHERE openid = ?";
        PreparedStatement pst=con.prepareStatement(sql);
        pst.setString(1,openid);
        ResultSet resultSet=pst.executeQuery();

        return resultSet;
    }

    public static ResultSet selectUserInfoAndRank(String openid) throws SQLException {
        Connection con=DBCPFactory.getConnection();
        String sql="select a.*, (select count(distinct b.score) from users b where b.score >= a.score)" +
                " as rank from users a where openid =? ";
        PreparedStatement pst=con.prepareStatement(sql);
        pst.setString(1,openid);
        ResultSet resultSet=pst.executeQuery();

        return resultSet;
    }

    public static ResultSet selectAllRank() throws SQLException {
        Connection con=DBCPFactory.getConnection();
        String sql="select a.*,(select count(distinct b.score) from users b where b.score>=a.score) " +
                "as rank from users a order by score desc limit 50";
        PreparedStatement pst=con.prepareStatement(sql);
        ResultSet resultSet=pst.executeQuery();

        return resultSet;
    }

    public static void updateScore(String openid,String score) throws SQLException {
        Connection con = DBCPFactory.getConnection();
        System.out.println("更新数据："+openid);
        ResultSet resultSet = selectUserInfo(openid);
        String  scoreFromMysql =null;

        if (resultSet.next()) {
            scoreFromMysql = resultSet.getString("score");
        }
        if (score.compareTo(scoreFromMysql)>0) {
            String sql = "UPDATE users SET score = ? WHERE openid = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, score);
            pst.setString(2, openid);
            int a = pst.executeUpdate();
            if (a == 1) {
                System.out.println(openid + ": 更改分数成功,分数：" + score);
            }
        }else {
            System.out.println("这次分数没有以往分数高，不更新分数！");
        }
    }

    public static void updateCount(String openid,int count) throws SQLException {
        Connection con=DBCPFactory.getConnection();
        String sql="UPDATE users SET count = ? WHERE openid=?";
        PreparedStatement pst=con.prepareStatement(sql);
        pst.setInt(1,count);
        pst.setString(2,openid);
        int a=pst.executeUpdate();
        if (a==1){
            System.out.println(openid+": 更改剩余游戏次数成功,次数："+count);
        }
    }
    public static void updateAllCount() throws SQLException {
        Connection con=DBCPFactory.getConnection();
        String sql="UPDATE users SET count = 40";
        PreparedStatement pst=con.prepareStatement(sql);
        int a=pst.executeUpdate();
        if (a==1){
            System.out.println("用户的count都被置为40");
        }
    }
}
