package Analysis.RedoUndo.CommandObj;

import Analysis.Constant.SharedPreference;
import Analysis.Database.DatabaseManager.DatabaseManager;
import Analysis.Main.ProjectAnalysis;
import Analysis.RedoUndo.CodeBuilder.CodeBuilder;
import Analysis.RedoUndo.CodeBuilder.Type;
import Analysis.RedoUndo.CommandKey;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by woong on 2016-02-15.
 */
public class FuncButtonCreate {
    private static int num = 1;

    private final String funcName = "initFind";
    private final String buttonName = "button" + (num++);
    private PsiJavaFile psiJavaFile;

    public void create(){
        if(psiJavaFile == null) psiJavaFile = makePsiJavaFile();
        insertPsiElement();
        syncProject();
    }

    private void syncProject() {
        ProjectAnalysis.getInstance(null,null).executeAll();
    }

    private void insertPsiElement() {
        for(PsiClass p : psiJavaFile.getClasses()){
            for(PsiMethod method : p.getMethods()){
                if(method.getName().equals("onCreate")){
                    new WriteCommandAction.Simple(method.getProject(), method.getContainingFile()) {
                        @Override
                        protected void run() throws Throwable {
                            String makeCode = buttonName + " = " + CodeBuilder.Component(Type.Button).findViewById(CommandKey.LOCALBUTTON.getId()).build();

                            PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(method.getProject());

                            PsiField field = elementFactory.createFieldFromText("private " + Type.Button + " " + buttonName + ";",p);
                            p.add(field);

                            PsiStatement statement = elementFactory.createStatementFromText(funcName+"();",method);
                            method.getBody().add(statement);

                            StringBuilder builder = new StringBuilder("private void "+ funcName + "(){\n");
                            builder.append(makeCode + "\n}\n");
                            PsiMethod createMethod = elementFactory.createMethodFromText(builder.toString(), p);
                            p.add(createMethod);
                            JavaCodeStyleManager.getInstance(p.getProject()).shortenClassReferences(createMethod);
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
        removePsiElement();
        syncProject();
    }

    private void removePsiElement() {
        // TODO: 2016-02-18 구현하기
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
