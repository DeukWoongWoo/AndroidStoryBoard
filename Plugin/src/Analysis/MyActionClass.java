package Analysis;

import Analysis.Database.*;
import Analysis.Database.DtatTransferObject.DTO;
import Analysis.Database.DtatTransferObject.JavaDTO;
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
        ProjectAnalysis projectAnalysis = ProjectAnalysis.getInstance(e, intellijPath);
//        projectAnalysis.execute(intellijPath, Constant.XML_PATTERN);

//        ActivityDAO storyBoardDAO = new ActivityDAO();
//        ManifestDAO manifestDAO = new ManifestDAO();
//
//        storyBoardDAO.onInsert("test");
//        manifestDAO.onInsert("test");

//        DAOManager daoManager = DAOManager.getInstance();
//        daoManager.useDAO(Constant.MANIFEST);
//        daoManager.insert(new DTO(new ManifestDTO(1,"test","test")));

//        ProjectAnalysis projectAnalysis1 = ProjectAnalysis.getInstance(null,null);
//        projectAnalysis.execute(Constant.PROJECT_JAVA_PATH, Constant.JAVA_PATTERN);

//        PluginTest test = new PluginTest(e);
    }

}
