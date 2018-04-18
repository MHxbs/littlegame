package org.redrock.util;

import org.redrock.bean.MessageBean;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Date;

public class XmlUtil {
    public static MessageBean paresXmlToMessageBean(InputStream is) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
        DocumentBuilder builder=factory.newDocumentBuilder();
        Document document=builder.parse(is);
        MessageBean messageBean=new MessageBean();
        messageBean.setToUserName(document.getElementsByTagName("ToUserName").item(0).getTextContent());
        messageBean.setFromUserName(document.getElementsByTagName("FromUserName").item(0).getTextContent());
        messageBean.setCreateTime(document.getElementsByTagName("CreateTime").item(0).getTextContent());
        messageBean.setMsgType(document.getElementsByTagName("MsgType").item(0).getTextContent());
        messageBean.setContent(document.getElementsByTagName("Content").item(0).getTextContent());
        return messageBean;

    }
    public static String createXml(String ToUserName,String FromUserName,String Content) throws ParserConfigurationException, TransformerException {

        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
        DocumentBuilder builder=factory.newDocumentBuilder();
        Document document=builder.newDocument();
        Element toUserName=document.createElement("ToUserName");
        Element fromUsername=document.createElement("FromUserName");
        Element creatTime=document.createElement("CreateTime");
        Element msgType=document.createElement("MsgType");
        Element content=document.createElement("Content");
        toUserName.setTextContent(ToUserName);
        fromUsername.setTextContent(FromUserName);
        creatTime.setTextContent(String.valueOf(new Date().getTime()));
        msgType.setTextContent("text");
        content.setTextContent(Content);

        Element root=document.createElement("xml");
        root.appendChild(toUserName);
        root.appendChild(fromUsername);
        root.appendChild(creatTime);
        root.appendChild(msgType);
        root.appendChild(content);
        document.appendChild(root);




        TransformerFactory transformerFactory=TransformerFactory.newInstance();
        Transformer transformer=transformerFactory.newTransformer();
        DOMSource source=new DOMSource(document);

        CharArrayWriter bos=new CharArrayWriter();
        //ByteArrayOutputStream bos = new ByteArrayOutputStream();
        transformer.transform(source, new StreamResult(bos));
        String xmlString = bos.toString();
        System.out.println(xmlString);
        return xmlString;
    }
}
