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
    private final String fieldName = "userLiporterActivity";

    private String xml;

    private PsiClass mainClass;

    private PsiMethod resumeMethod;
    private PsiMethod pauseMethod;

    private boolean isPauseMethod;
    private boolean isResumeMethod;

    private ElementFactory elementFactory = new ElementFactory();

    public ActivityLibCreate(String xml){
        this.xml = xml;
    }

    public void create(){
        XmlDTO xmlDTO = DatabaseManager.getInstance().selectToJava(table -> table.selectXml("xmlName='R.layout." + xml + "'")).get(0).getXml(0);
        File file = new File(DatabaseManager.getInstance().selectToJava(table -> table.selectJava("num=" + xmlDTO.getJavaId())).get(0).getPath());
        PsiJavaFile psiJavaFile = (PsiJavaFile) PsiManager.getInstance(SharedPreference.PROJECT.get()).findFile(LocalFileSystem.getInstance().findFileByIoFile(file));

        mainClass = psiJavaFile.getClasses()[0];

        new WriteCommandAction.Simple(mainClass.getProject(), mainClass.getContainingFile()) {
            @Override
            protected void run() throws Throwable {
                mainClass.add(elementFactory.createPsiField("private UserLiporter "+ fieldName +" = new CatchActivity();",mainClass));
            }
        }.execute();

        for(PsiMethod psiMethod : mainClass.getMethods()){
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

                    resumeMethod = elementFactory.createPsiMethod(builder.toString(),mainClass);

                    mainClass.add(resumeMethod);

                    isResumeMethod = true;
                }

                if(pauseMethod != null){
                    pauseMethod.getBody().add(elementFactory.createPsiStatement("userLiporterActivity.get(null);",mainClass));
                }else{
                    StringBuilder builder = new StringBuilder("@Override\n");
                    builder.append("protected void onPause() {\n");
                    builder.append("super.onPause();\n");
                    builder.append("userLiporterActivity.get(null);\n");
                    builder.append("}\n");

                    pauseMethod = elementFactory.createPsiMethod(builder.toString(),mainClass);

                    mainClass.add(pauseMethod);

                    isPauseMethod = true;
                }
            }
        }.execute();

        syncProject();
    }

    public void remove(){
        for(PsiField field : mainClass.getAllFields()){
            if(field.getName().equals(fieldName)){
                new WriteCommandAction.Simple(mainClass.getProject(), mainClass.getContainingFile()) {
                    @Override
                    protected void run() throws Throwable {
                        field.delete();
                    }
                }.execute();
            }
        }

        for(PsiMethod psiMethod : mainClass.getMethods()) {
            if(psiMethod.getName().equals(pauseMethod.getName())) {
                if (isPauseMethod) {
                    new WriteCommandAction.Simple(mainClass.getProject(), mainClass.getContainingFile()) {
                        @Override
                        protected void run() throws Throwable {
                            psiMethod.delete();
                        }
                    }.execute();
                } else {
                    for (PsiStatement statement : psiMethod.getBody().getStatements()) {
                        if(statement.getText().equals(fieldName+".get(null);")){
                            new WriteCommandAction.Simple(mainClass.getProject(), mainClass.getContainingFile()) {
                                @Override
                                protected void run() throws Throwable {
                                    statement.delete();
                                }
                            }.execute();
                        }
                    }
                }
            }else if(psiMethod.getName().equals(resumeMethod.getName())){
                if (isResumeMethod) {
                    new WriteCommandAction.Simple(mainClass.getProject(), mainClass.getContainingFile()) {
                        @Override
                        protected void run() throws Throwable {
                            psiMethod.delete();
                        }
                    }.execute();
                } else {
                    for (PsiStatement statement : psiMethod.getBody().getStatements()) {
                        if(statement.getText().equals(fieldName+".set(this);")){
                            new WriteCommandAction.Simple(mainClass.getProject(), mainClass.getContainingFile()) {
                                @Override
                                protected void run() throws Throwable {
                                    statement.delete();
                                }
                            }.execute();
                        }
                    }
                }
            }
        }
        syncProject();
    }

    private void syncProject() {
        ProjectAnalysis.getInstance(null).executeAll();
    }
}
