package com.bim.dces.ipam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectSQL {
  static final String DB_URL = "jdbc:mysql://mysqlflexserver-ecad-pt-dev-opssandbox-we1094.mysql.database.azure.com:3306/ipam?useSSL=true&requireSSL=false";
  static final String USER = "opssandboxadmin1094@mysqlflexserver-ecad-pt-dev-opssandbox-we1094";
  static final String PASS = "KA5[%8?<8oyTaVktiAp!";

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
