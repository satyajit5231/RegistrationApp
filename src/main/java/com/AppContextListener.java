package com;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.ArrayList;
import java.util.List;

//@WebListener
public class AppContextListener implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent sce){
        List<User> users=new ArrayList<>();
        sce.getServletContext().setAttribute("users", users);
        System.out.println("User List created in ServletContext");
    }
}
