package jdbc;

import java.sql.*;

/**
 * 在使用jdbc连接Mysql的时候，连接URL中指定连接数据库A，但是在操作表的时候使用db.table，可以操作其他数据库的表
 * Created by Niki on 2018/4/10 11:40
 */
public class JDBCDemo {
    private static void test() {
        String name = "com.mysql.jdbc.Driver";

        String url = "jdbc:mysql://10.1.50.55:20000/tql_business_user_db_00_db?characterEncoding=UTF-8";
        String user = "tqlbs_master";
        String awd = "tqlbs_master@123";
        try {
            Class.forName(name);//指定连接类型
            Connection conn = DriverManager.getConnection(url, user, awd);//获取连接
            delete(conn);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void select(Connection conn) throws SQLException {
//        String sql = "select * from tql_business_user_db_98_db.t_credit_user_identity_8 where  Fuid = 456988";
        String sql = "select * from tql_business_db.t_tql_business_info where Fbusiness_id = 'QDC201803130000007'";
        PreparedStatement pst = conn.prepareStatement(sql);//准备执行语句
        ResultSet resultSet = pst.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
            System.out.println(resultSet.getString(2));
            System.out.println(resultSet.getString(3));
        }

    }

    private static void insert(Connection connection) throws SQLException {
        String sql = "insert into test.t_t1(Fc1,Fc2,Fc3,Fmodify_time,Fversion) values(?,?,?,?,?)";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setInt(1, 1);
        pst.setInt(2, 2);
        pst.setInt(3, 3);
        pst.setDate(4, new Date(System.currentTimeMillis()));
        pst.setString(5, "1");

        int i = pst.executeUpdate();
        System.out.println("插入数据条数：" + i);
    }

    private static void update(Connection connection) throws SQLException {
        String sql = "update  test.t_t1 set Fversion = ?, Fmodify_time=?, Fc2 = ?  where Fc1=?";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, "2");
        pst.setDate(2, new Date(System.currentTimeMillis()));
        pst.setInt(3, 100);
        pst.setInt(4, 1);

        int i = pst.executeUpdate();
        System.out.println("修改数据条数：" + i);
    }

    private static void delete(Connection connection) throws SQLException {
        String sql = "delete from  test.t_t1 where Fc1=1 ";
        PreparedStatement pst = connection.prepareStatement(sql);
        int del = pst.executeUpdate();
        System.out.println("删除数据的条数："+del);
    }

    public static void main(String[] args) {
        test();
    }


}
