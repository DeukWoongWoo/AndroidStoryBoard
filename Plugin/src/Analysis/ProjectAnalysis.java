package Analysis;

import com.intellij.openapi.actionSystem.AnActionEvent;
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

    public static ProjectAnalysis getInstance(AnActionEvent e, String path){
        if(projectAnalysis == null) {
            synchronized (ProjectAnalysis.class) {
                if (projectAnalysis == null) projectAnalysis = new ProjectAnalysis(e, path);
            }
        }
        return projectAnalysis;
    }

    public ProjectAnalysis(AnActionEvent e,String path){
        this.project = e.getProject();
        baseDir = project.getBaseDir();

        PsiDirectory psiDirectory = currentDirectory(path);
        findFiles(Constant.XML_PATTERN, psiDirectory);
    }

    public void execute(String path, String pattern) {
        System.out.println("Current Path : "+path);
        PsiDirectory psiDirectory = currentDirectory(path);

        findFiles(pattern, psiDirectory);

        findDirectories(path, psiDirectory, pattern);
    }

    private void findFiles(String pattern, PsiDirectory psiDirectory) {
        PsiFile[] psiFiles = psiDirectory.getFiles();

        if(psiFiles.length != 0)
            checkFileType(psiFiles, pattern);
    }

    private void findDirectories(String path, PsiDirectory psiDirectory, String pattern) {
        PsiDirectory[] psiDirectories = psiDirectory.getSubdirectories();

        if(psiDirectories.length != 0){
            for(PsiDirectory subDirectory : psiDirectories){
                this.execute(path+"/"+subDirectory.getName(), pattern);
            }
        }
    }

    private PsiDirectory currentDirectory(String path) {
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
