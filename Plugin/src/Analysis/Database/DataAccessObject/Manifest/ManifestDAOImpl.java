package Analysis.Database.DataAccessObject.Manifest;

import Analysis.Constant.DatabaseQuery;
import Analysis.Database.DataAccessObject.Activity.ActivityDAO;
import Analysis.Database.DataAccessObject.Activity.ActivityDAOImpl;
import Analysis.Database.DtatTransferObject.ActivityDTO;
import Analysis.Database.DtatTransferObject.ManifestDTO;
import Analysis.Database.QueryBuilder.QueryBuilder;
import Analysis.Database.SQLiteOpenHelper;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by woong on 2016-01-21.
 */
public class ManifestDAOImpl extends SQLiteOpenHelper implements ManifestDAO {
    private final String tableName = "Manifest";

    private final ActivityDAOImpl activityDAO = new ActivityDAOImpl();

    private int currentManifestId;

    @Override
    public void createManifest() {
        System.out.println("Manifest table create ...");
        Statement statement = null;
        Connection connection = getConnection();
        try {
            statement = connection.createStatement();
            statement.executeUpdate(DatabaseQuery.dropTable + tableName);
            statement.executeUpdate(DatabaseQuery.createManifestTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(connection,statement);
        }
    }

    @Override
    public void createActivity() {
        activityDAO.create();
    }

    @Override
    public void insertManifest(ManifestDTO manifestDTO) {
        System.out.println("insertManifest...");
        Statement statement = null;
        PreparedStatement prep = null;
        Connection connection = getConnection();
        ResultSet rows = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(manifestDTO.getInsertQuery());
            prep = connection.prepareStatement(QueryBuilder.selectAll().from(tableName).where("package='"+manifestDTO.getPackageName()+"'").build());
            rows = prep.executeQuery();
            currentManifestId = rows.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(connection,statement);
            close(null, prep, rows);
        }
    }

    @Override
    public void insertActivity(ActivityDTO activityDTO) {
        activityDTO.setManifestId(currentManifestId);
        activityDAO.insert(activityDTO);
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
