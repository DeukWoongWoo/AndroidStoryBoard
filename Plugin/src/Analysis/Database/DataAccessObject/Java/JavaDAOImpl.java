package Analysis.Database.DataAccessObject.Java;

import Analysis.Constant.DatabaseQuery;
import Analysis.Database.DataAccessObject.Component.ComponentDAO;
import Analysis.Database.DataAccessObject.Component.ComponentDAOImpl;
import Analysis.Database.DataAccessObject.Event.EventDAO;
import Analysis.Database.DataAccessObject.Event.EventDAOImpl;
import Analysis.Database.DataAccessObject.Xml.XmlDAO;
import Analysis.Database.DataAccessObject.Xml.XmlDAOImpl;
import Analysis.Database.DtatTransferObject.ComponentDTO;
import Analysis.Database.DtatTransferObject.EventDTO;
import Analysis.Database.DtatTransferObject.JavaDTO;
import Analysis.Database.DtatTransferObject.XmlDTO;
import Analysis.Database.QueryBuilder.QueryBuilder;
import Analysis.Database.SQLiteOpenHelper;

import java.sql.*;

/**
 * Created by woong on 2016-01-21.
 */
public class JavaDAOImpl extends SQLiteOpenHelper implements JavaDAO {
    private final String javaTableName = "Java";
    private final String xmlTableName = "Xml";

    private final XmlDAO xmlDAO = new XmlDAOImpl();
    private final ComponentDAO componentDAO = new ComponentDAOImpl();
    private final EventDAO eventDAO = new EventDAOImpl();

    private int currentJavaId;
    private int currentXmlId;

    @Override
    public void createJava() {
        System.out.println("Java table create ...");
        Statement statement = null;
        Connection connection = getConnection();
        try {
            statement = connection.createStatement();
            statement.executeUpdate(DatabaseQuery.dropTable + javaTableName);
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
    public void insertJava(JavaDTO javaDTO) {
        System.out.println("insert Java Table ...");
        Statement statement = null;
        PreparedStatement prep = null;
        Connection connection = getConnection();
        ResultSet rows = null;
        try{
            statement = connection.createStatement();
            statement.executeUpdate(javaDTO.getInsertQuery());
            prep = connection.prepareStatement(QueryBuilder.selectAll().from(javaTableName).where("name='"+javaDTO.getName()+"'").build());
            rows = prep.executeQuery();
            currentJavaId = rows.getInt(1);
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(connection, statement);
            close(null, prep, rows);
        }
    }

    @Override
    public void insertXml(XmlDTO xmlDTO) {
        xmlDTO.setJavaId(currentJavaId);
        currentXmlId = xmlDAO.insert(xmlDTO);
    }

    @Override
    public void insertComponent(ComponentDTO componentDTO) {
        componentDTO.setXmlId(currentXmlId);
        componentDAO.insert(componentDTO);
    }

    @Override
    public void insertEvent(EventDTO eventDTO) {
        eventDTO.setComponentId(componentDAO.select(eventDTO.getComponentName()));
        eventDAO.insert(eventDTO);
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

    @Override
    public void updateJava(JavaDTO javaDTO) {

    }
}
