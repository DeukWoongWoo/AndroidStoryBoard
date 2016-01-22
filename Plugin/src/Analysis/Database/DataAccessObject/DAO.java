package Analysis.Database.DataAccessObject;

import Analysis.Database.DtatTransferObject.DTO;

/**
 * Created by woong on 2016-01-21.
 */
public interface DAO {
    public void onInsert(DTO dto);

    public void onUpdate(DTO dto);

    public void onSelect(DTO dto);
}
