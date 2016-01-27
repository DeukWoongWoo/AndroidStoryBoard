package Analysis.Database.DatabaseManager;

import Analysis.Constant.TableName;
import Analysis.Database.DataAccessObject.Java.JavaDAO;
import Analysis.Database.DataAccessObject.Java.JavaDAOImpl;
import Analysis.Database.DataAccessObject.Manifest.ManifestDAO;
import Analysis.Database.DataAccessObject.Manifest.ManifestDAOImpl;
import Analysis.Database.DtatTransferObject.DTO;
import Analysis.Database.DtatTransferObject.ManifestDTO;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by woong on 2016-01-24.
        */
public class DatabaseManager implements DBManager{
    private final ManifestDAO manifestDAO = new ManifestDAOImpl();
    private final JavaDAO javaDAO = new JavaDAOImpl();

//    @Override
//    public void useToManifest(Consumer<ManifestDAO> action) {
//        action.accept(manifestDAO);
//    }

    @Override
    public ArrayList<ManifestDTO> useToManifest(Function<ManifestDAO, ArrayList<ManifestDTO>> function) {
        return function.apply(manifestDAO);
    }

//    @Override
//    public ArrayList<ManifestDTO> useToManifest(Function<ManifestDAO, ArrayList<ManifestDTO>> function) {
//        System.out.println("useToManifest");
//        return function.apply(manifestDAO);
//    }

    @Override
    public void useToJava(Consumer<JavaDAO> action) {
        action.accept(javaDAO);
    }

}
