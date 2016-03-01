package Analysis.Database.DataAccessObject.NextActivity;

import Analysis.Constant.DatabaseQuery;
import Analysis.Database.DtatTransferObject.NextActivityDTO;
import Analysis.Database.QueryBuilder.QueryBuilder;
import Analysis.Database.SQLiteOpenHelper;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by woong on 2016-02-29.
 */
public class NextActivityDAOImpl extends SQLiteOpenHelper implements NextActivityDAO {
    private final String tableName = "NextActivity";

    @Override
    public void create() {
        System.out.println("NextActivity table create ...");
        Statement statement = null;
        Connection connection = getConnection();
        try {
            statement = connection.createStatement();
            statement.executeUpdate(DatabaseQuery.dropTable + tableName);
            statement.executeUpdate(DatabaseQuery.createNextActivity);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(connection,statement);
        }
    }

    @Override
    public void insert(NextActivityDTO nextActivityDTO) {
        System.out.println("insert NextActivity Table...");

        Statement statement = null;
        Connection connection = getConnection();
        try{
            statement = connection.createStatement();
            statement.executeUpdate(nextActivityDTO.getInsertQuery());
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(connection, statement);
        }
    }

    @Override
    public ArrayList<NextActivityDTO> select(String... col) {
        String query = null;
        if(col.length>0) query = QueryBuilder.selectAll().from(tableName).where(col[0]).build();
        else query = QueryBuilder.selectAll().from(tableName).build();

        System.out.println("select NextActivity Table...");
        PreparedStatement prep = null;
        Connection connection = getConnection();
        ResultSet rows = null;
        ArrayList<NextActivityDTO> nextActivities = null;
        try{
            prep = connection.prepareStatement(QueryBuilder.selectAll().from(tableName).where(query).build());
            rows = prep.executeQuery();
            while(rows.next()) {
                NextActivityDTO nextActivityDTO = new NextActivityDTO();
                nextActivityDTO.setNum(rows.getInt(1));
                nextActivityDTO.setJavaId(rows.getInt(2));
                nextActivityDTO.setName(rows.getString(3));
                nextActivityDTO.setIntentName(rows.getString(4));
                nextActivityDTO.setIntentFuncName(rows.getString(5));
                nextActivities.add(nextActivityDTO);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(connection, prep, rows);
            return nextActivities;
        }
    }
}
