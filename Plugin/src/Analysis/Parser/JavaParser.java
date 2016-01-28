package Analysis.Parser;

import com.intellij.psi.*;

/**
 * Created by woong on 2016-01-14.
 */
public class JavaParser implements FileParser {
    private PsiJavaFile psiJavaFile;
    public JavaParser(PsiFile psiFile){
        psiJavaFile = (PsiJavaFile) psiFile;
    }

    @Override
    public void parsing() {
//        System.out.println("PsiJavaFile Name : " + psiJavaFile.getName());
//        System.out.println("PsiJavaFile Length : " + psiJavaFile.getClasses().length);
        for(PsiClass p : psiJavaFile.getClasses()){
            System.out.println("PsiClass InnerClass Name : " + p.getName());
//            System.out.println("PsiClass Field Length : " + p.getAllFields().length);

            PsiJavaCodeReferenceElement[] extendsElement = p.getExtendsList().getReferenceElements();
            if(extendsElement.length != 0) System.out.println(">> Extends Name : " + extendsElement[0].getText());

            PsiJavaCodeReferenceElement[] implementsElement = p.getImplementsList().getReferenceElements();
            if(implementsElement.length != 0) System.out.println(">> Implements Name : "+ implementsElement[0].getText());

            for(PsiField f : p.getAllFields()){
                System.out.println(">> InnerClass Field Name : " + f.getName());
            }

            System.out.println("Methods length : "+p.getMethods().length);
            for(PsiMethod method : p.getMethods()){
                System.out.println(">> Method Name : "+method.getName());
                if(method.getName().equals("onCreate")){
                    PsiCodeBlock body = method.getBody();
                    for(PsiStatement statement : body.getStatements()){
                        System.out.println(">>>> Statement Name :"+statement.getText());
                        PsiElement element = statement.getFirstChild();
                        System.out.println(">>>>>> Element Children Length : " + element.getChildren().length);
                        for(PsiElement e : element.getChildren()){
                            System.out.println(">>>>>>>> Element Children Name : " + e.getText());
                        }
                    }
                }
            }
        }
    }
}
