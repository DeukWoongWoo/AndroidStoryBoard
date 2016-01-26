package Analysis.Database.DataAccessObject.Manifest;

import Analysis.Database.DataAccessObject.Activity.ActivityDAO;
import Analysis.Database.DataAccessObject.Activity.ActivityDAOImpl;
import Analysis.Database.QueryBuilder.QueryBuilder;
import Analysis.Database.SQLiteOpenHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by woong on 2016-01-21.
 */
public class ManifestDAOImpl extends SQLiteOpenHelper implements ManifestDAO {
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
    public void selectAll(String query) {
        PreparedStatement prep = null;
        Connection connection = getConnection();
        ResultSet rows = null;
        try {
            prep = connection.prepareStatement(query);
            rows = prep.executeQuery();
            while(rows.next()){
                System.out.println("num : " + rows.getString(1) + " / package : " + rows.getString(2) + " / theme : " + rows.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void selectManifest(String query) {
        PreparedStatement prep = null;
        Connection connection = getConnection();
        ResultSet rows = null;
        try {
            prep = connection.prepareStatement(query);
            rows = prep.executeQuery();
            while(rows.next()){
                System.out.println("num : " + rows.getString(1) + " / package : " + rows.getString(2) + " / theme : " + rows.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void selectManifest() {
        PreparedStatement prep = null;
        Connection connection = getConnection();
        ResultSet rows = null;
        try {
            prep = connection.prepareStatement(QueryBuilder.selectAll().from("Manifest").build());
            rows = prep.executeQuery();
            while(rows.next()){
                System.out.println("num : " + rows.getString(1) + " / package : " + rows.getString(2) + " / theme : " + rows.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void selectActivity() {

    }

}
