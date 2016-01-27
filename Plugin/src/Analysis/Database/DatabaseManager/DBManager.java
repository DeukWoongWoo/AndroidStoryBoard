package Analysis.Database.DatabaseManager;

import Analysis.Database.DataAccessObject.Java.JavaDAO;
import Analysis.Database.DataAccessObject.Manifest.ManifestDAO;
import Analysis.Database.DtatTransferObject.DTO;
import Analysis.Database.DtatTransferObject.ManifestDTO;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by woong on 2016-01-24.
 */
public interface DBManager {
    public ArrayList<ManifestDTO> selectToManifest(Function<ManifestDAO, ArrayList<ManifestDTO>> function);
    public void insertToManifest(Consumer<ManifestDAO> function);
    public void useToJava(Consumer<JavaDAO> action);
}
