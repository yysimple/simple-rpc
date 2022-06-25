package com.simple.rpc.test.common.starter.spi.collect;

import com.simple.rpc.common.config.SimpleRpcUrl;
import com.simple.rpc.common.interfaces.DataCollection;
import com.simple.rpc.common.interfaces.entity.CollectData;
import com.simple.rpc.common.util.MysqlHelper;
import com.simple.rpc.common.util.SimpleRpcLog;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-06-16 23:54
 **/
public class LogCollectData implements DataCollection {

    @Override
    public Boolean collect(CollectData collectData) {
        // SimpleRpcUrl simpleRpcUrl = SimpleRpcUrl.toSimpleRpcUrl(ConfigManager.getInstant().loadConfig(RegistryConfig.class));
        SimpleRpcUrl simpleRpcUrl = new SimpleRpcUrl();
        simpleRpcUrl.setUsername("root");
        simpleRpcUrl.setDatabase("simple_collect_log");
        simpleRpcUrl.setPassword("970412@wcx.com");
        simpleRpcUrl.setHost("127.0.0.1");
        simpleRpcUrl.setPort(3306);
        simpleRpcUrl.setTable("simple_agent_log");
        Connection connection = getConnection(simpleRpcUrl);
        insert(simpleRpcUrl, collectData, connection);
        return true;
    }

    private Connection getConnection(SimpleRpcUrl url) {
        MysqlHelper mysqlHelper = new MysqlHelper(url.getHost(), url.getPort(), url.getUsername(), url.getPassword(), url.getDatabase());
        return mysqlHelper.getConnection(url.getDatabase());
    }

    private Boolean insert(SimpleRpcUrl url, CollectData data, Connection connection) {
        try {
            String sql = "insert into " + url.getTable()
                    + "(trace_id,span_id,parent_span_id,level,invoke_status,invoker,entry_time,exit_time,app_name,host,clazz_name,method_name,request_info,result_info,exception_info) values('"
                    + data.getTraceId() + "','"
                    + data.getSpanId() + "','"
                    + data.getParentSpanId() + "','"
                    + data.getLevel() + "','"
                    + data.getInvokeStatus() + "','"
                    + data.getInvoker() + "','"
                    + (Objects.isNull(data.getEntryTime()) ? 0 : data.getEntryTime().getTime()) + "','"
                    + (Objects.isNull(data.getExitTime()) ? 0 : data.getExitTime().getTime()) + "','"
                    + data.getAppName() + "','"
                    + data.getHost() + "','"
                    + data.getClazzName() + "','"
                    + data.getMethodName() + "','"
                    + data.getRequestInfo() + "','"
                    + data.getResultInfo() + "','"
                    + data.getExceptionInfo() + "'"
                    + ")";
            Statement statement = connection.createStatement();
            return statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            closeConnection(connection);
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
