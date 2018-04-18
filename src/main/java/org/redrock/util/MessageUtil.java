package org.redrock.util;

import org.redrock.bean.MessageBean;

import java.util.Date;

public class MessageUtil {
    public static void setMessage(String ToUsername,String FromUserName,String Content){
        MessageBean messageBean=new MessageBean();
        messageBean.setToUserName(ToUsername);
        messageBean.setCreateTime(String.valueOf(new Date().getTime()));

    }
}
