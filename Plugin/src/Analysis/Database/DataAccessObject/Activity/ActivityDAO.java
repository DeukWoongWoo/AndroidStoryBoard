package Analysis.Database.DataAccessObject.Activity;

import Analysis.Database.DtatTransferObject.ActivityDTO;
import Analysis.Database.DtatTransferObject.ManifestDTO;

/**
 * Created by woong on 2016-01-24.
 */
public interface ActivityDAO {
    public void create();
    public void insert(ActivityDTO activityDTO);
    public ManifestDTO select();
//    public void select();
}
