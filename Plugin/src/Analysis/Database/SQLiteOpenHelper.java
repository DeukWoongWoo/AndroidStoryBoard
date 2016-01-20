package Analysis.Database;


import java.sql.*;

/**
 * Created by woong on 2016-01-18.
 */
public abstract class SQLiteOpenHelper {

    static{
        try{
            Class.forName("org.sqlite.JDBC");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public SQLiteOpenHelper(){
        onCreate();
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

    private void onCreate(){
        Statement statement = null;
        Connection connection = getConnection();
        try {
            statement = connection.createStatement();
            statement.executeUpdate(DatabaseQuery.dropTable + "Manifest");
            statement.executeUpdate(DatabaseQuery.createManifestTable);
            statement.executeUpdate(DatabaseQuery.dropTable + "Activity");
            statement.executeUpdate(DatabaseQuery.createActivityTable);
            statement.executeUpdate(DatabaseQuery.dropTable + "Java");
            statement.executeUpdate(DatabaseQuery.createJavaTable);
            statement.executeUpdate(DatabaseQuery.dropTable + "Xml");
            statement.executeUpdate(DatabaseQuery.createXmlTable);
            statement.executeUpdate(DatabaseQuery.dropTable + "Component");
            statement.executeUpdate(DatabaseQuery.createComponentTable);
            statement.executeUpdate(DatabaseQuery.dropTable + "Event");
            statement.executeUpdate(DatabaseQuery.createEventTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(connection,statement);
        }
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

    public abstract void onInsert(String query);

    public abstract void onUpdate(String query);

    public abstract void onSelect(String query);
}

