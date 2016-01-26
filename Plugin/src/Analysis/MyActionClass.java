package Analysis;

import Analysis.Constant.ConstantEtc;
import Analysis.Constant.TableName;
import Analysis.Database.DataAccessObject.Manifest.ManifestDAO;
import Analysis.Database.DataAccessObject.Manifest.ManifestDAOImpl;
import Analysis.Database.DatabaseManager.DatabaseManager;
import Analysis.Database.DtatTransferObject.ManifestDTO;
import Analysis.Main.ProjectAnalysis;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;


/**
 * Created by woong on 2015-12-22.
 */
public class MyActionClass extends AnAction {
    private String intellijPath = "/src";
    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        ProjectAnalysis projectAnalysis = ProjectAnalysis.getInstance(e, intellijPath);
//        projectAnalysis.execute(intellijPath+"/Activity", ConstantEtc.JAVA_PATTERN);
        projectAnalysis.execute(intellijPath, ConstantEtc.XML_PATTERN);

        ManifestDTO manifestDTO = new ManifestDTO();
//        manifestDTO.setPackageName("storyboard");
//        manifestDTO.setTheme("none");
        ManifestDAO testDao = new ManifestDAOImpl();

        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.selectPortion(TableName.MANIFEST, manifestDTO);

        databaseManager.selectToManifest(table->table.selectManifest(manifestDTO.selectPortionQuery()));

        databaseManager.selectToManifest(table->table.selectManifest());

        databaseManager.selectToJava(table->table.selectJava());

//        DatabaseManager daoManager = DatabaseManager.getInstance();
//        daoManager.useDAO(TableName.MANIFEST);
//        daoManager.insert(new DTO(new ManifestDTO(1,"test","test")));

//        ProjectAnalysis projectAnalysis1 = ProjectAnalysis.getInstance(null,null);
//        projectAnalysis.execute(ConstantEtc.PROJECT_JAVA_PATH, ConstantEtc.JAVA_PATTERN);

//        PluginTest test = new PluginTest(e);
    }

}
