package Analysis.RedoUndo.CommandObj;

import Analysis.Constant.ConstantEtc;
import Analysis.Constant.SharedPreference;
import Analysis.Database.DatabaseManager.DatabaseManager;
import Analysis.Database.QueryBuilder.QueryBuilder;
import Analysis.Main.ProjectAnalysis;
import Analysis.RedoUndo.CodeBuilder.CodeBuilder;
import Analysis.RedoUndo.CodeBuilder.Type;
import Analysis.RedoUndo.CommandKey;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.psi.*;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by woong on 2016-02-15.
 */
public class LocalButtonCreate {
    private static int num = 1;

    private final String buttonName = "button";
    private PsiJavaFile psiJavaFile;

    public void create(){
        if(psiJavaFile == null) psiJavaFile = makePsiJavaFile();
        insertPsiElement();
        syncProject();
    }

    private void syncProject() {
        ProjectAnalysis.getInstance(null,null).execute("src/Activity", ConstantEtc.JAVA_PATTERN,true);
    }

    private void insertPsiElement() {
        for(PsiClass p : psiJavaFile.getClasses()){
            for(PsiMethod method : p.getMethods()){
                if(method.getName().equals("onCreate")){
                    new WriteCommandAction.Simple(method.getProject(), method.getContainingFile()) {
                        @Override
                        protected void run() throws Throwable {
                            String makeCode = CodeBuilder.Component(Type.Button).name(buttonName + num++).findViewById(CommandKey.LOCALBUTTON.getId()).build();
                            PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(method.getProject());
                            PsiStatement statement = elementFactory.createStatementFromText(makeCode,method);
                            method.getBody().add(statement);
                        }
                    }.execute();
                }
            }
        }
    }

    private PsiJavaFile makePsiJavaFile() {
        File inFile = new File(DatabaseManager.getInstance().selectToJava(table->table.selectJava()).get(0).getPath());
        return (PsiJavaFile) PsiManager.getInstance(SharedPreference.ACTIONEVENT.getData().getProject()).findFile(LocalFileSystem.getInstance().findFileByIoFile(inFile));
    }

    public void remove(){
//        String selectMethod = DatabaseManager.getInstance().selectToJava(table->table.selectComponent("name='"+buttonName+"'")).get(0).getComponent(0).getMethodName();
        removePsiElement();
        syncProject();
    }

    private void removePsiElement() {
        for(PsiClass p : psiJavaFile.getClasses()){
            for(PsiMethod method : p.getMethods()){
                if(method.getName().equals("onCreate")){
                    for(PsiStatement statement : method.getBody().getStatements()){
                        Pattern pattern = Pattern.compile(buttonName);
                        Matcher matcher = pattern.matcher(statement.getText());

                        if(matcher.find()){
                            new WriteCommandAction.Simple(method.getProject(), method.getContainingFile()) {
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
    }
}
