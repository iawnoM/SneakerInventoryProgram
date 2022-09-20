package com.example.cssdemo;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import org.w3c.dom.ls.LSOutput;

import javax.xml.transform.Result;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

public class DBConnect {


    public static void main(String[] args) throws IOException, SQLException {

        String url = "jdbc:mysql://localhost:3306/jdbc";
        String username = "root";
        String password = "Retold@19";
        String laptopPassword = "Beasty@25";
//        FileInputStream inputStream = new FileInputStream();

//        try {
//            Connection connection = DriverManager.getConnection(url, username, password);
//            Statement statement = connection.createStatement();
//            System.out.println("Database connected!");
//            String sql = ("select * from jdbc.sneaker");
//            ResultSet rs = statement.executeQuery(sql);
//            String edit = "update jdbc.sneaker "
//                    + "set name='test'"
//                    + " where idSneaker=2";
//            statement.executeUpdate(edit);
//
//            System.out.println("Update Complete!");
//
//        } catch (SQLException e) {
//            throw new IllegalStateException("Cannot connect the database!", e);
//        }

    }
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "Beasty@25");
    }

    public static Connection getNewConnection() {
        try {
            return getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
