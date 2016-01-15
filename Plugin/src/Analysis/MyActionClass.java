package Analysis;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlFile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by woong on 2015-12-22.
 */
public class MyActionClass extends AnAction {
    private String intellijPath = "/src";
    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        ProjectAnalysis projectAnalysis = ProjectAnalysis.getInstance(e, intellijPath);
        projectAnalysis.execute(intellijPath, Constant.XML_PATTERN);
//        ProjectAnalysis projectAnalysis = ProjectAnalysis.getInstance(new InstanceItem.Builder().anActionEvent(e).path(intellijPath).build());
//        ProjectAnalysis projectAnalysis1 = ProjectAnalysis.getInstance(null,null);
//        projectAnalysis.execute(Constant.PROJECT_JAVA_PATH, Constant.JAVA_PATTERN);

//        PsiClass psiClass = getPsiClassFromContext(e);

//        PluginTest pluginTest = new PluginTest(psiClass);
//        PluginTest pluginTest = new PluginTest(e);

//        String AndroidPath = "/app/src/main/java";
//        String AndroidProjectPath = "/app/src/main";
//        String intellijPath = "/src";
//        String xmlPattern = ".xml";
//        findDirectoryPath1(e, intellijPath, xmlPattern);

//        projectAnalysis(e, intellijPath);

    }

    private void projectAnalysis(AnActionEvent e,String path){
        PsiDirectory[] psiDirectories = findDirectory(e,path).getSubdirectories();
        System.out.println("Current Path : "+path);
        System.out.println("psiDirectories Length : "+psiDirectories.length);

        for(PsiDirectory psiDirectory : findDirectory(e, path).getSubdirectories()){
            System.out.println("SubDirectory Name : "+psiDirectory.getName());
            projectAnalysis(e,intellijPath+"/"+psiDirectory.getName());
        }
//        for(PsiElement psi : findDirectory(e, path).getChildren()){
//            System.out.println("Element Name : "+psi.toString());
//        }


//        PsiFile psiFile[] = findDirectory(e, path).getFiles();
//        System.out.println("Directory File Length : " + psiFile.length);

        /*for(int i=0;i<psiFile.length;i++) {
                Pattern pattern = Pattern.compile(".java");
//            Pattern pattern = Pattern.compile(filePattern);
            Matcher matcher = pattern.matcher(psiFile[i].getName());

            if(matcher.find()){
                System.out.println("Math File Name : "+psiFile[i].getName());
                System.out.println("getChildren Length : "+psiFile[i].getChildren().length);
//                    System.out.println("getOriginalElement name : "+psiFile[i].getOriginalElement().getText());

                // java file
                    PsiJavaFile psiJavaFile = (PsiJavaFile)psiFile[i];
                    System.out.println("PsiJavaFile Name : " + psiJavaFile.getName());
                    System.out.println("PsiJavaFile Length : " + psiJavaFile.getClasses().length);


                    for(PsiClass p : psiJavaFile.getClasses()){
                        System.out.println("PsiClass InnerClass Length : " + p.getName());
                        System.out.println("PsiClass Field Length : " + p.getAllFields().length);
                        for(PsiField f : p.getAllFields()){
                            System.out.println("InnerClass Field Name : " + f.getName());
                        }
//                        PluginTest pluginTest = new PluginTest(p);
                    }

                // xml file
                XmlFile xmlFile = (XmlFile) psiFile[i];
                String packagePath = xmlFile.getRootTag().getAttributeValue("package");
//                    System.out.println("Attribute : " + packagePath);
                String str[]  = packagePath.split(".");
                String strPath = str[0];
                for(int s = 1; s < str.length; s++)
                    strPath += "/" + str[i];



                break;

            }
        }*/

    }

    private PsiDirectory findDirectory(AnActionEvent e, String path) {
        VirtualFile IntellijPathFile = e.getProject().getBaseDir().findFileByRelativePath(path);
        return PsiManager.getInstance(e.getProject()).findDirectory(IntellijPathFile);
    }


    private void findDirectoryPath1(AnActionEvent e, String path, String filePattern){
        VirtualFile IntellijPathFile = e.getProject().getBaseDir().findFileByRelativePath(path);

        PsiDirectory psiDirectory = PsiManager.getInstance(e.getProject()).findDirectory(IntellijPathFile);

        try {
            PsiFile psiFile[] = psiDirectory.getFiles();
            for(int i=0;i<psiFile.length;i++) {
//                Pattern pattern = Pattern.compile(".java");
                Pattern pattern = Pattern.compile(filePattern);
                Matcher matcher = pattern.matcher(psiFile[i].getName());

                if(matcher.find()){
                    System.out.println("Math File Name : "+psiFile[i].getName());
                    System.out.println("getChildren Length : "+psiFile[i].getChildren().length);
//                    System.out.println("getOriginalElement name : "+psiFile[i].getOriginalElement().getText());

                    // java file
                    /*PsiJavaFile psiJavaFile = (PsiJavaFile)psiFile[i];
                    System.out.println("PsiJavaFile Name : " + psiJavaFile.getName());
                    System.out.println("PsiJavaFile Length : " + psiJavaFile.getClasses().length);


                    for(PsiClass p : psiJavaFile.getClasses()){
                        System.out.println("PsiClass InnerClass Length : " + p.getName());
                        System.out.println("PsiClass Field Length : " + p.getAllFields().length);
                        for(PsiField f : p.getAllFields()){
                            System.out.println("InnerClass Field Name : " + f.getName());
                        }
//                        PluginTest pluginTest = new PluginTest(p);
                    }*/

                    // xml file
                    XmlFile xmlFile = (XmlFile) psiFile[i];
                    String packagePath = xmlFile.getRootTag().getAttributeValue("package");
//                    System.out.println("Attribute : " + packagePath);
                    String str[]  = packagePath.split(".");
                    String strPath = str[0];
                    for(int s = 1; s < str.length; s++)
                        strPath += "/" + str[i];



                    break;

                }
            }

        }catch(Exception error){
            System.out.println("PsiFileName !! : " + error);
        }
    }

//    @Override
//    public void update(AnActionEvent e) {
//        PsiClass psiClass = getPsiClassFromContext(e);
//        e.getPresentation().setEnabled(psiClass != null);
//    }

    private PsiClass getPsiClassFromContext(AnActionEvent e) {
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (psiFile == null || editor == null) {
            return null;
        }
        int offset = editor.getCaretModel().getOffset();
        PsiElement elementAt = psiFile.findElementAt(offset);
        PsiClass psiClass = PsiTreeUtil.getParentOfType(elementAt, PsiClass.class);
        return psiClass;
    }
}
