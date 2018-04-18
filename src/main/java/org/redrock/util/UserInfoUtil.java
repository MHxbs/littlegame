package org.redrock.util;

import com.google.gson.Gson;
import org.redrock.bean.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
// 这是微信基础获得用户信息的工具类
// 其余微信的基础access_token
public class UserInfoUtil {
    private static String access_token=null;
    private static String AppId="wx50cd7859ecffe7de";
    private static String Secret="ecec700ca2f0bdc859c5c302457937b8";
    public static User getUserInfo(Map<String,String> map) throws IOException {
        access_token= Access_Token.getAcces_Token(AppId,Secret);
        URL url=new URL("https://api.weixin.qq.com/cgi-bin/user/info?access_token="+access_token+"&openid="+map.get("FromUserName")+"&lang=zh_CN");
        HttpURLConnection connection= (HttpURLConnection) url.openConnection();
        BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
        StringBuilder stringBuilder=new StringBuilder();
        String line=null;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        System.out.println(stringBuilder);
        reader.close();
        connection.disconnect();

        Gson gson=new Gson();
        User user=gson.fromJson(stringBuilder.toString(),User.class);
        System.out.println(user.getNickname());
        return user;
    }
}
