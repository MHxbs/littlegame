package org.redrock.controller;

import org.redrock.bean.User;
import org.redrock.util.Access_Token;
import org.redrock.util.DBCPHelper;
import org.redrock.util.UserInfoUtil;
import org.redrock.util.XmlUtil;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


// 对用户发送的文本信息进行解析，回复，当发送game时返回游戏的首页
@WebServlet(value = "/Servlet")
public class Servlet extends HttpServlet {
    Map<String,String> map=new HashMap<String, String>();
    String xml=null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       req.setCharacterEncoding("UTF-8");
       resp.setCharacterEncoding("UTF-8");
        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
        try {
            // 解析微信发来的文本信息（xml格式）
            DocumentBuilder builder=factory.newDocumentBuilder();
            Document document=builder.parse(req.getInputStream());
            map.put(document.getElementsByTagName("ToUserName").item(0).getNodeName(),
                    document.getElementsByTagName("ToUserName").item(0).getTextContent());
            map.put(document.getElementsByTagName("FromUserName").item(0).getNodeName(),
                    document.getElementsByTagName("FromUserName").item(0).getTextContent());
            map.put(document.getElementsByTagName("CreateTime").item(0).getNodeName(),
                    document.getElementsByTagName("CreateTime").item(0).getTextContent());
            map.put(document.getElementsByTagName("MsgType").item(0).getNodeName(),
                    document.getElementsByTagName("MsgType").item(0).getTextContent());
            map.put(document.getElementsByTagName("Content").item(0).getNodeName(),
                    document.getElementsByTagName("Content").item(0).getTextContent());
            map.put(document.getElementsByTagName("MsgId").item(0).getNodeName(),
                    document.getElementsByTagName("MsgId").item(0).getTextContent());
            // 如果传来的内容是 game ，返回游戏首页，不是则返回请输入game获得游戏首页
            if (map.get("Content").equals("game")){
                try {
                    User user= UserInfoUtil.getUserInfo(map);
                    xml= XmlUtil.createXml(map.get("FromUserName"),map.get("ToUserName"),"http://hong.s1.natapp.cc/views/start.jsp");
                    resp.getWriter().print(xml);
                } catch (TransformerException e) {
                    e.printStackTrace();
                }
            }else {
                xml=XmlUtil.createXml(map.get("FromUserName"),map.get("ToUserName"),"请输入game获得游戏！");
                resp.getWriter().print(xml);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
