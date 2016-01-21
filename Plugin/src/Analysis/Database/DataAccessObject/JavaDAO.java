package Analysis.Database.DataAccessObject;

import Analysis.Database.DtatTransferObject.DTO;
import Analysis.Database.DtatTransferObject.JavaDTO;
import Analysis.Database.SQLiteOpenHelper;

/**
 * Created by woong on 2016-01-21.
 */
public class JavaDAO extends SQLiteOpenHelper implements DAO {
    @Override
    public void onInsert(DTO dto) {
        JavaDTO t = (JavaDTO) dto.getDTO();
    }

    @Override
    public void onUpdate(DTO dto) {

    }

    @Override
    public void onSelect(DTO dto) {

    }
}
