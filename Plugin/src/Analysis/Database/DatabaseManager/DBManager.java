package Analysis.Database.DatabaseManager;

import Analysis.Database.DataAccessObject.Java.JavaDAO;
import Analysis.Database.DataAccessObject.Manifest.ManifestDAO;
import Analysis.Database.DtatTransferObject.DTO;

import java.util.function.Consumer;

/**
 * Created by woong on 2016-01-24.
 */
public interface DBManager {
    public void selectAll(int key, DTO dto);
    public void selectPortion(int key, DTO dto);
    public void insert(int key, DTO dto);
    public void selectToManifest(Consumer<ManifestDAO> action);
    public void selectToJava(Consumer<JavaDAO> action);
}
