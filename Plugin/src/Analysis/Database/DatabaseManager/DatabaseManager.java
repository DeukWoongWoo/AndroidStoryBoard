package Analysis.Database.DatabaseManager;

import Analysis.Constant.TableName;
import Analysis.Database.DataAccessObject.Java.JavaDAO;
import Analysis.Database.DataAccessObject.Java.JavaDAOImpl;
import Analysis.Database.DataAccessObject.Manifest.ManifestDAO;
import Analysis.Database.DataAccessObject.Manifest.ManifestDAOImpl;
import Analysis.Database.DtatTransferObject.DTO;

import java.util.function.Consumer;

/**
 * Created by woong on 2016-01-24.
        */
public class DatabaseManager implements DBManager{
    private final ManifestDAO manifestDAO = new ManifestDAOImpl();
    private final JavaDAO javaDAO = new JavaDAOImpl();

    @Override
    public void selectAll(int key, DTO dto) {
        switch (key){
            case TableName.MANIFEST:
                manifestDAO.selectAll(dto.selectPortionQuery());
                break;
            case TableName.JAVA:
                javaDAO.selectAll();
                break;
        }
    }

    @Override
    public void selectPortion(int key, DTO dto) {
        switch (key){
            case TableName.MANIFEST:
                manifestDAO.selectManifest(dto.selectPortionQuery());
                break;
            case TableName.ACTIVITY:
                manifestDAO.selectActivity();
                break;
            case TableName.JAVA:
                javaDAO.selectJava();
                break;
            case TableName.XML:
                javaDAO.selectXml();
                break;
            case TableName.COMPONENT:
                javaDAO.selectComponent();
                break;
            case TableName.EVENT:
                javaDAO.selectEvent();
                break;
        }
    }

    @Override
    public void insert(int key, DTO dto) {
        switch (key){
            case TableName.MANIFEST:
                manifestDAO.insertManifest();
                break;
            case TableName.ACTIVITY:
                manifestDAO.insertActivity();
                break;
            case TableName.JAVA:
                javaDAO.insertJava();
                break;
            case TableName.XML:
                javaDAO.insertXml();
                break;
            case TableName.COMPONENT:
                javaDAO.insertComponent();
                break;
            case TableName.EVENT:
                javaDAO.insertEvent();
                break;
        }
    }

    @Override
    public void selectToManifest(Consumer<ManifestDAO> action) {
        action.accept(manifestDAO);
    }

    @Override
    public void selectToJava(Consumer<JavaDAO> action) {
        action.accept(javaDAO);
    }

}
