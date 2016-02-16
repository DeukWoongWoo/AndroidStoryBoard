package Analysis.Database.DataAccessObject.Component;

import Analysis.Constant.DatabaseQuery;
import Analysis.Database.DtatTransferObject.ComponentDTO;
import Analysis.Database.QueryBuilder.QueryBuilder;
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
    public int select(String col) {
        System.out.println("select Component Table...");
        PreparedStatement prep = null;
        Connection connection = getConnection();
        ResultSet rows = null;
        int componentId = 0;
        try{
            prep = connection.prepareStatement(QueryBuilder.selectAll().from(tableName).where("name='"+col+"'").build());
            rows = prep.executeQuery();
            componentId = rows.getInt(1);
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(connection, prep, rows);
            return componentId;
        }
    }
}
