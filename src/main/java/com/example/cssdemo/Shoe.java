package com.example.cssdemo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Shoe {
    private  String name;
    private  String brand;
    private String sku;
    private double size;
    private double price;

    public Shoe(String name, String brand, String sku, double size, double price) {
        this.name = name;
        this.brand = brand;
        this.sku = sku;
        this.size = size;
        this.price = price;
    }

    public Shoe(){};

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getSku() {
        return sku;
    }


    public double getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setSize(double size) {
        this.size = size;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Shoe{" +
                "name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", sku=" + sku +
                ", size=" + size +
                ", price=" + price +
                '}';
    }

    public String addShoeToDB(int shoeID) throws SQLException {
        Connection connection = DBConnect.getConnection();
        ResultSet rs = connection.createStatement().executeQuery("select count(*) from sneaker");
        int rows = 0;
        while(rs.next()) {
            rows = rs.getInt(1)+1;
        }
        return "insert into jdbc.sneaker(idSneaker, name, brand, sku, size, price) VALUES ('"+rows+"', '"+name+"', '"+brand+"', '"+sku+"', '"+size+"', '"+price+"')";
    }
}

