package Analysis.RedoUndo.Util;

import Analysis.Constant.SharedPreference;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;

/**
 * Created by woong on 2016-02-18.
 */
public class ElementFactory {

    private Project project;
    private PsiElementFactory elementFactory;

    public ElementFactory(){
        project = SharedPreference.ACTIONEVENT.getData().getProject();
        elementFactory = JavaPsiFacade.getElementFactory(project);
    }

    public PsiField makePsiField(String makeCode, PsiElement element){
        return elementFactory.createFieldFromText(makeCode, element);
    }

    public PsiStatement makePsiStatement(String makeCode, PsiElement element){
        return elementFactory.createStatementFromText(makeCode, element);
    }

    public PsiMethod makePsiMethod(String makeCode, PsiElement element){
        return elementFactory.createMethodFromText(makeCode, element);
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
