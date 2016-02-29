package Analysis.Database.DataAccessObject.Java;

import Analysis.Constant.DatabaseQuery;
import Analysis.Database.DataAccessObject.Component.ComponentDAO;
import Analysis.Database.DataAccessObject.Component.ComponentDAOImpl;
import Analysis.Database.DataAccessObject.Event.EventDAO;
import Analysis.Database.DataAccessObject.Event.EventDAOImpl;
import Analysis.Database.DataAccessObject.NextActivity.NextActivityDAO;
import Analysis.Database.DataAccessObject.NextActivity.NextActivityDAOImpl;
import Analysis.Database.DataAccessObject.Xml.XmlDAO;
import Analysis.Database.DataAccessObject.Xml.XmlDAOImpl;
import Analysis.Database.DtatTransferObject.*;
import Analysis.Database.QueryBuilder.QueryBuilder;
import Analysis.Database.SQLiteOpenHelper;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by woong on 2016-01-21.
 */
public class JavaDAOImpl extends SQLiteOpenHelper implements JavaDAO {
    private final String tableName = "Java";

    private final NextActivityDAO nextActivityDAO = new NextActivityDAOImpl();
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
            statement.executeUpdate(DatabaseQuery.dropTable + tableName);
            statement.executeUpdate(DatabaseQuery.createJavaTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(connection,statement);
        }
    }

    @Override
    public void createNextActivity() {
        nextActivityDAO.create();
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
            prep = connection.prepareStatement(QueryBuilder.selectAll().from(tableName).where("name='"+javaDTO.getName()+"'").build());
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
    public void insertNextActivity(NextActivityDTO nextActivityDTO) {
        nextActivityDTO.setJavaId(currentJavaId);
        nextActivityDAO.insert(nextActivityDTO);
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
        eventDTO.setComponentId(componentDAO.select("name='"+eventDTO.getComponentName()+"'").getNum());
        eventDAO.insert(eventDTO);
    }

    @Override
    public ArrayList<JavaDTO>  selectAll() {
        PreparedStatement prep = null;
        Connection connection = getConnection();
        ResultSet rows = null;
        ArrayList<JavaDTO> items = new ArrayList<>();
        try {
            prep = connection.prepareStatement(QueryBuilder.selectAll().from(tableName).build());
            rows = prep.executeQuery();
            while(rows.next()){
                JavaDTO javaDTO = new JavaDTO();
                javaDTO.setNum(rows.getInt(1));
                javaDTO.setName(rows.getString(2));
                javaDTO.setPath(rows.getString(3));
                javaDTO.setExtendsValue(rows.getString(4));
                javaDTO.setImplementsValue(rows.getString(5));
                javaDTO.setXml(xmlDAO.select());
                javaDTO.setNextActivity(nextActivityDAO.select());
                items.add(javaDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(connection,prep,rows);
            return items;
        }
    }

    @Override
    public ArrayList<JavaDTO> selectJava(String... col) {
        PreparedStatement prep = null;
        Connection connection = getConnection();
        ResultSet rows = null;
        ArrayList<JavaDTO> items = new ArrayList<>();
        try {
            prep = connection.prepareStatement(QueryBuilder.selectAll().from(tableName).where(col[0]).build());
            rows = prep.executeQuery();
            while(rows.next()){
                JavaDTO javaDTO = new JavaDTO();
                javaDTO.setNum(rows.getInt(1));
                javaDTO.setName(rows.getString(2));
                javaDTO.setPath(rows.getString(3));
                javaDTO.setExtendsValue(rows.getString(4));
                javaDTO.setImplementsValue(rows.getString(5));
                items.add(javaDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(connection,prep,rows);
            return items;
        }
    }

    @Override
    public ArrayList<JavaDTO> selectNextActivity(String... col) {
        JavaDTO javaDTO = new JavaDTO();
        javaDTO.setNextActivity(nextActivityDAO.select(col));
        ArrayList<JavaDTO> list = new ArrayList<>();
        list.add(javaDTO);
        return list;
    }

    @Override
    public ArrayList<JavaDTO> selectXml(String... col) {
        JavaDTO javaDTO = new JavaDTO();
        javaDTO.setXml(xmlDAO.select(col));
        ArrayList<JavaDTO> list = new ArrayList<>();
        list.add(javaDTO);
        return list;
    }

    @Override
    public ArrayList<JavaDTO> selectComponent(String... col) {
        JavaDTO javaDTO = new JavaDTO();
        javaDTO.setComponent(componentDAO.select(col));
        ArrayList<JavaDTO> list = new ArrayList<>();
        list.add(javaDTO);
        return list;
    }

    @Override
    public ArrayList<JavaDTO> selectEvent(String... col) {
        JavaDTO javaDTO = new JavaDTO();
        javaDTO.setEvent(eventDAO.select(col));
        ArrayList<JavaDTO> list = new ArrayList<>();
        list.add(javaDTO);
        return list;
    }

    @Override
    public void updateJava(JavaDTO javaDTO) {
//        System.out.println("update Java table ...");
//        Statement statement = null;
//        Connection connection = getConnection();
//        try {
//            statement = connection.createStatement();
//            statement.executeUpdate(QueryBuilder.update(tableName).set("nextActivity='"+javaDTO.getNextActivity()+"'","intentName='"+javaDTO.getIntentName()+"'","intentFuncName='"+javaDTO.getIntentFuncName()+"'").where("num="+currentJavaId).build());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally {
//            close(connection,statement);
//        }
    }
}
