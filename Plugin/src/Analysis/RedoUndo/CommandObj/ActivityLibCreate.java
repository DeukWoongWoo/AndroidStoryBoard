package Analysis.RedoUndo.CommandObj;

import Analysis.Constant.SharedPreference;
import Analysis.Database.DatabaseManager.DatabaseManager;
import Analysis.Database.DtatTransferObject.ComponentDTO;
import Analysis.Database.DtatTransferObject.EventDTO;
import Analysis.Database.DtatTransferObject.XmlDTO;
import Analysis.Main.ProjectAnalysis;
import Analysis.RedoUndo.Util.ElementFactory;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.psi.*;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by woong on 2016-02-23.
 */
public class ActivityLibCreate {
    private String xml;

    private PsiMethod resumeMethod;
    private PsiMethod pauseMethod;

    public ActivityLibCreate(String xml){
        this.xml = xml;
    }

    public void create(){
        XmlDTO xmlDTO = DatabaseManager.getInstance().selectToJava(table -> table.selectXml("xmlName='R.layout." + xml + "'")).get(0).getXml(0);
        File file = new File(DatabaseManager.getInstance().selectToJava(table -> table.selectJava("num=" + xmlDTO.getJavaId())).get(0).getPath());
        PsiJavaFile psiJavaFile = (PsiJavaFile) PsiManager.getInstance(SharedPreference.ACTIONEVENT.getData().getProject()).findFile(LocalFileSystem.getInstance().findFileByIoFile(file));

        PsiClass mainClass = psiJavaFile.getClasses()[0];

        ElementFactory elementFactory = new ElementFactory();

        new WriteCommandAction.Simple(mainClass.getProject(), mainClass.getContainingFile()) {
            @Override
            protected void run() throws Throwable {
                mainClass.add(elementFactory.createPsiField("private UserLiporter userLiporterActivity = new CatchActivity();",mainClass));
            }
        }.execute();

        for(PsiMethod psiMethod : psiJavaFile.getClasses()[0].getMethods()){
            if(psiMethod.getName().equals("onResume")) resumeMethod = psiMethod;
            else if(psiMethod.getName().equals("onPause")) pauseMethod = psiMethod;
        }

        new WriteCommandAction.Simple(mainClass.getProject(), mainClass.getContainingFile()) {
            @Override
            protected void run() throws Throwable {
                if(resumeMethod != null){
                    resumeMethod.getBody().add(elementFactory.createPsiStatement("userLiporterActivity.set(this);",mainClass));
                }else{
                    StringBuilder builder = new StringBuilder("@Override\n");
                    builder.append("protected void onResume() {\n");
                    builder.append("super.onResume();\n");
                    builder.append("userLiporterActivity.set(this);\n");
                    builder.append("}\n");

                    mainClass.add(elementFactory.createPsiMethod(builder.toString(),mainClass));
                }

                if(pauseMethod != null){
                    pauseMethod.getBody().add(elementFactory.createPsiStatement("userLiporterActivity.get(null);",mainClass));
                }else{
                    StringBuilder builder = new StringBuilder("@Override\n");
                    builder.append("protected void onPause() {\n");
                    builder.append("super.onPause();\n");
                    builder.append("userLiporterActivity.get(null);\n");
                    builder.append("}\n");

                    mainClass.add(elementFactory.createPsiMethod(builder.toString(),mainClass));
                }
            }
        }.execute();

        syncProject();
    }

    public void remove(){

    }

    private void syncProject() {
        ProjectAnalysis.getInstance(null, null).executeAll();
    }
}
