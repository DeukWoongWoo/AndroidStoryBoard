package Analysis.Database.DataAccessObject.Component;

import Analysis.Constant.DatabaseQuery;
import Analysis.Database.DtatTransferObject.ComponentDTO;
import Analysis.Database.QueryBuilder.QueryBuilder;
import Analysis.Database.QueryBuilder.StringUtils;
import Analysis.Database.SQLiteOpenHelper;

import java.sql.*;

/**
 * Created by Windows on 2016-02-01.
 */
public class ComponentDAOImpl extends SQLiteOpenHelper implements ComponentDAO {
    private final String tableName = "Component";

    @Override
    public void create() {
        System.out.println("Component table create ...");
        Statement statement = null;
        Connection connection = getConnection();
        try {
            statement = connection.createStatement();
            statement.executeUpdate(DatabaseQuery.dropTable + tableName);
            statement.executeUpdate(DatabaseQuery.createComponentTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(connection,statement);
        }
    }

    @Override
    public void insert(ComponentDTO componentDTO) {
        System.out.println("insert Component Table...");

        Statement statement = null;
        Connection connection = getConnection();
        try{
            statement = connection.createStatement();
            statement.executeUpdate(componentDTO.getInsertQuery());
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(connection, statement);
        }
    }

    @Override
    public ComponentDTO select(String... col) {
        String query = null;
        if(col.length>0) query = QueryBuilder.selectAll().from(tableName).where(StringUtils.join(" AND ",col)).build();
        else query = QueryBuilder.selectAll().from(tableName).build();

        System.out.println("select Component Table...");
        PreparedStatement prep = null;
        Connection connection = getConnection();
        ResultSet rows = null;
        ComponentDTO componentDTO = null;
        try{
            prep = connection.prepareStatement(query);
            rows = prep.executeQuery();
            componentDTO = new ComponentDTO();
            componentDTO.setNum(rows.getInt(1));
            componentDTO.setXmlId(rows.getInt(2));
            componentDTO.setName(rows.getString(3));
            componentDTO.setType(rows.getString(4));
            componentDTO.setMethodName(rows.getString(5));
            componentDTO.setXmlName(rows.getString(6));
            componentDTO.setTotalLine(rows.getInt(7));
            componentDTO.setStartLine(rows.getInt(8));
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(connection, prep, rows);
            return componentDTO;
        }
    }
}
