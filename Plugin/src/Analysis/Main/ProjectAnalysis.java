package Analysis.Main;

import Analysis.Constant.ConstantEtc;
import Analysis.Constant.SharedPreference;
import Analysis.Database.ConnectionPool;
import Analysis.Database.DatabaseManager.DatabaseManager;
import Analysis.Parser.JavaParser;
import Analysis.Parser.FileParser;
import Analysis.Parser.XmlParser;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.refactoring.PackageWrapper;
import com.intellij.refactoring.util.RefactoringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by woong on 2016-01-14.
 */
public class ProjectAnalysis {
    public volatile static ProjectAnalysis projectAnalysis = null;

    private Project project;
    private VirtualFile baseDir;

    private String curPath;

    public static ProjectAnalysis getInstance(String path) {
        if (projectAnalysis == null) {
            synchronized (ProjectAnalysis.class) {
                if (projectAnalysis == null) projectAnalysis = new ProjectAnalysis(path);
            }
        }
        return projectAnalysis;
    }

    public ProjectAnalysis(String path) {
        this.project = SharedPreference.PROJECT.get();
        baseDir = project.getBaseDir();
        ConnectionPool.projectDir = project.getBasePath();

        createTable();

        PsiDirectory psiDirectory = currentDirectory(path);
        findFiles(ConstantEtc.XML_PATTERN, psiDirectory);

        if (baseDir.findFileByRelativePath(path + "/java/assets") == null) makeDirectory(path+"/java", "assets");
    }

    private void makeDirectory(String path, String name) {
        ProjectFileIndex fileIndex = ProjectRootManager.getInstance(project).getFileIndex();
        final VirtualFile sourceRootForFile = fileIndex.getSourceRootForFile(currentDirectory(path).getVirtualFile());
        PackageWrapper packageWrapper = new PackageWrapper(PsiManager.getInstance(project).findFile(project.getProjectFile()).getManager(), name);
        new WriteCommandAction.Simple(project, PsiManager.getInstance(project).findFile(project.getProjectFile()).getContainingFile()) {
            @Override
            protected void run() throws Throwable {
                RefactoringUtil.createPackageDirectoryInSourceRoot(packageWrapper, sourceRootForFile);
            }
        }.execute();
    }

    public void executeAll() {
        createTable();

//        String path = "/src";
//        execute(path, ConstantEtc.XML_PATTERN);
//        execute(path, ConstantEtc.JAVA_PATTERN);

        execute(ConstantEtc.PROJECT_XML_PATH, ConstantEtc.XML_PATTERN);
        execute(ConstantEtc.PROJECT_XML_PATH + ConstantEtc.PROJECT_JAVA_PATH, ConstantEtc.JAVA_PATTERN);
    }

    public void execute(String path, String pattern, boolean start) {
        if (start) createTable();
        execute(path, pattern);
    }

    public void execute(String path, String pattern) {
        curPath = path;
        System.out.println("Current Path : " + curPath);
        PsiDirectory psiDirectory = currentDirectory(curPath);

        findFiles(pattern, psiDirectory);

        if (pattern.equals(ConstantEtc.JAVA_PATTERN)) findDirectories(curPath, psiDirectory, pattern);
    }

    public String[][] findResourcePath() {
        PsiDirectory psiDirectory = currentDirectory(ConstantEtc.PROJECT_XML_PATH + "/res/layout");

        PsiFile[] xmlFiles = psiDirectory.getFiles();
        String[][] path = new String[xmlFiles.length][2];
        for (int i = 0; i < xmlFiles.length; i++) {

            path[i][0] = project.getBasePath() + ConstantEtc.PROJECT_XML_PATH + "/res/layout/" + xmlFiles[i].getName();
            path[i][1] = xmlFiles[i].getName();
        }
        return path;
    }

    public String makeResourcePath(String xmlName) {
        String xmlPath;
        xmlPath = project.getBasePath() + ConstantEtc.PROJECT_XML_PATH + "/res/layout/" + xmlName;
        return xmlPath;
    }

    public String makeAssetsPath(String xmlName) {
        String xmlPath;
        xmlPath = project.getBasePath() + ConstantEtc.PROJECT_XML_PATH + "/assets/" + xmlName;
        return xmlPath;
    }

    public String findDrawablePath() {
        return project.getBasePath() + ConstantEtc.PROJECT_XML_PATH + "/res/drawable";
    }


    private void createTable() {
        DatabaseManager.getInstance().onCreateTable();
    }

    private void findFiles(String pattern, PsiDirectory psiDirectory) {
        PsiFile[] psiFiles = psiDirectory.getFiles();
        if (psiFiles.length != 0)
            checkFileType(psiFiles, pattern);
    }

    private void findDirectories(String path, PsiDirectory psiDirectory, String pattern) {
        PsiDirectory[] psiDirectories = psiDirectory.getSubdirectories();

        if (psiDirectories.length != 0) {
            for (PsiDirectory subDirectory : psiDirectories) {
                this.execute(path + "/" + subDirectory.getName(), pattern);
            }
        }
    }

    private PsiDirectory currentDirectory(String path) {
        VirtualFile virtualPathFile = baseDir.findFileByRelativePath(path);
        return PsiManager.getInstance(project).findDirectory(virtualPathFile);
    }

    private void checkFileType(PsiFile[] psiFiles, String patternStr) {
        for (int i = 0; i < psiFiles.length; i++) {
            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = pattern.matcher(psiFiles[i].getName());

            if (matcher.find()) {
                System.out.println("matcherFind..." + patternStr);
                if (patternStr.equals(ConstantEtc.XML_PATTERN)) codeParsing(new XmlParser(psiFiles[i]));
                else codeParsing(new JavaParser(psiFiles[i], curPath));
            }
        }
    }

    private void codeParsing(FileParser parser) {
        System.out.println("codeParsing!!!!");
        parser.parsing();
    }
}
