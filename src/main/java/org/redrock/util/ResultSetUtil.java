package org.redrock.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.redrock.bean.User;
import org.redrock.bean.UserPart;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultSetUtil {
    public static  Map<String,Map<String,Object>> resultSetToData(ResultSet resultSet) throws SQLException {
        int i=0;
        Map<String,Map<String,Object>> data=new HashMap<String, Map<String,Object>>();
        Gson gson=new Gson();
        while (resultSet.next()){
            Map<String,Object> map=new HashMap<String, Object>();
            String nickname=resultSet.getString("nickname");
            String headimgurl=resultSet.getString("headimgurl");
            int rank=resultSet.getInt("rank");
            map.put("nickname",nickname);
            map.put("imgurl",headimgurl);
            map.put("rank",rank);
            data.put(String.valueOf(i+1),map);
            i++;
        }

        return data;

    }

    public  static Map<String,Object> resultSetToUserPart(ResultSet resultSet) throws SQLException {
        UserPart user=new UserPart();
        Map<String,Object> map=new HashMap<String, Object>();
        if (resultSet.next()){
            String nickname=resultSet.getString("nickname");
            String headimgurl=resultSet.getString("headimgurl");
            int rank=resultSet.getInt("rank");
            map.put("nickname",nickname);
            map.put("imgurl",headimgurl);
            map.put("rank",rank);
           return map;

        }else {
            return map;
        }
    }

    public static String getData(String openid){
        String dataJson="";
        try {
            ResultSet resultSet = DBCPHelper.selectAllRank();
            Map<String,Map<String,Object>> data=resultSetToData(resultSet);
            ResultSet resultSet1=DBCPHelper.selectUserInfoAndRank(openid);
            Map<String,Object> my=resultSetToUserPart(resultSet1);
            data.put("my",my);
            Gson gson=new Gson();
            dataJson=gson.toJson(data,new TypeToken<Map<String,Map<String,Object>>>(){}.getType());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataJson;
    }
}
