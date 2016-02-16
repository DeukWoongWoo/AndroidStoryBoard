package Analysis.Database.DataAccessObject.Component;

import Analysis.Database.DtatTransferObject.ComponentDTO;

/**
 * Created by Windows on 2016-02-01.
 */
public interface ComponentDAO {
    public void create();
    public void insert(ComponentDTO componentDTO);
    public int select(String col);
}
