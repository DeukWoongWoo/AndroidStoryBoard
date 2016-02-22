package Analysis.Database.DataAccessObject.Event;

import Analysis.Constant.DatabaseQuery;
import Analysis.Database.DtatTransferObject.ComponentDTO;
import Analysis.Database.DtatTransferObject.EventDTO;
import Analysis.Database.QueryBuilder.QueryBuilder;
import Analysis.Database.QueryBuilder.StringUtils;
import Analysis.Database.SQLiteOpenHelper;

import java.sql.*;

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
    public void insert(EventDTO eventDTO) {
        System.out.println("insert Event Table ...");
        Statement statement = null;
        Connection connection = getConnection();
        try {
            statement = connection.createStatement();
            statement.executeUpdate(eventDTO.getInsertQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(connection,statement);
        }
    }

    @Override
    public EventDTO select(String... col) {
        String query = null;
        if(col.length>0) query = QueryBuilder.selectAll().from(tableName).where(StringUtils.join(" AND ",col)).build();
        else query = QueryBuilder.selectAll().from(tableName).build();

        System.out.println("select Component Table...");
        PreparedStatement prep = null;
        Connection connection = getConnection();
        ResultSet rows = null;
        EventDTO eventDTO = null;
        try{
            prep = connection.prepareStatement(query);
            rows = prep.executeQuery();
            if(rows.getRow() > 0) {
                eventDTO = new EventDTO();
                eventDTO.setNum(rows.getInt(1));
                eventDTO.setComponentId(rows.getInt(2));
                eventDTO.setMethodName(rows.getString(3));
                eventDTO.setType(rows.getInt(4));
                eventDTO.setTotalLine(rows.getInt(5));
                eventDTO.setStartLine(rows.getInt(6));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(connection, prep, rows);
            return eventDTO;
        }
    }
}
