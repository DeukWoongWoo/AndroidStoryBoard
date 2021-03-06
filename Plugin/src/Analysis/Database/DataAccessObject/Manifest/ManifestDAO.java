package Analysis.Database.DataAccessObject.Manifest;

import Analysis.Database.DtatTransferObject.ActivityDTO;
import Analysis.Database.DtatTransferObject.ManifestDTO;

import java.util.ArrayList;

/**
 * Created by woong on 2016-01-24.
 */
public interface ManifestDAO {
    public void createManifest();
    public void createActivity();

    public void insertManifest(ManifestDTO manifestDTO);
    public void insertActivity(ActivityDTO activityDTO);

    public void selectAll();
    public ArrayList<ManifestDTO> selectManifest();
    public ArrayList<ManifestDTO> selectActivity();
//    public void selectActivity();
}
