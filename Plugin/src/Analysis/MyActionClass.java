package Analysis;

import Analysis.Database.DatabaseManager.DatabaseManager;
import Analysis.Database.DtatTransferObject.ManifestDTO;
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
//        ProjectAnalysis projectAnalysis = ProjectAnalysis.getInstance(e, intellijPath);
//        projectAnalysis.execute(intellijPath+"/Activity", Constant.JAVA_PATTERN);

        ManifestDTO manifestDTO = new ManifestDTO();
//        manifestDTO.setPackageName("storyboard");
//        manifestDTO.setTheme("none");

        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.selectPortion(Constant.MANIFEST, manifestDTO);

//        DatabaseManager daoManager = DatabaseManager.getInstance();
//        daoManager.useDAO(Constant.MANIFEST);
//        daoManager.insert(new DTO(new ManifestDTO(1,"test","test")));

//        ProjectAnalysis projectAnalysis1 = ProjectAnalysis.getInstance(null,null);
//        projectAnalysis.execute(Constant.PROJECT_JAVA_PATH, Constant.JAVA_PATTERN);

//        PluginTest test = new PluginTest(e);
    }

}
