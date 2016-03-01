package Analysis.RedoUndo.CommandObj;

import Analysis.Constant.SharedPreference;
import Analysis.Database.DatabaseManager.DatabaseManager;
import Analysis.Database.DtatTransferObject.ComponentDTO;
import Analysis.Database.DtatTransferObject.EventDTO;
import Analysis.Database.DtatTransferObject.XmlDTO;
import Analysis.Main.ProjectAnalysis;
import Analysis.RedoUndo.Util.ElementFactory;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.psi.*;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by woong on 2016-02-22.
 */
public class ActivityLink {
    private final int IMPLEMENT = 1;
    private final int LISTENER = 2;
    private final int LOCAL = 3;
    private final int FUNC = 4;

    private String id;
    private String from;
    private String to;

    public ActivityLink(String id, String from, String to) {
        this.id = id;
        this.from = from;
        this.to = to;
    }

    public void create() {
        XmlDTO xmlDTO = DatabaseManager.getInstance().selectToJava(table -> table.selectXml("xmlName='R.layout." + from + "'")).get(0).getXml(0);
        int xmlId = xmlDTO.getNum();
        ComponentDTO component = DatabaseManager.getInstance().selectToJava(table -> table.selectComponent("xmlId=" + xmlId, "xmlName='R.id." + id + "'")).get(0).getComponent(0);
        int componentId = component.getNum();
        EventDTO eventDTO = DatabaseManager.getInstance().selectToJava(table -> table.selectEvent("componentId=" + componentId)).get(0).getEvent(0);
        File file = new File(DatabaseManager.getInstance().selectToJava(table -> table.selectJava("num=" + xmlDTO.getJavaId())).get(0).getPath());
        PsiJavaFile psiJavaFile = (PsiJavaFile) PsiManager.getInstance(SharedPreference.PROJECT.get()).findFile(LocalFileSystem.getInstance().findFileByIoFile(file));

        PsiClass mainClass = psiJavaFile.getClasses()[0];

        StringBuilder contentStr = new StringBuilder("private void nextActivity(){\n");
        contentStr.append("Intent intent = new Intent(this," + to + ".class);\n");
        contentStr.append("startActivity(intent);\n");
        contentStr.append("}\n");

        ElementFactory elementFactory = new ElementFactory();

        if (eventDTO == null) {
            new WriteCommandAction.Simple(psiJavaFile.getProject(), psiJavaFile.getContainingFile()) {
                @Override
                protected void run() throws Throwable {
                    StringBuilder clickStr = new StringBuilder("public void On" + id + "Clicked(View view){\n");
                    clickStr.append("nextActivity();\n");
                    clickStr.append("}\n");

                    mainClass.add(elementFactory.createPsiMethod(clickStr.toString(), mainClass));
                    mainClass.add(elementFactory.createPsiMethod(contentStr.toString(), mainClass));

                    if (!checkImport(psiJavaFile)) {
                        PsiImportStatement psiImportStatement = elementFactory.findPsiImportStatement("android.view", "View");
                        if (psiImportStatement != null) psiJavaFile.getImportList().add(psiImportStatement);
                        else Messages.showInfoMessage("Cannot find import file...", "File Search");
                    }
                }
            }.execute();
        } else {
            int type = eventDTO.getType();
            if (type == IMPLEMENT) {
                for(PsiMethod psiMethod : psiJavaFile.getClasses()[0].getMethods()){
                    if(psiMethod.getName().equals("onClick")){
                        new WriteCommandAction.Simple(psiJavaFile.getProject(),psiJavaFile.getContainingFile()){
                            @Override
                            protected void run() throws Throwable {
                                psiMethod.getBody().add(elementFactory.createPsiStatement("nextActivity();",psiMethod));
                                mainClass.add(elementFactory.createPsiMethod(contentStr.toString(), mainClass));
                            }
                        }.execute();
                    }
                }
            }else if(type == LOCAL){
                for(PsiMethod psiMethod : psiJavaFile.getClasses()[0].getMethods()) {
                    PsiCodeBlock body = psiMethod.getBody();
                    for(PsiStatement statement : body.getStatements()) {
                        Pattern pattern = Pattern.compile(component.getName()+".setOnClickListener");
                        Matcher matcher = pattern.matcher(statement.getText());

                        if(matcher.find()){
                            PsiElement[] element = ((PsiExpressionStatement) statement).getExpression().getChildren();
                            PsiMethod method = (PsiMethod) element[1].getChildren()[1].getChildren()[3].getChildren()[5];

                            new WriteCommandAction.Simple(method.getProject(), method.getContainingFile()) {
                                @Override
                                protected void run() throws Throwable {
                                    method.getBody().add(elementFactory.createPsiStatement("nextActivity();",method));
                                    mainClass.add(elementFactory.createPsiMethod(contentStr.toString(), mainClass));
                                }
                            }.execute();
                        }
                    }
                }
            }
        }

        syncProject();
    }

    public void remove() {

    }

    private boolean checkImport(PsiJavaFile psiJavaFile) {
        for (PsiImportStatement psiImportStatement : psiJavaFile.getImportList().getImportStatements()) {
            if (psiImportStatement.getText().equals("import android.view.View;")) return true;
        }
        return false;
    }

    private void syncProject() {
        ProjectAnalysis.getInstance(null).executeAll();
    }
}
