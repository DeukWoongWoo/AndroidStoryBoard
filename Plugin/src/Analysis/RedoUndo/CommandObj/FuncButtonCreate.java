package Analysis.RedoUndo.CommandObj;

import Analysis.Constant.SharedPreference;
import Analysis.Database.DatabaseManager.DatabaseManager;
import Analysis.Main.ProjectAnalysis;
import Analysis.RedoUndo.CodeBuilder.CodeBuilder;
import Analysis.RedoUndo.CodeBuilder.Type;
import Analysis.RedoUndo.CommandKey;
import Analysis.RedoUndo.Util.ElementFactory;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.ui.Messages;
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
    private final String packageName = "android.widget";

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
                            ElementFactory elementFactory = new ElementFactory();

                            String makeCode = buttonName + " = " + CodeBuilder.Component(Type.Button).findViewById(CommandKey.LOCALBUTTON.getId()).build();

                            p.add(elementFactory.makePsiField("private " + Type.Button + " " + buttonName + ";",p));

                            method.getBody().add(elementFactory.makePsiStatement(funcName+"();",method));

                            StringBuilder builder = new StringBuilder("private void "+ funcName + "(){\n");
                            builder.append(makeCode + "\n}\n");

                            PsiMethod createMethod = elementFactory.makePsiMethod(builder.toString(), p);
                            p.add(createMethod);

                            JavaCodeStyleManager.getInstance(p.getProject()).shortenClassReferences(createMethod);

                            if (!checkImport(packageName)){
                                PsiImportStatement psiImportStatement = elementFactory.findPsiImportStatement(packageName, Type.Button.name());
                                if(psiImportStatement != null) psiJavaFile.getImportList().add(psiImportStatement);
                                else Messages.showInfoMessage("Cannot find import file...","File Search");
                            }
                        }
                    }.execute();
                }
            }
        }
    }

    private boolean checkImport(String packageName) {
        for(PsiImportStatement psiImportStatement : psiJavaFile.getImportList().getImportStatements()){
            if(psiImportStatement.getText().equals("import "+packageName+"."+ Type.Button.name()+";")) return true;
        }
        return false;
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
