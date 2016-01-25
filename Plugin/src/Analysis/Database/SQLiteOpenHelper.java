package Analysis.Database;

import java.sql.*;

/**
 * Created by woong on 2016-01-18.
 */
public class SQLiteOpenHelper {

    static{
        try{
            Class.forName("org.sqlite.JDBC");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void close(Connection connection, Statement statement){
        try {
            if(statement != null) statement.close();
            if(connection != null) ConnectionPool.getInstance().releaseConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close(Connection connection, PreparedStatement prep, ResultSet resultSet){
        try {
            if(resultSet != null) resultSet.close();
            if(prep != null) prep.close();
            if(connection != null) ConnectionPool.getInstance().releaseConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

