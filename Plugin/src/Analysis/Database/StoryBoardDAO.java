package Analysis.Database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by woong on 2016-01-19.
 */
public class StoryBoardDAO extends SQLiteOpenHelper {

    public StoryBoardDAO(){
        super();
    }

    @Override
    public void onInsert(String query) {
        PreparedStatement prep = null;
        Connection connection = getConnection();
        try {
            prep = connection.prepareStatement(query);
            prep.setString(1,"test");
            prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(connection,prep,null);
        }
    }

    @Override
    public void onUpdate(String query) {
        PreparedStatement prep = null;
        Connection connection = getConnection();
        try {
            prep = connection.prepareStatement("INSERT INTO Manifest(package, theme, activity) VALUES(?, ? ,?)");
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
    public void onSelect(String query){
        PreparedStatement prep = null;
        ResultSet resultSet = null;
        Connection connection = getConnection();
        try {
            prep = connection.prepareStatement(query);
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
