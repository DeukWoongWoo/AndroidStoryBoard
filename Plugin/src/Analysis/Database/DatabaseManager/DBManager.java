package Analysis.Database.DatabaseManager;

import Analysis.Database.DataAccessObject.Java.JavaDAO;
import Analysis.Database.DataAccessObject.Java.JavaDAOImpl;
import Analysis.Database.DataAccessObject.Manifest.ManifestDAO;
import Analysis.Database.DataAccessObject.Manifest.ManifestDAOImpl;
import Analysis.Database.DtatTransferObject.DTO;
import Analysis.Database.DtatTransferObject.JavaDTO;
import Analysis.Database.DtatTransferObject.ManifestDTO;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by woong on 2016-01-24.
 */
public interface DBManager {
    public void onCreateTable();
    public ArrayList<ManifestDTO> selectToManifest(Function<ManifestDAOImpl, ArrayList<ManifestDTO>> function);
    public ArrayList<JavaDTO> selectToJava(Function<JavaDAOImpl, ArrayList<JavaDTO>> function);
    public void insertToManifest(Consumer<ManifestDAOImpl> function);
    public void insertToJava(Consumer<JavaDAOImpl> action);
    public void updateToJava(Consumer<JavaDAOImpl> action);
}
