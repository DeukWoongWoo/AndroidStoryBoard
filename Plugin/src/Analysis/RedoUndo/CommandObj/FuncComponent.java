package Analysis.RedoUndo.CommandObj;

import Analysis.Constant.SharedPreference;
import Analysis.Database.DatabaseManager.DatabaseManager;
import Analysis.Database.DtatTransferObject.XmlDTO;
import Analysis.Main.ProjectAnalysis;
import Analysis.RedoUndo.CodeBuilder.CodeBuilder;
import Analysis.RedoUndo.CodeBuilder.Type;
import Analysis.RedoUndo.Util.ElementFactory;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.psi.*;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by woong on 2016-02-25.
 */
public class FuncComponent {
    private static int num = 1;

    private final String packageName = "android.widget";
    private String componentName;

    private String id;
    private String xml;
    private Type type;

    private boolean isImport;
    private boolean isMethod;
    private boolean isFunc;

    private XmlDTO xmlDTO;

    private PsiJavaFile psiJavaFile;

    private ElementFactory elementFactory = new ElementFactory();

    public FuncComponent(String id, String xml, Type type) {
        this.id = id;
        this.xml = xml;
        this.type = type;
        componentName = type.getValue() + (num++);
    }

    public void create() {
        checkPsiJavaFile();
        insertCode();
        syncProject();
    }

    public void remove() {
        checkPsiJavaFile();
        deleteCode();
        syncProject();
    }

    private void insertCode() {
        insertField();
        insertComponent();
        insertImport();
    }

    private void insertField(){
        new WriteCommandAction.Simple(psiJavaFile.getProject(), psiJavaFile.getContainingFile()) {
            @Override
            protected void run() throws Throwable {
                psiJavaFile.getClasses()[0].add(elementFactory.createPsiField("private " + type.name() + " " + componentName + ";",psiJavaFile));
            }
        }.execute();
    }

    private void insertComponent() {
        PsiMethod createMethod = getPsiMethod("onCreate");
        if(createMethod != null){
            for(PsiStatement statement : createMethod.getBody().getStatements()) {
                if(statement.getText().equals("initFind();")) isFunc = true;
            }
            if(!isFunc) addPsiStatement(createMethod, "initFind();");
        }

        insertIntoFind();
    }

    private void insertIntoFind() {
        PsiMethod findMethod = getPsiMethod("initFind");
        String makeCode = componentName + " = " + CodeBuilder.Component(type).findViewById("R.id." + id).build();
        if (findMethod != null) {
            addPsiStatement(findMethod, makeCode);
        }else{
            new WriteCommandAction.Simple(psiJavaFile.getProject(), psiJavaFile.getContainingFile()) {
                @Override
                protected void run() throws Throwable {
                    StringBuilder builder = new StringBuilder("private void initFind(){\n");
                    builder.append(makeCode + "\n}\n");
                    psiJavaFile.getClasses()[0].add(elementFactory.createPsiMethod(builder.toString(),psiJavaFile));
                    isMethod = true;
                }
            }.execute();
        }
    }

    private void insertImport() {
        if (!checkImport(packageName)) {
            PsiImportStatement psiImportStatement = elementFactory.findPsiImportStatement(packageName, type.name());
            if (psiImportStatement != null) {
                addPsiImport(psiImportStatement);
                isImport = true;
            } else Messages.showInfoMessage("Cannot find import file...", "File Search");
        }
    }

    private void deleteCode() {
        checkXml();
        String componentName = DatabaseManager.getInstance().selectToJava(table -> table.selectComponent("xmlId=" + xmlDTO.getNum(), "xmlName='R.id." + id + "'")).get(0).getComponent(0).getName();
        findField(componentName);
        findStatement();
        findFunction(componentName);
        if(isImport){
            for (PsiImportStatement psiImportStatement : psiJavaFile.getImportList().getImportStatements()) {
                if (psiImportStatement.getText().equals("import " + packageName + "." + type.name() + ";"))
                    deleteImport(psiImportStatement);
            }
        }
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

    private void findStatement(){
        PsiMethod createMethod = getPsiMethod("onCreate");
        if(createMethod != null){
            for(PsiStatement statement : createMethod.getBody().getStatements()){
                if(statement.getText().equals("initFind();")){
                    deleteComponent(statement);
                }
            }
        }
    }

    private void deleteImport(final PsiImportStatement psiImportStatement) {
        new WriteCommandAction.Simple(psiJavaFile.getProject(), psiJavaFile.getContainingFile()) {
            @Override
            protected void run() throws Throwable {
                psiImportStatement.delete();
            }
        }.execute();
    }

    private void findFunction(String componentName) {
        PsiMethod findMethod = getPsiMethod("initFind");
        if (findMethod != null) {
            if(findMethod.getBody().getStatements().length == 1){
                new WriteCommandAction.Simple(psiJavaFile.getProject(), psiJavaFile.getContainingFile()) {
                    @Override
                    protected void run() throws Throwable {
                        findMethod.delete();
                    }
                }.execute();
            }else {
                for (PsiStatement statement : findMethod.getBody().getStatements()) {
                    Pattern pattern = Pattern.compile(componentName);
                    Matcher matcher = pattern.matcher(statement.getText());
                    if (matcher.find()) deleteComponent(statement);
                }
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
            File inFile = new File(DatabaseManager.getInstance().selectToJava(table -> table.selectJava("num=" + xmlDTO.getJavaId())).get(0).getPath());
            psiJavaFile = (PsiJavaFile) PsiManager.getInstance(SharedPreference.PROJECT.get()).findFile(LocalFileSystem.getInstance().findFileByIoFile(inFile));
        }
    }

    private void checkXml() {
        if (xmlDTO == null)
            xmlDTO = DatabaseManager.getInstance().selectToJava(table -> table.selectXml("xmlName='R.layout." + xml + "'")).get(0).getXml(0);
    }

    private void addPsiStatement(final PsiMethod psiMethod, final String makeCode) {
        new WriteCommandAction.Simple(psiJavaFile.getProject(), psiJavaFile.getContainingFile()) {
            @Override
            protected void run() throws Throwable {
                psiMethod.getBody().add(elementFactory.createPsiStatement(makeCode, psiMethod));
            }
        }.execute();
    }

    private void addPsiImport(final PsiImportStatement psiImportStatement) {
        new WriteCommandAction.Simple(psiJavaFile.getProject(), psiJavaFile.getContainingFile()) {
            @Override
            protected void run() throws Throwable {
                psiJavaFile.getImportList().add(psiImportStatement);
            }
        }.execute();
    }

    private boolean checkImport(String packageName) {
        for (PsiImportStatement psiImportStatement : psiJavaFile.getImportList().getImportStatements()) {
            if (psiImportStatement.getText().equals("import " + packageName + "." + type.name() + ";"))
                return true;
        }
        return false;
    }

    private void syncProject() {
        ProjectAnalysis.getInstance(null).executeAll();
    }
}
