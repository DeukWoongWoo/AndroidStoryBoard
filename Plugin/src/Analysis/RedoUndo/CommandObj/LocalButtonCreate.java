package Analysis.RedoUndo.CommandObj;

import Analysis.Constant.ConstantEtc;
import Analysis.Main.ProjectAnalysis;
import Analysis.RedoUndo.CodeBuilder.CodeBuilder;
import Analysis.RedoUndo.CodeBuilder.Type;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiElement;

import java.io.*;

/**
 * Created by woong on 2016-02-15.
 */
public class LocalButtonCreate {
    private PrintWriter out;
    private FileOutputStream fos;
    private BufferedReader in;

    public void create(){
        String str = CodeBuilder.Component(Type.Button).findViewById("R.id.button").build();

        insertString(str);
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
