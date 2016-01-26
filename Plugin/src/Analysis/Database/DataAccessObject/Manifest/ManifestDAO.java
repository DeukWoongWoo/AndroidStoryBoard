package Analysis.Database.DataAccessObject.Manifest;

/**
 * Created by woong on 2016-01-24.
 */
public interface ManifestDAO {
    public int insertManifest();
    public void insertActivity();

    public void selectAll(String query);
    public void selectManifest(String query);
    public void selectManifest();
    public void selectActivity();
}
