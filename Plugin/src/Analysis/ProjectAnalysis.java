package Analysis;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by woong on 2016-01-14.
 */
public class ProjectAnalysis {
    public volatile static ProjectAnalysis projectAnalysis = null;

    private Project project;
    private VirtualFile baseDir;

    public static ProjectAnalysis getInstance(InstanceItem item){
        if(projectAnalysis == null) {
            synchronized (ProjectAnalysis.class) {
                if (projectAnalysis == null) projectAnalysis = new ProjectAnalysis(item);
            }
        }
        return projectAnalysis;
    }

    /*public static ProjectAnalysis getInstance(AnActionEvent e,String path){
        if(projectAnalysis == null) {
            synchronized (ProjectAnalysis.class) {
                if (projectAnalysis == null) projectAnalysis = new ProjectAnalysis(e, path);
            }
        }
        return projectAnalysis;
    }*/

    public ProjectAnalysis(InstanceItem item){
        this.project = item.getAnActionEvent().getProject();
        baseDir = project.getBaseDir();
        execute(item.getPath(), Constant.XML_PATTERN);
    }

    /*public ProjectAnalysis(AnActionEvent e,String path){
        this.project = e.getProject();
        baseDir = project.getBaseDir();
        execute(path,Constant.XML_PATTERN);
    }*/

    public void execute(String path, String pattern) {
        PsiFile[] psiFiles = findDirectory(path).getFiles();
        checkFileType(psiFiles, pattern);
    }

    private PsiDirectory findDirectory(String path) {
        VirtualFile virtualPathFile = baseDir.findFileByRelativePath(path);
        return PsiManager.getInstance(project).findDirectory(virtualPathFile);
    }

    private void checkFileType(PsiFile[] psiFiles, String patternStr) {
        for(int i=0;i<psiFiles.length;i++){
            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = pattern.matcher(psiFiles[i].getName());

            if(matcher.find()){
                System.out.println("matcherFind..." + patternStr);
                if(patternStr.equals(Constant.XML_PATTERN)) codeParsing(new XmlParser(psiFiles[i]));
                else codeParsing(new JavaParser(psiFiles[i]));
            }
        }
    }
    private void codeParsing(Parser parser) {
        System.out.println("codeParsing!!!!");
        parser.parsing();
    }
}
