package Analysis.RedoUndo.CommandObj;

import Analysis.Constant.SharedPreference;
import Analysis.Database.DatabaseManager.DatabaseManager;
import Analysis.Database.DtatTransferObject.ComponentDTO;
import Analysis.Database.DtatTransferObject.EventDTO;
import Analysis.Database.DtatTransferObject.NextActivityDTO;
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
    private static int num = 1;

    private final int IMPLEMENT = 1;
    private final int LISTENER = 2;
    private final int LOCAL = 3;
    private final int FUNC = 4;

    private String intentName;
    private String clickFuncName;

    private String id;
    private String from;
    private String to;

    private XmlDTO xmlDTO;
    private ComponentDTO componentDTO;
    private EventDTO eventDTO;
    private PsiJavaFile psiJavaFile;

    private boolean isView;
    private boolean isIntent;
    private boolean isFunc;

    private ElementFactory elementFactory = new ElementFactory();

    public ActivityLink(String id, String from, String to) {
        this.id = id;
        this.from = from;
        this.to = to;
        intentName = "intent" + (num++);
        clickFuncName = "On" + id + "Clicked";
    }

    public void create() {
        checkPsiJavaFile();

        PsiClass mainClass = psiJavaFile.getClasses()[0];

        StringBuilder contentStr = new StringBuilder("private void nextActivity(){\n");
        contentStr.append("Intent " + intentName + " = new Intent(this," + to + ".class);\n");
        contentStr.append("startActivity(" + intentName + ");\n");
        contentStr.append("}\n");

        if (eventDTO == null) {
            new WriteCommandAction.Simple(psiJavaFile.getProject(), psiJavaFile.getContainingFile()) {
                @Override
                protected void run() throws Throwable {
                    StringBuilder clickStr = new StringBuilder("public void " + clickFuncName + "(View view){\n");
                    clickStr.append("nextActivity();\n");
                    clickStr.append("}\n");

                    mainClass.add(elementFactory.createPsiMethod(clickStr.toString(), mainClass));
                    mainClass.add(elementFactory.createPsiMethod(contentStr.toString(), mainClass));
                    isFunc = true;

                    if (!checkImport("android.view.View")) {
                        PsiImportStatement psiImportStatement = elementFactory.findPsiImportStatement("android.view", "View");
                        if (psiImportStatement != null) {
                            psiJavaFile.getImportList().add(psiImportStatement);
                            isView = true;
                        } else Messages.showInfoMessage("Cannot find import file...", "File Search");
                    }

                    if (!checkImport("android.content.Intent")) {
                        PsiImportStatement psiImportStatement = elementFactory.findPsiImportStatement("android.content", "Intent");
                        if (psiImportStatement != null) {
                            psiJavaFile.getImportList().add(psiImportStatement);
                            isIntent = true;
                        } else Messages.showInfoMessage("Cannot find import file...", "File Search");
                    }
                }
            }.execute();
        } else {
            int type = eventDTO.getType();
            if (type == IMPLEMENT) {
                for (PsiMethod psiMethod : psiJavaFile.getClasses()[0].getMethods()) {
                    if (psiMethod.getName().equals("onClick")) {
                        new WriteCommandAction.Simple(psiJavaFile.getProject(), psiJavaFile.getContainingFile()) {
                            @Override
                            protected void run() throws Throwable {
                                psiMethod.getBody().add(elementFactory.createPsiStatement("nextActivity();", psiMethod));
                                mainClass.add(elementFactory.createPsiMethod(contentStr.toString(), mainClass));
                            }
                        }.execute();
                    }
                }
            } else if (type == LOCAL) {
                for (PsiMethod psiMethod : psiJavaFile.getClasses()[0].getMethods()) {
                    PsiCodeBlock body = psiMethod.getBody();
                    for (PsiStatement statement : body.getStatements()) {
                        Pattern pattern = Pattern.compile(componentDTO.getName() + ".setOnClickListener");
                        Matcher matcher = pattern.matcher(statement.getText());

                        if (matcher.find()) {
                            PsiElement[] element = ((PsiExpressionStatement) statement).getExpression().getChildren();
                            PsiMethod method = (PsiMethod) element[1].getChildren()[1].getChildren()[3].getChildren()[5];

                            new WriteCommandAction.Simple(method.getProject(), method.getContainingFile()) {
                                @Override
                                protected void run() throws Throwable {
                                    method.getBody().add(elementFactory.createPsiStatement("nextActivity();", method));
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
        checkPsiJavaFile();

        NextActivityDTO nextActivity = DatabaseManager.getInstance().selectToJava(table -> table.selectNextActivity("javaId=" + xmlDTO.getJavaId(), "name='" + to + "'")).get(0).getNextActivity(0);

        findField(nextActivity.getIntentName());

        if(eventDTO.getType() == LOCAL){
            if(nextActivity.getIntentFuncName().equals("onClick")){
                for(PsiStatement statement :getPsiMethod(componentDTO.getMethodName()).getBody().getStatements()){
                    if(findPattern(componentDTO.getName()+".setOnClickListener", statement.getText())){
                        PsiElement[] element = ((PsiExpressionStatement) statement).getExpression().getChildren();
                        PsiMethod method = (PsiMethod) element[1].getChildren()[1].getChildren()[3].getChildren()[5];
                        for(PsiStatement state : method.getBody().getStatements()){
                            if(findPattern(nextActivity.getIntentName(), state.getText())) deleteComponent(state);
                        }
                    }
                }
            }else {
            }
        }else if(eventDTO.getType() == IMPLEMENT){
            for(PsiStatement statement : getPsiMethod("onClick").getBody().getStatements()) {
                if(findPattern("nextActivity", statement.getText())) deleteComponent(statement);
                else if(findPattern(nextActivity.getIntentName(), statement.getText())) deleteComponent(statement);
            }
        }

        PsiMethod intentMethod = getPsiMethod(nextActivity.getIntentFuncName());
        if(intentMethod.getName().equals("nextActivity")) deleteMethod(intentMethod);
        else{
            for(PsiStatement statement : intentMethod.getBody().getStatements())
                if(findPattern(nextActivity.getIntentName(), statement.getText())) deleteComponent(statement);
        }

        syncProject();
    }

    private boolean findPattern(String str, String text){
        Pattern pattern = Pattern.compile(str);
        Matcher matcher = pattern.matcher(text);
        if(matcher.find()) return true;
        return false;
    }

    private PsiMethod getPsiMethod(String methodName) {
        for (PsiMethod psiMethod : psiJavaFile.getClasses()[0].getMethods()) {
            if (psiMethod.getName().equals(methodName)) {
                return psiMethod;
            }
        }
        return null;
    }

    private void checkPsiJavaFile() {
        if (psiJavaFile == null) {
            checkXml();
            checkComponent();
            checkEvent();
            File inFile = new File(DatabaseManager.getInstance().selectToJava(table -> table.selectJava("num=" + xmlDTO.getJavaId())).get(0).getPath());
            psiJavaFile = (PsiJavaFile) PsiManager.getInstance(SharedPreference.PROJECT.get()).findFile(LocalFileSystem.getInstance().findFileByIoFile(inFile));
        }
    }

    private void checkXml() {
        if (xmlDTO == null)
            xmlDTO = DatabaseManager.getInstance().selectToJava(table -> table.selectXml("xmlName='R.layout." + from + "'")).get(0).getXml(0);
    }

    private void checkComponent() {
        if (componentDTO == null)
            componentDTO = DatabaseManager.getInstance().selectToJava(table -> table.selectComponent("xmlId=" + xmlDTO.getNum(), "xmlName='R.id." + id + "'")).get(0).getComponent(0);
    }

    private void checkEvent() {
        if (eventDTO == null)
            eventDTO = DatabaseManager.getInstance().selectToJava(table -> table.selectEvent("componentId=" + componentDTO.getNum())).get(0).getEvent(0);
    }

    private boolean checkImport(String name) {
        for (PsiImportStatement psiImportStatement : psiJavaFile.getImportList().getImportStatements()) {
            if (psiImportStatement.getText().equals("import " + name + ";")) return true;
        }
        return false;
    }

    private void findField(String componentName){
        for(PsiField field : psiJavaFile.getClasses()[0].getAllFields()){
            if(field.getName().equals(componentName)){
                new WriteCommandAction.Simple(psiJavaFile.getProject(), psiJavaFile.getContainingFile()) {
                    @Override
                    protected void run() throws Throwable {
                        field.delete();
                    }
                }.execute();
            }
        }
    }

    private void deleteComponent(final PsiStatement statement) {
        new WriteCommandAction.Simple(psiJavaFile.getProject(), psiJavaFile.getContainingFile()) {
            @Override
            protected void run() throws Throwable {
                statement.delete();
            }
        }.execute();
    }

    private void deleteMethod(final PsiMethod method){
        new WriteCommandAction.Simple(psiJavaFile.getProject(), psiJavaFile.getContainingFile()) {
            @Override
            protected void run() throws Throwable {
                method.delete();
            }
        }.execute();
    }

    private void syncProject() {
        ProjectAnalysis.getInstance(null).executeAll();
    }
}
