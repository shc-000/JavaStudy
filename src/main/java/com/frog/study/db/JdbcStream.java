package com.frog.study.db;

import java.sql.*;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/08/22
 */
public class JdbcStream {

    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://47.93.33.2:3306/modelfrog?useServerPrepStmts=false&rewriteBatchedStatements=true&useUnicode=true&characterEncoding=UTF-8&useSSL=false";
        String user = "root";
        String password = "123456";
        return DriverManager.getConnection(url, user, password);
    }

    public static void main(String[] args) throws SQLException {
        JdbcStream jdbcStream = new JdbcStream();
        String sql = "select * from t_order_event_log";
        int count = jdbcStream.testExecute(sql, false);
        System.out.println("result=" + count);
    }

    public int testExecute(String sql, boolean isStreamQuery) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int count = 0;
        try {
            //获取数据库连接
            conn = getConnection();
            if (isStreamQuery) {
                //设置流式查询参数
                stmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                stmt.setFetchSize(Integer.MIN_VALUE);
//                stmt.setFetchSize(5000);
            } else {
                //普通查询
                stmt = conn.prepareStatement(sql);
            }

            //执行查询获取结果
            rs = stmt.executeQuery();
            //遍历结果
            while (rs.next()) {
//                System.out.println(rs.getString(1));
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(stmt, rs, conn);
        }
        return count;
    }

    private void close(PreparedStatement stmt, ResultSet rs, Connection conn) throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (stmt != null) {
            stmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
}
