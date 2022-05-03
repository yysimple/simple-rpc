package com.simple.rpc.core.register.strategy;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.simple.rpc.core.config.entity.SimpleRpcUrl;
import com.simple.rpc.core.network.message.Request;
import com.simple.rpc.core.register.AbstractRegisterCenter;
import com.simple.rpc.core.util.MysqlHelper;
import com.simple.rpc.core.util.SimpleRpcLog;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: mysql注册中心
 *
 * @author: WuChengXing
 * @create: 2022-05-04 00:46
 **/
@SuppressWarnings("all")
public class MysqlRegisterCenter extends AbstractRegisterCenter {

    private static Connection connection;
    private static MysqlHelper mysqlHelper;
    private static String database;
    private static String table;

    @Override
    public void init(SimpleRpcUrl url) {
        database = url.getDatabase();
        table = url.getTable();
        mysqlHelper = new MysqlHelper(url.getHost(), url.getPort(), url.getUsername(), url.getPassword(), url.getDatabase());
        connection = mysqlHelper.getConnection(database);
    }

    @Override
    public Boolean register(Request request) {
        String key = request.getInterfaceName() + "_" + request.getAlias();
        String value = JSON.toJSONString(request);
        String one = getOne(key);
        if (!StrUtil.isBlank(one)) {
            return update(key, value);
        }
        return insert(key, value);
    }

    @Override
    public String get(Request request) {
        String key = request.getInterfaceName() + "_" + request.getAlias();
        return getOne(key);
    }

    private Connection getConnection(SimpleRpcUrl url) {
        MysqlHelper mysqlHelper = new MysqlHelper(url.getHost(), url.getPort(), url.getUsername(), url.getPassword(), url.getDatabase());
        return mysqlHelper.getConnection(url.getDatabase());
    }

    private Boolean insert(String key, String value) {
        try {
            String sql = "insert into " + table + " (register_key, register_value) values('" + key + "','" + value + "'" +")";
            SimpleRpcLog.info(sql);
            Statement statement = connection.createStatement();
            return statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            // closeConnection(connection);
        }
    }

    private Boolean update(String key, String value) {
        try {
            String sql = "update " + table + " set register_value = '" + value + "' where register_key ='" + key + "'";
            SimpleRpcLog.info(sql);
            Statement statement = connection.createStatement();
            return statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            //closeConnection(connection);
        }
    }

    private String getOne(String key) {
        try {
            String sql = "select * from " + table + " where register_key ='" + key + "'";
            SimpleRpcLog.info(sql);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            String value = "";
            while (rs.next()) {
                value = rs.getString("register_value");
            }
            return value;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            //closeConnection(connection);
        }
    }

    private void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ignored) {
            }
        }
    }
}
