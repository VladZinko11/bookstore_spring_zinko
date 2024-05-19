package com.zinko.data.dao.connection.impl;

import com.zinko.data.dao.connection.MyConnectionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Connection;

@Slf4j
@Component
public class MyConnectionManagerImpl implements MyConnectionManager {

    private ConnectionPool connectionPool;

    @Autowired
    public MyConnectionManagerImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        log.info("Connection pool initialize");
    }

    @Override
    public void close() throws IOException {
        connectionPool.destroyPool();
    }

    @Override
    public Connection getConnection() {
        log.info("Connection received");
        return connectionPool.getConnection();
    }

}
