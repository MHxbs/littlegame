package org.redrock.util;

import com.google.gson.Gson;
import org.redrock.bean.User;
import org.redrock.bean.UserToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeChatUtil {
    public static String getUrl(String AppId,String Redirect_URL,String Scope ) throws IOException {
        String urlPath="https://open.weixin.qq.com/connect/oauth2/authorize?" +
                "appid=APPID" +
                "&redirect_uri=REDIRECT_URI" +
                "&response_type=code" +
                "&scope=SCOPE#wechat_redirect";
        urlPath=urlPath.replace("APPID",AppId).replace("REDIRECT_URI",Redirect_URL).replace("SCOPE",Scope);
        return urlPath;
    }

    public static UserToken getAccess_token(String AppID,String code,String Secret) throws IOException {
        String path="https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=APPID&" +
                "secret=SECRET&" +
                "code=CODE&" +
                "grant_type=authorization_code";
        path=path.replace("APPID",AppID).replace("SECRET",Secret).replace("CODE",code);
        URL url=new URL(path);
        HttpURLConnection connection= (HttpURLConnection) url.openConnection();
        BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
        StringBuilder stringBuilder=new StringBuilder();
        String line=null;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        reader.close();
        connection.disconnect();

        Gson gson=new Gson();
        UserToken userToken=gson.fromJson(stringBuilder.toString(),UserToken.class);
        return userToken;
    }

    public static User getUserInfo(String Access_Token,String OpenId) throws IOException {
        String path="https://api.weixin.qq.com/sns/userinfo?" +
                "access_token=ACCESS_TOKEN&" +
                "openid=OPENID&lang=zh_CN";
        path=path.replace("ACCESS_TOKEN",Access_Token).replace("OPENID",OpenId);
        URL url=new URL(path);
        HttpURLConnection connection= (HttpURLConnection) url.openConnection();
        BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
        StringBuilder stringBuilder=new StringBuilder();
        String line=null;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        reader.close();
        connection.disconnect();

        Gson gson=new Gson();
        User user=gson.fromJson(stringBuilder.toString(),User.class);
        return user;

    }
}
