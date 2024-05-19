package com.zinko.data.dao.connection;

import java.io.Closeable;
import java.sql.Connection;

public interface MyConnectionManager extends Closeable {

    Connection getConnection();

}
