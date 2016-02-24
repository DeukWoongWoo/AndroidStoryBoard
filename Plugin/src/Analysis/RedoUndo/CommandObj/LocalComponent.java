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
 * Created by woong on 2016-02-24.
 */
public class LocalComponent {
    private static int num = 1;

    private final String packageName = "android.widget";
    private String componentName;

    private String id;
    private String xml;
    private Type type;

    private boolean isImprot;

    private XmlDTO xmlDTO;

    private PsiJavaFile psiJavaFile;

    private ElementFactory elementFactory = new ElementFactory();

    public LocalComponent(String id, String xml, Type type) {
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
        insertComponent();
        insertImport();
    }

    private void insertComponent() {
        PsiMethod createMethod = getPsiMethod("onCreate");
        if (createMethod != null) {
            String makeCode = type + " " + componentName + " = " + CodeBuilder.Component(type).findViewById("R.id." + id).build();
            addPsiStatement(createMethod, makeCode);
        }
    }

    private void insertImport() {
        if (!checkImport(packageName)) {
            PsiImportStatement psiImportStatement = elementFactory.findPsiImportStatement(packageName, type.name());
            if (psiImportStatement != null) {
                addPsiImport(psiImportStatement);
                isImprot = true;
            } else Messages.showInfoMessage("Cannot find import file...", "File Search");
        }
    }

    private void deleteCode() {
        checkXml();
        findComponent();
        if(isImprot){
            for (PsiImportStatement psiImportStatement : psiJavaFile.getImportList().getImportStatements()) {
                if (psiImportStatement.getText().equals("import " + packageName + "." + type.name() + ";"))
                    deleteImport(psiImportStatement);
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

    private void findComponent() {
        String componentName = DatabaseManager.getInstance().selectToJava(table -> table.selectComponent("xmlId=" + xmlDTO.getNum(), "xmlName='R.id." + id + "'")).get(0).getComponent(0).getName();

        PsiMethod createMethod = getPsiMethod("onCreate");
        if (createMethod != null) {
            for (PsiStatement statement : createMethod.getBody().getStatements()) {
                Pattern pattern = Pattern.compile(componentName);
                Matcher matcher = pattern.matcher(statement.getText());
                if (matcher.find()) deleteComponent(statement);

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
            psiJavaFile = (PsiJavaFile) PsiManager.getInstance(SharedPreference.ACTIONEVENT.getData().getProject()).findFile(LocalFileSystem.getInstance().findFileByIoFile(inFile));
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
        ProjectAnalysis.getInstance(null, null).executeAll();
    }

}
