package com.zinko.data.dao.connection.impl;

import com.zinko.data.dao.connection.MyConnectionManager;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.Connection;

@Slf4j
public class MyConnectionManagerImpl implements MyConnectionManager {

    private final String url;
    private final String user;
    private final String password;
    private final String driver;
    private ConnectionPool connectionPool;
    private int poolSize = 16;

    public MyConnectionManagerImpl(String url, String user, String password, String driver) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.driver = driver;
        connectionPool = new ConnectionPool(driver, url, user, password, poolSize);
        log.info("Connection pool initialize");
    }

    @Override
    public void close() throws IOException {
        connectionPool.destroyPool();
    }

    @Override
    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    @Override
    public Connection getConnection() {
        if(this.connectionPool==null) {
            connectionPool = new ConnectionPool(driver, url, user, password, poolSize);
            log.info("Connection pool initialized");
        }
        log.info("Connection received");
        return connectionPool.getConnection();
    }

}
