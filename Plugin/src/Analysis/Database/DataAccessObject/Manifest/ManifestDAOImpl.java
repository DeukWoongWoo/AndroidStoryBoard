package Analysis.Database.DataAccessObject.Manifest;

import Analysis.Database.DataAccessObject.Activity.ActivityDAO;
import Analysis.Database.DataAccessObject.Activity.ActivityDAOImpl;
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
 * Created by woong on 2016-01-21.
 */
public class ManifestDAOImpl extends SQLiteOpenHelper implements ManifestDAO {
    private final String tableName = "Manifest";

    private final ActivityDAO activityDAO = new ActivityDAOImpl();

    @Override
    public int insertManifest() {
        /*ManifestDTO rows = (ManifestDTO) dto.getDTO();
        PreparedStatement prep = null;
        Connection connection = getConnection();
        try {
            prep = connection.prepareStatement(DatabaseQuery.insertManifest);
            prep.setString(1,rows.getPackageName());
            prep.setString(2,rows.getTheme());
            prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(connection,prep,null);
        }*/
        return 0;
    }

    @Override
    public void insertActivity() {
        activityDAO.insert();
    }

    @Override
    public void selectAll() {
    }

    @Override
    public ArrayList<ManifestDTO> selectManifest() {
        PreparedStatement prep = null;
        Connection connection = getConnection();
        ResultSet rows = null;
        ArrayList<ManifestDTO> items = new ArrayList<>();
        try {
            prep = connection.prepareStatement(QueryBuilder.selectAll().from(tableName).build());
            rows = prep.executeQuery();
            while(rows.next()){
//                System.out.println("num : " + rows.getString(1) + " / package : " + rows.getString(2) + " / theme : " + rows.getString(3));
                items.add(new ManifestDTO(rows.getInt(1), rows.getString(2), rows.getString(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(connection,prep,rows);
            return items;
        }
    }

    @Override
    public ArrayList<ManifestDTO> selectActivity() {
        ArrayList<ManifestDTO> items = new ArrayList<>();
        items.add(activityDAO.select());
        return items;
    }

//    @Override
//    public void selectActivity() {
//        activityDAO.select();
//    }

}
