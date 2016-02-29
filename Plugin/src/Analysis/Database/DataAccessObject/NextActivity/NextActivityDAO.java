package Analysis.Database.DataAccessObject.NextActivity;

import Analysis.Database.DtatTransferObject.NextActivityDTO;

/**
 * Created by woong on 2016-02-29.
 */
public interface NextActivityDAO {
    public void create();
    public void insert(NextActivityDTO nextActivityDTO);
    public NextActivityDTO select(String... col);
}
