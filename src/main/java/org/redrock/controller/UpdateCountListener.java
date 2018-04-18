package org.redrock.controller;

import org.redrock.util.DBCPFactory;
import org.redrock.util.DBCPHelper;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateCountListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        SimpleDateFormat sdf=new SimpleDateFormat();
        String date=sdf.format(new Date());
        if (date.equals("00:00:00")){
            try {
                DBCPHelper.updateAllCount();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public void contextDestroyed(ServletContextEvent sce) {

    }
}
