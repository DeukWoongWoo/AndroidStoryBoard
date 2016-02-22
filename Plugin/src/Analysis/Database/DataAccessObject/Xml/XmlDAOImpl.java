package Analysis.Database.DataAccessObject.Xml;

import Analysis.Constant.DatabaseQuery;
import Analysis.Database.DtatTransferObject.ComponentDTO;
import Analysis.Database.DtatTransferObject.XmlDTO;
import Analysis.Database.QueryBuilder.QueryBuilder;
import Analysis.Database.SQLiteOpenHelper;

import java.sql.*;

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
    public int insert(XmlDTO xmlDTO) {
        System.out.println("insert Xml Table...");

        Statement statement = null;
        PreparedStatement prep = null;
        Connection connection = getConnection();
        ResultSet rows = null;
        int xmlId = 0;

        try{
            statement = connection.createStatement();
            statement.executeUpdate(xmlDTO.getInsertQuery());
            prep = connection.prepareStatement(QueryBuilder.selectAll().from(tableName).where("javaId="+xmlDTO.getJavaId()).build());
            rows = prep.executeQuery();
            xmlId = rows.getInt(1);
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(connection, statement);
            close(null, prep, rows);
            return xmlId;
        }
    }

    @Override
    public XmlDTO select(String... col) {
        String query = null;
        if(col.length>0) query = QueryBuilder.selectAll().from(tableName).where(col[0]).build();
        else query = QueryBuilder.selectAll().from(tableName).build();

        System.out.println("select Component Table...");
        PreparedStatement prep = null;
        Connection connection = getConnection();
        ResultSet rows = null;
        XmlDTO xmlDTO = null;
        try{
            prep = connection.prepareStatement(query);
            rows = prep.executeQuery();
            xmlDTO = new XmlDTO();
            xmlDTO.setNum(rows.getInt(1));
            xmlDTO.setJavaId(rows.getInt(2));
            xmlDTO.setName(rows.getString(3));
            xmlDTO.setXmlName(rows.getString(4));
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(connection, prep, rows);
            return xmlDTO;
        }
    }
}
