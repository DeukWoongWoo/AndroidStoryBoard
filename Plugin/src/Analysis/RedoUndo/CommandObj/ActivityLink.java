package Analysis.RedoUndo.CommandObj;

import Analysis.Constant.SharedPreference;
import Analysis.Database.DatabaseManager.DatabaseManager;
import Analysis.Database.DtatTransferObject.EventDTO;
import Analysis.Database.DtatTransferObject.XmlDTO;
import Analysis.Main.ProjectAnalysis;
import Analysis.RedoUndo.CodeBuilder.Type;
import Analysis.RedoUndo.Util.ElementFactory;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.psi.*;

import java.io.File;

/**
 * Created by woong on 2016-02-22.
 */
public class ActivityLink {
    private String id;
    private String from;
    private String to;

    public ActivityLink(String id, String from, String to){
        this.id = id;
        this.from = from;
        this.to = to;
    }

    public void create(){
        XmlDTO xmlDTO = DatabaseManager.getInstance().selectToJava(table->table.selectXml("xmlName='R.layout."+from+"'")).get(0).getXml(0);
        int xmlId = xmlDTO.getNum();
        int componentId = DatabaseManager.getInstance().selectToJava(table->table.selectComponent("xmlId="+xmlId,"xmlName='R.id."+id+"'")).get(0).getComponent(0).getNum();
        EventDTO eventDTO = DatabaseManager.getInstance().selectToJava(table->table.selectEvent("componentId="+componentId)).get(0).getEvent(0);

        if(eventDTO == null){
            File file = new File(DatabaseManager.getInstance().selectToJava(table->table.selectJava("num="+xmlDTO.getJavaId())).get(0).getPath());
            PsiJavaFile psiJavaFile = (PsiJavaFile) PsiManager.getInstance(SharedPreference.ACTIONEVENT.getData().getProject()).findFile(LocalFileSystem.getInstance().findFileByIoFile(file));
            new WriteCommandAction.Simple(psiJavaFile.getProject(), psiJavaFile.getContainingFile()){
                @Override
                protected void run() throws Throwable {
                    ElementFactory elementFactory = new ElementFactory();

                    StringBuilder clickStr = new StringBuilder("public void On"+id+"Clicked(View view){\n");
                    clickStr.append("nextActivity();\n");
                    clickStr.append("}\n");

                    StringBuilder contentStr = new StringBuilder("private void nextActivity(){\n");
                    contentStr.append("Intent intent = new Intent(this,"+to+".class);\n");
                    contentStr.append("startActivity(intent);\n");
                    contentStr.append("}\n");

                    PsiClass psiClass = psiJavaFile.getClasses()[0];

                    psiClass.add(elementFactory.createPsiMethod(clickStr.toString(), psiClass));
                    psiClass.add(elementFactory.createPsiMethod(contentStr.toString(), psiClass));

                    if (!checkImport(psiJavaFile)){
                        PsiImportStatement psiImportStatement = elementFactory.findPsiImportStatement("android.view", "View");
                        if(psiImportStatement != null) psiJavaFile.getImportList().add(psiImportStatement);
                        else Messages.showInfoMessage("Cannot find import file...","File Search");
                    }
                }
            }.execute();
        }else{
            // TODO: 2016-02-23 기존 event가 있을 때 추가하기
        }

        syncProject();
    }

    public void remove(){

    }

    private boolean checkImport(PsiJavaFile psiJavaFile) {
        for(PsiImportStatement psiImportStatement : psiJavaFile.getImportList().getImportStatements()){
            if(psiImportStatement.getText().equals("import android.view.View;")) return true;
        }
        return false;
    }

    private void syncProject() {
        ProjectAnalysis.getInstance(null,null).executeAll();
    }
}
