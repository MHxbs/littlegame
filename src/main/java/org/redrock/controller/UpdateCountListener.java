package org.redrock.controller;


import org.redrock.util.DBCPHelper;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import java.util.Timer;
import java.util.TimerTask;

public class UpdateCountListener implements ServletContextListener {
    private  java.util.Timer  timer  =  null;
    public void contextInitialized(ServletContextEvent sce) {
        timer=new Timer(true);
        System.out.println("listener");
        sce.getServletContext().log("定时器已启动");
        timer.schedule(new TimerTask() {
                @Override
                public void run() {
                        DBCPHelper.updateAllCount();
                }
                }, 0, 24 * 60 * 60 * 1000);
        sce.getServletContext().log("已经添加任务调度表");


        /*SimpleDateFormat sdf=new SimpleDateFormat();
        String date=sdf.format(new Date());
        if (date.equals("00:00:00")){
            try {
                DBCPHelper.updateAllCount();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }*/


    }

    public void contextDestroyed(ServletContextEvent sce) {
        timer.cancel();
        sce.getServletContext().log("定时器销毁");
    }
}
