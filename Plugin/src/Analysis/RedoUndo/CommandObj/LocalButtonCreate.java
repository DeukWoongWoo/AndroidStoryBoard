package Analysis.RedoUndo.CommandObj;

import Analysis.Constant.ConstantEtc;
import Analysis.Constant.SharedPreference;
import Analysis.Database.DatabaseManager.DatabaseManager;
import Analysis.Database.QueryBuilder.QueryBuilder;
import Analysis.Main.ProjectAnalysis;
import Analysis.RedoUndo.CodeBuilder.CodeBuilder;
import Analysis.RedoUndo.CodeBuilder.Type;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.psi.*;

import java.io.*;

/**
 * Created by woong on 2016-02-15.
 */
public class LocalButtonCreate {
    private PrintWriter out;
    private FileOutputStream fos;
    private BufferedReader in;

    private String buttonName = "button1";

    public void create(){
        insertPsiElement();
//        insertString(str);
        ProjectAnalysis.getInstance(null,null).execute("src/Activity", ConstantEtc.JAVA_PATTERN,true);
    }

    private void insertPsiElement() {
        File inFile = new File(DatabaseManager.getInstance().selectToJava(table->table.selectJava()).get(0).getPath());

        PsiJavaFile psiJavaFile = (PsiJavaFile) PsiManager.getInstance(SharedPreference.ACTIONEVENT.getData().getProject()).findFile(LocalFileSystem.getInstance().findFileByIoFile(inFile));

        for(PsiClass p : psiJavaFile.getClasses()){
            for(PsiMethod method : p.getMethods()){
                if(method.getName().equals("onCreate")){
                    new WriteCommandAction.Simple(method.getProject(), method.getContainingFile()) {
                        @Override
                        protected void run() throws Throwable {
                            String makeCode = CodeBuilder.Component(Type.Button).name(buttonName).findViewById("R.id.button").build();
                            PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(method.getProject());
                            PsiStatement statement = elementFactory.createStatementFromText(makeCode,method);
                            method.getBody().add(statement);
                        }
                    }.execute();
                }
            }
        }
    }

    private void insertString(String str) {
        File inFile = new File("C:/Users/woong/IdeaProjects/PluginTest/src/Activity/MainActivity.java");

        File outFile = new File("C:/Users/woong/IdeaProjects/PluginTest/src/Activity/temp.java");
        try {
            FileInputStream fis = new FileInputStream(inFile);
            in = new BufferedReader(new InputStreamReader(fis));
            fos = new FileOutputStream(outFile);
            out = new PrintWriter(fos);

            String thisLine = "";

            for (int i=1;(thisLine = in.readLine()) != null;i++) {
                if (i == 64)
                    out.println(str);
                out.println(thisLine);
            }

            out.flush();
            out.close();
            in.close();

            inFile.delete();

            outFile.renameTo(inFile);

            System.out.println("insert File String End !!!!");
        }catch(IOException e){
            System.out.println("LocalButtonCreate Error : " + e);
        }finally {
//            ProjectAnalysis projectAnalysis = ProjectAnalysis.getInstance(null, null);
//            projectAnalysis.execute("src/Activity", ConstantEtc.JAVA_PATTERN);
        }
    }

    public void remove(){

    }
}
