package Analysis.Database.DataAccessObject;

import Analysis.Database.DatabaseQuery;
import Analysis.Database.DtatTransferObject.DTO;
import Analysis.Database.DtatTransferObject.ManifestDTO;
import Analysis.Database.SQLiteOpenHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by woong on 2016-01-21.
 */
public class ManifestDAO extends SQLiteOpenHelper implements DAO{
    @Override
    public void onInsert(DTO dto) {
        ManifestDTO rows = (ManifestDTO) dto.getDTO();
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
        }
    }

    @Override
    public void onUpdate(DTO dto) {

    }

    @Override
    public void onSelect(DTO dto) {

    }
}
