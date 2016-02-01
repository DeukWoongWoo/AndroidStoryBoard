package Analysis.Database.DataAccessObject.Event;

import Analysis.Constant.DatabaseQuery;
import Analysis.Database.SQLiteOpenHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Windows on 2016-02-01.
 */
public class EventDAOImpl extends SQLiteOpenHelper implements EventDAO {
    private final String tableName = "Event";

    @Override
    public void create() {
        System.out.println("Event table create ...");
        Statement statement = null;
        Connection connection = getConnection();
        try {
            statement = connection.createStatement();
            statement.executeUpdate(DatabaseQuery.dropTable + tableName);
            statement.executeUpdate(DatabaseQuery.createEventTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(connection,statement);
        }
    }

    @Override
    public void insert() {

    }

    @Override
    public void select() {

    }
}
