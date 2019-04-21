package com.sakura.dormitory.socket;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class tcpServerListener implements ServletContextListener {
    private tcpServer tcpServer;

    public void contextDestroyed(ServletContextEvent e) {

        if (tcpServer != null && tcpServer.isInterrupted()) {
            tcpServer.closeServerSocket();
            tcpServer.interrupt();
        }
    }

    public void contextInitialized(ServletContextEvent e) {
        ServletContext servletContext = e.getServletContext();
        if (tcpServer == null) {
            tcpServer = new tcpServer(servletContext);
            tcpServer.start(); // servlet上下文初始化时启动socket服务端线程
        }

    }
}
