package common;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

public abstract class ObjectIO {

  private static final String PATH = "C:\\Users\\HYUNA\\study2024\\board-example\\resource\\database.properties";
  private final String URL;
  private final String USERNAME;
  private final String PASSWORD;
  private Connection conn = null;

  public ObjectIO() {
    try(Reader fileReader = new FileReader(PATH, StandardCharsets.UTF_8);) {
      Properties p = new Properties();
      p.load(fileReader);
      URL = p.getProperty("url");
      USERNAME = p.getProperty("username");
      PASSWORD = p.getProperty("password");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void open() {
    try {
      conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
  }

  protected void close() {
    try {
      conn.close();
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
  }

  protected ResultSet execute(String query, ResultSet rs) {
    try {
      open();
      Statement stmt = conn.createStatement();
      rs = stmt.executeQuery(query);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return rs;
  }

  protected boolean execute(String query) {
    try{
      open();
      Statement stmt = conn.createStatement();
      int rows = stmt.executeUpdate(query);
      return rows != 0;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      close();;
    }
  }

  protected boolean execute(String query, Map<Integer, Object> params) {
    try{
      open();
      PreparedStatement pstmt = conn.prepareStatement(query);
      setParams(pstmt, params);
      int rows = pstmt.executeUpdate();
      pstmt.close();
      return rows != 0;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      close();
    }
  }

  protected ResultSet execute(String query, Map<Integer, Object> params, ResultSet rs) {
    try{
      open();
      PreparedStatement pstmt = conn.prepareStatement(query);
      setParams(pstmt, params);
      rs = pstmt.executeQuery();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return rs;
  }

  private void setParams(PreparedStatement pstmt, Map<Integer, Object> param) throws SQLException {
    for(Map.Entry<Integer, Object> entry : param.entrySet()) {
      Object value = entry.getValue();
      if(value instanceof Integer intValue) {
        pstmt.setInt(entry.getKey(), intValue);
      } else if(value instanceof String strValue) {
        pstmt.setString(entry.getKey(), strValue);
      } else if(value instanceof LocalDate dateValue) {
        pstmt.setDate(entry.getKey(), java.sql.Date.valueOf(dateValue));
      }
    }
  }

}
