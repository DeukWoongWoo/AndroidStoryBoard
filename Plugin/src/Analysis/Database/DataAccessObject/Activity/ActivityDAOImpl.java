package Analysis.Database.DataAccessObject.Activity;



import Analysis.Database.DtatTransferObject.ActivityDTO;
import Analysis.Database.DtatTransferObject.ManifestDTO;
import Analysis.Database.QueryBuilder.QueryBuilder;
import Analysis.Database.SQLiteOpenHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by woong on 2016-01-24.
 */
public class ActivityDAOImpl extends SQLiteOpenHelper implements ActivityDAO {
    private final String tableName = "Activity";

    @Override
    public void insert() {

    }

    @Override
    public ManifestDTO select() {
        PreparedStatement prep = null;
        Connection connection = getConnection();
        ResultSet rows = null;
        ManifestDTO items = new ManifestDTO();
        try {
            prep = connection.prepareStatement(QueryBuilder.selectAll().from(tableName).build());
            rows = prep.executeQuery();
            while(rows.next()){
                items.setActivity(new ActivityDTO(rows.getInt(1), rows.getInt(3), rows.getString(2), rows.getInt(3), rows.getInt(4), rows.getInt(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(connection,prep,rows);
            return items;
        }
    }

//    @Override
//    public void select() {
//    }
}
