package Analysis.Database.DatabaseManager;

import Analysis.Constant.TableName;
import Analysis.Database.DataAccessObject.Java.JavaDAO;
import Analysis.Database.DataAccessObject.Java.JavaDAOImpl;
import Analysis.Database.DataAccessObject.Manifest.ManifestDAO;
import Analysis.Database.DataAccessObject.Manifest.ManifestDAOImpl;
import Analysis.Database.DtatTransferObject.DTO;
import Analysis.Database.DtatTransferObject.JavaDTO;
import Analysis.Database.DtatTransferObject.ManifestDTO;
import com.intellij.openapi.actionSystem.AnActionEvent;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by woong on 2016-01-24.
        */
public class DatabaseManager implements DBManager{
    private final ManifestDAOImpl manifestDAO = new ManifestDAOImpl();
    private final JavaDAOImpl javaDAO = new JavaDAOImpl();

    public volatile static DatabaseManager instance = null;

    public static DatabaseManager getInstance(){
        if(instance == null) {
            synchronized (DatabaseManager.class) {
                if (instance == null) instance = new DatabaseManager();
            }
        }
        return instance;
    }

    @Override
    public void onCreateTable() {
        System.out.println("onCreateTable...");
        manifestDAO.createManifest();
        manifestDAO.createActivity();
        javaDAO.createJava();
        javaDAO.createNextActivity();
        javaDAO.createXml();
        javaDAO.createComponent();
        javaDAO.createEvent();
    }

    @Override
    public ArrayList<ManifestDTO> selectToManifest(Function<ManifestDAOImpl, ArrayList<ManifestDTO>> function) {
        return function.apply(manifestDAO);
    }

    @Override
    public ArrayList<JavaDTO> selectToJava(Function<JavaDAOImpl, ArrayList<JavaDTO>> function) {
        return function.apply(javaDAO);
    }

    @Override
    public void insertToManifest(Consumer<ManifestDAOImpl> action) {
        action.accept(manifestDAO);
    }

    @Override
    public void insertToJava(Consumer<JavaDAOImpl> action) {
        action.accept(javaDAO);
    }

    @Override
    public void updateToJava(Consumer<JavaDAOImpl> action) {
        action.accept(javaDAO);
    }

}
