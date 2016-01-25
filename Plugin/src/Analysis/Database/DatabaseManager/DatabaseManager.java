package Analysis.Database.DatabaseManager;

import Analysis.Constant;
import Analysis.Database.DataAccessObject.Java.JavaDAO;
import Analysis.Database.DataAccessObject.Java.JavaDAOImpl;
import Analysis.Database.DataAccessObject.Manifest.ManifestDAO;
import Analysis.Database.DataAccessObject.Manifest.ManifestDAOImpl;
import Analysis.Database.DtatTransferObject.DTO;
import Analysis.Database.QueryBuilder.QueryBuilder;

/**
 * Created by woong on 2016-01-24.
        */
public class DatabaseManager implements DBManager{
    private final ManifestDAO manifestDAO = new ManifestDAOImpl();
    private final JavaDAO javaDAO = new JavaDAOImpl();

    @Override
    public void selectAll(int key, String query) {
        System.out.println("Select All : " + query);
        switch (key){
            case Constant.MANIFEST:
                manifestDAO.selectAll(query);
                break;
            case Constant.JAVA:
                javaDAO.selectAll();
                break;
        }
    }

    @Override
    public void selectPortion(int key, DTO dto) {
//        String query = QueryBuilder.selectAll().from("Manifest").build();
//        System.out.println("DatabaseManager Query : " + query);
//        query = QueryBuilder.selectAll().from("Manifest").where("id = 1").build();
//        System.out.println("DatabaseManager Query : " + query);
        switch (key){
            case Constant.MANIFEST:
                manifestDAO.selectManifest(dto.selectPortionQuery());
                break;
            case Constant.ACTIVITY:
                manifestDAO.selectActivity();
                break;
            case Constant.JAVA:
                javaDAO.selectJava();
                break;
            case Constant.XML:
                javaDAO.selectXml();
                break;
            case Constant.COMPONENT:
                javaDAO.selectComponent();
                break;
            case Constant.EVENT:
                javaDAO.selectEvent();
                break;
        }
    }

    @Override
    public void insert(int key, String query) {
        switch (key){
            case Constant.MANIFEST:
                manifestDAO.insertManifest();
                break;
            case Constant.ACTIVITY:
                manifestDAO.insertActivity();
                break;
            case Constant.JAVA:
                javaDAO.insertJava();
                break;
            case Constant.XML:
                javaDAO.insertXml();
                break;
            case Constant.COMPONENT:
                javaDAO.insertComponent();
                break;
            case Constant.EVENT:
                javaDAO.insertEvent();
                break;
        }
    }
}
