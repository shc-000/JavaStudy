package com.frog.study.db;

import com.frog.study.base.Student;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 批量插入百万数量级的数据到mysql的解决方案(单线程实现)
 *
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/05/07
 */
@Slf4j
@Data
public abstract class BigDataInsert<T> {
    //     String driverClassName = "com.mysql.cj.jdbc.Driver";
    String driverClassName = "";
    //     String url = "jdbc:mysql://localhost:3306/bigdata?useServerPrepStmts=false&rewriteBatchedStatements=true&useUnicode=true&characterEncoding=UTF-8";
    String url = "";
    //     String user = "root";
    String user = "";
    //     String password = "123456";
    String password = "";
    //     String sql = "";
    String sql = "";

    public BigDataInsert() {
        init();
    }

    public void insertBigData(List<T> list) {
        //定义连接、statement对象
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            // 检查初始化参数
            checkInit();
            //加载jdbc驱动
            Class.forName(driverClassName);
            //连接mysql
            conn = DriverManager.getConnection(url, user, password);
            //将自动提交关闭
            conn.setAutoCommit(false);
            //预编译sql
            pstm = conn.prepareStatement(sql);
            //开始总计时
            long bTime1 = System.currentTimeMillis();

            List<List<T>> listList = null;
            if (list.size() > 100000) {
                listList = fixedGrouping(list, 100000);
            } else {
                listList.add(list);
            }
            //循环10次，每次十万数据，一共100万
            for (int i = 0; i < listList.size(); i++) {
                //开启分段计时，计1W数据耗时
                long bTime = System.currentTimeMillis();
                //开始循环
                for (T object : listList.get(i)) {
                    //赋值
                    pstmToSetValue(pstm, object);
                    //添加到同一个批处理中
                    pstm.addBatch();
                }
                //执行批处理
                pstm.executeBatch();
                //提交事务
                conn.commit();
                //关闭分段计时
                long eTime = System.currentTimeMillis();
                //输出
                System.out.println("成功插入" + listList.get(i).size() + "条数据耗时：" + (eTime - bTime));
            }
            //关闭总计时
            long eTime1 = System.currentTimeMillis();
            //输出
            System.out.println("插入" + list.size() + "数据共耗时：" + (eTime1 - bTime1));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } finally {
            try {
                pstm.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkInit() {
        if ("".equals(driverClassName)) {
            log.warn("driverClassName未初始化！");
        }
        if ("".equals(url)) {
            log.warn("url未初始化！");
        }
        if ("".equals(user)) {
            log.warn("user未初始化！");
        }
        if ("".equals(password)) {
            log.warn("password未初始化！");
        }
        if ("".equals(sql)) {
            log.warn("sql未设置！");
        }
    }

    /**
     * 将一组数据固定分组，每组n个元素
     *
     * @param source 要分组的数据源
     * @param n      每组n个元素
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> fixedGrouping(List<T> source, int n) {

        if (null == source || source.size() == 0 || n <= 0) {
            return null;
        }
        List<List<T>> result = new ArrayList<List<T>>();
        int remainder = source.size() % n;
        int size = (source.size() / n);
        for (int i = 0; i < size; i++) {
            List<T> subset = null;
            subset = source.subList(i * n, (i + 1) * n);
            result.add(subset);
        }
        if (remainder > 0) {
            List<T> subset = null;
            subset = source.subList(size * n, size * n + remainder);
            result.add(subset);
        }
        return result;
    }

    public abstract PreparedStatement pstmToSetValue(PreparedStatement pstm, T object);

    public abstract void init();
}

class Test extends BigDataInsert<Student> {

//    继承BigDataInsert实现init()和pstmToSetValue()方法
//
//    init(): 初始化链接信息的方法（包括插入语句），在实例被创建出来的时候会被调用
//
//    pstmToSetValue(): 对预编译的sql语句set值，当实例出来的对象调用insertBigData()时执行
    @Override
    public PreparedStatement pstmToSetValue(PreparedStatement pstm, Student stu) {
        try {
            pstm.setString(1, stu.getSex());
            pstm.setString(2, stu.getSchool());
            pstm.setString(3, stu.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pstm;
    }

    @Override
    public void init() {
        this.driverClassName = "com.mysql.cj.jdbc.Driver";
        this.url = "jdbc:mysql://10.1.1.149:3306/cnleb139?useServerPrepStmts=false&rewriteBatchedStatements=true&useUnicode=true&characterEncoding=UTF-8";
        this.user = "root";
        this.password = "Root123!!!";
        this.sql = "INSERT INTO net_collect_data(collect_time,mac,edu_id,ip,time,TS_UP_4G,TS_UP,TS_DOWN_4G,TS_DOWN,PKG_UP_4G,PKG_UP,PKG_DOWN_4G,PKG_DOWN,status_code,created_date) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    }
}
//需要从上报的单个文件中解析出百万数据入库，项目中无论是使用jpa 还是 mybatis 存入数据达到10000时速度明显变慢，达到100000时就让人难以接受。所以就考虑使用存储过程或者是使用原生jdbc实现，该案例使用原生jdbc实现。

//上面代码是一个批量保存大数据的一个单线程的封装，使用方法如下：
