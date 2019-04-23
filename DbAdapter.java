import java.sql.*;

/*
to connect project.java (IntelliJ) with database MySQL must be:

URL: jdbc:mysql://localhost:3306/name_schema?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC
*/

public class DbAdapter {

    String jdbUrl = "jdbc:mysql://localhost:3306/test?useSSL=false";
    String username = "root";
    String password = "SQLSerwer2019";

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
  
      /**
     * Connect to database
     */

    public Connection connect() {
        try {
            connection = DriverManager.getConnection(jdbUrl, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Disconnect to database
     */

    public void disconnect (){
        try {
            if (connection != null){
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
          
        } catch (Exception e){
            e.getMessage();
        }
    }

}
