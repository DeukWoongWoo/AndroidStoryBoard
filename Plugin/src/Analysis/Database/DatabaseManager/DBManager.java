package Analysis.Database.DatabaseManager;

import Analysis.Database.DtatTransferObject.DTO;

/**
 * Created by woong on 2016-01-24.
 */
public interface DBManager {
    public void selectAll(int key, String query);
    public void selectPortion(int key, DTO dto);
    public void insert(int key, String query);
}
