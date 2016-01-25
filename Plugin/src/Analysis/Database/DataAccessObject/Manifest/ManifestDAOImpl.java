package Analysis.Database.DataAccessObject.Manifest;

import Analysis.Database.DataAccessObject.Activity.ActivityDAO;
import Analysis.Database.DataAccessObject.Activity.ActivityDAOImpl;
import Analysis.Database.SQLiteOpenHelper;

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
    public void selectAll() {

    }

    @Override
    public void selectManifest() {

    }
    @Override
    public void selectActivity() {

    }

}
