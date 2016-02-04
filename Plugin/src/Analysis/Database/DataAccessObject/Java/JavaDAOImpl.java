package Analysis.Database.DataAccessObject.Java;

import Analysis.Constant.DatabaseQuery;
import Analysis.Database.DataAccessObject.Component.ComponentDAO;
import Analysis.Database.DataAccessObject.Component.ComponentDAOImpl;
import Analysis.Database.DataAccessObject.Event.EventDAO;
import Analysis.Database.DataAccessObject.Event.EventDAOImpl;
import Analysis.Database.DataAccessObject.Xml.XmlDAO;
import Analysis.Database.DataAccessObject.Xml.XmlDAOImpl;
import Analysis.Database.SQLiteOpenHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by woong on 2016-01-21.
 */
public class JavaDAOImpl extends SQLiteOpenHelper implements JavaDAO {
    private final String tableName = "Java";

    private final XmlDAO xmlDAO = new XmlDAOImpl();
    private final ComponentDAO componentDAO = new ComponentDAOImpl();
    private final EventDAO eventDAO = new EventDAOImpl();

    @Override
    public void createJava() {
        System.out.println("Java table create ...");
        Statement statement = null;
        Connection connection = getConnection();
        try {
            statement = connection.createStatement();
            statement.executeUpdate(DatabaseQuery.dropTable + tableName);
            statement.executeUpdate(DatabaseQuery.createJavaTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(connection,statement);
        }
    }

    @Override
    public void createXml() {
        xmlDAO.create();
    }

    @Override
    public void createComponent() {
        componentDAO.create();
    }

    @Override
    public void createEvent() {
        eventDAO.create();
    }

    @Override
    public int insertJava() {
        return 0;
    }

    @Override
    public int insertXml() {
        return 0;
    }

    @Override
    public int insertComponent() {
        return 0;
    }

    @Override
    public int insertEvent() {
        return 0;
    }

    @Override
    public void selectAll() {

    }

    @Override
    public void selectJava() {

    }

    @Override
    public void selectXml() {

    }

    @Override
    public void selectComponent() {

    }

    @Override
    public void selectEvent() {

    }
}
