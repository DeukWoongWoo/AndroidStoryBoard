package Analysis.RedoUndo.CommandObj;

import Analysis.Constant.ConstantEtc;
import Analysis.Constant.SharedPreference;
import Analysis.Database.DatabaseManager.DatabaseManager;
import Analysis.Main.ProjectAnalysis;
import Analysis.RedoUndo.CodeBuilder.CodeBuilder;
import Analysis.RedoUndo.CodeBuilder.Type;
import Analysis.RedoUndo.CommandKey;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.refactoring.PackageWrapper;
import com.intellij.refactoring.util.RefactoringUtil;
import com.intellij.util.IncorrectOperationException;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by woong on 2016-02-15.
 */
public class LocalButtonCreate {
    private static int num = 1;

    private final String buttonName = "button";
    private final String packageName = "android.widget";
    private PsiJavaFile psiJavaFile;
    private Project project;
    VirtualFile virtualFile;

    public LocalButtonCreate(){
        init();
    }

    private void init() {
        project = SharedPreference.ACTIONEVENT.getData().getProject();
        File inFile = new File(DatabaseManager.getInstance().selectToJava(table->table.selectJava()).get(0).getPath());
        virtualFile = LocalFileSystem.getInstance().findFileByIoFile(inFile);
    }

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
                            String makeCode = Type.Button + " " + buttonName + (num++) +" = " +CodeBuilder.Component(Type.Button).findViewById(CommandKey.LOCALBUTTON.getId()).build();
                            PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(method.getProject());
                            PsiStatement statement = elementFactory.createStatementFromText(makeCode,method);
                            method.getBody().add(statement);
                            Messages.showInfoMessage("import beforel!!!","test");
                            if (!checkImport(packageName)) psiJavaFile.getImportList().add(elementFactory.createImportStatement(makeImportClass(packageName,Type.Button.name())));
                        }
                    }.execute();
                }
            }
        }
    }

    private boolean checkImport(String packageName) {
        for(PsiImportStatement psiImportStatement : psiJavaFile.getImportList().getImportStatements()){
            System.out.println(psiImportStatement.getText());
            if(psiImportStatement.getText().equals("import "+packageName+"."+ Type.Button.name()+";")) return true;
        }
        return false;
    }

    private PsiClass makeImportClass(String packageName, String className) {
        final PackageWrapper packageWrapper = new PackageWrapper(psiJavaFile.getManager(), packageName);
        ProjectRootManager rootManager = ProjectRootManager.getInstance(project);
        return getBoundaryClass(project, packageName, rootManager, virtualFile, packageWrapper, className);
    }

    private PsiClass getBoundaryClass(Project project, String newPackage, ProjectRootManager rootManager, VirtualFile virtualFile, PackageWrapper packageWrapper, String className) {
        PsiClass boundaryClass = lookupClass(project,newPackage, className);
        Messages.showInfoMessage("boundaryClass !!!" + boundaryClass,"test");
        if (boundaryClass == null) {
            Messages.showInfoMessage("boundaryClass Null!!!","test");
//            boundaryClass = createClass(rootManager, virtualFile, packageWrapper, className);
        }
        return boundaryClass;
    }

    private PsiClass createClass(ProjectRootManager rootManager, VirtualFile virtualFile, PackageWrapper packageWrapper, String className) {
        try {
            ProjectFileIndex fileIndex = rootManager.getFileIndex();
            final VirtualFile sourceRootForFile = fileIndex.getSourceRootForFile(virtualFile);
            PsiDirectory packageDirectoryInSourceRoot = RefactoringUtil.createPackageDirectoryInSourceRoot(packageWrapper, sourceRootForFile);
            return JavaDirectoryService.getInstance().createInterface(packageDirectoryInSourceRoot, className);
        } catch (IncorrectOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private PsiClass lookupClass(Project project, String boundaryPackage, String newClassName) {
        GlobalSearchScope globalsearchscope = GlobalSearchScope.allScope(project);
        return JavaPsiFacade.getInstance(project).findClass(boundaryPackage + "." + newClassName, globalsearchscope);
    }

    private PsiJavaFile makePsiJavaFile() {
        return (PsiJavaFile) PsiManager.getInstance(project).findFile(virtualFile);
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
