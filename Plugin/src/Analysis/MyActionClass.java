package Analysis;

import Analysis.Database.QueryBuilder.QueryBuilder;
import Analysis.Play.TestMainFrame;
import Analysis.RedoUndo.CodeDriver;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;


/**
 * Created by woong on 2015-12-22.
 */
public class MyActionClass extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
//        new PluginTest();
        new TestMainFrame();


//        ProjectAnalysis projectAnalysis = ProjectAnalysis.getInstance(ConstantEtc.INTELLIJ_PATH);
//        projectAnalysis.execute(ConstantEtc.INTELLIJ_PATH+"/Activity", ConstantEtc.JAVA_PATTERN);
//        ProjectAnalysis projectAnalysis = ProjectAnalysis.getInstance(ConstantEtc.PROJECT_XML_PATH);
//        projectAnalysis.executeAll();
//        projectAnalysis.execute(ConstantEtc.PROJECT_XML_PATH + ConstantEtc.PROJECT_JAVA_PATH, ConstantEtc.JAVA_PATTERN);

//        DatabaseManager.getInstance().selectToJava(JavaDAO::selectAll).forEach(java ->{
//            System.out.println("Name : " + java.getName());
//            System.out.println("NextActivity : " + java.getNextActivity());
//            java.getXmls().forEach(xml->{
//                System.out.println();
//            });
//        });
/*
        ArrayList<JavaDTO> javaDTOArray = DatabaseManager.getInstance().selectToJava(JavaDAO::selectAll);
        for(int i=0;i<javaDTOArray.size();i++ ){
             JavaDTO javaDTO=javaDTOArray.get(i);
            javaDTO.getName();//
            for (NextActivityDTO nextActivityDTO : javaDTO.getNextActivitys()) {
                nextActivityDTO.getName();//
            }
            for(int j=0;j<javaDTO.getXmls().size();j++){
                XmlDTO xmlDTO = javaDTO.getXmls().get(j);
                xmlDTO.getName();//xml
            }
        }
*/
//        Messages.showInfoMessage(DatabaseManager.getInstance().selectToJava(JavaDAO::selectAll).size()+"", "Test");


//        new CodeDriver();
//        projectAnalysis.execute(intellijPath, ConstantEtc.XML_PATTERN);

//        ManifestDTO manifestDTO = new ManifestDTO();
//        manifestDTO.setPackageName("storyboard");
//        manifestDTO.setTheme("none");
//        manifestDTO.setActivity(new ActivityDTO(0,1,"test",1,11,10));

//        DatabaseManager databaseManager = DatabaseManager.getInstance();
//        databaseManager.insertToManifest(table->table.insertManifest(manifestDTO));
//        databaseManager.selectToManifest(table->table.selectManifest()).forEach(row ->{System.out.println(row.getPackageName());});
//        databaseManager.useToManifest(table->table.selectActivity()).forEach(manifestItem -> manifestItem.getActivities().forEach(activityItem -> activityItem.getMethodName()));
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

        QueryBuilder.selectAll().from("Java").build();
        QueryBuilder.selectAll().from("Java").where("num=1");
        QueryBuilder.insert().into("Component").columns("name","id").values("test","test").build();


    }
}
