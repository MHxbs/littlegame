package org.redrock.util;

import com.google.gson.Gson;
import org.redrock.bean.Acces_TokenBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Access_Token {
    //wxb947b0e72bccc47b
    //f9912111ed04dd08bb9fa7cc3bd91ee9
    public static String getAcces_Token(String AppId,String Secret) throws IOException {
        URL url=new URL("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+AppId+"&secret="+Secret);
        HttpURLConnection connection= (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), "UTf-8"));

        StringBuilder builder = new StringBuilder();
        String line = null;
        //每行每行地读出
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        reader.close();
        connection.disconnect();
        System.out.println(builder);
        Gson gson=new Gson();
        Acces_TokenBean acces_tokenBean=gson.fromJson(builder.toString(),Acces_TokenBean.class);
        return acces_tokenBean.getAccess_token();
    }
}
