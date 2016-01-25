package Analysis.Database.DatabaseManager;

import Analysis.Database.DataAccessObject.Java.JavaDAO;
import Analysis.Database.DataAccessObject.Java.JavaDAOImpl;
import Analysis.Database.DataAccessObject.Manifest.ManifestDAO;
import Analysis.Database.DataAccessObject.Manifest.ManifestDAOImpl;
import Analysis.Database.QueryBuilder.QueryBuilder;

/**
 * Created by woong on 2016-01-24.
        */
public class DatabaseManager implements DBManager{
    private final ManifestDAO manifestDAO = new ManifestDAOImpl();
    private final JavaDAO javaDAO = new JavaDAOImpl();

    @Override
    public void select(int key) {
        String query = QueryBuilder.selectAll().from("Manifest").build();
        System.out.println("DatabaseManager Query : " + query);
        query = QueryBuilder.selectAll().from("Manifest").where("id = 1").build();
        System.out.println("DatabaseManager Query : " + query);
    }

    @Override
    public void insert() {
    }
}
