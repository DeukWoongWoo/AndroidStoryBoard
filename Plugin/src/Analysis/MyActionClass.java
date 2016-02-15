package Analysis;

import Analysis.Constant.ConstantEtc;
import Analysis.Database.DataAccessObject.Manifest.ManifestDAO;
import Analysis.Database.DatabaseManager.DatabaseManager;
import Analysis.Database.DtatTransferObject.ActivityDTO;
import Analysis.Database.DtatTransferObject.ManifestDTO;
import Analysis.Main.ProjectAnalysis;
import Analysis.RedoUndo.CodeDriver;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import java.util.ArrayList;


/**
 * Created by woong on 2015-12-22.
 */
public class MyActionClass extends AnAction {
    private String intellijPath = "/src";
    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
//        ProjectAnalysis projectAnalysis = ProjectAnalysis.getInstance(e, intellijPath);
//        projectAnalysis.execute(intellijPath+"/Activity", ConstantEtc.JAVA_PATTERN);
//        projectAnalysis.execute(intellijPath, ConstantEtc.XML_PATTERN);

//        ManifestDTO manifestDTO = new ManifestDTO();
//        manifestDTO.setPackageName("storyboard");
//        manifestDTO.setTheme("none");
//        manifestDTO.setActivity(new ActivityDTO(0,1,"test",1,11,10));

//        DatabaseManager databaseManager = DatabaseManager.getInstance();
//        databaseManager.insertToManifest(table->table.insertManifest(manifestDTO));
//        databaseManager.selectToManifest(table->table.selectManifest()).forEach(row ->{System.out.println(row.getPackageName());});
//        databaseManager.useToManifest(table->table.selectActivity()).forEach(manifestItem -> manifestItem.getActivities().forEach(activityItem -> activityItem.getName()));
//        databaseManager.useToJava(table->table.selectJava());
//        ArrayList<ManifestDTO> dto = databaseManager.useToManifest(ManifestDAO::selectManifest);
//        dto.forEach(row->{
//            System.out.println(row.getPackageName());
//        });
//        ArrayList arrayList = databaseManager.useToManifest(ManifestDAO::selectManifest);
//        ManifestDTO test = (ManifestDTO) arrayList.get(0);
//        System.out.println(test.getPackageName());

//        databaseManager.useToManifest(ManifestDAO::selectManifest).forEach(row ->{System.out.println(row.getPackageName());});
//        databaseManager.useToManifest(ManifestDAO::selectActivity).forEach(row -> System.out.println("Activity Select !!!"));
//        databaseManager.useToManifest();

//        DatabaseManager daoManager = DatabaseManager.getInstance();
//        daoManager.useDAO(TableName.MANIFEST);
//        daoManager.insert(new DTO(new ManifestDTO(1,"test","test")));

//        ProjectAnalysis projectAnalysis1 = ProjectAnalysis.getInstance(null,null);
//        projectAnalysis.execute(ConstantEtc.PROJECT_JAVA_PATH, ConstantEtc.JAVA_PATTERN);

//        PluginTest test = new PluginTest(e);

        new CodeDriver();
    }

}
