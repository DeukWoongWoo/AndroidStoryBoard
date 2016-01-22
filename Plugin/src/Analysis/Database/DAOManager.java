package Analysis.Database;

import Analysis.Constant;
import Analysis.Database.DataAccessObject.*;
import Analysis.Database.DtatTransferObject.DTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by woong on 2016-01-21.
 */
public class DAOManager extends SQLiteOpenHelper {
    public volatile  static DAOManager instance = null;

    private final DAO manifestDAO = new ManifestDAO();
    private final DAO activityDAO = new ActivityDAO();
    private final DAO javaDAO = new JavaDAO();
    private final DAO xmlDAO = new XmlDAO();
    private final DAO componentDAO = new ComponentDAO();
    private final DAO eventDAO = new EventDAO();
    private DAO currentDAO = null;

    public static DAOManager getInstance(){
        if(instance == null){
            synchronized (DAOManager.class){
                if(instance == null) instance = new DAOManager();
            }
        }
        return instance;
    }

    private DAOManager(){
        onCreate();
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

    public void useDAO(int type){
        switch(type){
            case Constant.MANIFEST:
                currentDAO = manifestDAO;
                break;
            case Constant.ACTIVITY:
                currentDAO = activityDAO;
                break;
            case Constant.JAVA:
                currentDAO = javaDAO;
                break;
            case Constant.XML:
                currentDAO = xmlDAO;
                break;
            case Constant.COMPONENT:
                currentDAO = componentDAO;
                break;
            case Constant.EVENT:
                currentDAO = eventDAO;
                break;
        }
    }

    public void insert(DTO dto){
        currentDAO.onInsert(dto);
    }
}
