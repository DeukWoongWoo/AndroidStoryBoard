package Analysis.RedoUndo.Util;

import Analysis.Constant.ConstantEtc;
import Analysis.Constant.SharedPreference;
import Analysis.MyActionClass;
import GUI.StoryBoard.StoryBoard_PlugIn;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.impl.file.PsiJavaDirectoryFactory;
import com.intellij.psi.javadoc.PsiDocTag;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.xml.XmlTag;
import com.intellij.refactoring.PackageWrapper;
import com.intellij.refactoring.util.RefactoringUtil;
import com.intellij.util.IncorrectOperationException;

/**
 * Created by woong on 2016-02-18.
 */
public class ElementFactory {

    private Project project;
    private PsiElementFactory elementFactory;
    private XmlElementFactory xmlElementFactory;

    public ElementFactory(){
        project = SharedPreference.PROJECT.get();
        elementFactory = JavaPsiFacade.getElementFactory(project);
        xmlElementFactory = XmlElementFactory.getInstance(project);
    }

    public PsiField createPsiField(String makeCode, PsiElement element){
        return elementFactory.createFieldFromText(makeCode, element);
    }

    public PsiStatement createPsiStatement(String makeCode, PsiElement element){
        return elementFactory.createStatementFromText(makeCode, element);
    }

    public PsiMethod createPsiMethod(String makeCode, PsiElement element){
        return elementFactory.createMethodFromText(makeCode, element);
    }

    public PsiClass createPsiClass(String className){
        try {
            ProjectFileIndex fileIndex = ProjectRootManager.getInstance(project).getFileIndex();
            final VirtualFile sourceRootForFile = fileIndex.getSourceRootForFile(project.getBaseDir().findFileByRelativePath(ConstantEtc.PROJECT_XML_PATH));
            PackageWrapper packageWrapper = new PackageWrapper(PsiManager.getInstance(project).findFile(project.getProjectFile()).getManager(),"");
            PsiDirectory packageDirectoryInSourceRoot = RefactoringUtil.createPackageDirectoryInSourceRoot(packageWrapper, sourceRootForFile);
            return JavaDirectoryService.getInstance().createClass(packageDirectoryInSourceRoot,className);
        } catch (IncorrectOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public PsiJavaCodeReferenceElement createPsiJavaCodeReferenceElement(String name, PsiElement element){
        return elementFactory.createReferenceFromText(name, element);
    }

    public XmlTag createActivityTag(String attr){
        XmlTag xmlTag = xmlElementFactory.createTagFromText("<activity/>\n");
        xmlTag.setAttribute("android:name","."+attr);
        return xmlTag;
    }

    public PsiImportStatement findPsiImportStatement(String newPackage, String className){
        PsiClass psiClass = lookupClass(newPackage, className);
        if(psiClass != null) return elementFactory.createImportStatement(psiClass);
        else return null;
    }

    private PsiClass lookupClass(String boundaryPackage, String newClassName) {
        GlobalSearchScope globalsearchscope = GlobalSearchScope.allScope(project);
        return JavaPsiFacade.getInstance(project).findClass(boundaryPackage + "." + newClassName, globalsearchscope);
    }
}
