package com.shad0wstv.footballapp.dao;

import java.sql.Connection;

public interface ConnectionDAO {
    Connection connect();
    void disconnect();
}
