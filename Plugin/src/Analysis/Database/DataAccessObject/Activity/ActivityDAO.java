package Analysis.Database.DataAccessObject.Activity;

import Analysis.Database.DtatTransferObject.ManifestDTO;

/**
 * Created by woong on 2016-01-24.
 */
public interface ActivityDAO {
    public void create();
    public void insert();
    public ManifestDTO select();
//    public void select();
}
