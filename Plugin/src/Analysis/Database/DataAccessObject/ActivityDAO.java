package Analysis.Database.DataAccessObject;


import Analysis.Database.DtatTransferObject.DTO;
import Analysis.Database.DatabaseQuery;
import Analysis.Database.SQLiteOpenHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by woong on 2016-01-19.
 */
public class ActivityDAO extends SQLiteOpenHelper implements DAO{

    public ActivityDAO(){
        super();
    }

    @Override
    public void onInsert(DTO dto) {
        PreparedStatement prep = null;
        Connection connection = getConnection();
        try {
            prep = connection.prepareStatement(DatabaseQuery.insertManifest);
            prep.setString(1,"test");
            prep.setString(2,"test");
            prep.setInt(3,2);
            prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(connection,prep,null);
        }
    }

    @Override
    public void onUpdate(DTO dto) {
        PreparedStatement prep = null;
        Connection connection = getConnection();
        try {
            prep = connection.prepareStatement(DatabaseQuery.insertManifest);
            prep.setString(1,"test");
            prep.setString(2,"test");
            prep.setInt(3,2);
            prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(connection,prep,null);
        }
    }

    @Override
    public void onSelect(DTO dto){
        PreparedStatement prep = null;
        ResultSet resultSet = null;
        Connection connection = getConnection();
        try {
            prep = connection.prepareStatement("");
            prep.setString(1,"test");
            resultSet = prep.executeQuery();
            if(resultSet.next()){

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(connection,prep,resultSet);
        }
    }
}
