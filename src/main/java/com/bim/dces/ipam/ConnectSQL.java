package com.bim.dces.ipam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectSQL {
  static final String DB_URL = "jdbc:mysql://testmysqlapps.mysql.database.azure.com:3306/ipam?useSSL=true&requireSSL=false";
  static final String USER = "azureuser@testmysqlapps";
  static final String PASS = "admin@2023";

  public static void main(String[] args) {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
      Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
      System.out.println(conn.getMetaData().getConnection());
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    } catch (InstantiationException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }
}
