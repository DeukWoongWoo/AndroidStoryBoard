package Analysis.Database.DataAccessObject.Xml;

import Analysis.Constant.DatabaseQuery;
import Analysis.Database.SQLiteOpenHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Windows on 2016-02-01.
 */
public class XmlDAOImpl extends SQLiteOpenHelper implements XmlDAO {
    private final String tableName = "Xml";

    @Override
    public void create() {
        System.out.println("Xml table create ...");
        Statement statement = null;
        Connection connection = getConnection();
        try {
            statement = connection.createStatement();
            statement.executeUpdate(DatabaseQuery.dropTable + tableName);
            statement.executeUpdate(DatabaseQuery.createXmlTable);
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
